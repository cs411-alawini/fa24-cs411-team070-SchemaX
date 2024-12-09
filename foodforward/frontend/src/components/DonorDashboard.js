import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation, useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';


function StarRating({ rating, maxStars = 5 }) {
    const stars = [];
    for (let i = 1; i <= maxStars; i++) {
        stars.push(
            <span key={i} style={{ color: i <= rating ? '#000000' : '#E0E0E0', fontSize: '20px' }}>★</span>
        );
    }
    return <span>{stars}</span>;
}

function DonorDashboard() {
    const { donorId } = useParams();
    const navigate = useNavigate();
    const location = useLocation();
    const [donorInfo, setDonorInfo] = useState(null);
    const [donations, setDonations] = useState([]);
    const [bookings, setBookings] = useState([]);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [showAlert, setShowAlert] = useState(false);
    const [containerHeight, setContainerHeight] = useState('auto');

    useEffect(() => {
        if (location.state?.listingCreated) {
            setShowAlert(true);
            setTimeout(() => {
                setShowAlert(false);
            }, 2500);
            navigate(location.pathname, { replace: true, state: { ...location.state, listingCreated: false } });
        }
    }, [location, navigate]);

    useEffect(() => {
        const fetchDonorData = async () => {
            if (!donorId) {
                navigate('/login');
                return;
            }

            try {
                const [profileRes, donationsRes, bookingRes, reviewsRes] = await Promise.all([
                    fetch(`http://localhost:8080/users/donor?donorId=${donorId}`),
                    fetch(`http://localhost:8080/listings/getAllListings?donorId=${donorId}`),
                    fetch(`http://localhost:8080/bookings/getDonorBookings?donorId=${donorId}`),
                    fetch(`http://localhost:8080/reviews/getReviews?donorId=${donorId}`),
                ]);

                if (!profileRes.ok || !donationsRes.ok || !bookingRes.ok || !reviewsRes.ok) {
                    throw new Error('Failed to fetch donor data');
                }

                const profileData = await profileRes.json();
                const donationsData = await donationsRes.json();
                const bookingData = await bookingRes.json();
                const reviewsData = await reviewsRes.json();

                setDonorInfo(profileData);
                setDonations(donationsData);
                setBookings(bookingData);
                setReviews(reviewsData);
            } catch (error) {
                console.error('Error fetching donor data:', error);
                alert('Failed to fetch donor data. Please try again later.');
            } finally {
                setLoading(false);
            }
        };

        fetchDonorData();
    }, [navigate, donorId]);

    useEffect(() => {
        const handleResize = () => {
            setContainerHeight(window.innerHeight - 40);
        };

        handleResize();
        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    const handleBookingDetails = (booking_id) => {
        navigate(`/users/${donorId}/booking_details/${booking_id}`);
    };

    const handleListingDetails = (listing_id) => {
        navigate(`/users/${donorId}/listing_details/${listing_id}`);
    };

    const handleDeleteListing = async (listingId) => {
        if (window.confirm("Are you sure you want to delete this listing?")) {
            try {
                const response = await fetch(`http://localhost:8080/listings/delete/${listingId}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    throw new Error('Failed to delete the listing');
                }
                alert("Listing deleted successfully");
                setDonations(donations.filter((donation) => donation.listingId !== listingId));
            } catch (error) {
                console.error("Error deleting listing:", error);
                alert("Failed to delete the listing. Please try again.");
            }
        }
    };


    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div>
            {showAlert && (
                <div style={styles.alert}>
                    New listing created!
                </div>
            )}

            <div style={{ ...styles.dashboardContainer, height: containerHeight }}>
                <div style={styles.profileSection}>
                    <h3 style={{ textAlign: 'center', fontSize: '1.2em', fontWeight: "bold", paddingBottom: '15px' }}>Donor Profile</h3>
                    <div style={{ textAlign: "center", margin: "20px 0", fontSize: "1.2em" }}>
                        <p>{donorInfo?.name || '[Name Not Available]'}</p>
                        <p>{donorInfo?.email || '[Email Not Available]'}</p>
                        <p>Overall Rating: <StarRating rating={donorInfo?.rating || 0} /> ({donorInfo?.rating || 0})</p>
                    </div>

                    <div style={styles.buttonContainer}>
                        <button className='w-full bg-green-500 text-white py-3 rounded-md font-semibold hover:bg-blue-600 transition duration-300' onClick={() => navigate(`/users/${donorId}/create_listing`)}>
                            Create New Listing
                        </button>
                    </div>
                </div>

                <div style={styles.Middlecontainer}>
                    <div style={styles.donationsSection}>
                        <style>{`div::-webkit-scrollbar {display: none;}`}</style>
                        <h3 style={styles.sectionHeading}>My Donations</h3>
                        {donations.length > 0 ? (
                            donations.map((donation) => (
                                <div key={donation.id} style={{ ...styles.bookingRow }}>
                                    <div style={{ flex: 1 }}>
                                        <p><strong>Location:</strong> {donation.location || "Not Available"}</p>
                                        <p><strong>Status:</strong> {donation.status}</p>
                                    </div>
                                    <div style={{ display: 'flex', gap: '10px', alignItems: 'center' }}>
                                        <button
                                            style={styles.detailsButton}
                                            onClick={() => handleListingDetails(donation.listingId)}
                                        >
                                            View Details
                                        </button>
                                        <FontAwesomeIcon
                                            icon={faTrash}
                                            style={styles.deleteIcon}
                                            onClick={() => handleDeleteListing(donation.listingId)}
                                        />
                                    </div>
                                </div>
                            ))
                        ) : (
                            <p>No Donations available.</p>
                        )}
                    </div>

                    <div style={styles.bookingsSection}>
                        <style>{`div::-webkit-scrollbar {display: none;}`}</style>
                        <h3 style={styles.sectionHeading}>My Previous Bookings</h3>
                        {bookings.length > 0 ? (
                            bookings.map((donation) => (
                                <div key={donation.id} style={{ ...styles.bookingRow }}>
                                    <div style={{ flex: 1 }}>
                                        <p><strong>Booking ID:</strong> <i>{donation.bookingId}</i></p>
                                        <p><strong>Recipient Name:</strong> {donation.name}</p>
                                        <p><strong>Booking Status:</strong> {donation.bookingStatus}</p>
                                    </div>
                                    {
                                        (donation.bookingStatus === "CANCELLED") ?
                                            <button
                                                style={{ ...styles.detailsButton, backgroundColor: '#d3d3d3', cursor: 'not-allowed' }}
                                                disabled
                                            >
                                                Booking Details
                                            </button>
                                            : (
                                                <button
                                                    className='w-1/4 bg-green-500 text-white py-3 rounded-md font-semibold hover:bg-blue-600 transition duration-300'
                                                    onClick={() => handleBookingDetails(donation.bookingId)}
                                                >
                                                    Booking Details
                                                </button>
                                            )
                                    }
                                </div>
                            ))
                        ) : (
                            <p>No Bookings available.</p>
                        )}
                    </div>

                </div>

                <div style={styles.reviewsSection}>
                    <h3 style={{ textAlign: 'center', fontSize: '1.2em', fontWeight: "bold", paddingBottom: '15px' }}>Reviews from Recipients</h3>
                    {reviews.length > 0 ? (
                        reviews.map((review) => (
                            <div key={review.id} style={styles.reviewRow}>
                                <p><strong>Recipient:</strong> {review.recipientName}</p>
                                <p><strong>Rating:</strong> <StarRating rating={review.rating || 0} /> ({review.rating || 0})</p>
                                <p><strong>Posted on:</strong> {review.reviewDate}</p>
                                <div style={styles.reviewContent}>
                                    <span><strong>Review:</strong></span>
                                    <p>"{review.review}"</p>
                                </div>
                            </div>
                        ))
                    ) : (
                        <p>No reviews available.</p>
                    )}
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
        padding: '20px',
        width: '90%',
        margin: '20px auto',
        maxHeight: 'calc(100vh - 40px)',
        boxSizing: 'border-box',
    },
    profileSection: {
        backgroundColor: '#fff9c4',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
        border: "1px solid lightgrey",
        height: '100%',
    },
    buttonContainer: {
        textAlign: 'center',
        marginTop: '20px',
    },
    Middlecontainer: {
        display: "flex",
        flexDirection: "column",
        gap: "20px",
        padding: "0",
    },
    donationsSection: {
        backgroundColor: "#eaf4fc",
        padding: "20px",
        borderRadius: "8px",
        boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
        overflowY: "auto",
        height: "400px",
        border: "1px solid lightgrey",
    },
    bookingsSection: {
        backgroundColor: "#f4f9ea",
        padding: "20px",
        borderRadius: "8px",
        boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
        overflowY: "auto",
        height: "400px",
        border: "1px solid lightgrey",
    },
    sectionHeading: {
        textAlign: "center",
        fontSize: "1.5em",
        fontWeight: "bold",
        marginBottom: "15px",
    },
    bookingRow: {
        backgroundColor: "#ffffff",
        padding: "10px",
        marginBottom: "10px",
        borderRadius: "5px",
        boxShadow: "0 1px 5px rgba(0, 0, 0, 0.1)",
        display: "flex",
        justifyContent: "space-between",
        gap: "20px",
        alignItems: "center",
    },
    detailsButton: {
        padding: '8px 12px',
        fontSize: '14px',
        backgroundColor: '#28A745',
        color: '#fff',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
    },
    deleteIcon: {
        color: 'red',
        cursor: 'pointer',
        fontSize: '18px',
    },
    reviewsSection: {
        backgroundColor: '#ffe0b2',
        padding: '20px',
        borderRadius: '8px',
        boxShadow: '0 4px 6px rgba(0,0,0,0.1)',
        overflowY: 'auto',
        height: '400px',
        border: "1px solid lightgrey"
    },
    reviewRow: {
        marginBottom: '15 px'
    }
};

export default DonorDashboard;
