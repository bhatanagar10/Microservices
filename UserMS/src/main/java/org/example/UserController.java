package org.example;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/index")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RestTemplate  restTemplate;

    @Autowired
    private UserMSFeign userMSFeign;

    @GetMapping
    @Retry(name = "index" , fallbackMethod = "fallbackResponse")
    public String message(){
        logger.info("UserMS call received");
        return userMSFeign.getData();
    }

    public String fallbackResponse(Exception e){
        return "returned from fallback";
    }

    @GetMapping("/data")
    public String data(){
        return restTemplate.getForObject("http://localhost:8086/account" , String.class);
    }

}
