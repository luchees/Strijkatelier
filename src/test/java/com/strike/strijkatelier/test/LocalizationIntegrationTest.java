package com.strike.strijkatelier.test;

import com.strike.strijkatelier.*;
import com.strike.strijkatelier.spring.*;
import io.restassured.*;
import io.restassured.specification.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {StrijkatelierApplication.class, TestIntegrationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocalizationIntegrationTest {

    @Value("${local.server.port}")
    int port;

    @Before
    public void init() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void given_theLanuageParamterIsEnglish_then_the_title_of_the_log_page_is_Login() {
        final RequestSpecification request = RestAssured.given().param("lang", "en");
        request.when().get("/login").then().assertThat().statusCode(200).and().body(containsString("<h1>Login</h1>"));
    }

    @Test
    public void given_theLanuageParamterIsSpanish_then_the_title_of_the_log_page_is_Ingreso() {
        final RequestSpecification request = RestAssured.given().param("lang", "es_ES");
        request.when().get("/login").then().assertThat().statusCode(200).and().body(containsString("<h1>Ingreso</h1>"));
    }
}
