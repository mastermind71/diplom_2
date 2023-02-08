import io.restassured.response.Response;
import org.example.DataForOrder;
import org.example.UsedUrl;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi {
    static public Response getIngredient(){
        return given()
                .spec(baseApi())
                .when()
                .get(UsedUrl.urlIngredients);
    }
    static public Response createOrderWithoutAutorization(DataForOrder dataForOrder){
        return given()
                .spec(baseApi())
                .body(dataForOrder)
                .when()
                .post(UsedUrl.urlOrder);
    }
    static public Response createOrderWithAuthorization(DataForOrder dataForOrder, String accessToken){
        return given()
                .spec(baseApi())
                .header("Authorization", accessToken)
                .header("Content-type", "application/json")
                .and()
                .body(dataForOrder)
                .post(UsedUrl.urlOrder);
    }
    static public Response getOrder(String accessToken){
        return given()
                .spec(baseApi())
                .header("Authorization", accessToken)
                .and()
                .get(UsedUrl.urlOrder);
    }
    static public Response getOrderWithoutAuth() {
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .get(UsedUrl.urlOrder);
    }
}
