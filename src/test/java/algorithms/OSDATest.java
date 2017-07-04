package algorithms;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OSDATest {
    final int maxCylinder = 199;
    final int minCylinder = 0;

    @Test
    public void testOSDAScanUp() {
        AbstractDiskScheduler scheduler = new OSDA(minCylinder, maxCylinder);
        scheduler.currentHeadCylinder = maxCylinder / 2;
        int firstCylinder = scheduler.currentHeadCylinder - 1;
        int finalCylinder = maxCylinder;
        int expectedTotalHeadMovement = Math.abs(firstCylinder - finalCylinder);
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(finalCylinder);
            add(firstCylinder);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(finalCylinder);
        }};
        scheduler.setRequestQueue(queue);

        scheduler.run();

        assertEquals("Expected final cylinder to be " + finalCylinder + ".",
                finalCylinder, scheduler.currentHeadCylinder);
        assertEquals("Expected total head movement to be " + expectedTotalHeadMovement + ".",
                expectedTotalHeadMovement, scheduler.totalHeadMovements);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }

    @Test
    public void testOSDAScanDown() {
        AbstractDiskScheduler scheduler = new OSDA(minCylinder, maxCylinder);
        scheduler.currentHeadCylinder = maxCylinder / 2;
        int firstCylinder = scheduler.currentHeadCylinder + 1;
        int finalCylinder = minCylinder;
        int expectedTotalHeadMovement = Math.abs(firstCylinder - finalCylinder);
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(finalCylinder);
            add(firstCylinder);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(finalCylinder);
        }};
        scheduler.setRequestQueue(queue);

        scheduler.run();

        assertEquals("Expected final cylinder to be " + finalCylinder + ".",
                finalCylinder, scheduler.currentHeadCylinder);
        assertEquals("Expected total head movement to be " + expectedTotalHeadMovement + ".",
                expectedTotalHeadMovement, scheduler.totalHeadMovements);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
