package cme.palindrome.app;

import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.WebTarget;


import io.helidon.microprofile.tests.junit5.AddBean;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.helidon.microprofile.tests.junit5.HelidonTest;

/**
 * Helidon based app level tests. Starts the CDI container and can exercise the REST API.
 * @author ciaranbunting@gmail.com
 */
@HelidonTest
class AppTest {

    @Inject
    private WebTarget target;

    @Test
    void testHelloWorld() {
        JsonObject palindromeFound = target
                .path("check")
                .queryParam("value", "kayak")
                .request()
                .get(JsonObject.class);
        Assertions.assertEquals("Nice! You found a palindrome!", palindromeFound.getString("message"),
                "Found palindrome message");
        Assertions.assertTrue(palindromeFound.getBoolean("result"), "result true");

        JsonObject palindromeNotFound = target
          .path("check")
          .queryParam("value", "hello")
          .request()
          .get(JsonObject.class);
        Assertions.assertEquals("Entry is not a palindrome. Please try again.", palindromeNotFound.getString("message"),
          "Found palindrome message");
        Assertions.assertFalse(palindromeNotFound.getBoolean("result"), "result false");

        try (Response r = target
                .path("metrics")
                .request()
                .get()) {
            Assertions.assertEquals(200, r.getStatus(), "GET metrics status code");
        }

        try (Response r = target
                .path("health")
                .request()
                .get()) {
            Assertions.assertEquals(200, r.getStatus(), "GET health status code");
        }
    }
}
