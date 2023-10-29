

function createDrawChart(ctx, chart_type ,data, options) {
  return new Chart(ctx, {
    type: chart_type,
    data: data,
    options: options
  });
}



document.addEventListener('DOMContentLoaded', function() {
  const doughunt = document.getElementById('doughnut_1').getContext('2d');
//  const bar = document.getElementById('doughnut_1').getContext('2d');
  const doughnut_data = {
      labels: ['예정', '진행중', '완료됨'],
      datasets: [{
          data: data,
          backgroundColor: ['orange', 'i', 'yellow'], // 데이터 포인트의 색상
          borderWidth: 1
      }]
  };

  // 옵션 정의
  const  doughnut_options = {
    plugins: {
      title: {
        display: true,
        text: 'Chart.js Bar Chart - Stacked'
      },

        legend: {
            // display: false // 범례 삭제
        },
        tooltip: {
            // enabled: false // 툴팁 비활성화
        },
        datalabels: {
            display: true, // 데이터 레이블 표시
            color: 'white', // 데이터 레이블의 텍스트 색상
            font: {
                weight: 'bold'
            },
            formatter: (value, context) => {
              return context.chart.data.labels[context.dataIndex] + ': ' + value;
          },
            // anchor: 'end', // 데이터 레이블 위치 설정
            // align: 'end' // 데이터 레이블 위치 설정
        }
    }
};


const horizontalStackBarData = {
  labels: ['이광현', '강준우', '문경훈', '차예지', '이진희', '조미혜', '황인정'],
  datasets: [
    {
      label: '진행전',
      data: [2, 2, 3, 2, 2, 3, 2],
      backgroundColor: 'red',
    },
    {
      label: '진행중',
      data: [2, 2, 3, 2, 2, 3, 2],
      backgroundColor: 'blue',
    },
    {
      label: '완료',
      data: [2, 2, 3, 2, 2, 3, 2],
      backgroundColor: 'orange',
    },
  ]
}


  
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
}





  createDrawChart(doughunt, 'doughnut',doughnut_data, doughnut_options);
  createDrawChart(bar_chart,'bar', horizontalStackBarData, horizontalStackBarOption);


});
