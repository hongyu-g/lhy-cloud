package com.hong.servicezuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liang
 * @description
 * @date 2020/6/9 17:27
 */
@Component
public class TokenFilter extends ZuulFilter {

    /**
     * 过滤器类型 pre表示在请求之前进行逻辑操作
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器执行顺序
     * 当一个请求在同一个阶段存在多个过滤器的时候 过滤器的执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 编写过滤器拦截的业务逻辑代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getParameter("token");
        if (token == null) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("token is null");
            currentContext.setResponseStatusCode(401);
        }
        return null;
    }
}
