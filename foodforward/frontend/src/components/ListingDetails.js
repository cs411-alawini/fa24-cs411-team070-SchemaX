import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const ListingDetails = () => {
    const { id } = useParams();
    const [listing, setListing] = useState(null);
    const [quantity, setQuantity] = useState(1);

    useEffect(() => {

        const fetchListingDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/listings/${id}`);
                setListing(response.data);
            } catch (error) {
                console.error('Error fetching listing details:', error);
            }
        };

        fetchListingDetails();
    }, [id]);

    if (!listing) {
        return <div>Loading...</div>;
    }

    return (
        <div className="max-w-xl mx-auto bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-xl font-bold mb-4">Listing Details</h2>

            <div className="mb-4">
                <p><strong>Donor:</strong> {listing.donorName}</p>
                <p><strong>Location:</strong> {listing.location}</p>
                <p><strong>Pickup Time:</strong> Between {listing.pickupTimeStart} - {listing.pickupTimeEnd}</p>
            </div>

            <h3 className="text-lg font-bold mb-2">Select Items and Quantities</h3>

            {listing.items.map((item, index) => (
                <div key={index} className="mb-4">
                    <div className="flex justify-between items-center">
                        <div>
                            <p><strong>Item Name:</strong> {item.name}</p>
                            <p><strong>Quantity Available:</strong> {item.quantityAvailable} {item.unit}</p>
                            <p><strong>Expiry Date:</strong> {item.expiryDate}</p>
                        </div>

                        {/* Quantity Selector */}
                        <div className="flex items-center">
                            <button
                                onClick={() => setQuantity(Math.max(1, quantity - 1))}
                                className="bg-green-500 text-white px-2 py-1 rounded-l"
                            >
                                -
                            </button>
                            <input
                                type="number"
                                value={quantity}
                                onChange={(e) => setQuantity(Number(e.target.value))}
                                min={1}
                                max={item.quantityAvailable}
                                className="w-12 text-center border border-gray-300"
                            />
                            <button
                                onClick={() => setQuantity(Math.min(item.quantityAvailable, quantity + 1))}
                                className="bg-green-500 text-white px-2 py-1 rounded-r"
                            >
                                +
                            </button>
                        </div>
                    </div>
                </div>
            ))}

            <button
                onClick={() => alert('Booking confirmed!')}
                className="w-full bg-blue-500 text-white py-2 rounded-lg mt-4"
            >
                Book Now
            </button>
        </div>
    );
};

export default ListingDetails;