$(document).ready( function () {
	$('.ui.hc-fms.login.form').form({
		fields: {
			login: {
				identifier  : 'login',
				rules: [
					{
					type   : 'empty',
					prompt : '유효하지 않은 아이디입니다.'
					}
				]
			},
			password: {
				identifier  : 'password',
				rules: [
					{
						type   : 'empty',
						prompt : '패스워드가 공란입니다'
					}
				]
			}
		},
		on: 'submit',
		onSuccess: tryLogin
	});
});
$(function() {
	if(credRemeberAvl = typeof(Storage) !== "undefined") {	
		var $credlgn = $("#credlgn");
		var $credpwd= $("#credpwd");
		var $rememberChk = $(".ui.remember-me.checkbox");
		$rememberChk.first().checkbox({
			onUnchecked: function () {
				localStorage.removeItem('hcfmscred');
			}
		});

		$(".ui.submit.button").click(function() {
			if($rememberChk.checkbox('is checked')) {
				cred = {};
				cred.lgn = $credlgn.val();
				cred.pwd = $credpwd.val();
				localStorage.hcfmscred = JSON.stringify(cred);
			}
		});	

		var cred = localStorage.hcfmscred;
		if(cred) {
			$rememberChk.checkbox('set checked');
			if(cred ) {
				cred = JSON.parse(cred);
				$credlgn.val(cred.lgn)
				$credpwd.val(cred.pwd);
			}
		}
	}
});

function tryLogin (event, fields) {
	event.preventDefault();
	//userAuth({login: fields.login, password: fields.password}, procRedirect);
	AuthApi.authenticate({login: fields.login, password: fields.password}, function(response){
		if(response.success) {
			window.location.replace('/fuel.html');
		} else {
			alert(response.status.description);
			window.location.replace("/login.html");
		}
	}, function(data) {
		alert("Failed to connect the system.\nPlease try again later.");
	});
}