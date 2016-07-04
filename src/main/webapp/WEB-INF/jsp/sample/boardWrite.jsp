<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html >
<!-- 화면을 작성하면 controller에 화면을 호출하는 주소를 등록한다. -->
<html lang="ko">
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
</head>
<body>
	<!-- 첨부파일을 생성하기 위해 enctype,name설정 -->
	<!-- 해당 폼을 Multipart 형식임을 알려주는데, 사진, 동영상 등 글자가 아닌 파일은 모두 Multipart 형식의 데이터다.  -->
	<!-- 파일 관련된 개발을 하다보면 상당히 많은 에러가 나는데, 그중에서 가장 많은 경우가 form에 enctype="multipart/form-data"가 선언되지 않은 경우다. -->
	<!-- 따라서, enctype을 설정해주는것을 잊으면 안된다.  -->
	<form id="frm" name="frm" enctype="multipart/form-data"> 
		<table class="board_view">
			<colgroup>
				<col width="15%">
				<col width="*" />
			</colgroup>
			<caption>게시글 작성</caption>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" id="TITLE" name="TITLE" class="wdp_90"></input></td>
				</tr>
				<tr>
					<td colspan="2" class="view_text">
						<textarea rows="20" cols="100" title="내용" id="CONTENTS" name="CONTENTS"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
<!-- 		<input type="file" name="file"> -->
<!-- 파일 추가버튼을 누르면 fileDiv라는 파일 영역의 마지막에 새로운 파일 태그 및 삭제버튼을 추가하도록 하였다. -->
		<div id="fileDiv">
			<p>
				<input type="file" id="file" name="file_0">
<!-- 				삭제버튼에도 삭제 기능을 위한 클릭이벤트를 바인딩 -->
				<a href="#this" class="btn" id="delete" name="delete">삭제</a>
			</p>
		</div>
		<br />
		<br />
		<a href="#this" class="btn" id="addFile">파일추가</a>
		<a href="#this" class="btn" id="write">작성하기</a>
		<a href="#this" class="btn" id="list">목록으로</a>
	</form>
	
	<%@ include file = "/WEB-INF/include/include-body.jspf" %>
	<script type="text/javascript">
	 	var gfv_count = 1;
		
	 	$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_insertBoard();
			});
			
			$("#addFile").on("click", function(e){
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){
				e.preventDefault();
				fn_deleteFile($(this));
			});
		});
		
		function fn_openBoardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/openBoardList.do' />");
			comSubmit.submit();
		}
		
		function fn_insertBoard(){
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/sample/insertBoard.do' />");
			comSubmit.submit();
		}
		
		function fn_addFile(){
// 			input type=file 태그의 name이 동일할 경우, 서버에는 단 하나의 파일만 전송되는 문제가 발생한다. 따라서 gfv_count 라는 전역변수를 선언하고, 태그가 추가될때마다 그 값을 1씩 증가시켜서 name값이 계속 바뀌도록 하였다. 
//파일의 크기나 유효성 검사도 하지 않았으며, 추가할 수 있는 파일의 개수도 제한하지 않았다. 
// 파일의 전송에 따라서 파일의 순서가 바뀔수도 있기 때문에, 첨부파일의 순서를 지정하는 작업도 필요하다. 
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'><a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
// 		삭제버튼을 누르면 해당 버튼이 위치한 <p>태그 자체를 삭제하도록 구성
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
		
	</script>
</body>
</html>