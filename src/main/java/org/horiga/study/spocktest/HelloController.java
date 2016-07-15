package org.horiga.study.spocktest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public Map<String, Object> hello(@RequestParam String name) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("message", helloService.process(name));
        return result;
    }

}
