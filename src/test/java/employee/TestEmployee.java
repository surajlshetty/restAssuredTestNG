package employee;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import dataReader.DataReader;
import dataReader.PropertyReader;
import io.restassured.RestAssured;

public class TestEmployee {
	@DataProvider
	public Object[] test002() throws IOException {
		Object[] testData = DataReader.getDataFromSheet();
		return testData;
	}

	@Test(dataProvider = "test002", description = "Get data related given employee ID and verify schema and message")
	public void test002(String employeeID) {
		RestAssured.baseURI = PropertyReader.read("app.baseUri");

		RestAssured
			.given()
				.basePath("employee")
			.when()
				.get("/{employeeID}", Integer.parseInt(employeeID))
			.then()
				.statusCode(200)
				.assertThat().body(matchesJsonSchemaInClasspath("employeeSchema.json"))
				.body("message", is("Successfully! Record has been fetched."));
	}
}
