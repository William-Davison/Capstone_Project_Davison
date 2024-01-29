import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import HomePage from './HomePage';
import BookingPage from './BookingPage'; // Import your booking page component

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/booking" element={<BookingPage />} /> {/* Add this line */}
        {/* Add more routes as needed */}
      </Routes>
    </Router>
  );
}

export default App;