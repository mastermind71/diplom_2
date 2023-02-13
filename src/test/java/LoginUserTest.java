import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class LoginUserTest {
    private String accessToken;
    private UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi();
        Response response = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response.then().statusCode(SC_OK);
    }
    @After
    public void deleteCourier() {
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        accessToken = response.then().extract().path("accessToken").toString();
        UserApi.deleteUser(accessToken);
    }
    @Test
    public void loginWithExistentUser(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_OK);
        String actualStatus = response.then().extract().path("success").toString();
        String expectedStatus = "true";
        Assert.assertEquals(actualStatus, expectedStatus);
    }

    @Test
    public void loginWithNotExistentUser(){
        Response response = UserApi.loginUser("akavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_UNAUTHORIZED);
        String actualErrorEmail = response.then().extract().path("message").toString();
        String expectedErrorEmail = "email or password are incorrect";
        Assert.assertEquals(actualErrorEmail, expectedErrorEmail);
        Response response1 = UserApi.loginUser("makavelka71@gmail.com", "12345678");
        response1.then().statusCode(SC_UNAUTHORIZED);
        String actualErrorPassword = response1.then().extract().path("message").toString();
        String expectedErrorPassword = "email or password are incorrect";
        Assert.assertEquals(actualErrorPassword, expectedErrorPassword);
    }
}
