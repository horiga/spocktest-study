package org.horiga.study.spocktest

import org.mockito.internal.util.reflection.Whitebox
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.http.HttpStatus.*
import static spock.util.matcher.HamcrestSupport.*
import static com.jayway.jsonpath.matchers.JsonPathMatchers.*
import static org.hamcrest.Matchers.is

class HelloControllerSpec extends SpringApplicationSpec {

    @Autowired
    HelloService helloService

    MockMvc mockMvc

    def setup() {
        def helloController = new HelloController();
        Whitebox.setInternalState(helloController, 'helloService', helloService);
        mockMvc = standaloneSetup(helloController).build();
    }

    def "simple success"() {
        setup:
        // ResultActions
        def resultActions = mockMvc.perform(get("/hello?name=${name}"))
        // MvcResult
        def mvcResult = resultActions.andReturn().response

        when:
        def json = mvcResult.contentAsString
        // output to console
        println json

        then:
        // resultActions expect
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath('\$.message').value(resultName))

        // mvcResult expect
        mvcResult.status == OK.value()
        mvcResult.contentType.contains('application/json')
        expect json, hasJsonPath('\$.message', is(resultName))

        expect:

        cleanup:
        // N/A

        where:
        name     | resultName
        'horiga' | 'Jamboree, horiga'
        'duke'   | 'Jamboree, duke'
    }

    @Ignore
    def "banned word test"() {
        setup:
        def res = mockMvc.perform(get("/hello?name=shit")).andReturn().response

        when:
        def json = res.contentAsString
        println json

        then:
        res.status == INTERNAL_SERVER_ERROR.value()
        res.contentType.contains('application/json')
        expect json, hasJsonPath('\$.error')
    }

    def "not found error"() {
        setup:
        def res = mockMvc.perform(get("/unknown")).andReturn().response

        when:
        def json = "json >> " + res.contentAsString
        println json

        then:
        res.status == NOT_FOUND.value()
    }
}