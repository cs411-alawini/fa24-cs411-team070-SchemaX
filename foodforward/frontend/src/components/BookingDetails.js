import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';

function BookingDetails() {
    const { id } = useParams();
    const [bookingDetails, setBookingDetails] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchBookingDetails = async () => {
            try {
                const response = await fetch(`http://localhost:8080/bookings/3`);
                if (!response.ok) {
                    throw new Error('Failed to fetch booking details');
                }
                const data = await response.json();
                setBookingDetails(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchBookingDetails();
    }, [id]);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    const commonDetails = bookingDetails[0];

    const getStatusColor = (status) => {
        switch (status) {
            case 'CONFIRMED':
                return '#4caf50'; 
            case 'PENDING':
                return '#ff9800';
            case 'COMPLETED':
                return '#2196f3';
            case 'CANCELLED':
                return '#f44336';
            case 'BOOKED':
                return '#9c27b0';
            default:
                return '#757575';
        }
    };

    return (
        <div style={styles.container}>
            <h2 style={styles.header}>Booking Details</h2>

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Pickup Details</h3>
                <p><strong>Date:</strong> {commonDetails.pickupDate}</p>
                <p><strong>Time:</strong> {commonDetails.pickupTime}</p>
                <p><strong>Location:</strong> {commonDetails.pickupLocation}</p>
            </div>

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Item Details</h3>
                {bookingDetails.map((item, index) => (
                    <div key={index} style={styles.itemSection}>
                        <p><strong>Name:</strong> {item.itemName}</p>
                        <p><strong>Quantity:</strong> {item.quantity}</p>
                        <p><strong>Expiration Date:</strong> {item.expirationDate}</p>
                    </div>
                ))}
            </div>

            <div style={styles.section}>
                <h3 style={styles.subHeader}>Recipient Information</h3>
                <p><strong>Name:</strong> {commonDetails.recipientName}</p>
                <p><strong>Email:</strong> {commonDetails.email}</p>
                <p><strong>Phone Number:</strong> {commonDetails.phoneNumber}</p>
            </div>

            <div style={styles.statusSection}>
                <h3 style={styles.subHeader}>Status</h3>
                <div style={{ ...styles.status, backgroundColor: getStatusColor(commonDetails.status) }}>
                    {commonDetails.status}
                </div>
            </div>

            <button style={styles.button} onClick={() => window.location.href = '/donor_dashboard'}>
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