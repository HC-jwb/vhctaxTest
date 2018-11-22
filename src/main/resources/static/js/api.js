function authenticate(callback) {
	Api.sendGet(ApiURL.rsvnBase + '/tracking/auth', function(data) {
		if(data.success) {
			callback(data.payload);
		} else {
			alert("추적 관리 시스템 접속에 실패하였습니다.\n잠시후 다시 시도해 주십시요.");
		}
	});
}
Date.prototype.yyyyMMdd = function() {
	var yyyy = this.getFullYear();
	var MM = this.getMonth() + 1;
	var dd = this.getDate();

	MM = MM < 10 ? '0' + MM: MM;
	dd = dd < 10 ? '0' + dd: dd;
	return [yyyy,'-', MM, '-', dd].join('');
};
Date.prototype.yyyyMMddHHmmss = function(ignoreTime) {
	var yyyy = this.getFullYear();
	var MM = this.getMonth() + 1;
	var dd = this.getDate(); 
	var HH, mm, ss;
	if(ignoreTime) {
		HH = 0;
		mm = 0;
		ss = 0;
	} else {
		HH = this.getHours();
		mm = this.getMinutes();
		ss = this.getSeconds();
	}
	MM = MM < 10 ? '0' + MM: MM;
	dd = dd < 10 ? '0' + dd: dd;
	HH = HH < 10 ? '0' + HH: HH;
	mm = mm < 10 ? '0' + mm: mm;
	ss = ss < 10 ? '0' + ss: ss;
	return [yyyy,'-', MM, '-', dd,' ', HH, ':',mm, ':',ss].join('');
};
function dateAdd(date, interval, units) {
	var ret = new Date(date); //don't change original date
	var checkRollover = function() { if(ret.getDate() != date.getDate()) ret.setDate(0);};
	switch(interval.toLowerCase()) {
		case 'year'   :  ret.setFullYear(ret.getFullYear() + units); checkRollover();  break;
		case 'quarter':  ret.setMonth(ret.getMonth() + 3*units); checkRollover();  break;
		case 'month'  :  ret.setMonth(ret.getMonth() + units); checkRollover();  break;
		case 'week'   :  ret.setDate(ret.getDate() + 7*units);  break;
		case 'day'    :  ret.setDate(ret.getDate() + units);  break;
		case 'hour'   :  ret.setTime(ret.getTime() + units*3600000);  break;
		case 'minute' :  ret.setTime(ret.getTime() + units*60000);  break;
		case 'second' :  ret.setTime(ret.getTime() + units*1000);  break;
		default       :  ret = undefined;  break;
	}
	return ret;
}
/*http://rsvn.myhandycar.com*/
var ApiURL = {
		auth: '/login',
		apiBase: '/api',
		compBase: '/api/company',
		userBase: '/api/user',
		vhcBase: '/api/vehicle',
		rsvnBase: '/api/rsvn'
}
var Api = {
	init: function() {
		$.postJson = function(url, data, callback, errorHandler) {
		    return jQuery.ajax({
				'type': 'POST',
				'url': url,
				'contentType': 'application/json',
				'data': JSON.stringify(data),
				'dataType': 'json',
				'success': callback,
				'error': errorHandler
		    });
		};
	}, postJson: function(uri, data, callback, errorCallback) {
		$.postJson(uri, data, callback).fail(function(error) {
			if(typeof(errorCallback) === 'undefined') {
				alert(error.responseJson);
			} else {
				errorCallback(error.responseJSON);
			}
		});
	}, sendPost: function (url, dataToPost, callback) {
		$.post(url, dataToPost, callback, "json")
		.fail(function(data) {
			if(data.status === 400) {
				callback(data.responseJSON);
			}
		});
	}, sendGet: function(url, callback) {
		$.getJSON(url, callback).fail(function() {
			console.log("$.getJson failed ");
		});
	}, sendAuth:function (authObj, callback) {
		$.ajax(ApiURL.auth,{type:'POST', data: authObj, 
			statusCode: {
				200: function() {callback(200);}
				,201: function() {callback(201);}
				,401: function() {callback(401);}
			}
		});
	}
};

Api.init();