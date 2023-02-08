import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.UsedUrl;

public class BaseApi {
    public static RequestSpecification baseApi(){
        return new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(UsedUrl.burgerUrl).build();
    }
}
