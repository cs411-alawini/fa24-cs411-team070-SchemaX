import React from 'react';
import { useNavigate } from 'react-router-dom';




function RecipientDashboard() {
    const navigate = useNavigate();

    const handleViewListings = () => {
        // Redirect to the listings page
        navigate('/listings');
    };

    return (
        <div style={styles.dashboardContainer}>
            {/* User Bio Section */}
            <div style={styles.bioSection}>
                <h3 style={styles.header}>User Bio</h3>
                <p>[User's details and preferences]</p>
            </div>

            {/* Recent Donations Section */}
            <div style={styles.donationsSection}>
                <h3 style={styles.header}>Recent Donations Received</h3>
                <div style={{ marginBottom: '20px' }}>
                    <p><strong>Donation Item:</strong> Item 1</p>
                    <p><strong>Donor:</strong> John Doe</p>
                    <div style={styles.buttonContainer}>
                        <button style={styles.button}>Leave a Review</button>
                    </div>
                </div>
                <div style={{ marginBottom: '20px' }}>
                    <p><strong>Donation Item:</strong> Item 2</p>
                    <p><strong>Donor:</strong> Jane Smith</p>
                    <div style={styles.buttonContainer}>
                        <button style={styles.button}>Leave a Review</button>
                    </div>
                </div>
            </div>

            {/* Search for Listings Section */}
            <div style={styles.searchSection}>
                <h3 style={styles.header}>Search for Available Listings</h3>
                <p>Find food donations that match your needs.</p>
                <div style={styles.buttonContainer}>
                    <button style={styles.button} onClick={() => handleViewListings()}
                    >
                        Search Listings
                    </button>
                </div>
            </div>
        </div>
    );
}

const styles = {

    bioSection: {
        backgroundColor: '#fff9c4', // Light yellow
        padding: '15px',
        borderRadius: '6px',
    },
    donationsSection: {
        backgroundColor: '#e3f2fd', // Light blue
        padding: '15px',
        borderRadius: '6px',
    },
    searchSection: {
        backgroundColor: '#ffe0b2', // Light orange
        padding: '15px',
        borderRadius: '6px',
    },
    header: {
        fontSize: '18px',
        fontWeight: 'bold',
        marginBottom: '10px',
    },
    button: {
        display: 'block',
        padding: '10px 15px',
        backgroundColor: '#2196f3', // Blue
        color: '#fff',
        textAlign: 'center',
        borderRadius: '5px',
        textDecoration: 'none',
        cursor: 'pointer',
        fontWeight: 'bold',
    },
    buttonContainer: {
        marginTop: '10px',
    },

    dashboardContainer: {
        display: 'grid',
        gridTemplateColumns: '1fr 2fr 1fr',
        gap: '20px',
        backgroundColor: '#fff',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
        width: '90%',
        margin: '20px auto',
    },
};

export default RecipientDashboard;
