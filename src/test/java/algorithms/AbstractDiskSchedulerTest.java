package algorithms;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import exceptions.InvalidRequestException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

public class AbstractDiskSchedulerTest {
    private int maxCylinder = 199;
    private int minCylinder = 0;
    private AbstractDiskScheduler scheduler;

    @Before
    public void setup() {
        scheduler = mock(AbstractDiskScheduler.class, withSettings()
                .useConstructor(minCylinder, maxCylinder)
                .defaultAnswer(CALLS_REAL_METHODS));
    }

    @Test
    public void setRequestQueueTest() {
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
           add(1);
        }};

        assertNull("Default queue var should be null.", scheduler.requestQueue);
        boolean queueReset = scheduler.setRequestQueue(queue);
        assertTrue("Expected queue to be set.", queueReset);
        assertEquals("Expected set queue to equal input queue.", queue, scheduler.requestQueue);
    }

    @Test
    public void resetTest() {
        scheduler.totalHeadMovements = 5;
        scheduler.currentHeadCylinder = 5;
        scheduler.requestQueue = new ArrayList<Integer>() {{
            add(1);
        }};
        scheduler.orderProcessed.add(1);

        scheduler.reset();

        assertEquals("Expected total head movements to be reset to 0.", 0, scheduler.totalHeadMovements);
        assertEquals("Expected current head cylinder to be reset to 0.", 0, scheduler.currentHeadCylinder);
        assertNull("Expected request queue to be null.", scheduler.requestQueue);
        assertTrue("Expected order proccessed list to be empty.", scheduler.orderProcessed.isEmpty());
    }

    @Test
    public void isValidRequestQueueFailTest() {
        ArrayList<Integer> queue = new ArrayList<>();
        boolean isValid;

        queue.add(minCylinder - 1);
        isValid = scheduler.setRequestQueue(queue);
        assertFalse("Request queue should not take requests less than MIN_DISK_CYLINDER.", isValid);

        queue.clear();
        queue.add(maxCylinder + 1);
        isValid = scheduler.setRequestQueue(queue);
        assertFalse("Request queue should not take requests more than MAX_DISK_CYLINDER.", isValid);

        isValid = scheduler.setRequestQueue(null);
        assertFalse("Request queue should not be null", isValid);

        queue.add(maxCylinder);
        queue.add(minCylinder);
        isValid = scheduler.setRequestQueue(queue);
        assertFalse("Request queue should not contain duplicates", isValid);
    }

    @Test
    public void validateRequestTest() {
        try {
            scheduler.validateRequest(minCylinder - 1);
            fail("Request should not be less than MIN_DISK_CYLINDER.");
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidRequestException);
        }

        try {
            scheduler.validateRequest(maxCylinder + 1);
            fail("Request should not be more than MAX_DISK_CYLINDER.");
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidRequestException);
        }

        try {
            scheduler.validateRequest(null);
            fail("Request should not be null.");
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidRequestException);
        }
    }

    @Test
    public void getNextRequestUpTest() {
        ArrayList<Integer> queue = new ArrayList<>();
        scheduler.requestQueue = queue;
        Integer next, nextExpected, nextNotExpected;
        String assertFormat = "Expected nextRequestUp to be %s.";

        scheduler.currentHeadCylinder = 100;
        nextExpected = 150;
        nextNotExpected = 50;
        queue.add(nextExpected);
        queue.add(nextNotExpected);
        next = scheduler.getNextRequestUp();

        assertEquals(String.format(assertFormat, nextExpected), nextExpected, next);

        queue.clear();
        nextNotExpected = 0;
        queue.add(nextNotExpected);
        next = scheduler.getNextRequestUp();
        assertNull(String.format(assertFormat, null), next);
    }

    @Test
    public void getNextRequestDownTest() {
        ArrayList<Integer> queue = new ArrayList<>();
        scheduler.requestQueue = queue;
        Integer next, nextExpected, nextNotExpected;
        String assertFormat = "Expected nextRequestDown to be %s.";

        scheduler.currentHeadCylinder = 100;
        nextExpected = 50;
        nextNotExpected = 150;
        queue.add(nextExpected);
        queue.add(nextNotExpected);
        next = scheduler.getNextRequestDown();

        assertEquals(String.format(assertFormat, nextExpected), nextExpected, next);

        queue.clear();
        nextNotExpected = 150;
        queue.add(nextNotExpected);
        next = scheduler.getNextRequestDown();
        assertNull(String.format(assertFormat, null), next);
    }
}
