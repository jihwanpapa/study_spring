package first.sample.service;

import java.util.HashMap;
//import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;

import first.common.util.FileUtils;
import first.sample.dao.SampleDAO;

@Service("sampleService")
public class SampleServiceImpl implements SampleService{
	Logger log = Logger.getLogger(this.getClass());
	
	//FileUtils 클래스를 만들 때, @Component 어노테이션을 이용하여 객체의 관리를 스프링에 맡긴다고 이야기를 하였다. 
	//따라서 클래스를 사용할때 new 를 사용하여 객체를 만드는것이 아니라, 위에서 보는것과 같이 @Resource 어노테이션을 이용하여 객체의 선언만 해주면 된다.
	//그렇게 되면 객체의 생성 및 정리는 스프링에서 알아서 처리를 해준다. 
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource(name="sampleDAO")
	private SampleDAO sampleDAO;
	
	@Override
	public List<Map<String, Object>> selectBoardList(Map<String, Object> map) throws Exception {
		return sampleDAO.selectBoardList(map);
		
	}
	
	@Override
	public void insertBoard(Map<String, Object> map, HttpServletRequest request) throws Exception {
	    sampleDAO.insertBoard(map);
	    
	    // FIleUtils 클래스를 이용하여 파일을 저장하고 그 데이터를 가져온 후, DB에 저장하는 부분이다. 
	    List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
	    for(int i=0, size=list.size(); i<size; i++){
			sampleDAO.insertFile(list.get(i));
		}
	     
//	    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
//	    Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
//	    MultipartFile multipartFile = null;
//	    while(iterator.hasNext()){
//	        multipartFile = multipartHttpServletRequest.getFile(iterator.next());
//	        if(multipartFile.isEmpty() == false){
//	            log.debug("------------- file start -------------");
//	            log.debug("name : "+multipartFile.getName());
//	            log.debug("filename : "+multipartFile.getOriginalFilename());
//	            log.debug("size : "+multipartFile.getSize());
//	            log.debug("-------------- file end --------------\n");
//	        }
//	    }
	}
	
	@Override
	public Map<String, Object> selectBoardDetail(Map<String, Object> map ) throws Exception{
		sampleDAO.updateHitCnt(map);
		//Map<String, Object> resultMap = sampleDAO.selectBoardDetail(map);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> tempMap = sampleDAO.selectBoardDetail(map);
		resultMap.put("map", tempMap);
		
		List<Map<String, Object>> list = sampleDAO.selectFileList(map);
		resultMap.put("list", list);
		
		return resultMap;
	}
	
	@Override
	public void updateBoard(Map<String, Object> map, HttpServletRequest request) throws Exception{
		sampleDAO.updateBoard(map);
		
		sampleDAO.deleteFileList(map);
		List<Map<String,Object>> list = fileUtils.parseInsertFileInfo(map, request);
		Map<String,Object> tempMap = null;
		for(int i=0, size=list.size();i<size;i++){
			tempMap = list.get(i);
			if(tempMap.get("IS_NEW").equals("Y")){
				sampleDAO.insertFile(tempMap);
			}else{
				sampleDAO.updateFile(tempMap);
			}
		}
	}
	
	@Override
	public void deleteBoard(Map<String, Object> map) throws Exception {
	    sampleDAO.deleteBoard(map);
	}

}
