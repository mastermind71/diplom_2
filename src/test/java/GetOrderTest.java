import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetOrderTest {
private String accessToken;
    @Before
    public void createUser(){
        Response response = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response.then().statusCode(SC_OK);
    }
    @After
    public void deleteUser(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        accessToken = response.then().extract().path("accessToken").toString();
        UserApi.deleteUser(accessToken);
    }
    @Test
    public void getOrderWithAuthorization(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_OK);
        accessToken = response.then().extract().path("accessToken");
        Response response1 = OrderApi.getOrder(accessToken);
        response1.then().statusCode(SC_OK);
        String actualStatus = response1.then().extract().path("success").toString();
        String expectedStatus = "true";
        Assert.assertEquals(actualStatus, expectedStatus);
    }
    @Test
    public void getOrderWithoutAuthorization(){
        Response response = OrderApi.getOrderWithoutAuth();
        response.then().statusCode(SC_UNAUTHORIZED);
        String actualError = response.then().extract().path("message").toString();
        String expectedError = "You should be authorised";
        Assert.assertEquals(actualError, expectedError);
    }
}
