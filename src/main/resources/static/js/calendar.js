
$(document).ready(function() {
    const $datesContainer = $('#dates');
    let currentYear, currentMonth;

	

    
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
		const color = [
			'red','blue','yellow'
		];
		
		
		const allDates= [];
		// 모든 날짜를 저장할 배열
		const size = $("#size").text()
		
	      for(let i=1; i<=size; i++){
		allDates[i-1] = [new Date ($('#start'+i ).text()),new Date ($('#end'+i ).text())];
		
		 }
		 
		 
		 
		console.log(allDates)
		
		
		
	
		 
	
		 
		/*
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
			
		 });*/
		 
	
		
	

		 
        $('.date').each(function() {
			console.log()
            const cellDate = new Date($(this).attr('data-date'));
			
		
			
				
			
          
			cellDate.setDate(cellDate.getDate()+1)
			
			
            startDates.forEach((startDate, index) => {
				
                const endDate = endDates[index];
                if (cellDate  >= startDate && cellDate <= endDate) {
					
                    $(this).css('background-color', color[Math.floor(Math.random()*3)]); // 색칠할 색상
                }
			
			      
            });
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
	
});


