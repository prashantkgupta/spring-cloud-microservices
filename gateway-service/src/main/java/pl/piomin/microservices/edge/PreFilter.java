package pl.piomin.microservices.edge;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
public class PreFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PreFilter.class);
    
	@Autowired
	ServletContext context;
	
	@Autowired
	HttpServletRequest httprequest;
	
    @Override
    public String filterType() {
        return "pre";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s Hello request to %s", request.getMethod(), request.getRequestURL().toString()));
        context.setAttribute("Hello", "hi");
        httprequest.setAttribute("Hello1", "hi1");
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
               // final String value = request.getParameter(name);
                if (name.equalsIgnoreCase("Token")) {
                    return "12345";
                }
                return super.getHeader(name);
            }
        };
        
        return null;
    }
}
