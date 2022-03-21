package employees;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.everyItem;

import org.testng.annotations.Test;

import dataReader.PropertyReader;
import io.restassured.RestAssured;

public class TestEmployees{
	@Test(description="Get all employees data and verify status code and empty profle image")
	public void test001() {
		RestAssured.baseURI = PropertyReader.read("app.baseUri");
		
		RestAssured
			.when()
				.get("/employees")
			.then()
				.statusCode(200)
				.body("data.profile_image", everyItem(emptyString()));
	}
}
