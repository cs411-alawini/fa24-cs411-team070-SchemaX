import React from 'react';
import { useNavigate } from 'react-router-dom';

function DonorDashboard() {
    const navigate = useNavigate();

    const handleViewDetails = (id) => {
        navigate(`/booking_details/${id}`);
    };

    return (
        <div style={styles.dashboardContainer}>
            <div style={styles.profileSection}>
                <h3>Donor Profile</h3>
                <p>[Donor's Name]</p>
                <p>[Email]</p>
                <p>Overall Rating:</p>
                <p>★★★★☆ (4.5)</p>
                <button style={styles.button}>Create New Listing</button>
            </div>

            <div style={styles.bookingsSection}>
                <h3>Your Donations & Bookings</h3>

                <div style={styles.bookingRow}>
                    <p><strong>Donation Item:</strong> Fresh Apples (10 kg)</p>
                    <p><strong>Recipient:</strong> NGO A</p>
                    <p><strong>Status:</strong> Awaiting Pickup</p>
                    <button style={styles.detailsButton} onClick={() => handleViewDetails(1)}>View Details</button>
                </div>

                <div style={styles.bookingRow}>
                    <p><strong>Donation Item:</strong> Canned Beans (20 cans)</p>
                    <p><strong>Recipient:</strong> NGO B</p>
                    <p><strong>Status:</strong> Completed</p>
                    <button style={styles.detailsButton} onClick={() => handleViewDetails(2)}>View Details</button>
                </div>

            </div>

            <div style={styles.reviewsSection}>
                <h3>Reviews from Recipients</h3>

                <div style={styles.reviewRow}>
                    <p><strong>Recipient:</strong> NGO A</p>
                    <p><strong>Review:</strong> "Great quality apples!" ★★★★★</p>
                </div>

                <div style={styles.reviewRow}>
                    <p><strong>Recipient:</strong> NGO B</p>
                    <p><strong>Review:</strong> "Very helpful donation." ★★★★☆</p>
                </div>

            </div>
        </div>
    );
}

const styles = {
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
    profileSection: {
        backgroundColor: '#fff9c4',
        padding: '15px',
        borderRadius: '6px',
    },
    bookingsSection: {
        backgroundColor: '#e0f7fa',
        padding: '15px',
        borderRadius: '6px',
    },
    reviewsSection: {
        backgroundColor: '#ffe0b2',
        padding: '15px',
        borderRadius: '6px',
    },
    button: {
        padding: '10px 20px',
        backgroundColor: '#28a745',
        color: 'white',
        borderRadius: '4px',
        border: 'none',
        fontSize: '16px',
        cursor: 'pointer',
    },
    detailsButton: {
        padding: '5px 10px',
        backgroundColor: '#28a745',
        color: 'white',
        borderRadius: '4px',
        border: 'none',
        fontSize: '14px',
        cursor: 'pointer',
    },
    bookingRow: {
        marginBottom: '15px',
    },
    reviewRow: {
        marginBottom: '15px',
    },
};

export default DonorDashboard;