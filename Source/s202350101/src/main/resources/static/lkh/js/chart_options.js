 // 옵션 정의
 const  doughnut_options = {
    plugins: {
      title: {
        display: true,
        text: '작업 현황도'
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
            stepSize: 1,
            beginAtZero: true,
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
        text: '기간 '
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

  function loadBarChart() {
	//그래프 가져오기
	$.ajax({
	    url			: "/workload_chart",
	    dataType	: 'json',
	    success		: function (workload) {
	        const barCtx = document.getElementById('bar_chart').getContext('2d');
			
	        var labels = []
	        var stats_0 = []
	        var stats_1 = []
	        var stats_2 = []
	        for (let i = 0; i < workload.length; i++) {
	            labels.push(workload[i].user_name);
	            stats_0.push(workload[i].status_0_count);
	            stats_1.push(workload[i].status_1_count);
	            stats_2.push(workload[i].status_2_count);
	        }
	        const horizontalStackBarData = {
	            labels: labels,
	            datasets: [
	                {
	                    label: '예정된 작업',
	                    data: stats_0,
	                    backgroundColor: 'rgba(255, 99, 132, 0.5)',  // 빨간색 배경
	                    borderColor: 'rgb(255, 99, 132)',  // 빨간색 테두리
	                },
	                {
	                    label: '진행중인 작업',
	                    data: stats_1,
	                    backgroundColor: 'rgba(75, 192, 192, 0.5)',  // 녹색 배경
	                    borderColor: 'rgb(75, 192, 192)',  // 녹색 테두리
	                },
	                {
	                    label: '완료된 작업',
	                    data: stats_2,
	                    backgroundColor: 'rgba(153, 102, 255, 0.5)',  // 보라색 배경
	                    borderColor: 'rgb(153, 102, 255)',  // 보라색 테두리
	                },
	            ]
	        };
	        createDrawChart(barCtx, 'bar', horizontalStackBarData, horizontalStackBarOption);
	    }
	});
  }
  
  function loadProjectDay() {
	$.ajax({
        url: "/project_day",
        dataType: 'json',
        success: function (prjInfo) {
            // Oracle에서 가져온 문자열을 JavaScript Date 객체로 변환
            const projectStartDate = new Date(prjInfo.project_startdate);
            const projectEndDate = new Date(prjInfo.project_enddate);
            var a = $("#project_time");
            a.text(prjInfo.project_startdate+'~'+prjInfo.project_enddate);

            const currentDate = new Date();                                     /// 현재 날짜를 가져오기
            const timeDiff = projectEndDate - projectStartDate;                 // 두 날짜 사이의 차이 계산
            const totalDays = Math.floor(timeDiff / (1000 * 60 * 60 * 24));     // 밀리초(ms)를 일(day)로 변환
            const timeDiffFromStart = currentDate - projectStartDate;           // 시작 날짜와 현재 날짜 사이의 차이 계산
            const daysPassed = Math.floor(timeDiffFromStart / (1000 * 60 * 60 * 24));
            const timeDiffToFinish = projectEndDate - currentDate;               // 현재로부터 프로젝트 종료일까지의 남은 기간 계산\
            const remainingDays = Math.floor(timeDiffToFinish / (1000 * 60 * 60 * 24));
            // projectStartDate 프로젝트 시작일
            // projectEndDate   프로젝트 종료일
            // totalDays        프로젝트 기간
            // daysPassed       시작이롤부터 오늘까지 경과한 일 수
            // remainingDays    현재로부터 프로젝트 종료일까지 남은 기간
            const barCtx = document.getElementById('project_chart').getContext('2d');
            const proejctData = {
                labels: ['프로젝트 기간 '],
                datasets: [
                    {
                        label: '일한일수',
                        data: [daysPassed],
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',  // 빨간색 배경
                        borderColor: 'rgb(255, 99, 132)',  // 빨간색 테두리
                    },
                    {
                        label: '남은일수',
                        data: [Math.abs(remainingDays)],
                        backgroundColor: 'rgba(54, 162, 235, 0.5)',  // 파란색 배경
                        borderColor: 'rgb(54, 162, 235)',  // 파란색 테두리
                    }

                ]
            }
            createDrawChart(barCtx, 'bar', proejctData, proejctOption);
        }
    });
  }
  
  function loadDoughnutChart() {
    $.ajax({
        url: "<%=request.getContextPath()%>/doughnut_chart",
        //data:data
        dataType: 'json',
        success: function (response) {
            const doughnutCtx = document.getElementById('doughnut_1').getContext('2d');
            const doughnut_data = {
                labels: ['예정된 작업', '진행중인 작업', '완료된 작업'],
                datasets: [{
                    data: response,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.5)', // 빨간색
                        'rgba(54, 162, 235, 0.5)', // 파란색
                        'rgba(153, 102, 255, 0.5)', // 보라색
                    ],
                    borderColor: [
                        'rgb(255, 99, 132)', // 빨간색
                        'rgb(54, 162, 235)', // 파란색
                        'rgb(153, 102, 255)', // 보라색
                    ],
                    borderWidth: 1
                }]
            };

            createDrawChart(doughnutCtx, 'doughnut', doughnut_data, doughnut_options);
        }
    });  
  }
  
  function loadProjectStepChart() {
	$.ajax({
	    url: "<%=request.getContextPath()%>/project_step_chart",
	    dataType: 'json',
	    success: function (data) {
	        ////////////////////// 현재 진행중인 프로젝트 보기 /////////////////////////////
	        const onelist = data.onelist;
	        const current_task = $('.current_task');
	        if(onelist.length > 0) {
	            for (let i = 0; i < onelist.length; i++) {
	                var cur_task = $('<div></div>');
	                cur_task.addClass("cur_task");
	                if (i > 6) {
	                    cur_task.text('......');
	                    current_task.add(cur_task)
	                    break;
	                }
	                cur_task.html("<a href='task_detail?task_id=" + onelist[i].task_id + '&project_id=' + onelist[i].project_id + "'>" + onelist[i].task_subject + "</a>");
	                current_task.append(cur_task);
	            }
	        }
	        else{
	            var cur_task = $('<div></div>');
	            cur_task.addClass("cur_task");
	            cur_task.text('진행중인 작업이 아직 없습니다.');
	            current_task.add(cur_task)
	        }
	        ////////////////////// 프로젝트 단계별 보기 /////////////////////////////
	        const project_step_chart = $('.project_step_chart');
	        $.each(data.mapData, function(key, values) {
	            // 새로운 div 요소 생성
	            const newDiv = $('<div></div>');
	            newDiv.addClass("project_step");
	            const newstep = $('<div>'+key+'</div>'); // 텍스트를 담을 span 요소 생성
	            newDiv.append(newstep); // div에 span을 추가
	            newstep.addClass("project_step_subject");
	            // // key 값으로 가져온 데이터를 div에 추가
	            for(let i=0; i<values.length;i++) {
	                var newtask = $('<div></div>').text(values[i]);
	                newtask.addClass("project_step_task_subject")
	                newDiv.append(newtask);
	                if(i === 7){
	                    var newtask = $('<div></div>').text(" ... ");
	                    newtask.addClass("project_step_task_subject")
	                    newDiv.append(newtask);
	                    break;
	                }
	            }
	            project_step_chart.append(newDiv);
	        });
	    }
	});  
  }