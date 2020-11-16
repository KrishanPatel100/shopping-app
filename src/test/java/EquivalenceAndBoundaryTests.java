/*
 * SE 333 Fall 2020
 * Krishan Patel
 * Class Project
 */

import static org.junit.jupiter.api.Assertions.*;

import edu.depaul.se433.shoppingapp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * - This class performs weak normal, weak robust, strong normal, and strong robust equivalence tests as well as boundary tests on the TotalCostCalculator class. The tests in this class verify
 * that the correct result is received for all cases containing valid input values and that the expected result is received for all cases containing at least one invalid input value.
 * - I used the calculate functions from the TotalCostCalculator class to calculate the actual results.
 * - To test both calculate functions within the TotalCostCalculator class, we need one of the enum values from the ShippingType class as an input value, an instance of the ShoppingCart class,
 * an instance of the PurchaseItem class, and an instance of the Bill class.
 */

public class EquivalenceAndBoundaryTests {
  //***********************************************
  // Equivalence tests
  //***********************************************
  @ParameterizedTest
  @MethodSource("provideValidInputForWeakTests")
  void performWeakTestsWithValidInput1(double expectedTotal, double rawPurchasePrice, ShippingType shipping, String state) {
    double actualTotal = TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
    assertEquals(expectedTotal, actualTotal, "performWeakTestsWithValidInput1: expectedTotal does not equal actualTotal");
  }

  @ParameterizedTest
  @MethodSource("provideValidInputForWeakTests")
  void performWeakTestsWithValidInput2(double expectedTotal, double rawPurchasePrice, ShippingType shipping, String state) {
    ShoppingCart cart = new ShoppingCart();
    PurchaseItem newItem = new PurchaseItem("Donuts", rawPurchasePrice, 1);
    cart.addItem(newItem);
    Bill actual = TotalCostCalculator.calculate(cart, state, shipping);
    assertEquals(rawPurchasePrice + TaxCalculator.calculate(rawPurchasePrice, state) + shipping.value, actual.getTotal(), "performWeakTestsWithValidInput2: expected total does not equal actual total");
  }

  private static Stream<Arguments> provideValidInputForWeakTests() {
    return Stream.of(
      Arguments.of(30.00, 20.00, ShippingType.STANDARD, "WA"), //Weak normal & weak robust
      Arguments.of(131.00, 100.00, ShippingType.NEXT_DAY, "IL") //Weak normal & weak robust
    );
  }



  @ParameterizedTest
  @MethodSource("provideInvalidInputForWeakTests")
  void performWeakTestsWithInvalidInput(Object expectedValue, double rawPurchasePrice, ShippingType shipping, String state) {
    try {
      TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
      fail("Exception was expected but did not occur");
    } catch(Exception exc) {
      assertEquals(expectedValue.getClass(), exc.getClass(), "performWeakTestsWithInvalidInput: actual class is not Exception");
    }
  }

  private static Stream<Arguments> provideInvalidInputForWeakTests() {
    return Stream.of(
      Arguments.of(new Exception(), 20.00, ShippingType.STANDARD, null) //Weak robust
    );
  }



  @ParameterizedTest
  @MethodSource("provideValidInputForStrongTests")
  void performStrongTestsWithValidInput(double expectedTotal, double rawPurchasePrice, ShippingType shipping, String state) {
    double actualTotal = TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
    assertEquals(expectedTotal, actualTotal, "performStrongTestsWithValidInput: expectedTotal does not equal actualTotal");
  }

  private static Stream<Arguments> provideValidInputForStrongTests() {
    return Stream.of(
      Arguments.of(30.00, 20.00, ShippingType.STANDARD, "WA"), //Strong normal & strong robust
      Arguments.of(31.20, 20.00, ShippingType.STANDARD, "NY"), //Strong normal & strong robust
      Arguments.of(45.00, 20.00, ShippingType.NEXT_DAY, "WA"), //Strong normal & strong robust
      Arguments.of(46.20, 20.00, ShippingType.NEXT_DAY, "NY"), //Strong normal & strong robust
      Arguments.of(100.00, 100.00, ShippingType.STANDARD, "WA"), //Strong normal & strong robust
      Arguments.of(106.00, 100.00, ShippingType.STANDARD, "IL"), //Strong normal & strong robust
      Arguments.of(125.00, 100.00, ShippingType.NEXT_DAY, "WA"), //Strong normal & strong robust
      Arguments.of(131.00, 100.00, ShippingType.NEXT_DAY, "IL") //Strong normal & strong robust
    );
  }



  @ParameterizedTest
  @MethodSource("provideInvalidInputForStrongTests")
  void performStrongTestsWithInvalidInput(Object expectedValue, double rawPurchasePrice, ShippingType shipping, String state) {
    try {
      TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
      fail("Exception was expected but did not occur");
    } catch(Exception exc) {
      assertEquals(expectedValue.getClass(), exc.getClass(), "performStrongTestsWithInvalidInput: actual class is not Exception");
    }
  }

  private static Stream<Arguments> provideInvalidInputForStrongTests() {
    return Stream.of(
      Arguments.of(new Exception(), 20.00, ShippingType.STANDARD, null), //Strong robust
      Arguments.of(new Exception(), 20.00, ShippingType.NEXT_DAY, null), //Strong robust
      Arguments.of(new Exception(), 100.00, ShippingType.STANDARD, null), //Strong robust
      Arguments.of(new Exception(), 100.00, ShippingType.NEXT_DAY, null) //Strong robust
    );
  }



  //***********************************************
  // Boundary tests
  //***********************************************
  @Test
  @DisplayName("testMaxValueMinusOneAndStandardShipping: Should return the initial cost + $10")
  void testMaxValueMinusOneAndStandardShipping() {
    double actualTotal = TotalCostCalculator.calculate(49.99, "WA", ShippingType.STANDARD);
    double expectedTotal = 59.99;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValueMinusOneAndStandardShipping: expectedTotal does not equal actualTotal");
  }

  @Test
  @DisplayName("testMaxValueMinusOneAndNextDayShipping: Should return the initial cost + $25")
  void testMaxValueMinusOneAndNextDayShipping() {
    double actualTotal = TotalCostCalculator.calculate(49.99, "WA", ShippingType.NEXT_DAY);
    double expectedTotal = 74.99;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValueMinusOneAndNextDayShipping: expectedTotal does not equal actualTotal");
  }

  @Test
  @DisplayName("testMaxValueAndStandardShipping: Should return the initial cost + $10")
  void testMaxValueAndStandardShipping() {
    double actualTotal = TotalCostCalculator.calculate(50.00, "WA", ShippingType.STANDARD);
    double expectedTotal = 60.00;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValueAndStandardShipping: expectedTotal does not equal actualTotal");
  }

  @Test
  @DisplayName("testMaxValueAndNextDayShipping: Should return the initial cost + $25")
  void testMaxValueAndNextDayShipping() {
    double actualTotal = TotalCostCalculator.calculate(50.00, "WA", ShippingType.NEXT_DAY);
    double expectedTotal = 75.00;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValueAndNextDayShipping: expectedTotal does not equal actualTotal");
  }

  @Test
  @DisplayName("testMaxValuePlusOneAndStandardShipping: Should return the initial cost (shipping is free)")
  void testMaxValuePlusOneAndStandardShipping() {
    double actualTotal = TotalCostCalculator.calculate(50.01, "WA", ShippingType.STANDARD);
    double expectedTotal = 50.01;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValuePlusOneAndStandardShipping: expectedTotal does not equal actualTotal");
  }

  @Test
  @DisplayName("testMaxValuePlusOneAndNextDayShipping: Should return the initial cost + $25")
  void testMaxValuePlusOneAndNextDayShipping() {
    double actualTotal = TotalCostCalculator.calculate(50.01, "WA", ShippingType.NEXT_DAY);
    double expectedTotal = 75.01;
    assertEquals(expectedTotal, actualTotal, 1e-10, "testMaxValuePlusOneAndNextDayShipping: expectedTotal does not equal actualTotal");
  }
}
