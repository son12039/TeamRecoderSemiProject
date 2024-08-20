let page = 1;
window.addEventListener("scroll", () => {
	console.log("현ㅈ:", window.innerHeight + window.pageYOffset); // 브라우저 창의 보이는 영역 높이
		    console.log("전체:", document.body.offsetHeight); // 전체 웹 페이지 높이
    if (document.body.offsetHeight <= (window.pageYOffset + window.innerHeight + 10)) {
        page++;
        $.ajax({
            url: '/list',
            type: 'GET',
            data: { page: page },
            success: function(membership) {
				console.log(membership);
                let membershipList = $(".membership-list");
                $.each(membership, function(index, id) {
                    let membershipImg = id.membership.membershipImg !== null ? 
                        'http://192.168.10.51:8081/membership/' + id.membership.membershipCode + '/' + id.membership.membershipImg : 
                        'http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg';
                    
                    let hostImg = id.member.memberImg !== null ? 
                        'http://192.168.10.51:8081/member/' + id.member.id + '/' + id.member.memberImg : 
                        'http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg';

                    let membershipCard =
                        '<div class="membership-card">' +
                            '<div class="membership-img">' +
                                '<a href="/' + id.membership.membershipCode + '">' +
                                    '<img src="' + membershipImg + '">' +
                                '</a>' +
                            '</div>' +
                            '<div class="membership-info">' +
                                '<h1 class="membership-name">' + id.membership.membershipName + '</h1>' +
                                '<h2>' + id.membership.membershipInfo + '</h2>' +
                                '<h3>멤버수: ' + '2' + '/' + id.membership.membershipMax + '</h3>' +
                                '<div id="host">' +
                                    '<img class="user-img" src="' + hostImg + '">' +
                                    '<h2>호스트: ' + id.member.nickname + '</h2>' +
                                    '<input type="hidden" name="code" value="' + id.membership.membershipCode + '">' +
                                '</div>' +
                            '</div>' +
                        '</div>';
                    
                    membershipList.append(membershipCard);
                });
            }
        });
    }
});