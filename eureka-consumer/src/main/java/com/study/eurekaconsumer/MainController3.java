package com.study.eurekaconsumer;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhenpengmu
 */
@RestController
public class MainController3 {

    @Autowired
    // 抽象
    DiscoveryClient client;

    @Autowired
    EurekaClient client2;

    @Autowired
    LoadBalancerClient lb;


    @Autowired
    RestTemplate restTemplate;


    @GetMapping("/client10")
    public Object client10() {
        // 自动处理URL
        String url ="http://provider/getHi";

        String respStr = restTemplate.getForObject(url, String.class);

        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        System.out.println("entity:" + entity);


        System.out.println(respStr);
        return respStr;
    }
    @GetMapping("/client11")
    public Object client11() {
        // 自动处理URL
        String url ="http://provider/getMap";

        Map<String,String> map = restTemplate.getForObject(url, Map.class);


        System.out.println(map);
        return map;
    }

    @GetMapping("/client12")
    public Object client12() {
        // 自动处理URL
        String url ="http://provider/getObj";

        Person object = restTemplate.getForObject(url, Person.class);

        return object;
    }


    @GetMapping("/client13")
    public Object client13() {
        // 自动处理URL，后面加的占位符参数 代表resttenplate后面的请求参数
        String url = "http://provider/getObj2?name={1}&age={1}";

        Person object = restTemplate.getForObject(url, Person.class, "maxiaoliu666", "123");

        return object;
    }


    @GetMapping("/client14")
    public Object client14() {
        // 自动处理URL
        // 用map的话占位符的名字要对应上
        String url = "http://provider/getObj2?name={name}";
        Map<String, String> map = Collections.singletonMap("name", "xiao66");

        Person object = restTemplate.getForObject(url, Person.class, map);

        return object;
    }


    /**
     * location资源重定向用的
     *
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/client15")
    public Object client15(HttpServletResponse response) throws Exception {
        // 自动处理URL
        String url = "http://provider/postLocation";
        Map<String, String> map = Collections.singletonMap("name", " memeda");
        URI location = restTemplate.postForLocation(url, map, Person.class);
        response.sendRedirect(location.toURL().toString());
        return null;


    }

}
