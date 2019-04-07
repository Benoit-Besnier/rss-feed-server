package com.bbesniner.rssfeedserver.integration;

import com.bbesniner.rssfeedserver.entities.hibernate.Feed;
import com.bbesniner.rssfeedserver.entities.requestbody.PreferredFeeds;
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

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserIntegrationTest {

    @LocalServerPort
    private int port;

    private String userToken;

    @Before
    public void setup() {
        RestAssured.port = this.port;
        this.userToken = this.getAuthenticatedToken("user", "password");

    }

    private String getAuthenticatedToken(final String user, final String password) {
        final String token = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(UserCredentials.builder().username(user).password(password).build())
                .when()
                .post("/auth/signin")
                .andReturn().jsonPath().getString("token");

        log.debug("Got token: [{}]", token);
        return token;
    }

    @Test
    public void shouldGetPersonalInformation() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get("/users/me")
                .then()
                .assertThat()
                .body("username", equalTo("user"))
                .body("roles", hasItem("ROLE_USER"));
    }

    @Test
    public void shouldAddOneFeedAsPreferred() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get("/users/me")
                .then()
                .assertThat()
                .body("preferredFeeds", is(Collections.emptyList()));

        List<Feed> feeds = RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/feeds")
                .andReturn().jsonPath().getList("", Feed.class);

        assertTrue(feeds.size() > 0);

        final PreferredFeeds preferredFeeds = PreferredFeeds.builder()
                .preferredFeeds(List.of(feeds.get(0).getUuid()))
                .build();

        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .body(preferredFeeds)
                .when()
                .put("/users/me/feeds")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + userToken)
                .when()
                .get("/users/me")
                .then()
                .assertThat()
                .body("preferredFeeds", hasItem(feeds.get(0).getUuid()));
    }

}
