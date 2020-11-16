/*
 * SE 333 Fall 2020
 * Krishan Patel
 * Class Project
 */

import io.github.bonigarcia.wdm.WebDriverManager;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * - This class tests the UI using Selenium. Specifically, this class tests the six input boxes and the three buttons in the UI to verify that they are performing as expected in all possible cases.
 * - This class is related to the ShoppingCartApi class because this class tests the functionality of the functions within ShoppingCartApi.
 */
@Tag("End2end")
public class SeleniumUITests {
  private static WebDriver driver;
  private SoftAssertions softly;

  @BeforeAll
  static void setup() {
        WebDriverManager.chromedriver().setup();
    }

  @BeforeEach
  void setupSoftly() {
    softly = new SoftAssertions();
    driver = new ChromeDriver();
  }

  @AfterEach
  void executeVerification() {
    softly.assertAll();
    driver.quit();
  }

  //******************************************************************
  // Tests with empty input values
  //******************************************************************
  @Test
  @DisplayName("Press the 'Add item' button")
  void addItemEMPTY() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is an error message
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Error: Request failed with status code 500", msg); //Should have been true, but returned false
  }

  @Test
  @DisplayName("Press the 'Add item' and 'Current total' buttons")
  void addItemAndCurrentTotalEMPTY() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(2000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement currentTotalButton = driver.findElement(By.id("get-price-btn"));
    currentTotalButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is an error message
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Error: Request failed with status code 500", msg);
  }

  @Test
  @DisplayName("Press the 'Add item' and 'Checkout' buttons")
  void addItemAndCheckoutEMPTY() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(2000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement checkoutButton = driver.findElement(By.id("checkout-btn"));
    checkoutButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is an error message
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Error: Request failed with status code 500", msg);
  }



  //******************************************************************
  // Tests with valid input values
  //******************************************************************
  @Test
  @DisplayName("Insert valid input values")
  void insertInputsVALID() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that all text boxes contain data
    Assertions.assertTrue(customerNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(stateField.getAttribute("value").length() > 0);
    Assertions.assertTrue(shippingField.getAttribute("value").length() > 0);
    Assertions.assertTrue(productNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(unitPriceField.getAttribute("value").length() > 0);
    Assertions.assertTrue(quantityField.getAttribute("value").length() > 0);
  }

  @Test
  @DisplayName("Press the 'Add item' button")
  void addItemVALID() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");
    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");

    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");

    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");

    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");

    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");

    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is "Cart contains 1 items"
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Cart contains 1 items", msg);
  }

  @Test
  @DisplayName("Press the 'Add item' and 'Current total' buttons")
  void addItemAndCurrentTotalVALID() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");
    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");

    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");

    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");

    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");

    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");

    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(2000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement currentTotalButton = driver.findElement(By.id("get-price-btn"));
    currentTotalButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is 15
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("total: 15", msg);
  }

  @Test
  @DisplayName("Press the 'Add item' and 'Checkout' buttons")
  void addItemAndCheckoutVALID() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");
    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");

    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");

    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");

    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");

    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");

    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(2000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement checkoutButton = driver.findElement(By.id("checkout-btn"));
    checkoutButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is "ok"
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("ok", msg);
  }

  @Test
  @DisplayName("Press the 'Add item', 'Current total', and 'Checkout' buttons")
  void addItemAndCurrentTotalAndCheckoutVALID() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");
    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");

    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");

    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");

    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");

    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");

    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement currentTotalButton = driver.findElement(By.id("get-price-btn"));
    currentTotalButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement checkoutButton = driver.findElement(By.id("checkout-btn"));
    checkoutButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result = "ok"
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("ok", msg);
  }



  //******************************************************************
  // Other tests
  //******************************************************************
  @Test
  @DisplayName("Add an item that contains a Quantity value < 1")
  void useQuantityLessThanOne() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("-5");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is an error message
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Error: Request failed with status code 500", msg); //Should have been true, but returned false
  }

  @Test
  @DisplayName("Verify that shipping fee and sales tax are included in the total purchase cost")
  void verifyTotalPurchaseCost() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("IL");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("NEXT_DAY");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("3");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement currentTotalButton = driver.findElement(By.id("get-price-btn"));
    currentTotalButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is the correct cost
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("total: 40.9", msg); //Sales tax was calculated on shipping fee, but it shouldn't
  }

  @Test
  @DisplayName("Using an invalid state")
  void useInvalidState() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("Washington");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that the result is an error message
    WebElement resultDiv = driver.findElement(By.id("result"));
    String msg = resultDiv.getText();
    Assertions.assertEquals("Error: Request failed with status code 500", msg); //Should have been true, but returned false
  }

  @Test
  @DisplayName("Verify that the average purchase cost changes after each purchase")
  void checkAveragePurchaseCost() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement checkoutButton = driver.findElement(By.id("checkout-btn"));
    checkoutButton.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Save the first average cost
    WebElement avgDiv = driver.findElement(By.id("avg"));
    String avgPrice1 = avgDiv.getText();

    customerNameField.clear();
    stateField.clear();
    productNameField.clear();
    unitPriceField.clear();
    quantityField.clear();

    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    //Add a second customer
    WebElement customerNameField2 = driver.findElement(By.id("customer-name"));
    customerNameField2.sendKeys("Mitch Jones");
    WebElement stateField2 = driver.findElement(By.id("state"));
    stateField2.sendKeys("WI");
    WebElement shippingField2 = driver.findElement(By.id("shipping"));
    shippingField2.sendKeys("STANDARD");
    WebElement productNameField2 = driver.findElement(By.id("name"));
    productNameField2.sendKeys("Milk");
    WebElement unitPriceField2 = driver.findElement(By.id("unit_price"));
    unitPriceField2.sendKeys("3");
    WebElement quantityField2 = driver.findElement(By.id("quantity"));
    quantityField2.sendKeys("4");

    WebElement addItemButton2 = driver.findElement(By.id("add-item-btn"));
    addItemButton2.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    WebElement checkoutButton2 = driver.findElement(By.id("checkout-btn"));
    checkoutButton2.click();
    try {
      Thread.sleep(5000);
    } catch(Exception e) {
      //Do nothing
    }

    //Compare the first average cost with this new average cost
    WebElement avgDiv2 = driver.findElement(By.id("avg"));
    String avgPrice2 = avgDiv2.getText();
    Assertions.assertNotEquals(avgPrice1, avgPrice2); //The average price should change
  }

  @Test
  @DisplayName("Verify that the screen is not cleared after any button click")
  void checkButtonClicks() {
    driver.get("http://localhost:8080/?#");
    String title = driver.getTitle();
    softly.assertThat(title).as("title").isEqualTo("Online Shopping");

    WebElement customerNameField = driver.findElement(By.id("customer-name"));
    customerNameField.sendKeys("John Smith");
    WebElement stateField = driver.findElement(By.id("state"));
    stateField.sendKeys("WA");
    WebElement shippingField = driver.findElement(By.id("shipping"));
    shippingField.sendKeys("STANDARD");
    WebElement productNameField = driver.findElement(By.id("name"));
    productNameField.sendKeys("Eggs");
    WebElement unitPriceField = driver.findElement(By.id("unit_price"));
    unitPriceField.sendKeys("5");
    WebElement quantityField = driver.findElement(By.id("quantity"));
    quantityField.sendKeys("1");

    WebElement addItemButton = driver.findElement(By.id("add-item-btn"));
    addItemButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that none of the input data has been cleared
    Assertions.assertTrue(customerNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(stateField.getAttribute("value").length() > 0);
    Assertions.assertTrue(shippingField.getAttribute("value").length() > 0);
    Assertions.assertTrue(productNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(unitPriceField.getAttribute("value").length() > 0);
    Assertions.assertTrue(quantityField.getAttribute("value").length() > 0);

    WebElement currentTotalButton = driver.findElement(By.id("get-price-btn"));
    currentTotalButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that none of the input data has been cleared
    Assertions.assertTrue(customerNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(stateField.getAttribute("value").length() > 0);
    Assertions.assertTrue(shippingField.getAttribute("value").length() > 0);
    Assertions.assertTrue(productNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(unitPriceField.getAttribute("value").length() > 0);
    Assertions.assertTrue(quantityField.getAttribute("value").length() > 0);

    WebElement checkoutButton = driver.findElement(By.id("checkout-btn"));
    checkoutButton.click();
    try {
      Thread.sleep(3000);
    } catch(Exception e) {
      //Do nothing
    }

    //Verify that none of the input data has been cleared
    Assertions.assertTrue(customerNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(stateField.getAttribute("value").length() > 0);
    Assertions.assertTrue(shippingField.getAttribute("value").length() > 0);
    Assertions.assertTrue(productNameField.getAttribute("value").length() > 0);
    Assertions.assertTrue(unitPriceField.getAttribute("value").length() > 0);
    Assertions.assertTrue(quantityField.getAttribute("value").length() > 0);
  }
}
