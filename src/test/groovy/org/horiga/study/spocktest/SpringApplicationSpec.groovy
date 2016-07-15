package org.horiga.study.spocktest;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

import spock.lang.Specification;

@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SpringApplicationSpec extends Specification {
    //
}
