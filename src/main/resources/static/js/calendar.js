var calendarEl = document.getElementById("calendar");
var calendar = new FullCalendar.Calendar(calendarEl, {
	timeZone: "UTC",
	initialView: "dayGridMonth",
	events: allDates,
	editable: true,
	selectable: true,
	height: 600,
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


calendar.render();





