var FormUI = {
	errMsgDiv: '<div class="ui error message"><i class="close icon"></i><span class="msg" style="padding: 1em;"></span></div>'
	, displayMsgIn: function ($frm, msg) {
		setTimeout(function() {
			$frm.find(".error.field").empty().append(FormUI.errMsgDiv);
			$frm.find(".error.field .error.message .msg").text(msg);
			$frm.removeClass("success").addClass("error");
		}, 100);
	}
};
$(function () {
	$("#topMenu .enabled.item").click(function() {
		var $this = $(this);
		if($this.hasClass("fuel report")) {
			window.location.replace("/fuel.html");
		} else if($this.hasClass("tracking")) {
			window.location.replace("/fms.html");
		} else if($this.hasClass("filling")) {
			window.location.replace("/filling.html")
		} else if($this.hasClass("vehicle tax")) {
			window.location.replace("/vhctax.html");
		}
	});
	$('.error.field').on('click','.close', function() {
		$(this).closest('.message').transition('fade');
	});
	AuthApi.validateSession(function(response) {
		if(response.success && response.payload) {
			if(typeof(getReportGenList) !== 'undefined') getReportGenList();
		} else {
			window.location.replace('/login.html');
		}
	});
});