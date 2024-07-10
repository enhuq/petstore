package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpointMethods;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	public Faker faker;
	public User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setup()
	{
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		//for log4j
		logger = LogManager.getLogger(this.getClass());

	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("*******  creating user ********");
		Response response=UserEndpointMethods.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("******* user is created********");
	}
	
	@Test(priority=2)
	public void testGetUserByName() {

		logger.info("*******  get user information ********");
		Response response = UserEndpointMethods.getUser(this.userPayload.getUsername());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("******* user information is displayed ********");

	}	

	@Test(priority=3)
	public void testUpdateUserByUserName()
	{
		logger.info("******* updating user information ********");

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		Response response=UserEndpointMethods.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);

		logger.info("******* user information is updated ********");

		//Checking data after update
		response = UserEndpointMethods.getUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
			
	}

	@Test(priority=4)
	public void testDeleteUserByUserName()
	{
		logger.info("******* deleting user ********");

		Response response=UserEndpointMethods.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("******* user  deleted ********");
			
	}
	
}
