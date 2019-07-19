package com.bjpowernode.crm.web.listen;

import com.bjpowernode.crm.setting.domain.DicValue;
import com.bjpowernode.crm.setting.service.DicService;
import com.bjpowernode.crm.setting.service.impl.DicServiceImpl;
import com.bjpowernode.crm.utils.ServiceFactory;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Author: 甘明波
 * 2019-07-18
 */
public class SysListen implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        DicService service = (DicService) ServiceFactory.getService(DicServiceImpl.class);
        Map<String, List<DicValue>> map = service.getAll();
        Set<String> set = map.keySet();
        for (String s : set) {
            context.setAttribute(s, map.get(s));
        }
    }
}
