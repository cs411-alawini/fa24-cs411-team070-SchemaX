import React, { useState, useEffect } from 'react';
import Filter from './Filter';
import axios from 'axios';


const Card = ({ listing }) => {
  return (
    <div className="bg-gray-100 p-6 rounded-lg shadow-md transform transition duration-300 ease-in-out hover:-translate-y-2 hover:shadow-lg hover:bg-blue-100">
      <h3 className="text-lg font-bold text-gray-800 mb-4">Donor: {listing.donorName}</h3>
      <div className="grid grid-cols-2 gap-y-2 gap-x-6 text-gray-600">
        <p className="text-md"><span className="font-semibold text-gray-800">Listing ID:</span> {listing.listingId}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Item ID:</span> {listing.itemId}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Item Name:</span> {listing.itemName}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Category:</span> {listing.category}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Quantity:</span> {listing.quantity}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Expiration Date:</span> {listing.expirationDate}</p>
        <p className="text-md"><span className="font-semibold text-gray-800">Donor ID:</span> {listing.donorId}</p>
      </div>
    </div>
  );
};

function ViewListings() {
  const [filters, setFilters] = useState({
    foodType: '' ,
    quantityNeeded: '',
    expiryDate: '',
    pickupTimeStart: '',
    pickupTimeEnd: '',
    location: '',
    distance: ''
  });

  const [listings, setListings] = useState([]);  // Store the filtered listings
  const [loading, setLoading] = useState(false);  // Loading state to show a loading spinner
  const [userInfo, setUserInfo] = useState([]);


  // uncomment the below code to fetch listings from the API
  const fetchListings = async (filters, userInfo) => {
    setLoading(true);

    const postApiUrl = `http://localhost:8080/listings/search`
    
    try {
      const requestPayload = {
        foodType:  filters.foodType.toUpperCase(),
        quantityNeeded: filters.quantityNeeded ? parseInt(filters.quantityNeeded, 10) : null,
        expiryDate: filters.expiryDate?.trim() || null,
        pickupTimeStart: filters.pickupTimeStart?.trim() || null,
        pickupTimeEnd: filters.pickupTimeEnd?.trim() || null,
        location: filters.location?.trim() || null,
        distance: filters.distance ? parseInt(filters.distance, 10) : null,
        latitude: userInfo.latitude || null,
        longitude: userInfo.longitude || null
      };
  
      // console.log('payload:',requestPayload);
      const response = await axios.post(postApiUrl, requestPayload);
      setListings(response.data);  // Set the data from the API response
      // console.log('Fetched Listings [yes]:', response.data);  // Log the fetched data
    } catch (error) {
      console.error('Error fetching listings:', error);
    } finally {
      setLoading(false);  // Stop loading when fetch is complete
    }
};

  useEffect(() => {
    const userId = 5; // Hardcoded userId
    const apiUrl = `http://localhost:8080/users/${userId}`;

    const fetchUserInfo = async () => {
      setLoading(true)
      try {
        const response = await axios.get(apiUrl);
        // console.log('Fetched Data:', response.data); // Print data in console
        setUserInfo(response.data);
        setFilters(prevState => ({
          ...prevState,
          location: response.data.location  // Autofill location field with user data

        }));

      } catch (err) {
        console.error('Error fetching user data:', err.message); // Log error in console
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, []);

  
  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
    fetchListings(newFilters,userInfo);  // Fetch listings with applied filters
  };

  // return (
  //   <div className="flex min-h-screen overflow-hidden">
  //     {/* Left side: Filter form (non-scrollable) */}
  //     <div className="w-1/3 h-screen bg-gray-50 p-4 overflow-hidden">
  //       <Filter onApplyFilters={handleApplyFilters} location={filters.location} />
  //     </div>

  //     {/* Right side: Results display (scrollable) */}
  //     <div className="w-2/3 p-4 bg-gray-100 overflow-y-auto">
  //       <h2 className="text-2xl font-bold text-gray-800 mb-4">All Listings</h2>

  //       {loading ? (
  //         <div className="text-center text-xl text-gray-600">Loading...</div>
  //       ) : (
  //         <div className="grid grid-cols-1 gap-6">
  //           {/* Iterate over listings and display each one as a Card */}
  //           {listings.length > 0 ? (
  //             listings.map((listing, index) => (
  //               <Card key={index} listing={listing} />
  //             ))
  //           ) : (
  //             <div className="text-center text-xl text-gray-600">No results found</div>
  //           )}
  //         </div>
  //       )}
  //     </div>
  //   </div>
  // );
  return (
    <div className="flex h-screen overflow-hidden">
      {/* Left side: Filter form (sticky) */}
      <div className="w-1/3 h-full bg-gray-50 p-4 overflow-y-hidden">
          <Filter onApplyFilters={handleApplyFilters} location={filters.location} />
      </div>

      {/* Right side: Results display (scrollable) */}
      <div className="w-2/3 p-4 bg-gray-100 overflow-y-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">All Listings</h2>

        {loading ? (
          <div className="text-center text-xl text-gray-600">Loading...</div>
        ) : (
          <div className="grid grid-cols-1 gap-6">
            {listings.length > 0 ? (
              listings.map((listing, index) => (
                <Card key={index} listing={listing} />
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
