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

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/listings" element={<ViewListings />} />
        <Route path="/listing/:id" element={<ListingDetails />} />
        <Route path="/confirmation" element={<BookingConfirmation />} />
        <Route path="/recipient_dashboard" element={<RecipientDashboard />} />
        <Route path="/donor_dashboard" element={<DonorDashboard />} />
        <Route path="/booking_details/:id" element={<BookingDetails />} />
      </Routes>
    </Router>
  );
}

export default App;