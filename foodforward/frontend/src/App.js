// src/App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login';
import ViewListings from './components/ViewListings';
import ListingDetails from './components/ListingDetails';

function App() {
  return (
    <Router>
      <Routes>
        {/* <Route path="/" element={<Login />} /> */}
        <Route path="/" element={<ViewListings />} />
        <Route path="/listing/:id" element={<ListingDetails />} />
      </Routes>
    </Router>
  );
}

export default App;