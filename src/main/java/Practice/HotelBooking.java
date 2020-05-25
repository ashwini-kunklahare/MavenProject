package Practice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HotelBooking {
	public static WebDriver driver;
	String actual;
	String expected;
	String checkinDate;
	String checkoutDate;

	@BeforeTest
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.phptravels.net");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test(priority = 0, enabled = false)
	public void verifyHomepageTitle() {
		expected = "PHPTRAVELS | Travel Technology Partner";
		actual = driver.getTitle();
		Assert.assertEquals(actual, expected);
		System.out.println("Title checked : " + actual);
	}

	@Test(priority = 1, enabled = false)
	public void verifyLogoAvailability() {
		WebElement actual = driver.findElement(By.xpath("//div[@class='header-logo go-right']//a//img"));
		expected = "logo";
		boolean LogoPresent = true;
		if (!LogoPresent) {
			System.out.println("Logo not Displayed");

		} else {
			System.out.println("Logo Displayed");
		}

	}

	@Test(priority = 2, enabled = false)
	public void verifyLogoFunctionalityOfRedirectingToHomepage() throws InterruptedException {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//body/div/header/div/div/div/div/div/nav/ul/li[2]/a[1]")))
				.build().perform();
		driver.findElement(By.xpath("//body//header[@id='header-waypoint-sticky']//li//li[1]//a[1]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@class='header-logo go-right']//a//img")).click();
		String actual = "https://www.phptravels.net/home";
		String expected = "https://www.phptravels.net/contact-us";
		Assert.assertEquals(actual, expected);
		System.out.println("Test Case will fail");

	}

	@Test(priority = 3, enabled = false)
	public void verifySignUp() throws InterruptedException {

		driver.findElement(
				By.xpath("//div[@class='dropdown dropdown-login dropdown-tab']/descendant::a[@id='dropdownCurrency']"))
				.click();
		driver.findElement(By.xpath("//a[@class=\"dropdown-item tr\"]")).click();
		System.out.println("Clicked Signup");
		driver.findElement(By.xpath("//input[@name=\"firstname\"]")).sendKeys("Ashwini");
		driver.findElement(By.xpath("//input[@name=\"lastname\"]")).sendKeys("Kunklahare");
		driver.findElement(By.xpath("//input[@name=\"phone\"]")).sendKeys("8877665544");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("ashwini12345@gmail.com");
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("phptest");
		driver.findElement(By.xpath("//input[@name=\"confirmpassword\"]")).sendKeys("phptest");
		driver.findElement(By.xpath("//form[@id='headersignupform']//div//button")).click();
		actual = driver.getTitle();
		expected = "Register";
		Assert.assertEquals(actual, expected);
		System.out.println("Registered Successfully");
	}

	@Test(priority = 4, enabled = false)
	public void verifyLoginWithValidInput() {
		driver.findElement(
				By.xpath("//div[@class='dropdown dropdown-login dropdown-tab']/descendant::a[@id='dropdownCurrency']"))
				.click();
		driver.findElement(By.xpath("//a[@class='dropdown-item active tr']")).click();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("user@phptravels.com");
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("demouser");
		driver.findElement(By.xpath("//form[@id='loginfrm']//button")).click();
		actual = driver.getTitle();
		expected = "Login";
		Assert.assertEquals(actual, expected);
		System.out.println("Logged Successfully");
	}

	@Test(priority = 5, enabled = false)
	public void verifyLoginWithInvalidInput() {
		driver.findElement(
				By.xpath("//div[@class='dropdown dropdown-login dropdown-tab']/descendant::a[@id='dropdownCurrency']"))
				.click();
		driver.findElement(By.xpath("//a[@class='dropdown-item active tr']")).click();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("user123@phptravels.com");
		driver.findElement(By.xpath("//input[@name=\"password\"]")).sendKeys("demouser123");
		driver.findElement(By.xpath("//form[@id='loginfrm']//button")).click();
		WebElement actual = driver.findElement(By.xpath("//div[contains(text(),\"Invalid Email or Password\")]"));
		expected = "Login";
		Assert.assertEquals(actual, expected);
		System.out.println("LogIn Failed");
	}

	@Test(priority = 6, enabled = false)
	public void verifyDefaultHotelTabSelected() {
		driver.get("https://www.phptravels.net");
		if (driver.findElement(By.xpath("//a[@class='text-center hotels active']")).isDisplayed()) {
			boolean selectedHotelTab = true;

			Assert.assertTrue(selectedHotelTab);
			System.out.println("Test case for by default selected HOtel tab");
		}
	}

	@Test(priority = 7, enabled = true)
	public void searchHotels(String checkinDate, String checkoutDate) throws InterruptedException {
		driver.findElement(By.xpath("//a[text()='Home']")).click();
		driver.findElement(By.xpath("//input[@id=\"s2id_autogen2\"]")).sendKeys("Pune");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement destination = wait.until(
				ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(text(),', India')]"))));
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//div[contains(text(),', India')]"))).click().build()
				.perform();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id='checkin']")).clear();
		driver.findElement(By.xpath("//input[@id='checkin']")).sendKeys(checkinDate);
		driver.findElement(By.xpath("//input[@id='checkout']")).clear();
		driver.findElement(By.xpath("//input[@id='checkout']")).sendKeys(checkoutDate);
	}

	// @AfterTest
	// public void tearDown() {
	// driver.close();

	// }

}
