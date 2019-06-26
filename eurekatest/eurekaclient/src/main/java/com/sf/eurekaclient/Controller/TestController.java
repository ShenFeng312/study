package com.sf.eurekaclient.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by SF on 2019/6/20.
 */
@RestController
public class TestController {
    @RequestMapping("/info")
    public String hello(){
        return  "hello!";
    }
}
