import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ListingDetails = () => {
    const { id } = useParams();
    const [listing, setListing] = useState(null);
    const [selectedItems, setSelectedItems] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchListingDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/listings/getListing?listingId=${id}`);
                setListing(response.data);
            } catch (error) {
                console.error('Error fetching listing details:', error);
            }
        };

        fetchListingDetails();
    }, [id]);

    const handleToggleItem = (listingItemId) => {
        setSelectedItems((prevItems) => {
            if (prevItems.includes(listingItemId)) {
                return prevItems.filter((item) => item !== listingItemId);
            }
            return [...prevItems, listingItemId];
        });
    };

    const handleBooking = async () => {
        if (selectedItems.length === 0) {
            alert('Please select at least one item to book.');
            return;
        }

        const bookedBy = localStorage.getItem('recipientId');
        const bookingStatus =
            selectedItems.length === listing.listingItems.length ? 'BOOKED' : 'PARTIALLY BOOKED';

        const createBookingDTO = {
            bookedBy: parseInt(bookedBy, 10),
            bookingStatus,
            listingItemIds: selectedItems,
            pickupDate: new Date(),
        };

        try {
            const response = await axios.post('http://localhost:8080/bookings/add', createBookingDTO);
            alert(response.data);

            navigate('/confirmation', { state: { donorId: listing.donor.donorId } });
        } catch (error) {
            console.error('Error creating booking:', error);
            alert('Failed to create booking. Please try again.');
        }
    };

    if (!listing) {
        return <div>Loading...</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-4xl w-full bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-bold text-center mb-6 text-gray-800">Listing Details</h2>

                <div className="mb-6">
                    <p className="text-lg"><strong>Donor:</strong> {listing.donor.name}</p>
                    <p className="text-lg"><strong>Location:</strong> {listing.location}</p>
                    <p className="text-lg"><strong>Pickup Time:</strong> Between {listing.pickupTimeRange}</p>
                </div>

                <h3 className="text-xl font-bold mb-4 text-gray-700">Available Items</h3>

                {listing.listingItems.map((item) => (
                    <div
                        key={item.listingItemId}
                        className={`mb-6 border-b pb-4 rounded-md transition ${selectedItems.includes(item.listingItemId)
                            ? 'bg-blue-100 border-blue-300 p-5'
                            : 'bg-white'
                            }`}
                    >
                        <div className="flex justify-between items-center">
                            <div>
                                <p className="text-lg font-semibold text-gray-800"><strong>Item Name:</strong> {item.item.itemName}</p>
                                <p className="text-md text-gray-600"><strong>Quantity Available:</strong> {item.quantity} units</p>
                                <p className="text-md text-gray-600"><strong>Expiry Date:</strong> {new Date(item.expirationDate).toLocaleDateString()}</p>
                            </div>

                            {/* Toggle Selection Button */}
                            <button
                                onClick={() => handleToggleItem(item.listingItemId)}
                                className={`px-4 py-2 rounded-full focus:outline-none transition ${selectedItems.includes(item.listingItemId)
                                    ? 'bg-red-500 text-white hover:bg-red-600'
                                    : 'bg-green-500 text-white hover:bg-green-600'
                                    }`}
                            >
                                {selectedItems.includes(item.listingItemId) ? 'Deselect' : '+'}
                            </button>
                        </div>
                    </div>
                ))}

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