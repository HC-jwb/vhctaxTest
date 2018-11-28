<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  	<link rel="stylesheet" type="text/css" href="/css/main.css">
	<link rel="stylesheet" type="text/css" href="/css/modules.min.css">
	<title>연료 소모량 리포트</title>
</head>
<body>
	<div class="ui accordion">
		<div class="active title">
			<i class="ui dropdown icon"></i>
			연비 보고서 생성하기
		</div>
		<div class="active content">
			<div class="ui stackable three column grid basic segment">
				<div class="column">
					<select name="skills" multiple="" class="ui fluid dropdown">
						<option value="">그룹 선택</option>
						<option value="0">메인그룹</option>
					</select>
				</div>
			</div>
		</div>
	</div>
<script src="/js/nprogress.js"></script>
<script src="/js/jquery-3.1.1.min.js"></script>
<script src="/js/jquery.tablesort.min.js"></script>
<script src="/js/modules.min.js"></script>
<script>
	$(".ui.accordion").accordion();
	$(".ui.dropdown").dropdown();
</script>
</body>
</html>