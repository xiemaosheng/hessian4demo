package com.xms.task.controller;

import com.alibaba.fastjson.JSONArray;
import com.xms.task.api.HelloService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/8 0008.
 */
@Controller
@RequestMapping("/v0.1/hello")
public class HelloController {

    @Resource
    private HelloService helloService;

    @RequestMapping(value = "/test", name = "测试Hessian框架", method = RequestMethod.GET)
    public void test(HttpServletResponse response) throws IOException {

        int count = 100000;
        JSONArray ja = new JSONArray();
        for (int i = 0; i < 100000; i++) {
            Student s = new Student();
            s.setName("POI" + i);
//            s.setAge(i);
//            s.setBirthday(new Date());
//            s.setHeight(i);
//            s.setWeight(i);
//            s.setSex(i / 2 == 0 ? false : true);
            ja.add(s);
        }
        Map<String, String> headMap = new LinkedHashMap<String, String>();
        headMap.put("name", "姓名");
        headMap.put("age", "年龄");
        headMap.put("birthday", "生日");
        headMap.put("height", "身高");
        headMap.put("weight", "体重");
        headMap.put("sex", "性别");

        String title = "测试";
        /*
        OutputStream outXls = new FileOutputStream("E://a.xls");
        System.out.println("正在导出xls....");
        Date d = new Date();
        ExcelUtil.exportExcel(title,headMap,ja,null,outXls);
        System.out.println("共"+count+"条数据,执行"+(new Date().getTime()-d.getTime())+"ms");
        outXls.close();*/

        String fileName = String.format("%s-%s.xls", "xsxssx", "dsadasd");

        response.setContentType("application/octet-stream");// 设置强制下载不打开
        response.addHeader("Content-Disposition",
                "attachment;fileName=" + fileName);// 设置文件名

        System.out.println("正在导出xlsx....");
        Date d2 = new Date();
        ExcelUtil.downloadExcelFile(title, headMap, ja,response);
        System.out.println("共" + count + "条数据,执行" + (new Date().getTime() - d2.getTime()) + "ms");

    }
}
