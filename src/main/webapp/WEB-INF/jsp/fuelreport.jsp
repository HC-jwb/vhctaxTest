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
		<style>
		@import url(//fonts.googleapis.com/earlyaccess/notosanskr.css); .notosanskr * { font-family: 'Noto Sans KR', sans-serif; }
		@import url(//fonts.googleapis.com/earlyaccess/nanumgothiccoding.css); .nanumgothiccoding * { font-family: 'Nanum Gothic Coding', monospace; }
		
		html,body, .main-container, .left-column, .ui.segment {height: 100%; font-family: 'Nanum Gothic Coding';}
		.ui.dropdown .menu .item {font-size: 0.95em;}
		.main-container {padding: 0.2em 0.3em ;}
		.left-column {display: inline-block; width: 370px; padding:0.3em 0; vertical-align: top; overflow: auto;}
		div.right-column{display: inline-block; width: calc(100% - 375px); padding: 0 0.5em;}
		#reportGenFrm {min-width: 850px;}
		</style>
	<title>연료 소모량 리포트</title>
</head>
<body>
	<div class="main-container">
		<div class="left-column">
			<div class="ui raised segment">
				<div class="ui blue large top attached label">연료 분석 레포트</div>	
					<div class="ui relaxed divided small link list" id="genReportList"></div>
			</div>
		</div>

		<div class="right-column">
			<div class="scrollable-container">
				<div class="ui small accordion">
				<div class="active title"><i class="dropdown icon"></i><div class="ui teal tag label">연비 보고서 생성하기</div></div>
				<div class="active content">
						<div class="ui fluid small form" id="reportGenFrm">
							<div class="fields">
									<div class="seven wide required field">
										<label>보고서 제목 &amp; 설명</label>
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
									<div class="ui two wide field">
										<label>처리</label>
										<div class="ui small blue compact generate button">생성</div>
									</div>
							</div>
						</div><!--  end of form-->
					</div>
				</div>
			</div>
		</div><!--  end of right column -->
	</div>	
<script src="/js/nprogress.js"></script>
<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script src="/js/modules.min.js"></script>
<script src="/js/api.js"></script>
<script>
function getGroupList() {
	ReportApi.getGroupList(function(response) {
		if(response.success) {
			console.log(response);
		} else {
			alert(response.status.description);
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
			console.log(response.status.description);
		}
	});
}
function generateReport() {
	$reportGenFrm.form("validate form");
	if($reportGenFrm.form("is valid")) {
		var valueMap = $reportGenFrm.form("get values");

		
		//trackers => [{trackerId:73, sensorId: 647} ,{trackerId:69, sensorId: 645}], from=> 2018-11-23 00:00:00, to=> 2018-11-23 23:59:59, detailsIntervalMinutes => 360
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
					console.log(response.id);
				} else {
					console.log(response.status.description);
				}
		});
	}
}
function getReportGenList() {
	ReportApi.getReportGenList(function(response) {
		if(response.success) {
			console.log(response.payload);
			var list = response.payload;
			$genReportList.empty();
			var $item = $("<div class='item'><i class='middle aligned large chart line teal icon'></i><div class='content'><div class='description'></div></div>");
			var $clone;
			for(var i = 0; i < list.length; i++) {
				var genReport = list[i];
				$clone = $item.clone(false);
 				$clone.data("report", genReport);
 				
 				$clone.find(".description").html(genReport.from + " - " + genReport.to );/*+ "생성일:" + genReport.createdDate*/
 				if(genReport.fuelReportProcessed) {
 					$clone.addClass("active").find(".description").prepend("<a class='header'>" + genReport.label + "</a>");
 				} else {
 					$clone.find(".description").prepend("<div class='header'>" + genReport.label + "</div>");
 					$clone.find(".icon").removeClass("teal chart line").addClass('cog loading');
 				}
				$genReportList.append($clone);
				
				ReportApi.getReportGenListInProgress(function(response) {
					if(response.success) {
						console.log(response.payload);
					} else {
						console.log(response);
					}
				});
				
			}
		} else {
			console.log(response);
		}
	});
}
var $reportGenFrm, $trackerListDropdown, $genReportList;
$(function() {
	$reportGenFrm = $("#reportGenFrm");
	$trackerListDropdown = $("#trackerListDropdown");
	$genReportList = $("#genReportList");
	$(".ui.accordion").accordion();
	$("#trackerGroupListDropdown").dropdown({onChange: getTrackerList});
	$trackerListDropdown.dropdown({fullTextSearch: true, clearable: true});
	
	$reportGenFrm.form({
		fields: {description: 'empty', trackerGroup: 'empty', trackerList: 'empty', fromDate:'empty', toDate: 'empty'}
	});
	$reportGenFrm.find(".generate.button").click(generateReport);
	from = $( "#fromDate").datepicker({dateFormat:'yy-mm-dd',defaultDate: "-28d",changeMonth: true,numberOfMonths: 1})
	.on("change", function() {
		to.datepicker("option", "minDate", getDate( this ) );
	}),
	to = $( "#toDate" ).datepicker({dateFormat:'yy-mm-dd', defaultDate: "-1d", maxDate: "0", changeMonth: true, numberOfMonths: 1})
	.on( "change", function() {
		from.datepicker( "option", "maxDate", getDate( this ) );
	});

  function getDate( element ) {
		var date;
		try {
			date = $.datepicker.parseDate("yy-mm-dd", element.value );
		} catch( error ) { date = null; }
		return date;
	}
	
	
/*
	ReportApi.authenticate({login: 'test@cesco.co.kr', password:'123456'}, function(response) {
		if(!response.success) {
			alert(response.status.description);
		}
	});
*/
//getGroupList();
getReportGenList();
});
	
</script>
</body>
</html>