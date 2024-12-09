import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function BookingDetails() {
    const { donorId, id } = useParams();
    const navigate = useNavigate();
    const [mainQuery, setMainQuery] = useState([]);
    const [itemSummary, setItemSummary] = useState({});
    const [donorDetails, setDonorDetails] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/bookings/${id}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch booking details');
                }
                const data = await response.json();
                setMainQuery(data.mainQuery || []);
                setItemSummary(data.itemSummary?.[0] || {});
                setDonorDetails(data.donorDetails?.[0] || {});
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBookingDetails();
    }, [id]);

    // Handle loading and error states
    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    // Extract common details (assumes all items share these attributes)
    const commonDetails = mainQuery.length > 0 ? mainQuery[0] : null;

    // Get status color dynamically
    const getStatusColor = (status) => {
        switch (status) {
            case 'CONFIRMED':
                return '#4caf50'; // Green
            case 'PENDING':
                return '#ff9800'; // Orange
            case 'COMPLETED':
                return '#2196f3'; // Blue
            case 'CANCELLED':
                return '#f44336'; // Red
            case 'BOOKED':
                return '#9c27b0'; // Purple
            default:
                return '#757575'; // Gray
        }
    };

    return (
        <div style={styles.container}>
            <h2 style={styles.header}>Booking Details</h2>

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Donation Information</h3>
                {mainQuery.map((item, index) => (
                    <div key={index} style={styles.itemSection}>
                        <p><strong>Item Name:</strong> {item.itemName}</p>
                        <p><strong>Quantity:</strong> {item.quantity}</p>
                        <p><strong>Expiry Date:</strong> {item.expirationDate}</p>
                    </div>
                ))}
            </div>

            {commonDetails && (
                <div style={styles.section}>
                    <h3 style={styles.subHeader}>Recipient Information</h3>
                    <p><strong>Recipient Name:</strong> {commonDetails.recipientName}</p>
                    <p><strong>Email:</strong> {commonDetails.email}</p>
                    <p><strong>Phone Number:</strong> {commonDetails.phoneNumber}</p>
                </div>
            )}

            {commonDetails && (
                <div style={styles.section}>
                    <h3 style={styles.subHeader}>Pickup Details</h3>
                    <p><strong>Pickup Date:</strong> {commonDetails.pickupDate}</p>
                    <p><strong>Pickup Time:</strong> {commonDetails.pickupTime}</p>
                    <p><strong>Pickup Location:</strong> {commonDetails.pickupLocation}</p>
                </div>
            )}

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Item Summary</h3>
                <p><strong>Total Items:</strong> {itemSummary.totalItems || 0}</p>
                <p><strong>Total Quantity:</strong> {itemSummary.totalQuantity || 0} kg</p>
                <p><strong>Latest Expiration Date:</strong> {itemSummary.latestExpirationDate || 'N/A'}</p>
            </div>

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Donor Details</h3>
                <p><strong>Name:</strong> {donorDetails.donorName || 'N/A'}</p>
                <p><strong>Email:</strong> {donorDetails.donorEmail || 'N/A'}</p>
                <p><strong>Phone:</strong> {donorDetails.donorPhone || 'N/A'}</p>
                <p><strong>Average Rating:</strong> {donorDetails.averageRating ? donorDetails.averageRating.toFixed(1) : 'N/A'}</p>
                <p><strong>Total Reviews:</strong> {donorDetails.totalReviews || 0}</p>
            </div>

            {commonDetails && (
                <div style={styles.statusSection}>
                    <h3 style={styles.subHeader}>Status</h3>
                    <div style={{ ...styles.status, backgroundColor: getStatusColor(commonDetails.status) }}>
                        {commonDetails.status}
                    </div>
                </div>
            )}

            <button style={styles.button} onClick={() => navigate(`/users/${donorId}/donor_dashboard`)}>
                Back to Dashboard
            </button>
        </div>
    );
}

const styles = {
    container: {
        backgroundColor: '#fff',
        padding: '30px',
        borderRadius: '10px',
        boxShadow: '0px 4px 15px rgba(0, 0, 0, 0.1)',
        maxWidth: '700px',
        margin: '30px auto',
        fontFamily: 'Arial, sans-serif',
    },
    header: {
        textAlign: 'center',
        color: '#333',
        marginBottom: '30px',
        fontSize: '28px',
        fontWeight: 'bold',
    },
    subHeader: {
        fontSize: '20px',
        marginBottom: '15px',
        color: '#555',
        fontWeight: 'bold'
    },
    section: {
        marginBottom: '25px',
        paddingLeft: '10px',
    },
    itemSection: {
        borderBottom: '1px solid #ddd',
        paddingBottom: '15px',
        marginBottom: '15px',
        paddingLeft: '10px',
    },
    statusSection: {
        marginBottom: '30px',
        textAlign: 'center',
    },
    status: {
        padding: '12px 20px',
        borderRadius: '5px',
        fontWeight: 'bold',
        fontSize: '18px',
        color: '#000',
    },
    button: {
        padding: '12px 25px',
        backgroundColor: '#28a745',
        color: '#fff',
        borderRadius: '5px',
        border: 'none',
        fontSize: '18px',
        cursor: 'pointer',
        display: 'block',
        marginLeft: 'auto',
        marginRight: 'auto',
    },
};

export default BookingDetails;