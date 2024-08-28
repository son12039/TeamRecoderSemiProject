var calendarEl = document.getElementById("calendar");
var calendar = new FullCalendar.Calendar(calendarEl, {
	timeZone: "UTC",
	initialView: "dayGridMonth",
	events: allDates,
	editable: true,
	selectable: true,
	height: 800,
	locale: "ko", // 여기
	eventClick: function(info) {
	
		location.href="/meetingDetail?meetCode="+info.event.extendedProps.meetCode;
		//info.
	
	},
	dayCellContent: function (arg) {
	    const { date } = arg;
	    return date.getDate();
	  },
});

$(".fc-event-title").click((e)=>{
	//const input = e.target.prevAll()
//	console.log(input[0])
	
	alert("!!!!!!!!!!");
});

calendar.render();

/*$(document).ready(function() {
	const $datesContainer = $('#dates');
	let currentYear, currentMonth;

	
	const color = [
			'red','blue','yellow'
		];
    
	// 날짜를 생성하는 함수
	function generateCalendar(year, month) {
		$datesContainer.empty(); // 초기화
	    
		const firstDay = new Date(year, month, 1);
		const lastDay = new Date(year, month + 1, 0);
		const daysInMonth = lastDay.getDate();
	    
		// 빈 셀 추가
		const dayOfWeek = firstDay.getDay();
		for (let i = 0; i < dayOfWeek; i++) {
			$datesContainer.append('<div class="date"></div>');
		}
	    
		// 날짜 셀 추가
		for (let day = 1; day <= daysInMonth; day++) {
			const date = new Date(year, month, day);
			const formattedDate = date.toISOString().split('T')[0]; // yyyy-mm-dd 형식으로 변환
			$datesContainer.append(`<div class="date" data-date="${formattedDate}">${day}</div>`);
		}
	}

	// 날짜 범위를 색칠하는 함수
	function colorDates() {
		const startDates = $('.start').map(function() {
			return new Date($(this).text().trim());
		}).get();
		
	    
		const endDates = $('.end').map(function() {
			return new Date($(this).text().trim());
		}).get();
	
		
		/*
		const allDates= [];
		// 모든 날짜를 저장할 배열
		const size = $("#size").text()
		
		  for(let i=1; i<=size; i++){
			const num = Math.floor(Math.random()*3)
		allDates[i-1] = [new Date ($('#start'+i ).text()),new Date ($('#end'+i ).text()),
		$("#color"+i).text()	
		
		];
		
		 }
		 console.log(allDates)
		
	
		
		
		$('.date').each(function() {
				
				const cellDate = new Date($(this).attr('data-date'));
				
				cellDate.setDate(cellDate.getDate()+1)		
				
				for(let i =0; i<allDates.length; i++){
						
					if(new Date(allDates[i].startDate) <= cellDate && cellDate <= new Date(allDates[i].endDate)) {
						$(this).css("background-color",allDates[i].color);
						console.log(allDates[i].info);
					}
					
				}


				
				
				
				
		 })
		 
		
		 
		
		 
		 
	// allDates[0][0] ~ allDates[0][1]
		 
		
		 // 각 시작 날짜와 종료 날짜 쌍을 처리
		 startDates.forEach((startDate, index) => {
			 const endDate = endDates[index];
			 if (endDate) {
				 let currentDate = new Date(startDate);
				 while (currentDate <= endDate) {
					 allDates.push(new Date(currentDate));
					 currentDate.setDate(currentDate.getDate() + 1);
				 }
			 }
			
		 });
		 
	
		
	


	
		
		
		
		
		
	}
    
	// 날짜를 로드하고 색칠하는 함수
	function loadCalendar() {
		const today = new Date();
		currentYear = today.getFullYear();
		currentMonth = today.getMonth();
	    
		generateCalendar(currentYear, currentMonth);
		colorDates();
	    
		$('#monthYear').text(`${today.toLocaleString('default', { month: 'long' })} ${currentYear}`);
	}
    
	// 월 전환 버튼 클릭 이벤트
	$('#prevMonth').click(function() {
		if (currentMonth === 0) {
			currentMonth = 11;
			currentYear--;
		} else {
			currentMonth--;
		}
		generateCalendar(currentYear, currentMonth);
		colorDates();
		$('#monthYear').text(`${new Date(currentYear, currentMonth).toLocaleString('default', { month: 'long' })} ${currentYear}`);
	});
    
	$('#nextMonth').click(function() {
		if (currentMonth === 11) {
			currentMonth = 0;
			currentYear++;
		} else {
			currentMonth++;
		}
		generateCalendar(currentYear, currentMonth);
		colorDates();
		$('#monthYear').text(`${new Date(currentYear, currentMonth).toLocaleString('default', { month: 'long' })} ${currentYear}`);
	});
	



	loadCalendar();
	
});*/


