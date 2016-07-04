package first.common.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import first.common.common.CommandMap;
import first.common.service.CommonService;

//화면에서 특정 첨부파일 다운로드 요청 -> 서버에서 해당 첨부파일 정보 요청 -> DB에서 파일정보 조회 -> 조회된 데이터를 바탕으로 클라이언트에 다운로드가 가능하도록 데이터 전송

@Controller
public class CommonController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@RequestMapping(value="/common/downloadFile.do")
	public void downloadFile(CommandMap commandMap, HttpServletResponse response) throws Exception{
		//메서드의 파라미터로 기존에 보지 못했던 HttpServletResponse response 라는것이 추가되었다.
		//기존에 첨부파일을 업로드 할때는 HttpServletRequest request가 추가되었었는데, 이번에는 response가 추가되었다.
		//화면에서 서버로 어떤 요청을 할때는 request가 전송되고, 반대로 서버에서 화면으로 응답을 할때는 response에 응답내용이 담기게 된다.
		
		// 첨부파일의 정보를 가져온다.
		Map<String, Object> map = commonService.selectFileInfo(commandMap.getMap());
		
		//idx누락으로 인한 null point exception처리 필요
		String storedFileName = (String)map.get("STORED_FILE_NAME");
		String originalFileName = (String)map.get("ORIGINAL_FILE_NAME");
		
		//실제로 파일이 저장된 위치에서 해당 첨부파일을 읽어서 byte[] 형태로 변환을 해야한다. 
		byte fileByte[] = FileUtils.readFileToByteArray(new File("c:\\dev\\file\\"+storedFileName));
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8")+"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.getOutputStream().write(fileByte);
		
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
}
