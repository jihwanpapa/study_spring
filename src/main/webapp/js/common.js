/**
 * 널(null) 체크하는 함수
 */

function gfn_isNull(str){
	if (str == null) return true;
	if (str == "NaN") return true;
	if (new String(str).valueOf()=="undefined") return true;
	var chkStr = new String(str);
	if (chkStr.valueOf()== "undefined") return true;
	if (chkStr == null) return true;
	if (chkStr.toString().length == 0) return true;
	return false;
}

/**
 * submit 기능을 하는 함수
 * 보통 전송기능인 submit은 form과 <input type="submit"> 을 많이 사용한다. 
 * 파라미터의 동적 추가나 공통적인 파라미터 추가, 아무것도 없을때의 화면이동이 불편한 경우가 많다. 따라서 숨겨둔 form을 하나 만들어놓고,그 폼을 이용하여 페이지의 이동 및 입력값 전송을 하려고 한다.
 */

function ComSubmit(opt_formId){
	this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
	this.url = "";
	
	if(this.formId == "commonForm"){
		$("#commonForm")[0].reset();
	}
	
	this.setUrl = function setUrl(url){
		this.url = url;
	};
	
	this.addParam = function addParam(key,value){
		$("#"+this.formId).append($("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' >"));
	};
	
	this.submit = function submit(){
		var frm = $("#"+this.formId)[0];
		frm.action = this.url;
		frm.method = "post";
		frm.submit();
	};
}