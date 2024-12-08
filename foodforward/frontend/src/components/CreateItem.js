import React from "react";
import Modal from "react-modal";

const modalStyles = {
    content: {
      maxWidth: "800px", 
      margin: "auto",
      padding: "20px",
      borderRadius: "10px",
      backgroundColor: "#fff",
      position: "relative",
      boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
    },
};
  
const styles = {
    form: {
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "space-between",
        gap: "20px", 
    },
    formGroup: {
        display: "flex",
        flexDirection: "column",
        width: "48%",
    },
    label: {
        fontWeight: "bold",
        marginBottom: "5px",
        fontSize: "14px",
        
    },
    input: {
        padding: "10px",
        border: "1px solid #ddd",
        borderRadius: "5px",
        fontSize: "14px",
        width: "100%", 
        boxSizing: "border-box",
    },
    textarea: {
        padding: "10px",
        border: "1px solid #ddd",
        borderRadius: "5px",
        fontSize: "14px",
        width: "100%",
        height: "100px",
        boxSizing: "border-box",
    },
    button: {
        padding: "12px 20px",
        border: "none",
        borderRadius: "5px",
        backgroundColor: "#4CAF50",
        color: "#fff",
        fontSize: "16px",
        cursor: "pointer",
        marginTop: "20px",
        width: "100%",
    },
    modalClose: {
        position: "absolute",
        top: "10px",
        right: "10px",
        fontSize: "24px",
        backgroundColor: "transparent",
        border: "none",
        color: "#aaa",
        cursor: "pointer",
    },
};


const Form = ({ isFormVisible, setIsFormVisible, handleInputChange, handleSubmit, newItem }) => {
return (
    <Modal
    isOpen={isFormVisible}
    onRequestClose={() => setIsFormVisible(false)}
    style={modalStyles}
    ariaHideApp={false}
    >
    <button onClick={() => setIsFormVisible(false)} style={styles.modalClose}>
        &times;
    </button>
    <form onSubmit={handleSubmit} style={styles.form}>
        {/* Item Name */}
        <div style={styles.formGroup}>
        <label style={styles.label}>Item Name:</label>
        <input
            type="text"
            name="name"
            value={newItem.name}
            onChange={handleInputChange}
            required
            placeholder="Enter the item name"
            style={styles.input}
        />
        </div>

        {/* Quantity */}
        <div style={styles.formGroup}>
        <label style={styles.label}>Quantity (Number or Weight):</label>
        <input
            type="number"
            name="quantity"
            value={newItem.quantity}
            onChange={(e) =>
            handleInputChange({
                target: { name: "quantity", value: parseInt(e.target.value) || 0 },
            })
            }
            required
            placeholder="Enter quantity"
            min="1"
            style={styles.input}
        />
        </div>

        {/* Category/Type of Food */}
        <div style={styles.formGroup}>
        <label style={styles.label}>Category/Type of Food:</label>
        <select
            name="category"
            value={newItem.category || ""}
            onChange={handleInputChange}
            style={styles.input}
            required
        >
            <option value="">Select Category</option>
            <option value="vegetables">Vegetables</option>
            <option value="fruits">Fruits</option>
            <option value="dairy">Dairy</option>
            <option value="meat">Meat</option>
            <option value="other">Other</option>
        </select>
        </div>

        {/* Expiry Date */}
        <div style={styles.formGroup}>
        <label style={styles.label}>Expiry Date:</label>
        <input
            type="date"
            name="expiryDate"
            value={newItem.expiryDate}
            onChange={handleInputChange}
            required
            style={styles.input}
        />
        </div>

        <button
        type="submit"
        style={styles.button}
        >
        Save Item
        </button>
    </form>
    </Modal>
);
};

  
export default Form;
  