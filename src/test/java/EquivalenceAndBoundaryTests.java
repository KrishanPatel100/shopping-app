import static org.junit.jupiter.api.Assertions.*;

import edu.depaul.se433.shoppingapp.ShippingType;
import edu.depaul.se433.shoppingapp.TotalCostCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class EquivalenceAndBoundaryTests {
    //***********************************************
    // Equivalence tests
    //***********************************************

    @ParameterizedTest
    @MethodSource("provideValidInputForWeakTests")
    void weakTestsWithValidInput(double expectedTotal, double rawPurchasePrice, ShippingType shipping, String state) {
        double actualTotal = TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
        assertTrue(actualTotal == expectedTotal);
    }

    private static Stream<Arguments> provideValidInputForWeakTests() {
        return Stream.of(
                Arguments.of(30.00, 20.00, ShippingType.STANDARD, "WA"), //Weak normal & weak robust
                Arguments.of(131.00, 100.00, ShippingType.NEXT_DAY, "IL") //Weak normal & weak robust
        );
    }



    @ParameterizedTest
    @MethodSource("provideInvalidInputForWeakTests")
    void weakTestsWithInvalidInput(Object expectedValue, double rawPurchasePrice, ShippingType shipping, String state) {
        try {
            TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
            fail("Exception was expected but did not occur");
        }
        catch(IllegalArgumentException e) {
            assertEquals(e.getClass(), expectedValue.getClass());
        }
        catch(NullPointerException n) {
            assertEquals(n.getClass(), expectedValue.getClass());
        }
    }

    private static Stream<Arguments> provideInvalidInputForWeakTests() {
        return Stream.of(
            Arguments.of(new NullPointerException(), 20.00, ShippingType.STANDARD, null) //Weak robust
        );
    }



    @ParameterizedTest
    @MethodSource("provideValidInputForStrongTests")
    void StrongTestsWithValidInput(double expectedTotal, double rawPurchasePrice, ShippingType shipping, String state) {
        double actualTotal = TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
        System.out.println("actualTotal = " + actualTotal);
        assertTrue(actualTotal == expectedTotal);
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
    void StrongTestsWithInvalidInput(Object expectedValue, double rawPurchasePrice, ShippingType shipping, String state) {
        try {
            TotalCostCalculator.calculate(rawPurchasePrice, state, shipping);
            fail("Exception was expected but did not occur");
        }
        catch(IllegalArgumentException e) {
            assertEquals(e.getClass(), expectedValue.getClass());
        }
        catch(NullPointerException n) {
            assertEquals(n.getClass(), expectedValue.getClass());
        }
    }

    private static Stream<Arguments> provideInvalidInputForStrongTests() {
        return Stream.of(
            Arguments.of(new NullPointerException(), 20.00, ShippingType.STANDARD, null), //Strong robust
            Arguments.of(new NullPointerException(), 20.00, ShippingType.NEXT_DAY, null), //Strong robust
            Arguments.of(new NullPointerException(), 100.00, ShippingType.STANDARD, null), //Strong robust
            Arguments.of(new NullPointerException(), 100.00, ShippingType.NEXT_DAY, null) //Strong robust
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
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }

    @Test
    @DisplayName("testMaxValueMinusOneAndNextDayShipping: Should return the initial cost + $25")
    void testMaxValueMinusOneAndNextDayShipping() {
        double actualTotal = TotalCostCalculator.calculate(49.99, "WA", ShippingType.NEXT_DAY);
        double expectedTotal = 74.99;
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }

    @Test
    @DisplayName("testMaxValueAndStandardShipping: Should return the initial cost + $10")
    void testMaxValueAndStandardShipping() {
        double actualTotal = TotalCostCalculator.calculate(50.00, "WA", ShippingType.STANDARD);
        double expectedTotal = 60.00;
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }

    @Test
    @DisplayName("testMaxValueAndNextDayShipping: Should return the initial cost + $25")
    void testMaxValueAndNextDayShipping() {
        double actualTotal = TotalCostCalculator.calculate(50.00, "WA", ShippingType.NEXT_DAY);
        double expectedTotal = 75.00;
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }

    @Test
    @DisplayName("testMaxValuePlusOneAndStandardShipping: Should return the initial cost (shipping is free)")
    void testMaxValuePlusOneAndStandardShipping() {
        double actualTotal = TotalCostCalculator.calculate(50.01, "WA", ShippingType.STANDARD);
        double expectedTotal = 50.01;
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }

    @Test
    @DisplayName("testMaxValuePlusOneAndNextDayShipping: Should return the initial cost + $25")
    void testMaxValuePlusOneAndNextDayShipping() {
        double actualTotal = TotalCostCalculator.calculate(50.01, "WA", ShippingType.NEXT_DAY);
        double expectedTotal = 75.01;
        assertEquals(actualTotal, expectedTotal, 1e-10);
    }
}
