/*
 * SE 333 Fall 2020
 * Krishan Patel
 * Class Project
 */

import edu.depaul.se433.shoppingapp.Purchase;
import edu.depaul.se433.shoppingapp.PurchaseAgent;
import edu.depaul.se433.shoppingapp.PurchaseDBO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * - This class performs unit testing using mocks of the database interface.
 * - This class tests the class purchaseAgent by mocking the class purchaseDBO.
 */

public class MockTests {
  @Test
  @DisplayName("Should call the PurchaseDBO and return an empty list and 0.0")
  void testZeroPurchases() {
    PurchaseDBO pDBO = mock(PurchaseDBO.class);

    when(pDBO.getPurchases()).thenReturn(new ArrayList<Purchase>());

    PurchaseAgent pAgent = new PurchaseAgent(pDBO);
    pAgent.getPurchases();
    pAgent.averagePurchase();
    verify(pDBO, times(2)).getPurchases();
  }

  @Test
  @DisplayName("Should call the PurchaseDBO and return a list containing 1 purchase and the cost of that purchase")
  void testOnePurchase() {
    PurchaseDBO pDBO = mock(PurchaseDBO.class);
    List<Purchase> purchasesList = new ArrayList<Purchase>();
    purchasesList.add(Purchase.make("John Smith", LocalDate.of(2020, 10, 31), 20, "IL", "STANDARD"));

    when(pDBO.getPurchases()).thenReturn(purchasesList);

    PurchaseAgent pAgent = new PurchaseAgent(pDBO);
    pAgent.save(purchasesList.get(0));
    pAgent.getPurchases();
    pAgent.averagePurchase();
    verify(pDBO, times(1)).savePurchase(any(Purchase.class));
    verify(pDBO, times(2)).getPurchases();
  }

  @Test
  @DisplayName("Should call the PurchaseDBO and return a list containing multiple purchases and the average cost of those purchases")
  void testMultiplePurchases() {
    PurchaseDBO pDBO = mock(PurchaseDBO.class);
    List<Purchase> purchasesList = new ArrayList<Purchase>();
    purchasesList.add(Purchase.make("John Smith", LocalDate.of(2020, 10, 31), 20, "IL", "STANDARD"));
    purchasesList.add(Purchase.make("David Johnson", LocalDate.of(2016, 1, 11), 60, "CA", "NEXT_DAY"));
    purchasesList.add(Purchase.make("Janet Jones", LocalDate.of(2018, 4, 26), 13, "WA", "NEXT_DAY"));
    purchasesList.add(Purchase.make("Ethan Butler", LocalDate.of(2019, 8, 3), 48, "AZ", "STANDARD"));

    when(pDBO.getPurchases()).thenReturn(purchasesList);

    PurchaseAgent pAgent = new PurchaseAgent(pDBO);
    pAgent.getPurchases();
    pAgent.averagePurchase();
    verify(pDBO, times(2)).getPurchases();
  }
}
