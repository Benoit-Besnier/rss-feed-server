package com.bbesniner.rssfeedserver.integration;

import com.bbesniner.rssfeedserver.entities.requestbody.UserCredentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class AuthIntegrationTest {

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        RestAssured.port = this.port;
    }


    @Test
    public void shouldRegister() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(UserCredentials.builder().username("Jacquie").password("Michel").build())
                .when()
                .post("/auth/register")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldAuthenticate() {
        final String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(UserCredentials.builder().username("user").password("password").build())
                .when()
                .post("/auth/signin")
                .andReturn()
                .jsonPath().getString("token");

        assertNotNull(token);
    }
}
