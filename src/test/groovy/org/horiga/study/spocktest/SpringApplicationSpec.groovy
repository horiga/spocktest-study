package org.horiga.study.spocktest;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import spock.lang.Specification;

@ActiveProfiles("test")
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SpringApplicationSpec extends Specification {
    //
}
