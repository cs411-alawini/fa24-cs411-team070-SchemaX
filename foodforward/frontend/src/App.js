// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import ViewListings from './components/ViewListings';
import ListingDetails from './components/ListingDetails';
import BookingConfirmation from './components/BookingConfirmation';

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path="/" element={<Login />} /> */}
        <Route path="/" element={<ViewListings />} />
        <Route path="/listing/:id" element={<ListingDetails />} />
        <Route path="/confirmation" element={<BookingConfirmation />} />
      </Routes>
    </Router>
  );
}

export default App;