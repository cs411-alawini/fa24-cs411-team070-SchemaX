import React, {useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function DonorDashboard() {
    const navigate = useNavigate();
    const [donorInfo, setDonorInfo] = useState(null);
    const [bookings, setBookings] = useState([]);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchDonorData = async () => {
            const donorId = localStorage.getItem('donorId'); // Get donorId from localStorage
            if (!donorId) {
                navigate('/login'); // Redirect to login if donorId is missing
                return;
            }
            console.log('Donor ID:', donorId);

            try {
                const response = await fetch(`http://localhost:8080/users/donor?donorId=${donorId}`);
                if (!response.ok) {
                    throw new Error('Failed to fetch donor data');
                }

                const data = await response.json();
                console.log('Donor data after fetch:', data);
                setDonorInfo(data);
                setBookings(data); //change this to setBookings(data.bookings)
                setReviews(data); //change this to setReviews(data.reviews)
            } catch (error) {
                console.error('Error fetching donor data:', error);
                alert('Failed to fetch donor data. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        fetchDonorData();
    }, [navigate]);

    const handleViewDetails = (id) => {
        navigate(`/booking_details/${id}`);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div style={styles.dashboardContainer}>
            <div style={styles.profileSection}>
                <h3>Donor Profile</h3>
                <p>{donorInfo?.name || '[Name Not Available]'}</p>
                <p>{donorInfo?.email || '[Email Not Available]'}</p>
                <p>Overall Rating:</p>
                <p>★★★★☆ ({donorInfo?.rating || 'N/A'})</p>
                <button style={styles.button} onClick={() => navigate('/create_listing')}>
                    Create New Listing
                </button>
            </div>

            <div style={styles.bookingsSection}>
                <h3>Your Donations & Bookings</h3>
                {bookings.password !== null ? ( //change this to bookings.length > 0
                    bookings.map((booking) => (
                        <div key={booking.id} style={styles.bookingRow}>
                            <p><strong>Donation Item:</strong> {booking.item}</p>
                            <p><strong>Recipient:</strong> {booking.recipient}</p>
                            <p><strong>Status:</strong> {booking.status}</p>
                            <button
                                style={styles.detailsButton}
                                onClick={() => handleViewDetails(booking.id)}
                            >
                                View Details
                            </button>
                        </div>
                    ))
                ) : (
                    <p>No bookings available.</p>
                )}
            </div>

            <div style={styles.reviewsSection}>
                <h3>Reviews from Recipients</h3>
                {reviews.password !== null ? ( //change this to reviews.length > 0
                    reviews.map((review) => (
                        <div key={review.id} style={styles.reviewRow}>
                            <p><strong>Recipient:</strong> {review.recipient}</p>
                            <p><strong>Review:</strong> {review.comment} ★★★★★</p>
                        </div>
                    ))
                ) : (
                    <p>No reviews available.</p>
                )}
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