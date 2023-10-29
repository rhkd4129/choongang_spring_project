const horizontalStackBarOption = {
  plugins: {
    title: {
      display: true,
      text: 'Chart.js Bar Chart - Stacked'
    }
  },
  scales: {
    x: {
      beginAtZero: true,
      stacked: true
    },
    y: {
      stacked: true
    }
  },
  indexAxis: 'y',
  datalabels: {
    display: true,
    color: 'white',
    font: { weight: 'bold' }
  }
};
  
  
const doughnut_options = {
    plugins: {
      title: {
        display: true,
        text: 'Chart.js Doughnut Chart'
      },
      legend: {
        display: true
      },
      tooltip: {
        enabled: true
      },
      datalabels: {
        display: true,
        color: 'white',
        font: {
          weight: 'bold'
        },
        formatter: (value, context) => {
          return context.chart.data.labels[context.dataIndex] + ': ' + value;
        }
      }
    }
  };

  
function createDrawChart(ctx, chart_type, data, options) {
    return new Chart(ctx, {
      type: chart_type,
      data: data,
      options: options
    });
  }
  