let isLoading = false; // 로딩 상태를 추적
const currentDate = new Date(); // 오늘
const today = currentDate.toISOString().split("T")[0];
currentDate.setDate(currentDate.getDate() - 30);
const today30 = currentDate.toISOString().split("T")[0]; // 30일전

window.addEventListener("scroll", () => {
  const link = document.createElement("link");
  link.rel = "stylesheet";
  link.href =
    "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css";
  document.head.appendChild(link);

  if (isLoading) return; // 로딩 중이면 AJAX 요청을 방지

  if (document.body.offsetHeight - 700 <= window.scrollY + window.innerHeight) {
    isLoading = true; // 로딩 상태 설정

    searchDto.page++;
    $.ajax({
      url: "list",
      type: "get",
      data: searchDto,
      success: function (clubList) {
        let div = $(".membership-list");
        $.each(clubList, function (index, club) {
          let ajaxLocationType =
            '<div class="membership-card">' ;
			if (club.membershipDate > today30 && today > club.membershipDate) {
				ajaxLocationType +=
					'<img alt="" src="http://192.168.10.51:8081/sungil/2%ED%8A%B8.png" class="new">';
			}
            ajaxLocationType += '<div class="membership-img">' +
            '<a href="/' +
            club.membershipCode +
            '">';
          if (club.membershipImg) {
            ajaxLocationType +=
              '<img src="http://192.168.10.51:8081/membership/' +
              club.membershipCode +
              "/" +
              club.membershipImg +
              '">';
          } else {
            ajaxLocationType +=
              '<img src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%EB%AA%A8%EC%9E%84%EC%9D%B4%EB%AF%B8%EC%A7%80.jpg">';
          }
          ajaxLocationType +=
            "</a>" +
            "</div>" +
            '<div class="membership-info">' +
            '<h1 class="membership-name">' +
            club.membershipName +
            "</h1>" +
            "<h2>" +
            club.membershipSimpleText +
            "</h2>" +
            '<a href="/userInfo/' +
            club.nickname +
            '">' +
            '<div class="host">';
          if (club.memberImg) {
            ajaxLocationType +=
              '<img class="user-img" src="http://192.168.10.51:8081/member/' +
              club.id +
              "/" +
              club.memberImg +
              '">';
          } else {
            ajaxLocationType +=
              '<img class="user-img" src="http://192.168.10.51:8081/%EA%B8%B0%EB%B3%B8%ED%94%84%EC%82%AC.jpg">';
          }
          ajaxLocationType +=
            "<h2>호스트 : " +
            club.nickname +
            "</h2>" +
            '<input type="hidden" name="code" value="' +
            club.membershipCode +
            '">' +
            '<div class="memberMannerIndex">';

          if (club.memberManner < 30) {
            ajaxLocationType +=
              '<span style="color: red"><i class="fa-solid fa-face-angry fa-2x"></i></span>' +
              "<p>" +
              club.memberManner +
              "℃</p>";
          } else if (club.memberManner >= 30 && club.memberManner <= 40) {
            ajaxLocationType +=
              '<span style="color: rgb(252, 177, 3)"><i class="fa-solid fa-face-smile fa-2x"></i></span>' +
              "<p>" +
              club.memberManner +
              "℃</p>";
          } else {
            ajaxLocationType +=
              '<span style="color: green"><i class="fa-solid fa-face-grin fa-2x"></i></span>' +
              "<p>" +
              club.memberManner +
              "℃</p>";
          }
          ajaxLocationType +=
            "</div>" +
            "</div>" +
            "</a>" +
            '<h3><i class="fa-solid fa-users"></i> : ' +
            club.count +
            "/" +
            club.membershipMax +
            "</h3>" +
            '<div class="locationTypeBox">' +
            '<div class="location">';
          $.each(club.locations, function (i, location) {
            ajaxLocationType +=
              '<div class="locationList"># ' +
              location.locLaName +
              " " +
              location.locSName +
              "</div>";
          });
          ajaxLocationType += "</div>" + "<br>" + '<div class="type">';
          $.each(club.types, function (i, type) {
            ajaxLocationType +=
              '<div class="typeList">' + type.typeSName + "</div>";
          });
          ajaxLocationType += "</div>" + "</div>" + "</div>";
          div.append(ajaxLocationType);
        });
        isLoading = false; // 로딩 상태 해제
      },
    });
  }
});
