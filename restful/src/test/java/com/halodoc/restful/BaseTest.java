package com.halodoc.restful;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Random;

import static io.restassured.RestAssured.*;

public class BaseTest {

    public Logger LOGGER = LogManager.getLogger(this.getClass());

    //method to read data from file
    public String readFile(String fileName) throws IOException {
        String data = "";
        String path = "src/test/resources/" + fileName;

        try {
            FileInputStream fis = new FileInputStream(path);
            data = IOUtils.toString(fis, StandardCharsets.UTF_8);
        } catch(IOException e) {
            LOGGER.error(data+ " File not read properly error");
        }
        return data;
    }

    //read data from properties file
    public String readDataFromPropertiesFile(String fileName, String variable) throws IOException {
        Properties prop = new Properties();
        prop.load(BaseTest.class.getClassLoader().getResourceAsStream(fileName));

        return prop.getProperty(variable);
    }

    //method to generate Auth Token
    public String generateAuthToken() throws IOException, JSONException {

        String propertiesFile = "booking.properties";
        String url=readDataFromPropertiesFile(propertiesFile,"base_url");
        String data = readFile("authToken.json");


        System.out.println(data);
        Response resp = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post(url+"/auth");

        JSONObject response = new JSONObject(resp.getBody().asString());
        System.out.println("RESPONSE TOKEN= "+response.toString());
        String authToken = response.get("token").toString();

        return authToken;

    }

    public String generateDataForCreateBooking(String body) {

        Faker faker = new Faker();
        body = body.replace("{firstname}", faker.name().firstName())
        .replace("{lastname}", faker.name().lastName())
        .replace("{additionalneeds}",faker.food().dish());

        return body;
    }
}


//commons io
//jackson databind
//log4j2
//faker
//lombok
