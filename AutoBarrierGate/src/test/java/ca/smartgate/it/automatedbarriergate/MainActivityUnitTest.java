package ca.smartgate.it.automatedbarriergate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityUnitTest {

    @Mock
    LocationFragment.OnParkingSpotSelectedListener locationListenerMock;

    @Mock
    PaymentFragment.OnPaymentSelectedListener paymentListenerMock;

    private MainActivity mainActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainActivity = new MainActivity();
    }

    @After
    public void tearDown() {
        mainActivity = null;
    }

    @Test
    public void testOnParkingSpotSelected() {
        // Set up mock behavior for locationListenerMock
        doNothing().when(locationListenerMock).onParkingSpotSelected(true);

        // Call the method under test
        mainActivity.onParkingSpotSelected(true);

        // Verify that the option is selected
        assertTrue(mainActivity.isOptionSelected);
    }

    @Test
    public void testOnPaymentSelected() {
        // Set up mock behavior for paymentListenerMock
        doNothing().when(paymentListenerMock).onPaymentSelected(true);

        // Call the method under test
        mainActivity.onPaymentSelected(true);

        // Verify that the option is selected
        assertTrue(mainActivity.OptionSelected);
    }

    @Test
    public void testOnPaymentUnselected() {
        // Set up mock behavior for paymentListenerMock
        doNothing().when(paymentListenerMock).onPaymentSelected(false);

        // Call the method under test
        mainActivity.onPaymentSelected(false);

        // Verify that the option is unselected
        assertFalse(mainActivity.OptionSelected);
    }
}