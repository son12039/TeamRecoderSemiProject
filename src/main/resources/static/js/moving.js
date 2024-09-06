const draggable = document.getElementById("container-title");
const divs = document.getElementById("deleteMembership");

let offsetX, offsetY, isDragging = false;

draggable.addEventListener("mousedown", (e) => {
    // 드래그 시작 시 <div>의 위치와 클릭 위치 계산
    const rect = divs.getBoundingClientRect();
    offsetX = e.clientX - rect.left;
    offsetY = e.clientY - rect.top;

    // 드래그가 시작되었음을 표시
    isDragging = true;

    // `mousemove` 및 `mouseup` 이벤트 리스너 추가
    document.addEventListener("mousemove", onMouseMove);
    document.addEventListener("mouseup", onMouseUp);
});

function onMouseMove(e) {
    if (isDragging) {
        // 마우스 이동에 따라 <div> 위치 업데이트
        divs.style.left = `${e.clientX - offsetX}px`;
        divs.style.top = `${e.clientY - offsetY}px`;
    }
}

function onMouseUp() {
    isDragging = false;

    // `mousemove` 및 `mouseup` 이벤트 리스너 제거
    document.removeEventListener("mousemove", onMouseMove);
    document.removeEventListener("mouseup", onMouseUp);
}

// 예시로 `#deleteMembership`을 보이게 하는 함수
function showDeleteMembership() {
    divs.style.display = "block";
}