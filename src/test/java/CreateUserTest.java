import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CreateUserTest {
    private String accessToken;
    private UserApi userApi;

    @Before
    public void setUp() {
        userApi = new UserApi();

    }
    @Test
    public void weCanCreateUser(){
        Response response = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response.then().statusCode(SC_OK);
        accessToken = response.then().extract().path("accessToken");
        UserApi.deleteUser(accessToken);
    }

    @Test
    public void doubleCreateUser(){
        Response response = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response.then().statusCode(SC_OK);
        Response response1 = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response1.then().statusCode(SC_FORBIDDEN);
        String actualMessage = response1.then().extract().path("message").toString();
        String expectedMessage = "User already exists";
        Assert.assertEquals(actualMessage, expectedMessage);
        accessToken = response.then().extract().path("accessToken");
        UserApi.deleteUser(accessToken);
    }
    @Test
    public void createWithoutOneField(){
        Response response = UserApi.createUser("", "123456789", "Dmitry");
        response.then().statusCode(SC_FORBIDDEN);
        String actualError = response.then().extract().path("message").toString();
        String expectedError = "Email, password and name are required fields";
        Assert.assertEquals(actualError, expectedError);
    }

}
