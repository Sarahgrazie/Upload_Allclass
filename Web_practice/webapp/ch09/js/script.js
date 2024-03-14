/**
 * 
 */
let id=promt('아이디');
if(id=="admin"){
	//id는 정상 입력
	let password=prompt('비밀번호');
	if(password=='123456'){
		location.href="20_login.html"; 
	}else{
		//비밀번호 오류
		location.href="./20_errer.html"
	}
}else{ //아이디 입력 오류 
	location.href='./20_error.html'
}
