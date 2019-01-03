function saveTemplate() {
	$form.form("validate form");
	if(!$form.form('is valid')) return;
	var svcTmpl = $form.form('get values');
	svcTmpl.id = $form.data("id");/*if id is not null, it means updating existing template*/
	svcTmpl.notificationEmail = $.trim(svcTmpl.notificationEmail);
	if(svcTmpl.notificationEmail && !validateEmail(svcTmpl.notificationEmail)) {
		$form.find(".email.field").addClass("error");
		return;
	}
	svcTmpl.certificationCost = svcTmpl.certificationCost.replace(/\,/g,"");
	svcTmpl.taxCost = svcTmpl.taxCost.replace(/\,/g,"");
	TaxServiceApi.savePaymentTemplate(svcTmpl, function(response) {
		if(response.success) {
			console.log(response.payload);
			listServiceTemplate();
		} else {
			FormUI.displayMsgIn($form, response.status.description);
		}
	});
}
function listServiceTemplate() {
	$tableLoader.addClass("active");
	TaxServiceApi.listPaymentTemplate(function(response) {
		if(response.success) {
			buildTemplateListTable(response.payload);
			hideModal();
		} else {
			alert(response.status.description);
		}
		setTimeout(function() {$tableLoader.removeClass("active")}, 200);
	});
}
function buildTemplateListTable(tmplList) {
	var $TBODY = $tmplListTable.find("tbody");
	$TBODY.empty();
	var len = tmplList.length;
	if(len) {
		var $TR = $("<tr></tr>");
		var $clone;
		for(var i = 0; i < len; i++) {
			$clone = $TR.clone(false);
			$clone.data('tmplid', tmplList[i].id);
			$clone.append("<td class='right aligned collapsing'>" +(i+1) + "</td>");
			$clone.append("<td>" +tmplList[i].title+"</td>");
			$clone.append("<td>" +tmplList[i].description+"</td>");
			$clone.append("<td class='collapsing'>" +tmplList[i].notificationEmail+"</td>");
			$clone.append("<td class='collapsing'>" +tmplList[i].notificationSms+"</td>");
			$clone.append("<td class='center aligned'><div class='ui mini compact icon blue edit basic button' title='view and edit template'><i class='edit icon'></i></div><div class='ui mini compact icon red basic delete button' title='remove template'><i class='trash alternate icon'></i></div></td>");
			$TBODY.append($clone);
		}
	} else {
		$TBODY.append("<tr><td colspan='6' class='center aligned'>No template found.</td></tr>");
	}
}

function loadPaymentTemplateForEdit(id) {
	TaxServiceApi.getPaymentTemplate(id, function(response) {
		if(response.success) {
			showModal(response.payload);
		} else {
			alert(response.status.description);
		}
	});
}
function removePaymentTemplate(id) {
	$tableLoader.addClass("active");
	TaxServiceApi.removePaymentTemplate(id, function(response) {
		if(response.success) {
			listServiceTemplate();
		} else {
			alert(response.status.description);
		}
	});
}
function showModal(tmpl) {
	$form.data('id', null);
	$form.form('clear');
	if(typeof(tmpl) === 'undefined' ) {
		$('.ui.radio.checked.checkbox').checkbox('set checked');
	} else {
		$form.data('id', tmpl.id);
		tmpl.certificationCost = addCommas(tmpl.certificationCost);
		tmpl.taxCost = addCommas(tmpl.taxCost);
		$form.form('set values', tmpl);
	}
	
	$templateModal.modal("show");
}
function hideModal() {
	$templateModal.modal("hide");
	FormUI.resetMsgIn($form);
}
var $templateModal, $form, $tmplListTable, $loadingSegment;
$(function() {
	$(".ui.radio.checkbox").checkbox();
	$tmplListTable = $("#tmplListTable");
	$templateModal = $("#templateModal");
	$tableLoader = $("#tableLoader");
	$templateModal.modal({dimmerSettings:{opacity: 0.3}, autofocus:false, closable:false});
	$form = $templateModal.find(".ui.form");
	$form.form({fields: {title:'empty', description:'empty', certificationCost:'number', certificationCost:'empty', taxCost:'empty'}});
	
	$tmplListTable.on("click", "tbody > tr > td > .ui.button", function() {
		var $this = $(this);
		var id = $this.parent().parent().data("tmplid");
		if($this.hasClass("edit")) {
			loadPaymentTemplateForEdit(id);
		} else if($this.hasClass("delete")) {
			removePaymentTemplate(id);
		}
	});
	$form.find(".ui.button").click(function() {
		var $this = $(this);
		if($this.hasClass("close")) {
			hideModal();
		} else if($this.hasClass("save")) {
			saveTemplate();
		}
	});
	$(".ui.add.button").click(function() {showModal(); });
	var cleave = new Cleave('.taxcost-input', {
	    numeral: true,
	    numeralThousandsGroupStyle: 'thousand'
	});
	cleave = new Cleave('.certcost-input', {
	    numeral: true,
	    numeralThousandsGroupStyle: 'thousand'
	});
	listServiceTemplate();
});