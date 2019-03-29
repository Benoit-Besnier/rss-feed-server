package com.bbesniner.rssfeedserver.integration;

import com.bbesniner.rssfeedserver.entities.requestbody.FeedCandidate;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Slf4j
public class FeedIntegrationTest {

    @LocalServerPort
    private int port;

    private String userToken;
    private String adminToken;

    private static final String LE_MONDE_FEED_URL = "https://www.lemonde.fr/rss/une.xml";

    @Before
    public void setup() {
        RestAssured.port = this.port;
        this.userToken = this.getAuthenticatedToken("user", "password");
        this.adminToken = this.getAuthenticatedToken("admin", "password");
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
    public void getAllFeeds() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get("/feeds")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void saveLeMondeFeedWithoutBeingAuthenticated() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(FeedCandidate.builder().url(LE_MONDE_FEED_URL).build())
                .when()
                .post("/feeds")
                .then()
                .statusCode(403);
    }

    @Test
    public void saveLeMondeFeedWhenAuthenticated() {
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .body(FeedCandidate.builder().url(LE_MONDE_FEED_URL).build())
                .when()
                .post("/feeds")
                .then()
                .statusCode(201);
    }

    @Test
    public void deleteFeedWithIdEqualOneWhenAuthenticatedAsUser() {
        RestAssured.given()
                .header("Authorization", "Bearer " + userToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("/feeds/1")
                .then()
                .statusCode(403);
    }

    @Test
    public void deleteFeedWithIdEqualOneWhenAuthenticatedAsAdmin() {
        RestAssured.given()
                .header("Authorization", "Bearer " + adminToken)
                .contentType(ContentType.JSON)
                .when()
                .delete("/feeds/1")
                .then()
                .statusCode(204);
    }
}
