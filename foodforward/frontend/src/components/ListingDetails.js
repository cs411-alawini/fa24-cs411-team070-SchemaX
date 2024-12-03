import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ListingDetails = () => {
    const { id } = useParams(); // Get the listing ID from the URL
    const [listing, setListing] = useState(null);
    const [quantities, setQuantities] = useState({}); // Store selected quantities for each item
    const navigate = useNavigate(); // Hook for navigation

    useEffect(() => {
        // Fetch listing details based on ID
        const fetchListingDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/listings/getListing?listingId=${id}`);
                setListing(response.data);

                // Initialize quantities for each item to 1
                const initialQuantities = {};
                response.data.listingItems.forEach(item => {
                    initialQuantities[item.listingItemId] = 1;
                });
                setQuantities(initialQuantities);
            } catch (error) {
                console.error('Error fetching listing details:', error);
            }
        };

        fetchListingDetails();
    }, [id]);

    // Handle quantity change for a specific item
    const handleQuantityChange = (listingItemId, newQuantity) => {
        setQuantities(prevQuantities => ({
            ...prevQuantities,
            [listingItemId]: Math.max(0, newQuantity), // Ensure quantity is at least 1
        }));
    };

    // Handle booking and navigate to confirmation page
    const handleBooking = () => {
        alert('Booking confirmed!'); // You can replace this with actual booking logic
        navigate('/confirmation'); // Redirect to confirmation page
    };

    if (!listing) {
        return <div>Loading...</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-4xl w-full bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-bold text-center mb-6 text-gray-800">Listing Details</h2>

                {/* Donor and Pickup Information */}
                <div className="mb-6">
                    <p className="text-lg"><strong>Donor:</strong> {listing.donor.user.name}</p>
                    <p className="text-lg"><strong>Location:</strong> {listing.location}</p>
                    <p className="text-lg"><strong>Pickup Time:</strong> Between {listing.pickupTimeRange}</p>
                </div>

                {/* Items and Quantities */}
                <h3 className="text-xl font-bold mb-4 text-gray-700">Select Items and Quantities</h3>

                {/* Loop through items in the listing */}
                {listing.listingItems.map((item) => (
                    <div key={item.listingItemId} className="mb-6 border-b pb-4">
                        <div className="flex justify-between items-center">
                            <div>
                                <p className="text-lg font-semibold text-gray-800"><strong>Item Name:</strong> {item.item.itemName}</p>
                                <p className="text-md text-gray-600"><strong>Quantity Available:</strong> {item.quantity} {item.item.category === 'DAIRY' ? 'kg' : 'units'}</p>
                                <p className="text-md text-gray-600"><strong>Expiry Date:</strong> {new Date(item.expirationDate).toLocaleDateString()}</p>
                            </div>

                            {/* Quantity Selector */}
                            <div className="flex items-center">
                                <span className="mr-2 text-md font-semibold text-gray-700">Select Quantity:</span>
                                <button
                                    onClick={() => handleQuantityChange(item.listingItemId, quantities[item.listingItemId] - 1)}
                                    className="bg-green-500 text-white px-2 py-1 rounded-l focus:outline-none hover:bg-green-600"
                                >
                                    -
                                </button>
                                <input
                                    type="number"
                                    value={quantities[item.listingItemId]}
                                    onChange={(e) => handleQuantityChange(item.listingItemId, Number(e.target.value))}
                                    min={1}
                                    max={item.quantity}
                                    className="w-12 text-center border border-gray-300 focus:outline-none"
                                />
                                <button
                                    onClick={() => handleQuantityChange(item.listingItemId, quantities[item.listingItemId] + 1)}
                                    className="bg-green-500 text-white px-2 py-1 rounded-r focus:outline-none hover:bg-green-600"
                                >
                                    +
                                </button>
                            </div>
                        </div>
                    </div>
                ))}

                {/* Book Now Button */}
                <button
                    onClick={handleBooking}
                    className="w-full bg-blue-500 text-white py-3 rounded-lg mt-6 font-semibold hover:bg-blue-600 transition duration-300"
                >
                    Book Now
                </button>
            </div>
        </div>
    );
};

export default ListingDetails;