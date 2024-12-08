import React, { useState, useEffect } from 'react';
import Filter from './Filter';
import axios from 'axios';
import { Link } from 'react-router-dom';

const Card = ({ listing }) => {
  return (
    <Link to={`/listing/${listing.listingId}`}>
      <div className="bg-gray-100 p-6 rounded-lg shadow-md transform transition duration-300 ease-in-out hover:-translate-y-2 hover:shadow-lg hover:bg-blue-100">
        <h3 className="text-lg font-bold text-gray-900 mb-4">Donor: {listing.donor.name}</h3>
        <p className="text-md"><span className="font-semibold text-gray-800">Location:</span> {listing.location}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Pickup Time:</span> {listing.pickupTimeRange}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Status:</span> {listing.status}</p>

        <h4 className="text-md font-semibold mt-4">Items:</h4>
        <ul className="list-disc ml-5">
          {listing.listingItems.map((item) => (
            <li key={item.listingItemId}>
              {item.item.itemName} ({item.quantity} units, Expiry: {item.expirationDate || 'N/A'})
            </li>
          ))}
        </ul>
      </div>
    </Link>
  );
};

function ViewListings() {
  const [filters, setFilters] = useState({
    foodType: '',
    quantityNeeded: '',
    expiryDate: '',
    pickupTimeStart: '',
    pickupTimeEnd: '',
    location: '',
    distance: '',
  });

  const [listings, setListings] = useState([]); 
  const [loading, setLoading] = useState(false);
  const [userInfo, setUserInfo] = useState({}); 

  const fetchListings = async (filters, userInfo) => {
    setLoading(true);

    const postApiUrl = `http://localhost:8080/listings/search`;

    try {
      const requestPayload = {
        foodType: filters.foodType.toUpperCase(),
        quantityNeeded: filters.quantityNeeded ? parseInt(filters.quantityNeeded, 10) : null,
        expiryDate: filters.expiryDate?.trim() || null,
        pickupTimeStart: filters.pickupTimeStart?.trim() || null,
        pickupTimeEnd: filters.pickupTimeEnd?.trim() || null,
        distance: filters.distance ? parseInt(filters.distance, 10) * 1000 : null,
      };

      // latitude and longitude only if distance filter is applied
      debugger
      if (requestPayload.distance !== null) {
        requestPayload.userLatitude = userInfo.latitude || null;
        requestPayload.userLongitude = userInfo.longitude || null;
      }

      const response = await axios.post(postApiUrl, requestPayload);
      setListings(response.data);
    } catch (error) {
      console.error('Error fetching listings:', error);
    } finally {
      setLoading(false); 
    }
  };

  useEffect(() => {
    const recipientId = localStorage.getItem('recipientId'); 
    const apiUrl = `http://localhost:8080/users/${recipientId}`;

    const fetchUserInfo = async () => {
      setLoading(true);
      try {
        const response = await axios.get(apiUrl);
        setUserInfo(response.data); 
      } catch (err) {
        console.error('Error fetching user data:', err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  // Apply Filters and Fetch Listings
  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
    fetchListings(newFilters, userInfo);
  };

  return (
    <div className="flex h-screen overflow-hidden">
      <div className="w-1/3 h-full bg-gray-20 p-4 overflow-y-hidden">
        <Filter onApplyFilters={handleApplyFilters} location={filters.location} />
      </div>

      <div className="w-2/3 p-4 bg-gray-20 overflow-y-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-4 text-center">All Listings</h2>

        {loading ? (
          <div className="text-center text-xl text-gray-600">Loading...</div>
        ) : (
          <div className="grid grid-cols-1 gap-6">
            {listings.length > 0 ? (
              listings.map((listing) => (
                <Card key={listing.listingId} listing={listing} />
              ))
            ) : (
              <div className="text-center text-xl text-gray-600">No results found</div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default ViewListings;