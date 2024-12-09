import React, { useState, useEffect } from 'react';

const FilterComponent = ({ onApplyFilters, location }) => {
  const [filters, setFilters] = useState({
    foodType: '',
    quantityNeeded: '',
    expiryDate: '',
    pickupTimeStart: '',
    pickupTimeEnd: '',
    location: location || '',
    distance: '',
    recipientId: '', 
  });

  useEffect(() => {
    const recipientId = localStorage.getItem('recipientId') || '';
    setFilters((prevState) => ({
      ...prevState,
      recipientId,
      location: location || '',
    }));
  }, [location]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Applied Filters:', filters);
    onApplyFilters(filters); 
  };

  return (
    <form onSubmit={handleSubmit} className="bg-white p-6 shadow-lg rounded-lg max-w-md mx-auto">
      <div className="space-y-3">
        {/* Food Type */}
        <div>
          <label className="block text-gray-700 font-semibold">Food Type</label>
          <select
            name="foodType"
            value={filters.foodType}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          >
            <option value="">Select Food Type</option>
            <option value="baby food">Baby Food</option>
            <option value="beverages">Beverages</option>
            <option value="snacks">Snacks</option>
            <option value="canned_goods">Canned Goods</option>
            <option value="pantry">Pantry</option>
            <option value="frozen">Frozen</option>
            <option value="meat">Meat</option>
            <option value="grains">Grains</option>
            <option value="dairy">Dairy</option>
            <option value="bakery">Bakery</option>
            <option value="produce">Produce</option>
          </select>
        </div>

        {/* Quantity Needed */}
        <div>
          <label className="block text-gray-700 font-semibold">Quantity Needed</label>
          <input
            type="number"
            name="quantityNeeded"
            value={filters.quantityNeeded}
            onChange={handleChange}
            placeholder="Enter quantity"
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        {/* Expiry Date */}
        <div>
          <label className="block text-gray-700 font-semibold">Expiry Date</label>
          <input
            type="date"
            name="expiryDate"
            value={filters.expiryDate}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        {/* Pickup Time Start */}
        <div>
          <label className="block text-gray-700 font-semibold">Pickup Time Start</label>
          <input
            type="time"
            name="pickupTimeStart"
            value={filters.pickupTimeStart}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        {/* Pickup Time End */}
        <div>
          <label className="block text-gray-700 font-semibold">Pickup Time End</label>
          <input
            type="time"
            name="pickupTimeEnd"
            value={filters.pickupTimeEnd}
            onChange={handleChange}
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        {/* Distance */}
        <div>
          <label className="block text-gray-700 font-semibold">Distance (in km)</label>
          <input
            type="number"
            name="distance"
            value={filters.distance}
            onChange={handleChange}
            placeholder="Enter distance in kilometers"
            className="w-full p-2 mt-1 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        {/* Apply Filters Button */}
        <button
          type="submit"
          className="w-full bg-emerald-600 text-white font-semibold py-2 rounded-md mt-4 hover:bg-emerald-500 transition"
        >
          Apply Filters
        </button>
      </div>
    </form>
  );
};

export default FilterComponent;