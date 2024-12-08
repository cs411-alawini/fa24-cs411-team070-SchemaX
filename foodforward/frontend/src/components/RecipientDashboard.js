import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import Modal from 'react-modal';

function RecipientDashboard() {
    const { recipientId } = useParams();
    const [recipientInfo, setRecipientInfo] = useState(null);
    const [recipientBookings, setRecipientBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [review, setReview] = useState('');
    const [rating, setRating] = useState(0);
    const [selectedBooking, setSelectedBooking] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchRecipientIdData = async () => {
            if (!recipientId) {
                navigate('/login');
                return;
            }

            try {
                const [profileRes, bookingRes] = await Promise.all([
                    fetch(`http://localhost:8080/users/recipient?recipientId=${recipientId}`),
                    fetch(`http://localhost:8080/bookings/getRecipientBookings?recipientId=${recipientId}`),
                ]);

                if (!profileRes.ok || !bookingRes.ok) {
                    throw new Error('Failed to fetch recipient data');
                }

                const profileData = await profileRes.json();
                const bookingData = await bookingRes.json();

                setRecipientInfo(profileData);
                setRecipientBookings(bookingData);
            } catch (error) {
                console.error('Error fetching recipient data:', error);
                alert('Failed to fetch recipient data. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        fetchRecipientIdData();
    }, [navigate, recipientId]);

    const handleViewListings = () => {
        navigate('/listings');
    };

    const openModal = (booking) => {
        setSelectedBooking(booking);
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setReview('');
        setRating(0);
        setSelectedBooking(null);
    };

    const handleReviewSubmit = async (e) => {
        e.preventDefault();

        const reviewData = {
            recipientId: recipientInfo.recipientId,
            donorId: selectedBooking?.donorId,
            review,
            rating,
            reviewDate: new Date().toISOString().split('T')[0] ,
        };

    

        try {
            const response = await fetch('http://localhost:8080/reviews/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(reviewData),
            });

            if (!response.ok) {
                throw new Error('Failed to submit the review');
            }

            alert('Review submitted successfully!');
        } catch (error) {
            console.error('Error submitting review:', error);
            alert('Failed to submit review. Please try again later.');
        } finally {
            closeModal();
        }
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div style={styles.dashboardContainer}>
            {/* User Bio Section */}
            <div style={styles.bioSection}>
                <h3 style={{ textAlign: 'center', fontSize: '1.2em', fontWeight: "bold", paddingBottom: '15px' }}>My Profile</h3>
                <div style={{ margin: "20px 0" }}>
                    <p><strong>Name: </strong> {recipientInfo?.name || '[Name Not Available]'}</p>
                    <p><strong>Email: </strong>{recipientInfo?.email || '[Email Not Available]'}</p>
                    <p><strong>Phone: </strong>{recipientInfo?.phone || '[Phone Not Available]'}</p>
                    <p><strong>Address: </strong>{recipientInfo?.location || 'None'}</p>
                    <p><strong>Preferences: </strong>{recipientInfo?.preferences || 'None'}</p>
                </div>
            </div>

            {/* Recent Donations Section */}
            <div style={styles.donationsSection}>
                <h3 style={styles.header}>Recent Donations Received</h3>
                {recipientBookings.length > 0 ? (
                    recipientBookings.map((booking, index) => (
                        <div key={index} style={{ display: "flex", gap:"20px", alignItems: "center", marginBottom: '30px' }}>
                            
                            <div>

                                <p><strong>Donor name:</strong> {booking.name || "N/A"}</p>
                                <p><strong>Donation Date:</strong> {booking.pickupDatetime}</p>

                            </div>
                            
                            <div>

                                <p><strong>Bookings Status:</strong> {booking.bookingStatus}</p>
                                <p><strong>No. of items booked:</strong> {booking.listingItems.length}</p>

                            </div>
                                <button className='w-1/5 bg-blue-500 text-white py-3 rounded-md font-semibold hover:bg-blue-600 transition duration-300' onClick={() => openModal(booking)}>Leave a Review</button>

                        </div>
                    ))
                ) : (
                    <p>No donations received yet.</p>
                )}
            </div>

            {/* Search for Listings Section */}
            <div style={styles.searchSection}>
                <h3 style={styles.header}>Search for Available Listings</h3>
                <p>Find food donations that match your needs.</p>
                <div style={styles.buttonContainer}>
                    <button className='w-full bg-green-600 text-white py-3 rounded-md font-semibold hover:bg-green-700 transition duration-300' onClick={handleViewListings}>Search Listings</button>
                </div>
            </div>

            {/* Modal for Leaving a Review */}
            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                contentLabel="Leave a Review"
                style={{
                    content: {
                        width: '500px',
                        height: '450px',
                        margin: '0 auto',
                        padding: '20px',
                        borderRadius: '8px',
                        boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)',
                    },
                }}
            >
                <h3 style  = {{textAlign : "center", fontWeight:"bold", fontSize:"1.3em", paddingBottom:"10px"}}>Leave a Review for Donation</h3>

                {/* Star Rating Section */}
                <div style={styles.starRatingContainer}>
                    {[1, 2, 3, 4, 5].map((star) => (
                        <span
                            key={star}
                            style={styles.star}
                            onClick={() => setRating(star)}
                        >
                            {star <= rating ? '★' : '☆'}
                        </span>
                    ))}
                </div>

                {/* Review Textarea */}
                <form onSubmit={handleReviewSubmit}>
                    <textarea
                        placeholder="Leave additional comments or feedback here..."
                        value={review}
                        onChange={(e) => setReview(e.target.value)}
                        rows="4"
                        style={styles.textarea}
                    />
                    <div style={styles.buttonContainer}>
                        <button type="submit" className='w-full bg-green-500 text-white py-3 rounded-md font-semibold hover:bg-green-600 transition duration-300'>
                            Submit Rating & Return to Dashboard
                        </button>
                    </div>
                </form>

                <div style={styles.buttonContainer}>
                    <button onClick={closeModal} className='w-full bg-red-500 text-white py-3 rounded-md font-semibold hover:bg-red-600 transition duration-300'>
                        Cancel
                    </button>
                </div>
            </Modal>
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
    starRatingContainer: {
        fontSize: '30px',
        color: '#ff9800',
        marginBottom: '15px',
        textAlign: 'center',
    },
    star: {
        cursor: 'pointer',
    },
    textarea: {
        width: '100%',
        padding: '10px',
        borderRadius: '5px',
        marginBottom: '20px',
        border:"1px solid grey"
    }
};

export default RecipientDashboard;
