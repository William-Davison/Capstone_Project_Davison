import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import Chart from 'chart.js/auto';

const HomePage = () => {
  const [loading, setLoading] = useState(true);
  const [predictionData, setPredictionData] = useState([]);
  const [bookingData, setBookingData] = useState([]);
  const [seasonalData, setSeasonalData] = useState([]);
  const [selectedThreshold, setSelectedThreshold] = useState(0); // Default threshold value
  const [modelResult, setModelResult] = useState([]);
  const [thresholdMessage, setThresholdMessage] = useState('');

  useEffect(() => {
    fetchData();
    fetchModelData();
  }, []); // Fetch data on initial render

  useEffect(() => {
    if (seasonalData.length > 0) {
      renderScatterChart();
      renderBarChart();
      renderSeasonalChart();
    }
  }, [selectedThreshold, seasonalData]); // Update charts when selectedThreshold or seasonalData changes

  const scatterChartContainer = useRef(null);
  const barChartContainer = useRef(null);
  const seasonalChartContainer = useRef(null);

  const fetchData = async () => {
    try {
      const [predictionResponse, seasonalResponse] = await Promise.all([
        fetch('/api/prediction-accuracy-per-threshold'),
        fetch('/api/seasonal-prediction-accuracy')
      ]);

      const [predictionData, bookingData] = await predictionResponse.json();
      const seasonalData = await seasonalResponse.json();

      setPredictionData(predictionData);
      setBookingData(bookingData);
      setSeasonalData(seasonalData);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching data:', error);
      setLoading(false);
    }
  };

  const fetchModelData = async () => {
    try {
      const modelResponse = await fetch('/api/model-data');
      const modelResult = await modelResponse.json();
      setModelResult(modelResult);
    } catch (error) {
      console.error('Error fetching model data:', error);
    }
  };

  const handleThresholdChange = async (event) => {
    const selectedIndex = parseInt(event.target.value);
    setSelectedThreshold(selectedIndex);

    try {
      const response = await fetch('/api/set-selected-threshold', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ threshold: (selectedIndex + 10) / 100 }), // Convert index to threshold value (e.g., 0.1 for index 0)
      });
      if (!response.ok) {
        throw new Error('Failed to set selected threshold');
      }
      const message = await response.text();
      setThresholdMessage(message);
    } catch (error) {
      console.error('Error setting selected threshold:', error);
    }
  };

  const renderScatterChart = () => {
    const ctx = scatterChartContainer.current.getContext('2d');
    if (scatterChartContainer.current.chart) {
      scatterChartContainer.current.chart.destroy();
    }
    const dataLength = predictionData.length;
    scatterChartContainer.current.chart = new Chart(ctx, {
      type: 'scatter',
      data: {
        labels: Array.from({ length: dataLength }, (_, i) => (i + 10).toString() + '%'),
        datasets: [
          {
            label: 'Prediction Accuracy',
            data: predictionData.map((value, index) => ({ x: (index + 10) / 100, y: value })),
            borderColor: 'rgba(75,192,192,1)',
            backgroundColor: 'rgba(75,192,192,0.2)',
            pointBackgroundColor: 'rgba(75,192,192,1)',
            pointBorderColor: '#fff',
          },
          {
            label: 'Percentage of Bookings within Threshold',
            data: bookingData.map((value, index) => ({ x: (index + 10) / 100, y: value })),
            borderColor: 'rgba(255,99,132,1)',
            backgroundColor: 'rgba(255,99,132,0.2)',
            pointBackgroundColor: 'rgba(255,99,132,1)',
            pointBorderColor: '#fff',
          },
        ],
      },
      options: {
        aspectRatio: 1,
        scales: {
          x: {
            title: {
              display: true,
              text: 'Threshold (%)',
              font: { weight: 'bold' },
            },
            min: 0.1,
            max: 0.9,
            ticks: { callback: (value) => (value * 100) + '%' },
          },
          y: {
            title: {
              display: true,
              text: 'Percentage',
              font: { weight: 'bold' },
            },
            min: 0.01,
            max: 1,
            ticks: { callback: (value) => (value * 100) + '%' },
          },
        },
      },
    });
  };

  const renderBarChart = () => {
    const ctx = barChartContainer.current.getContext('2d');
    if (barChartContainer.current.chart) {
      barChartContainer.current.chart.destroy();
    }
    const maxDataValue = Math.max(...modelResult.slice(0, 7));
    const data = {
      labels: ['Guest', 'Weekend Nights', 'Week Nights', 'Reserved Room Type', 'Meal', 'Arrival Date Month', 'Deposit Type'],
      datasets: [{
        label: 'Weighted Average',
        data: modelResult.slice(0, 7),
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }]
    };
    barChartContainer.current.chart = new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        scales: {
          y: {
            beginAtZero: true,
            suggestedMin: 0,
            suggestedMax: Math.ceil(maxDataValue * 1.1),
            ticks: { callback: (value) => (value * 100) + '%' },
            title: { display: true, text: 'Weighted Average', font: { weight: 'bold' } }
          },
          x: {
            title: { display: true, text: 'Feature', font: { weight: 'bold' } }
          }
        }
      }
    });
  };

  const renderSeasonalChart = () => {
    const ctx = seasonalChartContainer.current.getContext('2d');
    if (seasonalChartContainer.current.chart) {
      seasonalChartContainer.current.chart.destroy();
    }
    seasonalChartContainer.current.chart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [
          {
            label: `Seasonal Prediction Accuracy for ${selectedThreshold + 10}% Threshold`,
            data: seasonalData[selectedThreshold],
            backgroundColor: 'rgba(255, 159, 64, 0.2)',
            borderColor: 'rgba(255, 159, 64, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            ticks: { callback: (value) => (value * 100) + '%' },
            title: { display: true, text: 'Accuracy', font: { weight: 'bold' } }
          },
          x: {
            title: { display: true, text: 'Month', font: { weight: 'bold' } }
          }
        }
      }
    });
  };

  return (
    <div style={{ fontFamily: 'Broadway' }}>
      <header>
        <h1>William's Booking Predictor</h1>
      </header>
      <main>
        <p>Welcome to my project. This page shows interactive visualization of the model. You can change the threshold prediction percentage below - Default is 50%. This threshold is used by the model to determine if a hotel booking is likely to be a cancellation or not. Go to the booking page below to get your booking prediction! </p>
        <Link to="/booking">CLICK HERE TO MAKE A BOOKING</Link>

        <div>
          <h2>Scatter Plot</h2>
          <button onClick={fetchData} disabled={loading}>
            {loading ? 'Loading...' : 'Fetch Data'}
          </button>
          <br />
          <canvas ref={scatterChartContainer} width="400" height="400"></canvas>
        </div>

        <div>
          <label htmlFor="thresholdSelect">Choose a Threshold:</label>
          <select id="thresholdSelect" value={selectedThreshold} onChange={handleThresholdChange}>
            {Array.from({ length: 82 }, (_, i) => (
              <option key={i} value={i === 0 ? '' : i - 1}>{`${i === 0 ? 'Select' : i + 9}%`}</option>
            ))}
          </select>
        </div>

        {thresholdMessage && (
          <div>
            <p>{thresholdMessage}</p>
          </div>
        )}

        {predictionData.length > 0 && bookingData.length > 0 && (
          <div>
            <p>Prediction Accuracy: {(predictionData[selectedThreshold] * 100).toFixed(2)}%</p>
            <p>Percentage of Bookings within Threshold: {(bookingData[selectedThreshold] * 100).toFixed(2)}%</p>
          </div>
        )}

        <div>
          <h2>Seasonal Prediction Accuracy</h2>
          <canvas ref={seasonalChartContainer} width="400" height="400"></canvas>
        </div>

        {modelResult.length > 0 && (
          <div>
            <h2>Model Data</h2>
            <canvas ref={barChartContainer} width="400" height="400"></canvas>
          </div>
        )}

      </main>
      <footer>
        <p>© 2024 My Website</p>
      </footer>
    </div>
  );
}

export default HomePage;
