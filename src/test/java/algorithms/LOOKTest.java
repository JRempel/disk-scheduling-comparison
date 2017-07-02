package algorithms;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LOOKTest {
    @Test
    public void testLOOKBounceTop() {
        int maxCylinder = 199;
        int minCylinder = 0;
        AbstractDiskScheduler scheduler = new LOOK(minCylinder, maxCylinder);
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(40);
            add(100);
            add(35);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(100);
            add(40);
            add(35);
        }};
        scheduler.setRequestQueue(queue);
        scheduler.currentHeadCylinder = 50;

        scheduler.run();

        assertEquals("Expected total head movements to equal 115.",
                115, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal 35.",
                35, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }

    @Test
    public void testLOOKBounceBottom() {
        int maxCylinder = 199;
        int minCylinder = 0;
        AbstractDiskScheduler scheduler = new LOOK(minCylinder, maxCylinder);
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(40);
            add(35);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(40);
            add(35);
            add(100);
        }};
        scheduler.setRequestQueue(queue);
        scheduler.currentHeadCylinder = 50;

        scheduler.run();

        queue.clear();
        queue.add(100);
        scheduler.setRequestQueue(queue);

        scheduler.run();

        assertEquals("Expected total head movements to equal 80.",
                80, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal 100.",
                100, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
