import React, { useEffect, useState } from "react";
import Modal from "react-modal";

const Form = ({ isFormVisible, setIsFormVisible, handleInputChange, handleSubmit, newItem, setNewItem }) => {
    const [allItems, setAllItems] = useState([]);
    const [filteredItems, setFilteredItems] = useState([]);
    const [searchQuery, setSearchQuery] = useState("");
    const [itemSelected, setItemSelected] = useState({
        itemId: 0,
        name: "",
        quantity: 1,
        category: "",
    });

    useEffect(() => {
        if (isFormVisible) {
            fetchItems();
        }
    }, [isFormVisible]);

    const fetchItems = async () => {
        try {
            const response = await fetch("http://localhost:8080/listings/items");
            if (!response.ok) {
                throw new Error("Failed to fetch items");
            }
            const data = await response.json();
            setAllItems(data);
        } catch (error) {
            console.error("Error fetching items:", error);
        }
    };

    const handleSearchChange = (e) => {
        const query = e.target.value.toLowerCase();
        setSearchQuery(query);
        if (query) {
            const filtered = allItems.filter((item) =>
                item.itemName.toLowerCase().includes(query)
            );
            setFilteredItems(filtered);
        } else {
            setFilteredItems([]);
        }

        handleInputChange(e); // Update the form state as well
    };

    const handleSuggestionClick = (itemName) => {
        const selectedItem = allItems.find((item) => item.itemName === itemName);
        setItemSelected(selectedItem);

        if (selectedItem) {
            setNewItem({
                ...newItem,
                itemId: selectedItem.itemId,
                name: selectedItem.itemName,
                category: selectedItem.category,
            });

            setSearchQuery(itemName);
            setFilteredItems([]); // Hide suggestions
        }
        // setItemSelected({ itemId: 0, name: "", quantity: 1, category: "" });
    };

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
                        value={searchQuery}
                        onChange={handleSearchChange}
                        required
                        placeholder="Enter the item name"
                        style={styles.input}
                        autoComplete="off"
                    />
                    {filteredItems.length > 0 && (
                        <ul style={styles.suggestions}>
                            {filteredItems.map((item) => (
                                <li
                                    key={item.itemId}
                                    onClick={() => handleSuggestionClick(item.itemName)}
                                    style={styles.suggestionItem}
                                >
                                    {item.itemName}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>

                {/* Quantity */}
                <div style={styles.formGroup}>
                    <label style={styles.label}>Quantity:</label>
                    <input
                        type="number"
                        name="quantity"
                        value={newItem.quantity}
                        onChange={(e) =>
                            handleInputChange({
                                target: { name: "quantity", value: parseInt(e.target.value) },
                            })
                        }
                        required
                        placeholder="Enter quantity"
                        style={styles.input}
                    />
                </div>

                {/* Category/Type of Food */}
                <div style={styles.formGroup}>
                    <label style={styles.label}>Category/Type of Food:</label>
                    <input
                        type="text"
                        name="category"
                        value={itemSelected.category}
                        onChange={handleSearchChange}
                        required
                        placeholder="Enter the item name"
                        style={styles.input}
                        autoComplete="off"
                    />
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

                <button type="submit" style={styles.button}>
                    Save Item
                </button>
            </form>
        </Modal>
    );
};


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
    suggestions: {
        listStyle: "none",
        padding: "0",
        margin: "5px 0 0 0",
        border: "1px solid #ddd",
        borderRadius: "5px",
        backgroundColor: "#fff",
        maxHeight: "150px",
        overflowY: "auto",
    },
    suggestionItem: {
        padding: "10px",
        cursor: "pointer",
        borderBottom: "1px solid #ddd",
    },
};

export default Form;
