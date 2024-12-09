import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';
import { useLocation } from 'react-router-dom';
import axios from 'axios';

const BookingConfirmation = () => {
    const location = useLocation(); // Access state passed via navigate
    const donorId = location.state?.donorId; // Retrieve donorId from state
    const [rating, setRating] = useState(0);
    const [feedback, setFeedback] = useState('');
    const recipientId = localStorage.getItem('recipientId'); // Get recipient ID dynamically

    const handleRatingClick = (value) => {
        setRating(value);
    };

    const handleSubmit = async () => {
        if (!rating) {
            alert('Please provide a rating before submitting.');
            return;
        }

        const createReviewDTO = {
            review: feedback,
            reviewDate: new Date(),
            rating,
            donorId: parseInt(donorId, 10),
            recipientId: parseInt(recipientId, 10),
        };

        try {
            const response = await axios.post('http://localhost:8080/reviews/add', createReviewDTO);
            alert(response.data); 
            window.location.href = '/users/'+recipientId+'/recipient_dashboard'; 
        } catch (error) {
            console.error('Error submitting review:', error);
            alert('Failed to submit review. Please try again.');
        }
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-xl w-full bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-xl font-bold text-green-600 mb-4 text-center">Booking Successful!</h2>
                <p className="text-center mb-4">Thank you for booking this donation.</p>
                <p className="text-center mb-8">You can pick up your items at the agreed time and location.</p>

                <h3 className="text-lg font-semibold mb-4">Leave a rating for this donation:</h3>

                <div className="flex justify-center mb-4">
                    {[1, 2, 3, 4, 5].map((star) => (
                        <FontAwesomeIcon
                            key={star}
                            icon={faStar}
                            onClick={() => handleRatingClick(star)}
                            className={`w-8 h-8 cursor-pointer transition ${rating >= star ? 'text-yellow-500' : 'text-gray-400'
                                }`}
                        />
                    ))}
                </div>

                <textarea
                    value={feedback}
                    onChange={(e) => setFeedback(e.target.value)}
                    placeholder="Leave additional comments or feedback here..."
                    rows={4}
                    className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-200 mb-6"
                />

                <button
                    onClick={handleSubmit}
                    className="w-full bg-blue-500 text-white py-3 rounded-lg mt-auto font-semibold hover:bg-blue-600 transition duration-300"
                >
                    Submit Rating & Return to Dashboard
                </button>
            </div>
        </div>
    );
};

export default BookingConfirmation;