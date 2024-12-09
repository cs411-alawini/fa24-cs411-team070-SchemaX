// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import ViewListings from './components/ViewListings';
import ListingDetails from './components/ListingDetails';
import BookingConfirmation from './components/BookingConfirmation';
import RecipientDashboard from './components/RecipientDashboard';
import DonorDashboard from './components/DonorDashboard';
import BookingDetails from './components/BookingDetails';
import CreateListing  from './components/CreateListing';
import CreateItem from './components/CreateItem';
import ViewListingDetails from './components/ViewListingDetails';
import Registration from './components/Registration';

function App() {
  return (
    <Router>
      <Routes>
      <Route path="/" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Registration />} />
        <Route path="/users/:recipientId/recipient_dashboard" element={<RecipientDashboard />} />
        <Route path="/listings" element={<ViewListings />} />
        <Route path="/listing/:id" element={<ListingDetails />} />
        <Route path="/confirmation" element={<BookingConfirmation />} />
        <Route path="/users/:donorId/donor_dashboard" element={<DonorDashboard />} />
        <Route path="/users/:donorId/create_listing" element={<CreateListing />} />
        <Route path="/create_item" element={<CreateItem />} />
        <Route path="/users/:donorId/listing_details/:id" element={<ViewListingDetails />} />
        <Route path="/users/:donorId/booking_details/:id" element={<BookingDetails />} />
      </Routes>
    </Router>
  );
}

export default App;