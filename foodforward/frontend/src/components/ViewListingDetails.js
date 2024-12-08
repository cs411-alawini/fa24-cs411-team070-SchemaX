import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const ViewListingDetails = () => {
    const { donorId, id } = useParams();
    const [listing, setListing] = useState(null);
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


    const backToDashboard = () => {
        navigate('/users/' + donorId + '/donor_dashboard'); 
    };

    if (!listing) {
        return <div>Loading...</div>;
    }

    return (
        <div className="min-h-screen bg-gray-100 flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-4xl w-full bg-white p-8 rounded-lg shadow-lg">
                <h2 className="text-3xl font-bold text-center mb-6 text-gray-800">Listing Details</h2>


                <div className="mb-6">
                    <p className="text-lg"><strong>Location:</strong> {(listing.location?listing.location: "Not Available")}</p>
                    <p className="text-lg"><strong>Pickup Time:</strong> Between {listing.pickupTimeRange}</p>
                </div>


                <h3 className="text-xl font-bold mb-4 text-gray-700">Select Items and Quantities</h3>

                {
                    listing.listingItems.length > 0 ? (
                        listing.listingItems.map((item) => (
                            <div key={item.listingItemId} className="mb-6 border-b pb-4">
                                <div className="flex justify-between items-center">
                                    <div>
                                        <p className="text-md font-semibold text-gray-800"><strong>Item Name:</strong> {item.item.itemName}</p>
                                        <p className="text-md font-semibold text-gray-800"><strong>Item Category:</strong> {item.item.category}</p>
                                        <p className="text-md font-semibold text-gray-800"><strong>Quantity Available:</strong> {item.quantity} {item.item.category === 'DAIRY' ? 'kg' : 'units'}</p>
                                        <p className="text-md font-semibold text-gray-800"><strong>Expiry Date:</strong> {new Date(item.expirationDate).toLocaleDateString()}</p>
                                        <p className="text-md font-semibold text-gray-800"><strong>Item Status:</strong> {item.status}</p>
                                    </div>
        
                                    
                                </div>
                            </div>
                        ))
                    ):(
                        <p className="text-md text-gray-800">No items available in this listing</p>
                    )
                }
                


                <button
                    onClick={backToDashboard}
                    className="w-full bg-green-500 text-white py-3 rounded-lg mt-6 font-semibold hover:bg-blue-600 transition duration-300"
                >
                    Back to Dashboard
                </button>
            </div>
        </div>
    );
};

export default ViewListingDetails;