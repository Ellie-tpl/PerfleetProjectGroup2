package com.Perfleet.step_definitions;

import com.Perfleet.pages.BasePage;
import com.Perfleet.pages.DashboardPage;
import com.Perfleet.pages.LoginPage;
import com.Perfleet.utilities.BrowserUtils;
import com.Perfleet.utilities.ConfigurationReader;
import com.Perfleet.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;


public class ContactStepDefs {

    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String userType) {
        //go to login page
        Driver.get().get(ConfigurationReader.get("url"));
        //based on input enter that user information
        String username =null;
        String password =null;

        if(userType.equals("driver")){
            username = ConfigurationReader.get("driver_username");
            password = ConfigurationReader.get("driver_password");
        }else if(userType.equals("sales manager")){
            username = ConfigurationReader.get("sales_manager_username");
            password = ConfigurationReader.get("sales_manager_password");
        }else if(userType.equals("store manager")){
            username = ConfigurationReader.get("store_manager_username");
            password = ConfigurationReader.get("store_manager_password");
        }
        //send username and password and login
        new LoginPage().login(username,password);
    }

    @Then("the user should see following options")
    public void the_user_should_see_following_options(List<String> menuOptions) {
        BrowserUtils.waitFor(2);
        //get the list of webelement and convert them to list of string and assert
        List<String> actualOptions = BrowserUtils.getElementsText(new DashboardPage().menuOptions);

        Assert.assertEquals(menuOptions,actualOptions);
        System.out.println("menuOptions = " + menuOptions);
        System.out.println("actualOptions = " + actualOptions);
    }

    @When("the user logs in using following credentials")
    public void the_user_logs_in_using_following_credentials(Map<String,String> userInfo) {
        System.out.println(userInfo);
        //use map information to login and also verify firstname and lastname
        //login with map info
        new LoginPage().login(userInfo.get("username"),userInfo.get("password"));
        //verify firstname and lastname
        String actualName = new DashboardPage().getUserName();
        String expectedName = userInfo.get("firstname")+" "+ userInfo.get("lastname");

        Assert.assertEquals(expectedName,actualName);
        System.out.println("expectedName = " + expectedName);
        System.out.println("actualName = " + actualName);

    }

}
