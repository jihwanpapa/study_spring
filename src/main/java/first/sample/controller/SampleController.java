package first.sample.controller;

//import java.util.Iterator;
import java.util.List;
import java.util.Map;
//import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import first.common.common.CommandMap;
import first.sample.service.SampleService;

@Controller
public class SampleController {
	Logger log = Logger.getLogger(this.getClass());
	
	@Resource(name="sampleService")
	private SampleService sampleService;
	
	@RequestMapping(value="/sample/openBoardList.do")
    public ModelAndView openBoardList(CommandMap commandMap) throws Exception{
    	ModelAndView mv = new ModelAndView("/sample/boardList");
    	
    	List<Map<String,Object>> list = sampleService.selectBoardList(commandMap.getMap());
    	mv.addObject("list", list);
    	
    	return mv;
    }
//	
//	@RequestMapping(value="/sample/testMapArgumentResolver.do")
//	public ModelAndView testMapArgumentResolver(CommandMap commandMap) throws Exception{
//		ModelAndView mv = new ModelAndView("");
//		
//		if(commandMap.isEmpty()==false){
//			// commandMap에 있는 모든 파라미터를 iterator를 이용하여 출력
//			Iterator<Entry<String,Object>> iterator = commandMap.getMap().entrySet().iterator();
//			Entry<String,Object> entry = null;
//			while(iterator.hasNext()){
//				entry = iterator.next();
//				log.debug("key : "+entry.getKey()+", value :" + entry.getValue());
//			}
//		}
//		return mv;
//	}
//	
	@RequestMapping(value="/sample/openBoardWrite.do")
	public ModelAndView openBoardWrite(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardWrite");
		return mv;
	}
	
	@RequestMapping (value="/sample/insertBoard.do")
	public ModelAndView insertBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
		sampleService.insertBoard(commandMap.getMap(), request);
		return mv;
	}
	
//	우리가 화면에서 전송한 모든 데이터는 HttpServletRequest에 담겨서 전송되고, 그것을 HandlerMethodArgumentResolver를 이용하여 CommandMap에 담아주었다. 
	
	@RequestMapping(value="/sample/openBoardDetail.do")
	public ModelAndView openBoardDetail(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardDetail");
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		//mv.addObject("map",map);
		mv.addObject("map", map.get("map"));
		mv.addObject("list", map.get("list"));
		
		return mv;
	}
	
	@RequestMapping(value="/sample/openBoardUpdate.do")
	public ModelAndView openBoardUpdate(CommandMap commandMap) throws Exception{
		ModelAndView mv = new ModelAndView("/sample/boardUpdate");
		
		Map<String, Object> map = sampleService.selectBoardDetail(commandMap.getMap());
		//mv.addObject("map",map);
		mv.addObject("map",map.get("map"));
		mv.addObject("list",map.get("list"));
		
		return mv;
	}
	
//	게시글을 수정할 때 해당되는 첨부파일의 수정은 등록과 다르게 좀 복잡한 프로세스를 가진다. 
//	다음의 경우를 생각해보자.
//	1) 게시글의 내용만 수정을 하고, 첨부파일은 수정하지 않는다.
//	2) 첨부파일을 수정할 때 기존에 등록한 파일을 변경한다. 
//	3) 기존에 등록한 파일은 놔두고, 새로운 파일을 추가한다. 
//	4) 기존에 등록한 파일을 모두 삭제하고, 새로운 파일을 등록한다.
//	5) 기존에 등록한 파일의 일부를 삭제하고, 새로운 파일을 등록한다.

	@RequestMapping(value="/sample/updateBoard.do")
	public ModelAndView updateBoard(CommandMap commandMap, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("redirect:/sample/openBoardDetail.do");
		//sampleService.updateBoard(commandMap.getMap());
		sampleService.updateBoard(commandMap.getMap(), request);
		mv.addObject("IDX", commandMap.get("IDX"));
		return mv;
	}
	
	@RequestMapping(value="/sample/deleteBoard.do")
	public ModelAndView deleteBoard(CommandMap commandMap) throws Exception{
	    ModelAndView mv = new ModelAndView("redirect:/sample/openBoardList.do");
	     
	    sampleService.deleteBoard(commandMap.getMap());
	     
	    return mv;
	}
}
