// HotelBooking.js
import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import image from './marten-bjork-n_IKQDCyrG0-unsplash.jpg';
import image2 from './wilhelm-gunkel-L04Kczg_Jvs-unsplash.jpg';
import backgroundImage from './keith-misner-h0Vxgz5tyXA-unsplash.jpg';

const HotelBooking = () => {
  const [guestInfo, setGuestInfo] = useState({
    firstName: '',
    lastName: '',
    mobileNumber: '',
    emailAddress: ''
  });

  const [bookingInfo, setBookingInfo] = useState({
    numberOfGuests: '',
    checkInDate: '',
    checkOutDate: '',
    roomType: '',
    mealOption: '',
    isRefundable: false
  });

  const handleInputChange = (event, setState, field) => {
    const value = event.target.value;
    setState(prevState => ({
      ...prevState,
      [field]: field === 'roomType' ? value : value || '',
    }));
  };

  const saveBooking = async () => {
    try {
      const defaultRoomType = bookingInfo.roomType || 'Standard';
      const defaultMealOption = bookingInfo.mealOption || 'Full_Board';
      const defaultDepositOption = bookingInfo.depositOption || 'No_Deposit';

      const updatedBookingInfo = {
        ...bookingInfo,
        roomType: defaultRoomType,
        mealOption: defaultMealOption,
        depositOption: defaultDepositOption,
      };

      const response = await axios.post('http://localhost:8080/api/hotelbooking', { guestInfo, bookingInfo: updatedBookingInfo });

      if (response.status === 200) {
        alert(response.data);
      } else {
        alert(`Error: ${response.status}`);
      }
    } catch (error) {
      alert(`Error: ${error}`);
    }
  };

  return (
    <div style={{
      fontFamily: 'Cursive',
      fontSize: 30,
      color: 'Beige',
      backgroundImage: `url(${backgroundImage}`,
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat',
      backgroundAttachment: 'fixed',
    }}>

      <Link to="/">Go back to Homepage</Link>

      <img src={image} alt="Guest Image" style={{ width: '3000px', height: '500px' }} />
      <h2>Main Guest Information</h2>
      <label> First Name </label>
      <input type="text" onChange={event => handleInputChange(event, setGuestInfo, 'firstName')} />
      <label> Last Name </label>
      <input type="text" onChange={event => handleInputChange(event, setGuestInfo, 'lastName')} />
      <label> Mobile Number </label>
      <input type="text" onChange={event => handleInputChange(event, setGuestInfo, 'mobileNumber')} />
      <label> Email Address </label>
      <input type="email" onChange={event => handleInputChange(event, setGuestInfo, 'emailAddress')} />

      <h2>Booking Information</h2>
      <label>Number of Guests</label>
      <input type="number" onChange={event => handleInputChange(event, setBookingInfo, 'numberOfGuests')} />
      <label>Check In Date</label>
      <input type="date" onChange={event => handleInputChange(event, setBookingInfo, 'checkInDate')} />
      <label>Check Out Date</label>
      <input type="date" onChange={event => handleInputChange(event, setBookingInfo, 'checkOutDate')} />
      <label>Room Type</label>
      <select onChange={event => handleInputChange(event, setBookingInfo, 'roomType')}>
        <option value="Regular">Regular</option>
        <option value="Deluxe">Deluxe</option>
        <option value="Suite">Suite</option>
      </select>
      <label> Meal Option </label>
      <select onChange={event => handleInputChange(event, setBookingInfo, 'mealOption')}>
        <option value="Full_Board">Full Board</option>
        <option value="Half_Board">Half Board</option>
        <option value="Bed_And_Breakfast">Bed and Breakfast</option>
        <option value="Self_Catering">Self_Catering</option>
      </select>
      <label> Deposit Option</label>
      <select onChange={event => handleInputChange(event, setBookingInfo, 'depositOption')}>
        <option value="No_Deposit">No Deposit</option>
        <option value="Refundable">Refundable Deposit - save 20%!</option>
        <option value="Nonrefundable">Nonrefundable Deposit - save 50%!</option>
      </select>
      <label>  Will you cancel? </label>
      <select onChange={event => handleInputChange(event, setBookingInfo, 'Will you cancel?')}>
        <option value="0">No</option>
        <option value="1">Yes</option>
      </select>

      <button onClick={saveBooking}>Save Booking</button>
      <img src={image2} alt="Guest Image" style={{ width: '300px', height: '300px' }} />
    </div>
  );
};

export default HotelBooking;

