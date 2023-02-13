import io.restassured.response.Response;
import org.example.DataForOrder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;

public class CreateOrderTest {
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
    public void createOrderWithAuthorization(){
        Response response  = UserApi.loginUser("makavelka71@gmail.com", "123456789");
        accessToken = response.then().extract().path("accessToken").toString();
        Response response1 = OrderApi.getIngredient();
        List <String> ingredients = new ArrayList<>();
        ingredients.add(response1.then().extract().path("data[1]._id"));
        ingredients.add(response1.then().extract().path("data[2]._id"));
        DataForOrder dataForOrder = new DataForOrder(ingredients);
        Response response2 = OrderApi.createOrderWithAuthorization(dataForOrder, accessToken);
        response2.then().statusCode(SC_OK);
        String actualStatus = response1.then().extract().path("success").toString();
        String expectedStatus = "true";
        Assert.assertEquals(actualStatus, expectedStatus);
    }
    @Test
    public void createOrderWithoutAuthorization(){
        Response response = OrderApi.getIngredient();
        List <String> ingredients = new ArrayList<>();
        ingredients.add(response.then().extract().path("data[0]._id"));
        ingredients.add(response.then().extract().path("data[1]._id"));
        DataForOrder dataForOrder = new DataForOrder(ingredients);
        Response response1 = OrderApi.createOrderWithoutAutorization(dataForOrder);
        response1.then().statusCode(SC_OK);
        String actualStatus = response1.then().extract().path("success").toString();
        String expectedStatus = "true";
        Assert.assertEquals(actualStatus, expectedStatus);
    }
    @Test
    public void createOrderWithoutIngredients(){
        List <String> ingredients = new ArrayList<>();
        DataForOrder dataForOrder = new DataForOrder(ingredients);
        Response response = OrderApi.createOrderWithoutAutorization(dataForOrder);
        response.then().statusCode(SC_BAD_REQUEST);
        String actualError = response.then().extract().path("message").toString();
        String expectedStatus = "Ingredient ids must be provided";
        Assert.assertEquals(actualError, expectedStatus);
    }
    @Test
    public void createOrderWithFalseIngredients(){
        List<String> ingredients = new ArrayList<>();
        ingredients.add("Vse ravno kakoi ingredient");
        ingredients.add("eseOdinNepravilniiIngredient");
        DataForOrder dataForOrder = new DataForOrder(ingredients);
        Response response = OrderApi.createOrderWithoutAutorization(dataForOrder);
        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}

