// PredictionGraph.js

import React, { useState, useEffect } from 'react';
import { Line } from 'react-chartjs-2';
import axios from 'axios';

const Historical_Accuracy_Graph = () => {
  const [chartData, setChartData] = useState({
    labels: [], // X-axis labels (bookings)
    datasets: [
      {
        label: 'Prediction Accuracy',
        data: [], // Y-axis data (prediction accuracy)
        fill: false,
        borderColor: 'rgb(75, 192, 192)',
        tension: 0.1,
      },
    ],
  });

  useEffect(() => {
    // Fetch data from the backend (adjust the API endpoint accordingly)
    axios.get('http://localhost:8080/api/admin')
      .then(response => {
        const { bookings, Historical_Accuracy_Graph } = response.data;

        // Update chart data
        setChartData({
          labels: bookings,
          datasets: [
            {
              label: 'Historical_Accuracy_Graph',
              data: Historical_Accuracy_Graph,
              fill: false,
              borderColor: 'rgb(75, 192, 192)',
              tension: 0.1,
            },
          ],
        });
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, []); // Run only once on component mount

  return (
    <div>
      <Line data={chartData} />
    </div>
  );
};

export default Historical_Accuracy_Graph;

