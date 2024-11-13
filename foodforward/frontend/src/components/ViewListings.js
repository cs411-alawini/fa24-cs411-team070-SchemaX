import React, { useState, useEffect } from 'react';
import Filter from './Filter';

const Card = ({ listing }) => {
  return (
    <div className="bg-white p-6 rounded-md shadow-lg">
      <h3 className="text-xl font-semibold text-gray-800">{listing.foodType}</h3>
      <p><strong>Quantity:</strong> {listing.quantity}</p>
      <p><strong>Expiry Date:</strong> {listing.expiryDate}</p>
      <p><strong>Pickup Time:</strong> {listing.pickupTimeStart} - {listing.pickupTimeEnd}</p>
      <p><strong>Location:</strong> {listing.location}</p>
      <p><strong>Distance:</strong> {listing.distance} km</p>
    </div>
  );
};

function ViewListings() {
  const [filters, setFilters] = useState({
    foodType: '',
    quantity: '',
    expiryDate: '',
    pickupTimeStart: '',
    pickupTimeEnd: '',
    location: '',
    distance: ''
  });

  const [listings, setListings] = useState([]);  // Store the filtered listings
  const [loading, setLoading] = useState(false);  // Loading state to show a loading spinner

  // Sample list of JSON objects (mock data)
  const mockListings = [
    {
      foodType: 'fruit',
      quantity: '33',
      expiryDate: '2024-11-06',
      pickupTimeStart: '15:47',
      pickupTimeEnd: '14:48',
      location: 'pune',
      distance: "33"
    },
    {
      foodType: 'vegetable',
      quantity: '10',
      expiryDate: '2024-11-15',
      pickupTimeStart: '09:00',
      pickupTimeEnd: '11:00',
      location: 'Los Angeles',
      distance: "50"
    },
    {
      foodType: 'dairy',
      quantity: '7',
      expiryDate: '2024-11-18',
      pickupTimeStart: '08:00',
      pickupTimeEnd: '10:00',
      location: 'Chicago',
      distance: "30"
    },
    {
      foodType: 'fruit',
      quantity: '3',
      expiryDate: '2024-11-25',
      pickupTimeStart: '11:00',
      pickupTimeEnd: '13:00',
      location: 'San Francisco',
      distance: "20"
    }
  ];
  
  
  // Function to fetch the listings based on the filters
  const fetchListings = async (filters) => {
    setLoading(true);  // Set loading to true before starting fetch
    console.log("Filters applied:", filters);  // Debug log to check filter values

    try {
      // Simulate API call by filtering mock data
      const filteredListings = mockListings.filter(listing => {
        return (
          (filters.foodType ? listing.foodType === filters.foodType : true) ||
          (filters.quantity ? listing.quantity === filters.quantity : true) ||
          (filters.expiryDate ? listing.expiryDate === filters.expiryDate : true) ||
          (filters.location ? listing.location.includes(filters.location) : true) ||
          (filters.distance ? listing.distance === filters.distance : true)
        );
      });

      // Log the filtered results for debugging
      console.log("Filtered listings:", filteredListings);

      // Set the filtered listings after "API" call
      setListings(filteredListings);
    } catch (error) {
      console.error('Error fetching listings:', error);
    } finally {
      setLoading(false);  // Stop loading when fetch is complete
    }
  };

  // uncomment the below code to fetch listings from the API
  // const fetchListings = async (filters) => {
  //   setLoading(true);  // Set loading to true before starting fetch
  //   try {
  //     const response = await fetch('/api/listings', {
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //       },
  //       body: JSON.stringify(filters),  // Send the filters in the request body
  //     });
  
  //     // Check if the response is okay (status 200)
  //     if (!response.ok) {
  //       throw new Error('Failed to fetch listings');
  //     }
  
  //     const data = await response.json();  // Parse the JSON data from the response
  //     setListings(data);  // Set the data from the API response
  
  //   } catch (error) {
  //     console.error('Error fetching listings:', error);
  //   } finally {
  //     setLoading(false);  // Stop loading when fetch is complete
  //   }
  // };

  
  const handleApplyFilters = (newFilters) => {
    setFilters(newFilters);
    fetchListings(newFilters);  // Fetch listings with applied filters
  };

  return (
    <div className="flex min-h-screen overflow-hidden">
      {/* Left side: Filter form (non-scrollable) */}
      <div className="w-1/3 h-screen bg-gray-50 p-4 overflow-hidden">
        <Filter onApplyFilters={handleApplyFilters} />
      </div>

      {/* Right side: Results display (scrollable) */}
      <div className="w-2/3 p-4 bg-gray-100 overflow-y-auto">
        <h2 className="text-2xl font-bold text-gray-800 mb-4">All Listings</h2>

        {loading ? (
          <div className="text-center text-xl text-gray-600">Loading...</div>
        ) : (
          <div className="grid grid-cols-1 gap-6">
            {/* Iterate over listings and display each one as a Card */}
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
