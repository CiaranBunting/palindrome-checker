package cme.palindrome.app;

import cme.validation.Validator;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * Simple JAX-RS resource to check for a pallindrome.
 * <p>
 * The message is returned as a JSON object.
 * @author ciaranbunting@gmail.com
 */
@Path("/check")
public class CheckerResource {

  private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

  @Inject
  @Any
  private Instance<Validator> validators;

  @Inject
  private CheckerService checkerService;

  /**
   * Checks if the query param is a palindrome.
   * Username param not currently used.
   *
   * @return {@link Response}
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response checkPalindrome(
    @QueryParam("username") final String username,
    @QueryParam("value") final String value) {

    // Invoke each validator to validate the input value.
    if (validators.stream().allMatch((validator -> validator.validate(value)))) {
      if (checkerService.check(value)) {
        return Response.status(Response.Status.OK)
          .entity(getJsonObject("Nice! You found a palindrome!", true))
          .build();
      } else {
        return Response.status(Response.Status.OK)
          .entity(getJsonObject("Entry is not a palindrome. Please try again.", false))
          .build();
      }
    } else {
      JsonObject entity = JSON.createObjectBuilder()
        .add("error", "Invalid input.")
        .build();
      return Response.status(Response.Status.BAD_REQUEST).entity(entity).build();
    }
  }

  private JsonObject getJsonObject(String message, boolean result) {
    JsonObject entity = JSON.createObjectBuilder()
      .add("result", result)
      .add("message", message)
      .build();
    return entity;
  }
}
