package org.horiga.study.spocktest;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public String process(String name) {
        return "Hello, " + name;
    }
}
