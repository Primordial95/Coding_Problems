import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;
/*
 * Eg: Suppose argument passed is : Dec 2024
 * we have to print Dec 2024 with no of holidays and also dates of weekends
 * and also all the months before Dec 2024 to current month with no of holidays for each month
 * 
 */

public class CodingChallenge1 {

	static WebDriver driver;

	public static void main(String[] args) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--disable-notifications");
		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.redbus.in/");
		List<String> weekendDates = getWeekEndDates("Jan 2025");
		System.out.println(weekendDates);
		driver.quit();
	}

	static List<String> getWeekEndDates(String month) {
		List<String> months = new ArrayList<String>();
		driver.findElement(By.cssSelector("div[id='onwardCal']")).click();
		WebElement noOfHolidays = driver.findElement(By.cssSelector("div[style='flex-grow: 2; font-size: 0.875rem;']"));
		while (!noOfHolidays.getText().contains(month)) {
			System.out.println(noOfHolidays.getText());
			WebElement nextBtn = driver.findElement(By.cssSelector("path[d*='M25']"));
			WebElement parentElement = (WebElement) ((JavascriptExecutor) driver)
					.executeScript("return arguments[0].parentNode;", nextBtn);
			parentElement.click();
		}
		System.out.println(noOfHolidays.getText());
		List<WebElement> weekendDates = driver
				.findElements(By.cssSelector("span[class*='DayTiles__CalendarDaysSpan']"));
		for (WebElement res : weekendDates) {
			String color = res.getCssValue("color");
			String hexValue = Color.fromString(color).asHex();
			if (hexValue.equals("#d84e55") || hexValue.equals("#ffffff")) {
				months.add(res.getText());
			}
		}
		return months;
	}
}
