package com.halodoc.restful;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.*;

public class HotelBookingTest extends BaseTest {

    String propertiesFile = "booking.properties";
    String bookingId = null;
    String createUserBody = null;


    @Test
    public void createBooking() throws IOException, JSONException {

        String body = readFile("createBooking.json");
        body = generateDataForCreateBooking(body);
        String url = readDataFromPropertiesFile(propertiesFile,"base_url");

        Response response = given()
                .body(body)
                .contentType("application/json")

                .when()
                .post(url+"/booking");

        Assert.assertEquals(response.getStatusCode(),200);

        JSONObject responseBody = new JSONObject(response.getBody().asString());
        bookingId = responseBody.getString("bookingid");
        createUserBody = body;
        System.out.println(bookingId);
    }

    @Test()
    public void getUserDetails() throws JSONException, IOException {

        createBooking();

        String url = readDataFromPropertiesFile(propertiesFile,"base_url");

        Response response = given()
                .when()
                .get(url+"/booking/"+bookingId);

        Assert.assertEquals(response.getStatusCode(),200);

        JSONObject respBody = new JSONObject(response.getBody().asString());
        JSONObject expectedBody = new JSONObject(createUserBody);

        Assert.assertEquals(respBody.get("firstname"),expectedBody.get("firstname"));
        Assert.assertEquals(respBody.get("lastname"),expectedBody.get("lastname"));
        Assert.assertEquals(respBody.get("additionalneeds"),expectedBody.get("additionalneeds"));

    }


    @Test
    public void updateUserDetails() throws IOException, JSONException {

        getUserDetails();
        String url = readDataFromPropertiesFile(propertiesFile,"base_url");

        String body = readFile("createBooking.json");
        body = generateDataForCreateBooking(body);

        String token = generateAuthToken();
        Response response = given()
                .header("Cookie","token="+token)
                .body(body)
                .contentType("application/json")

                .when()
                .put(url+"/booking"+bookingId);

        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
    }
}
