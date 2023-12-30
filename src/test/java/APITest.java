import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;
import space.gbsdev.endpoint.EndpointBase;

import java.nio.file.Path;

public class APITest {

    public Path resourcePath = Path.of("src","test","resources");
    @Test
    public void checkHebrew(){
        var base = EndpointBase
                .builder("https://www.hebcal.com")
                .withJsonBasePath(resourcePath);
        //save above variable, and use anytime like:
        var endpoint= base.makeWithJson("hebrew_converter.json");
        Response response = endpoint.send();
        response.then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON)
                .body("$", Matchers.hasKey("afterSunset"))
                .body("$", Matchers.hasKey("heDateParts"))
                .body("$", Matchers.hasKey("events"));
    }
    @Test
    public void checkWithoutParameters(){
        EndpointBase
                .builder("https://emojihub.yurace.pro")
                .withJsonBasePath(resourcePath)
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
