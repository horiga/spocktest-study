package org.horiga.study.spocktest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Value("${hello.namePrefix:Hello, }")
    private String namePrefix;

    public String process(String name) throws Exception {
        if ("shit".equalsIgnoreCase(name)) {
            throw new IllegalArgumentException("Banned word.");
        }
        return namePrefix + ' ' + name;
    }
}
