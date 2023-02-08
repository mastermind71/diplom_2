import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class EditUserTest {
    private String accessToken;
    private UserApi userApi;

    @Before
    public void setUp(){
        userApi = new UserApi();
        Response response = UserApi.createUser("makavelka71@gmail.com", "123456789", "Dmitry");
        response.then().statusCode(SC_OK);
    }

    @Test
    public void editUserEmail(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_OK);
        accessToken = response.then().extract().path("accessToken");
        Response response1 = UserApi.editUser("makavelka70@gmail.com", "123456789", accessToken);
        response1.then().statusCode(SC_OK);
        String actualMessageWithEmail = response1.then().extract().path("success").toString();
        String expectedMessageWithEmail = "true";
        Assert.assertEquals(actualMessageWithEmail, expectedMessageWithEmail);
        UserApi.deleteUser(accessToken);
    }

    @Test
    public void editUserPassword(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_OK);
        accessToken = response.then().extract().path("accessToken");
        Response response1 = UserApi.editUser("makavelka71@gmail.com", "234567890", accessToken);
        response1.then().statusCode(SC_OK);
        String actualMessageWithPassword = response1.then().extract().path("success").toString();
        String expectedMessageWithPassword = "true";
        Assert.assertEquals(actualMessageWithPassword , expectedMessageWithPassword);
        UserApi.deleteUser(accessToken);
    }

    @Test
    public void editUserWithoutAuthorization(){
        Response response = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        response.then().statusCode(SC_OK);
        accessToken = response.then().extract().path("accessToken");
        Response response1 = UserApi.editUser("makavelka71@gmail.com", "123456780", "NoAccessToken");
        response1.then().statusCode(SC_UNAUTHORIZED);
        String actualErrorForEdit = response1.then().extract().path("message");
        String expectedErrorForEdit = "You should be authorised";
        Assert.assertEquals(actualErrorForEdit, expectedErrorForEdit);
        UserApi.deleteUser(accessToken);
    }
}
