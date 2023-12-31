import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import space.gbsdev.endpoint.EndpointBase;

import java.nio.file.Path;

/**
 * Test class for API endpoint validations.
 */
public class APITest {

    private final Path resourcePath = Path.of("src", "test", "resources");

    /**
     * Verifies the behavior of the Hebrew conversion API.
     * This test checks specific properties in the response after making a request to the Hebrew conversion API.
     */
    @Test
    public void verifyHebrewConversionAPI() {
        EndpointBase.builder("https://www.hebcal.com")
                .withJsonBasePath(resourcePath)
                .makeWithJson("hebrew-api/hebrew_converter.json")
                .send()
                .then()
                .assertThat().statusCode(200);
    }

    /**
     * Verifies the behavior of the Emoji Hub API without providing specific parameters.
     * This test checks various properties in the response after making a request to the Emoji Hub API without parameters.
     */
    @Test
    public void verifyEmojiHubAPIWithoutParameters() {
        EndpointBase.builder("https://emojihub.yurace.pro")
                .makeWithPath("/api/random")
                .send()
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON)
                .body("$", Matchers.hasKey("name"))
                .body("$", Matchers.hasKey("category"))
                .body("$", Matchers.hasKey("group"))
                .body("$", Matchers.hasKey("htmlCode"))
                .body("$", Matchers.hasKey("unicode"));

    }
}
