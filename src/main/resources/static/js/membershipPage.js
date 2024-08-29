// 문서가 준비되었을 때 이벤트를 설정합니다.
$(document).ready(function() {
    // 버튼 클릭 시 AJAX 요청을 보냅니다.
    $(document).on('click', '[id^=agreeMember-]', function(e) {
        e.preventDefault(); // 버튼 클릭 시 폼의 기본 제출을 방지합니다.

        // 버튼의 ID에서 listCode를 추출합니다.
        let num = $(this).attr('id').split('-')[1];

        // 폼의 ID를 설정합니다.
        let frm = "#agreefrm-" + num;

        // AJAX 요청을 수행합니다.
        $.ajax({
            url: "/agreeMember",
            type: 'post',
            data: $(frm).serialize(),
            success: function() {
                // 성공 시 페이지를 새로 고칩니다.
                location.reload();
            },
            error: function(xhr, status, error) {
                // 오류 시 콘솔에 오류를 기록합니다.
                console.error("AJAX 요청 실패: ", status, error);
            }
        });
    });
});

