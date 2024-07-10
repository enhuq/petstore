package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import api.payload.User;

public class UserEndpointMethods {

	//method to get URLs from properties file
	public static ResourceBundle getURLs() {

		ResourceBundle routes = ResourceBundle.getBundle("routes");  //Loads properties file from resources folder
		return routes;

	}

	public static Response createUser(User payload) {

		String post_url = getURLs().getString("post_url");
		Response response = 
				given()
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.body(payload)
				.when()
					.post(post_url);
		return response;

	}

	public static Response getUser(String userName) {

		String get_url = getURLs().getString("get_url");
		Response response = 
				given()
					.pathParam("username", userName)
				.when()
					.get(get_url);
		return response;

	}

	public static Response updateUser(String userName, User payload) {

		String update_url = getURLs().getString("update_url");
		Response response = 
				given()
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.pathParam("username", userName)
					.body(payload)
				.when()
					.put(update_url);
		return response;

	}

	public static Response deleteUser(String userName) {

		String delete_url = getURLs().getString("delete_url");
		Response response = 
				given()
					.pathParam("username", userName)
				.when()
					.delete(delete_url);
		return response;

	}

}
