import io.restassured.response.Response;
import org.example.DataForLogin;
import org.example.DataForRegistrationUser;
import org.example.UsedUrl;


import static io.restassured.RestAssured.given;

public class UserApi extends BaseApi {
 static public Response createUser(String email, String  password, String name){
     DataForRegistrationUser dataForRegistrationUser = new DataForRegistrationUser(email, password, name);
     return given()
             .spec(baseApi())
             .header("Content-type", "application/json")
             .and()
             .body(dataForRegistrationUser)
             .when()
             .post(UsedUrl.urlCreateUser);
 }
 public static Response deleteUser(String accessToken){
     return given()
             .spec(baseApi())
             .header("Authorization", accessToken)
             .when()
             .delete(UsedUrl.urlDelete);
 }

 static public Response loginUser(String email, String password){
     DataForLogin dataForLogin = new DataForLogin(email, password);
     return given()
             .spec(baseApi())
             .header("Content-type", "application/json")
             .and()
             .body(dataForLogin)
             .when()
             .post(UsedUrl.urlLoginUser);
 }
 static public Response editUser(String email, String password, String accessToken){
     DataForLogin dataForLogin = new DataForLogin(email, password);
     return given()
             .spec(baseApi())
             .header("Authorization", accessToken)
             .header("Content-type", "application/json")
             .and()
             .body(dataForLogin)
             .when()
             .patch(UsedUrl.urlDelete);
 }
}
