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
	NProgress.configure({showSpinner: false});
	NProgress.start();	NProgress.inc();
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
	$("#taxSubMenu .item").click(function() {
		var $this = $(this);
		if($this.hasClass("payment task")) {
			window.location.replace("/vhctax.html");
		} else if($this.hasClass("tax template")) {
			window.location.replace("/taxtmpl.html");
		} else {
			console.log("invalid sub menu", $this.attr("class"));
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
		NProgress.done();
	});
});