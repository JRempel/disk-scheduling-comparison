package algorithms;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CSCANTest {
    int maxCylinder = 199;
    int minCylinder = 0;

    @Test
    public void testCSCAN() {
        AbstractDiskScheduler scheduler = new CSCAN(minCylinder, maxCylinder);
        int finalCylinder = maxCylinder / 4;
        int firstCylinder = maxCylinder / 4 * 3;
        int secondCylinder = maxCylinder / 5;
        int startHeadCylinder = maxCylinder / 2;
        int expectedTotalHeadMovements = (maxCylinder - startHeadCylinder) + (finalCylinder - minCylinder);

        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(finalCylinder);
            add(firstCylinder);
            add(secondCylinder);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(firstCylinder);
            add(secondCylinder);
            add(finalCylinder);
        }};
        scheduler.setRequestQueue(queue);
        scheduler.currentHeadCylinder = startHeadCylinder;

        scheduler.run();

        assertEquals("Expected total head movements to equal " + expectedTotalHeadMovements + ".",
                expectedTotalHeadMovements, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal " + finalCylinder + ".",
                finalCylinder, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
