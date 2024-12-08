import React, { useState, useEffect } from 'react';
import Filter from './Filter';
import axios from 'axios';
import { Link } from 'react-router-dom';

const Card = ({ listing }) => {
  const isRecommended = listing.priority === 1;

  return (
    <Link to={`/listing/${listing.listingId}`}>
      <div
        className={`p-6 rounded-lg shadow-md transform transition duration-300 ease-in-out ${
          isRecommended
            ? 'bg-blue-100 border-2 border-blue-500 hover:bg-blue-200'
            : 'bg-gray-100 hover:bg-gray-200'
        }`}
      >
        {isRecommended && (
          <p className="text-sm font-semibold text-blue-700 mb-2">
            ML Recommended
          </p>
        )}
        <h3 className="text-lg font-bold text-gray-900 mb-4">
          Donor: {listing.donor.name}
        </h3>
        <p className="text-md">
          <span className="font-semibold text-gray-800">Location:</span>{' '}
          {listing.location}
        </p>
        <p className="text-md">
          <span className="font-semibold text-gray-800">Pickup Time:</span>{' '}
          {listing.pickupTimeRange}
        </p>
        <h4 className="text-md font-semibold mt-4">Items:</h4>
        <ul className="list-disc ml-5">
          {listing.listingItems.map((item) => (
            <li key={item.listingItemId}>
              {item.item.itemName} ({item.quantity} units, Expiry:{' '}
              {item.expirationDate || 'N/A'})
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
    recipientId: '', 
  });

  const [listings, setListings] = useState([]);
  const [loading, setLoading] = useState(false);
  const [userInfo, setUserInfo] = useState({});

  const fetchListings = async (filters, userInfo) => {
    setLoading(true);

    const postApiUrl = 'http://localhost:8080/listings/search';

    try {
      
      const requestPayload = {
        recipientId: filters.recipientId?.trim() || null,
        foodType: filters.foodType.toUpperCase(),
        quantityNeeded: filters.quantityNeeded
          ? parseInt(filters.quantityNeeded, 10)
          : null,
        expiryDate: filters.expiryDate?.trim() || null,
        pickupTimeStart: filters.pickupTimeStart?.trim() || null,
        pickupTimeEnd: filters.pickupTimeEnd?.trim() || null,
        distance:
          filters.distance ? parseInt(filters.distance, 10) * 1000 : null,
      };

      if (requestPayload.distance !== null) {
        requestPayload.userLatitude = userInfo.latitude || null;
        requestPayload.userLongitude = userInfo.longitude || null;
      }

      console.log('Request Payload:', requestPayload); // Debugging log to verify payload


      const response = await axios.post(postApiUrl, requestPayload);
      console.log('API Response:', response.data); // Debugging log to verify response


      const groupedListings = Object.values(
        response.data.reduce((acc, listing) => {
          if (!acc[listing.listingId]) {
            acc[listing.listingId] = {
              ...listing,
              listingItems: [],
            };
          }

          const uniqueItemsMap = new Map(
            acc[listing.listingId].listingItems.concat(listing.listingItems).map((item) => [
              item.listingItemId,
              item,
            ])
          );
          acc[listing.listingId].listingItems = Array.from(uniqueItemsMap.values());

          return acc;
        }, {})
      );

      setListings(groupedListings);
    } catch (error) {
      console.error('Error fetching listings:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const userId = localStorage.getItem('recipientId'); 
    const apiUrl = `http://localhost:8080/users/${userId}`;

    const fetchUserInfo = async () => {
      setLoading(true);
      try {
        const response = await axios.get(apiUrl);
        console.log('User Info:', response.data); 
        setUserInfo(response.data);


        setFilters((prevState) => ({
          ...prevState,
          location: response.data.location,
        }));
      } catch (err) {
        console.error('Error fetching user data:', err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
    fetchListings(newFilters, userInfo);
  };


  const recommendedListings = listings.filter(
    (listing) => listing.priority === 1
  );
  const otherListings = listings.filter((listing) => listing.priority !== 1);

  return (
    <div className="flex h-screen overflow-hidden">
      {/* Sidebar */}
      <div className="w-1/3 h-full bg-gray-20 p-4 overflow-y-hidden">
        {/* Filters */}
        <Filter onApplyFilters={handleApplyFilters} location={filters.location} />
      </div>

      {/* Main Content */}
      <div className="w-2/3 p-4 bg-gray-20 overflow-y-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-4 text-center">
          All Listings
        </h2>

        {loading ? (
          <div className="text-center text-xl text-gray-600">Loading...</div>
        ) : (
          <div className="grid grid-cols-1 gap-6">
            {/* Recommended Listings */}
            {recommendedListings.length > 0 && (
              <>
                <h3 className="text-lg font-bold text-blue-800">
                  Recommended Listings
                </h3>
                {recommendedListings.map((listing) => (
                  <Card key={listing.listingId} listing={listing} />
                ))}
              </>
            )}

            {/* Other Listings */}
            {otherListings.length > 0 && (
              <>
                <h3 className="text-lg font-bold text-gray-800 mt-6">
                  Other Listings
                </h3>
                {otherListings.map((listing) => (
                  <Card key={listing.listingId} listing={listing} />
                ))}
              </>
            )}

            {/* No Results */}
            {listings.length === 0 && (
              <div className="text-center text-xl text-gray-600">
                No results found
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default ViewListings;