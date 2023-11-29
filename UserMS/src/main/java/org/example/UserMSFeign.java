package org.example;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "accountMS" )
public interface UserMSFeign {

    @GetMapping("/account")
    String getData();
}
