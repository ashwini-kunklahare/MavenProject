package Practice;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Guru99Test {
	public static WebDriver driver;
	String actual;
	String expected;
	boolean sortingByName;

	@BeforeTest
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("http://live.demoguru99.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	@Test(priority = 0)
	public void verifyTitleOfTheHomePage() {
		driver.findElement(By.xpath("//title[contains(text(),'Home page')]"));
		expected = "Home page";
		actual = driver.getTitle();
		Assert.assertEquals(actual, expected);
		System.out.println("Home page Title verified..");
	}

	@Test(priority = 1, dependsOnMethods = { "verifyTitleOfTheHomePage" })
	public void verifyTitleOfTheMobilePage() {
		driver.findElement(By.xpath("//a[contains(text(),'Mobile')]")).click();
		expected = "Mobile";
		actual = driver.getTitle();
		Assert.assertEquals(actual, expected);
		System.out.println("Mobile page Title verified..");
	}

	@Test(priority = 2, enabled = true)
	public void verifyProductsSortingByName(){
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@title=\"Sort By\"][1]")));
		dropdown.selectByVisibleText("Name");
		System.out.println("Dropdown selected as a Name..");

		List<WebElement> list = driver.findElements(By.xpath("//h2[@class=\"product-name\"]/descendant::a"));
		for (int i = 0; i < list.size(); i++) {

			System.out.println("Sorted Result:" + list.get(i).getText());

			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getText().compareToIgnoreCase(list.get(j).getText()) > 0) {
					sortingByName = true;
					break;
				}

			}
		}
		Assert.assertTrue(sortingByName);
		System.out.println("Products sorted by name..");

	}

	@Test(priority = 3)
	public void verifyCostOfProduct() {
		driver.findElement(By.xpath("//a[contains(text(),'Sony Xperia')]")).click();
		// span[contains(text(),'$100.00')]
		driver.findElement(By.xpath("//span[contains(text(),'$100.00')]")).click();
		String actual = driver.findElement(By.xpath("//span[@id='product-price-1']")).getText();
		expected = "$100.00";
		Assert.assertEquals(actual, expected);
		System.out.println("verified cost of product in List page and detail page are Equal..");

	}

	@AfterTest
	public void tearDown() {
		driver.close();

	}
}
