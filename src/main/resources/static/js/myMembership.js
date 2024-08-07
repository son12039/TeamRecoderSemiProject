$(document).ready(function() {
       // 기본적으로 'allmem' div만 보이도록 설정
       $('#' + 'allmem').show();
       
       // 버튼 클릭 이벤트 핸들러
       $('.membership-type button').click(function() {
           // 모든 membership-card div 숨기기
           $('.membership-card').hide();

           // 클릭된 버튼에서 data-target 속성을 가져와 해당 div만 표시
           var targetId = $(this).data('target');
           $('#' + targetId).show();
       });
   });