import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CreateItem from "./CreateItem";

const CreateListing = () => {
  const navigate = useNavigate();
  const { donorId } = useParams();
  const [listingItems, setListingItems] = useState([]);
  const [isFormVisible, setIsFormVisible] = useState(false);
  const [newItem, setNewItem] = useState({
    itemId: 0,

    name: "",
    quantity: 1,
    category: "",
    expiryDate: "",
  });
  const [additionalInputs, setAdditionalInputs] = useState({
    pickupLocation: "",
    pickupStart: "",
    pickupEnd: "",
    
  });
  const [editingIndex, setEditingIndex] = useState(null);


  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewItem({ ...newItem, [name]: value });
  };

  const handleAdditionalInputsChange = (e) => {
    const { name, value } = e.target;
    setAdditionalInputs({ ...additionalInputs, [name]: value });
  };


  const handleSubmit = (e) => {
    e.preventDefault();
    if (editingIndex !== null) {
      const updatedItems = [...listingItems];
      updatedItems[editingIndex] = newItem;
      setListingItems(updatedItems);
      setEditingIndex(null);
    } else {
      setListingItems([...listingItems, newItem]);
    }
    setNewItem({ itemId: 0, name: "", expiryDate: "", category: "", quantity: 1 });
    setIsFormVisible(false); 
  };


  const handleQuantityChange = (index, change) => {
    const updatedItems = listingItems.map((item, idx) =>
      idx === index
        ? { ...item, quantity: Math.max(0, item.quantity + change) }
        : item
    );
    setListingItems(updatedItems);
  };

  const handleSaveListing = async () => {


      if (listingItems.length === 0) {
          alert("Please add at least one item to the listing before saving.");
          return;
      }
  
      if (
          !additionalInputs.pickupLocation.trim() ||
          !additionalInputs.pickupStart.trim() ||
          !additionalInputs.pickupEnd.trim()
      ) {
          alert("Please fill out all pickup details before saving.");
          return;
      }
  
    
    const listingData = {

      donorId: donorId,
      location: additionalInputs.pickupLocation,
      pickupTimeRange: `${additionalInputs.pickupStart} - ${additionalInputs.pickupEnd}`,
      status: "AVAILABLE",
      listingItems: listingItems.map(item => ({
        quantity: item.quantity,
        item: {
          itemName: item.name,
          category: item.category, 
        },
        expirationDate: item.expiryDate,
        itemId: item.itemId,
        quantity: item.quantity,
        status: "AVAILABLE",
      })),
    };


    console.log("Listing Data:", listingData);
    
    // ideally we should send this data to the backend/database



    
    const response = await fetch('http://localhost:8080/listings/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(listingData),
    });
    
    if (!response.ok) {
      const constraintError = await response.text();
      if (constraintError.includes("chk_valid_quantity")) {
          alert("Failed to create listing: Quantity constraint violated. Please check the quantity of the items.");
      }
      return; 
    }
    else{
      navigate(`/users/${donorId}/donor_dashboard`, { state: { listingCreated: true} });
    }
    
  };
  

  const handleEditItem = (index) => {
    setEditingIndex(index);
    setNewItem(listingItems[index]); 
    setIsFormVisible(true); 
  };

  return (
    <div style={styles.container}>
      <h1 style={{ textAlign: "center", fontSize: "1.2em" }}>
        Create or Update Listing
      </h1>


        {/* pickup time/location info  */}
        {listingItems.length > 0 && (
        <div style={styles.additionalInfo}>
          <div >
            {/* Pickup Location */}
            <div style={styles.formGroup}>
              <label style={styles.label}>Pickup Location:</label>
              <input
                type="text"
                name="pickupLocation"
                value={additionalInputs.pickupLocation}
                onChange={handleAdditionalInputsChange}
                style={styles.input}
                required
              />
            </div>
          </div>

          {/* Pickup Time Range below the Pickup Location and Expiration Date */}
          <div style={styles.formGroup}>
            <label style={styles.label}>Pickup Time Range:</label>
            <div style={{ display: "flex", gap: "10px" }}>
              <input
                type="time"
                name="pickupStart"
                value={additionalInputs.pickupStart}
                onChange={handleAdditionalInputsChange}
                style={styles.input}
                required
              />
              <span> to </span>
              <input
                type="time"
                name="pickupEnd"
                value={additionalInputs.pickupEnd}
                onChange={handleAdditionalInputsChange}
                style={styles.input}
                required
              />
            </div>
          </div>
        </div>
      )}

      <div style={styles.viewItems}>
        {listingItems.length > 0 ? (
          listingItems.map((item, index) => (
            <div key={index} style={{ ...styles.itemCard }}>
              <div>
                <p style={{ margin: "5px 0" }}>
                  <strong>Item Name:</strong> {item.name}
                </p>
                <p style={{ margin: "5px 0" }}>
                  <strong>Category</strong> {item.category}
                </p>
              </div>
              <div style={{ display: "flex", alignItems: "center", gap: "5px" }}>
                <span style={{ marginRight: "5px" }}>Quantity:</span>
                <button
                  onClick={() => handleQuantityChange(index, -1)}
                  style={styles.button}
                >
                  -
                </button>
                <input
                  type="text"
                  value={item.quantity}
                  style={{ width: "50px", textAlign: "center", padding: "2px" }}
                  readOnly
                />
                <button
                  onClick={() => handleQuantityChange(index, 1)}
                  style={styles.button}
                >
                  +
                </button>
                <button
                  onClick={() => handleEditItem(index)}
                  style={{ ...styles.button, backgroundColor: "#FFA500" }}
                >
                  Edit
                </button>
              </div>
            </div>
          ))
        ) : (
          <p>Your List is Empty. Click 'Add New Item' to get started!</p>
        )}
      </div>




      {/* Buttons */}
      <div style={{ display: "flex", justifyContent: "center", gap: "50px" }}>
        <button
          onClick={() => setIsFormVisible(true)}
          style={{ ...styles.button, ...styles.addButton }}
        >
          Add New Item
        </button>

        <button
          onClick={handleSaveListing}
          style={{ ...styles.button, ...styles.saveButton }}
        >
          Save Listing
        </button>
      </div>

      <CreateItem
        isFormVisible={isFormVisible}
        setIsFormVisible={setIsFormVisible}
        handleInputChange={handleInputChange}
        handleSubmit={handleSubmit}
        newItem={newItem}
        setNewItem={setNewItem}

        editingIndex={editingIndex} 
      />

    </div>
  );
};


const styles = {
  itemCard: {
    backgroundColor: "#ffffff",
    fontFamily: "Arial, sans-serif",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    width: "100%",
    padding: "4% 10%",
    borderBottom: "2px solid #ddd",
  },
  button: {
    color: "#fff",
    backgroundColor: "#4CAF50",
    borderColor: "#4CAF50",
    padding: "0.375rem 0.75rem",
    fontSize: "1rem",
    borderRadius: "0.25rem",
    transitionColor: "0.15s, background-color 0.15s, border-color 0.15s",
  },
  container: {
    display: "grid",
    gap: "20px",
    backgroundColor: "#fff",
    padding: "20px",
    borderRadius: "8px",
    boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
    width: "60%",
    margin: "20px auto",
  },
  viewItems: {
    textAlign: "center",
    fontSize: "1em",
  },
  additionalInfo: {
    marginTop: "20px",
  },
  formGroup: {
    marginBottom: "15px",
  },
  label: {
    fontWeight: "bold",
    fontSize: "14px",
    marginBottom: "5px",
  },
  input: {
    padding: "10px",
    border: "1px solid #ddd",
    borderRadius: "5px",
    fontSize: "14px",
    width: "100%", 
    boxSizing: "border-box",
  },
};

export default CreateListing;
