<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  	<link rel="stylesheet" type="text/css" href="/css/main.css">
  	<link rel="stylesheet" type="text/css" href="/css/jquery-ui.min.css">
		<link rel="stylesheet" type="text/css" href="/css/modules.min.css">
		<link rel="stylesheet" type="text/css" href="/css/scrolltabs.css">
		<style>
		@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css); .notosanskr * { font-family: 'Noto Sans KR', sans-serif; }
		@import url(//fonts.googleapis.com/earlyaccess/nanumgothiccoding.css); .nanumgothiccoding * { font-family: 'Nanum Gothic Coding', monospace; }
		
		html,body, .main-container, .left-column, .ui.segment {height: 100%; font-family: 'Nanum Gothic Coding';}
		.ui.dropdown .menu .item {font-size: 0.95em;}
		.main-container {padding: 0.2em 0.3em ;}
		.ui.list .description {font-size: 0.95em;}
		.ui.list .content .active-header{font-weight: 600; color: #415059;}
		.left-column {display: inline-block; width: 370px; padding:0.3em 0; vertical-align: top; overflow: auto;}
		div.right-column{display: inline-block; width: calc(100% - 380px); padding: 0 0.5em;}
		#reportGenFrm {min-width: 850px;}
		@media (min-width: 1601px){.scrolltabs-container {width: 1220px;}	}
		@media (min-width: 1501px) and (max-width: 1600px){	.scrolltabs-container {width: 1120px;} }
		@media (min-width: 1301px) and (max-width: 1500px){ .scrolltabs-container {width: 920px;} }
		@media (min-width: 1201px) and (max-width: 1300px){ .scrolltabs-container {width: 820px;} }
		@media (min-width: 1101px) and (max-width: 1200px){ .scrolltabs-container {width: 720px;} }
		@media (min-width: 1001px) and (max-width: 1100px){ .scrolltabs-container {width: 620px;} }
		@media (min-width: 901px) and (max-width: 1000px){ .scrolltabs-container {width: 520px;} }
		@media (max-width: 900px){ .scrolltabs-container {width: 400px;} }
		.scroll_tabs_theme_light div.scroll_tab_inner span, .scroll_tabs_theme_light div.scroll_tab_inner li {font-size: 12px;}
		.scroll_tabs_theme_light div.scroll_tab_inner span, .scroll_tabs_theme_light div.scroll_tab_inner li {padding-left: 10px; padding-right: 10px; line-height: 30px;}
		.scroll_tabs_theme_light .scroll_tab_left_button,.scroll_tabs_theme_light .scroll_tab_right_button{height: 31px;}
		.scroll_tabs_theme_light .scroll_tab_right_button::before,.scroll_tabs_theme_light .scroll_tab_left_button::before  {line-height: 31px;}
		
		</style>
	<title>연료 소모량 리포트</title>
</head>
<body>
<div class="main-container">
	<div class="left-column">
		<div class="ui raised segment">
			<div class="ui blue large top attached label"><i class="line bar chart icon"></i>연비 분석 레포트</div>	
				<div class="ui relaxed divided tiny selection list" id="genReportList"></div>
		</div>
	</div>
	<div class="right-column">
		<div class="ui small accordion" id="reportGenAccordion">
			<div class="title"><i class="dropdown icon"></i><div class="ui teal tag label">연비 보고서 생성하기</div></div>
			<div class="content">
				<div class="ui fluid small form" id="reportGenFrm">
					<div class="fields">
						<div class="seven wide required field">
							<label>보고서 제목</label>
							<div class="ui small input">
								<input type="text" placeholder="보고서 제목 및 설명" name="description">
							</div>
						</div>
					</div>
					<div class="fields">
						<div class="ui three wide required field">
							<label>그룹선택</label>
							<div class="ui fluid search selection dropdown" id="trackerGroupListDropdown">
								<input type="hidden" name="trackerGroup">
								<div class="default text">그룹 선택</div>
								<div class="ui menu">
									<div class="item" data-value="0">메인그룹</div>
								</div>
							</div>
						</div>
						<div class="ui four wide required field">
							<label>차량선택</label>
							<div class="ui fluid search selection dropdown" id="trackerListDropdown">
								<i class="dropdown icon"></i>
								<input type="hidden" name="trackerList">
								<div class="default text">차량 선택</div>
								<div class="ui menu">
								</div>
							</div>
						</div>
						<div class="ui three wide required field">
							<label>시작일</label>
							<div class="ui small left icon input">
								<input type="text" placeholder="시작일" id="fromDate" name="fromDate"><i class="calendar alternate outline icon"></i>
							</div>
						</div>
						<div class="ui three wide required field">
							<label>종료일</label>	
							<div class="ui small icon input">
								<input type="text" placeholder="종료일" id="toDate" name="toDate"><i class="calendar alternate outline icon"></i>
							</div>
						</div>
					</div>
					<div class="fields">
						<div class="ui thirteen wide field">
							<div style="text-align: right;">
								<div class="ui small close compact button">닫기</div>
								<div class="ui small blue compact generate button">보고서 생성</div>
							</div>
						</div>
					</div>
					<div class="fields"><div class="thirteen wide error field"></div></div>
				</div><!--  end of form-->
			</div><!-- end of content -->
		</div><!-- end of accordion -->
		<div class="scrolltabs-container">
				<ul id="scrollTabs" class="scroll_tabs_theme_light"></ul>
		</div>

	</div><!--  end of right column -->
</div><!-- end of main -->
<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/modules.min.js"></script>
<script src="/js/jquery.scrolltabs.js"></script>
  <script src="/js/jquery.mousewheel.js"></script>
<script src="/js/api.js"></script>
<script>
function getGroupList() {
	ReportApi.getGroupList(function(response) {
		if(response.success) {
			console.log(response);
		} else {
			FormUI.displayMsgIn($reportGenFrm, response.status.description);
		}
	});
}
function getTrackerList(groupId) {
	var $menu = $trackerListDropdown.find(".menu").empty();
	if(!groupId) return;
	ReportApi.getTrackerList(groupId, function(response){
		if(response.success) {
			var list = response.list;
			if(list.length > 0) {
				$menu.append("<div class='item' data-value='0'>차량 전체</div>");
			}
			for(var i = 0; i < list.length; i++) {
				$menu.append("<div class='tracker item' data-value='" + list[i].id + "'>" + list[i].label + "</div>");
			}
		} else {
			FormUI.displayMsgIn($reportGenFrm, response.status.description);;
		}
	});
}
function generateReport() {
	$reportGenFrm.form("validate form");
	if($reportGenFrm.form("is valid")) {
		var valueMap = $reportGenFrm.form("get values");
		var trackers = [];
		if(valueMap.trackerList == '0') {
			$trackerListDropdown.find(".menu .tracker.item").each(function() {
				trackers.push({trackerId: $(this).data("value")});
			});
		} else {
			trackers.push({trackerId:valueMap.trackerList});
		}
		ReportApi.sendGenRequest({
				trackers: trackers,
				from: valueMap.fromDate,
				to:valueMap.toDate,
				intervalInMin: 360,
				label: valueMap.description
			}, function(response) {
			if(response.success) {
				closeAccordion();
				prependReportGen(response.payload);
				ReportApi.startCheckProgress();
			} else {
				FormUI.displayMsgIn($reportGenFrm, response.status.description);
			}
		});
	}
}
function getReportGenList() {
	ReportApi.getReportGenList(function(response) {
		if(response.success) {
			var list = response.payload;
			$genReportList.empty();
			for(var i = 0; i < list.length; i++) {
				$genReportList.append(reportGenAsItem(list[i]));
			}
			ReportApi.startCheckProgress();
			
		} else {
			console.log(response);
		}
	});
}
function reportGenAsItem(reportGen) {
	var $clone = $reportGenItem.clone(false);
	$clone.data("report", reportGen);
	$clone.find(".description").text(reportGen.from + " - " + reportGen.to);/*"생성일: " + reportGen.formattedCreatedDate*/
	if(reportGen.fuelReportProcessed) {
		$clone.addClass('processed').find(".content").prepend("<div class='active-header'>" + reportGen.label + "</div>");
	} else {
		$clone.addClass('processing').find(".content").prepend("<div class=''>" + reportGen.label + " <i class='spinner loading icon'></i></div>");
	}
	return $clone;
}
function prependReportGen(reportGen) {
	$genReportList.prepend(reportGenAsItem(reportGen));
}
function refreshStatus(pendingReportGenIds) {
	$genReportList.find(".processing.item").each(function() {
		var $this = $(this);
		var reportGen = $this.data("report");
		var found = false;
		for(var i = 0; i < pendingReportGenIds.length; i++) {
			if(reportGen.id == pendingReportGenIds[i]) {
				found = true;
				break;
			}
		}
		if(!found) {
			$this.removeClass("processing").addClass("processed").find(".content > div").first().addClass('active-header').find("i").remove();
		}
	});
}
function closeAccordion() {
	$reportGenAccordion.accordion('close', 0);
}

function buildReportTab(sectionList) {
	scrollTabs.clearTabs();
	$scrollTabs.data('reportid', sectionList[0].reportId);
	for(var i = 0; i < sectionList.length; i++) {
		scrollTabs.addTab("<li data-trackerid='" + sectionList[i].trackerId+ "'>" + sectionList[i].header+ "</li>")
	}
}
function reportTabClicked() {
	console.log($scrollTabs.data('reportid'),$(this).data("trackerid"));
}
var $reportGenFrm, $trackerListDropdown, $genReportList, $reportGenItem, $reportGenAccordion, scrollTabs, $scrollTabs;
$(function() {
	$scrollTabs = $("#scrollTabs");
	scrollTabs = $scrollTabs.scrollTabs({click_callback: reportTabClicked});
	$reportGenAccordion = $("#reportGenAccordion");
	$reportGenFrm = $("#reportGenFrm");
	$trackerListDropdown = $("#trackerListDropdown");
	$genReportList = $("#genReportList");
	$reportGenItem = $("<div class='item'><div class='content'><div class='description'></div></div>");
	$reportGenAccordion.accordion();
	$("#trackerGroupListDropdown").dropdown({onChange: getTrackerList});
	$trackerListDropdown.dropdown({fullTextSearch: true, clearable: true});
	
	$reportGenFrm.find(".close.button").click(closeAccordion);
	$reportGenFrm.form({
		fields: {description: 'empty', trackerGroup: 'empty', trackerList: 'empty', fromDate:'empty', toDate: 'empty'}
	});
	$reportGenAccordion
	$reportGenFrm.find(".generate.button").click(generateReport);
	from = $( "#fromDate").datepicker({dateFormat:'yy-mm-dd',defaultDate: "-28d",changeMonth: true,numberOfMonths: 1})
	.on("change", function() {
		to.datepicker("option", "minDate", getDate(this));
	}),
	to = $( "#toDate" ).datepicker({dateFormat:'yy-mm-dd', defaultDate: "-1d", maxDate: "0", changeMonth: true, numberOfMonths: 1})
	.on( "change", function() {
		from.datepicker( "option", "maxDate", getDate(this));
	});
	function getDate( element ) {
		var date;
		try {
			date = $.datepicker.parseDate("yy-mm-dd", element.value );
		} catch( error ) { date = null; }
		return date;
	}
	
	$genReportList.on("click", ".processed.item", function() {
		ReportApi.getSectionList($(this).data("report"), function(response){
			if(response.success) {
				buildReportTab(response.payload);
			} else {
				console.log(response.status.description);
			}
		});
	});
	

	ReportApi.authenticate({login:'test@cesco.co.kr', password:'123456'}, function(response) {
		if(response.success) {
			getReportGenList();
			
		} else {
			alert(response.status.description);
		}
	});

//getGroupList();


});
$('.error.field').on('click','.close', function() {
	$(this).closest('.message').transition('fade');
});
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
</script>
</body>
</html>