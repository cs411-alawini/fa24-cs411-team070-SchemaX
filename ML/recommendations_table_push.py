from sqlalchemy import create_engine, text
from sentence_transformers import SentenceTransformer, util

engine = create_engine("mysql+pymysql://root:schemaXGCP@34.28.150.93:3306/food_donation", isolation_level="AUTOCOMMIT")

model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')

def fetch_all_recipient_data():
    query = """
    SELECT 
        b.booked_by AS recipient_id,
        i.item_name,
        i.category
    FROM 
        Booking b
    JOIN 
        ListingItem li ON b.booking_id = li.booking_id
    JOIN 
        Item i ON li.item_id = i.item_id
    WHERE 
        b.booking_status = 'COMPLETED';
    """
    with engine.connect() as conn:
        result_set = conn.execute(text(query))

        result_data = result_set.mappings().fetchall()

        data = {}
        for row in result_data:
            recipient_id = row['recipient_id']
            if recipient_id not in data:
                data[recipient_id] = []
            data[recipient_id].append(f"{row['item_name']} {row['category']}")

        return data


def fetch_donor_data():
    query = """
    SELECT d.donor_id, i.item_name, i.category
    FROM Donor d
    JOIN Listing l ON d.donor_id = l.listed_by
    JOIN ListingItem li ON l.listing_id = li.listing_id
    JOIN Item i ON li.item_id = i.item_id
    WHERE l.status = 'ACTIVE' AND li.status = 'AVAILABLE';
    """
    with engine.connect() as conn:
        return conn.execute(text(query)).fetchall()


def generate_recommendations_for_all():

    recipient_data = fetch_all_recipient_data()
    donor_data = fetch_donor_data()

    if not recipient_data or not donor_data:
        return "No data available for recommendations."

    donor_texts = [" ".join([row[1], row[2]]) for row in donor_data]  # Skip donor_id
    donor_embeddings = model.encode(donor_texts, convert_to_tensor=True)


    with engine.connect() as conn:
        for recipient_id, recipient_texts in recipient_data.items():

            recipient_embedding = model.encode(recipient_texts, convert_to_tensor=True).mean(dim=0)

            similarities = util.pytorch_cos_sim(recipient_embedding, donor_embeddings)[0]

            top_indices = similarities.cpu().topk(5).indices.tolist()
            #print(top_indices)

            for idx in top_indices:
                donor = donor_data[idx]
                insert_query = """
                INSERT INTO Recommendation (type, food_type, recipient_id, donor_id, score)
                VALUES (:type, :food_type, :recipient_id, :donor_id, :score);
                """
                # print(insert_query)
                print({
                    "type": "food",
                    "food_type": donor[2],
                    "recipient_id": recipient_id,
                    "donor_id": donor[0],
                    "score": similarities[idx].item()
                })

                conn.execute(text(insert_query), {
                    "type": "food",
                    "food_type": donor[2],
                    "recipient_id": recipient_id,
                    "donor_id": donor[0],
                    "score": similarities[idx].item()
                })
            #print(donor_data)

    return "Recommendations inserted successfully for all recipients."


status = generate_recommendations_for_all()
print(status)
