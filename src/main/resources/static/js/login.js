



 // id가 id인 태그에 input 값이 입력될때마다 실행되는 함수 


let check=false;

function validate(){
	
    
   if($("#id").val() ==""){
	alert("아이디를 입력해주세요!")
	$("#id").focus()
	check=false;
   } else if($("#pwd").val()==""){
	$("#pwd").focus()
	alert("비밀번호를 입력해주세요!")
	check=false;
   } else {
	$.ajax({
		type:'post',
		url:'/login',
		data :{
			id:$("#id").val(),
			pwd:$("#pwd").val()
			
		},
		success: function(data){
			if(data){
				window.location.href = '/';
				check=true;
			}else if(!data){
				alert("아이디 또는 비밀번호가 일치하지 않습니다")
				$("#id").val("")
				$("#pwd").val("")
			}
			
		}
			
})

}
return check;
}