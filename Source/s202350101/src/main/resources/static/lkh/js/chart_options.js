 // 옵션 정의
 const  doughnut_options = {
    plugins: {
      title: {
        display: true,
        text: '작업 현황도 '
      },
        legend: {// display: false // 범례 삭제
         },
        tooltip: {// enabled: false // 툴팁 비활성화
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


const horizontalStackBarOption = {
  plugins: {
    title: {
      display: true,
      text: '멤버별 작업 현황도'
    }
  },
  scales: {
    x: {
      beginAtZero: true,
      stacked: true,
        ticks: {
            stepSize: 1, // Set step size to 1 for natural numbers
            beginAtZero: true, // Start the axis at zero
        }
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

const proejctOption = {
    plugins: {
      title: {
        display: true,
        text: '언제 '
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

  function createDrawChart(ctx, chart_type ,data, options) {
    return new Chart(ctx, {
      type: chart_type,
      data: data,
      options: options
    });
  };
