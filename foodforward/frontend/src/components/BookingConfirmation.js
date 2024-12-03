import React, { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faStar } from '@fortawesome/free-solid-svg-icons';

const BookingConfirmation = () => {
    const [rating, setRating] = useState(0);
    const [feedback, setFeedback] = useState('');

    const handleRatingClick = (value) => {
        setRating(value);
    };

    const handleSubmit = () => {
        alert(`Rating: ${rating}\nFeedback: ${feedback}`);
        // Redirect or perform any other action after submitting feedback.
    };

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-xl w-full bg-white p-8 rounded-lg shadow-lg">
                {/* Success Message */}
                <h2 className="text-xl font-bold text-green-600 mb-4 text-center">Booking Successful!</h2>
                <p className="text-center mb-4">Thank you for booking this donation.</p>
                <p className="text-center mb-8">You can pick up your items at the agreed time and location.</p>

                {/* Rating Section */}
                <h3 className="text-lg font-semibold mb-4">Leave a rating for this donation:</h3>

                {/* Star Rating */}
                <div className="flex justify-center mb-4">
                    {[1, 2, 3, 4, 5].map((star) => (
                        <FontAwesomeIcon
                            key={star}
                            icon={faStar}
                            onClick={() => handleRatingClick(star)}
                            className={`w-8 h-8 cursor-pointer ${rating >= star ? "text-yellow-500" : "text-gray-400"}`}
                        />
                    ))}
                </div>

                {/* Feedback Input */}
                <textarea
                    value={feedback}
                    onChange={(e) => setFeedback(e.target.value)}
                    placeholder="Leave additional comments or feedback here..."
                    rows={4}
                    className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:ring focus:ring-blue-200 mb-6"
                />

                {/* Submit Button */}
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