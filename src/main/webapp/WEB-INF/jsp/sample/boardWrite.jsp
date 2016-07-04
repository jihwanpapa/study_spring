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
		<input type="file" name="file">
		<br />
		<br />
		<a href="#this" class="btn" id="write">작성하기</a>
		<a href="#this" class="btn" id="list">목록으로</a>
	</form>
	
	<%@ include file = "/WEB-INF/include/include-body.jspf" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_openBoardList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_insertBoard();
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
	</script>
</body>
</html>