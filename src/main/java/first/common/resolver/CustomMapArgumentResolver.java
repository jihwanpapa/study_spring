package first.common.resolver;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import first.common.common.CommandMap;

public class CustomMapArgumentResolver implements HandlerMethodArgumentResolver { 
	@Override //Resolver가 적용 가능한지 검사하는 역할
	public boolean supportsParameter(MethodParameter parameter){
		return CommandMap.class.isAssignableFrom(parameter.getParameterType()); //컨트롤러의 파라미터가 CommandMap 클래스인지 검사, Controller의 Map<String,Object> 형식을 CommandMap이라고 변경
	}
	
	@Override //파라미터와 기타 정보를 받아서 실제 객체를 반환
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception{
		CommandMap commandMap = new CommandMap(); //CommandMap 객체를 생성
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		Enumeration<?> enumeration = request.getParameterNames();
		
		String key = null;
		String[] values = null;
		while(enumeration.hasMoreElements()){
			//request에 있는 값을 iterator를 이용하여 하나씩 가져온다.
			key = (String)enumeration.nextElement();
			values = request.getParameterValues(key);
			if(values != null){
				commandMap.put(key,(values.length > 1) ? values:values[0]); //request에 담겨있는 모든 키(key)와 값(value)을 commandMap에 저장
			}
		}
		return commandMap; //모든 파라미터가 담겨있는 commandMap을 반환
	}

}
