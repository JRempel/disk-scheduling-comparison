package algorithms;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SCANTest {
    @Test
    public void testSCANBounceTop() {
        AbstractDiskScheduler scheduler = new SCAN();
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

        assertEquals("Expected total head movements to equal 315.",
                315, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal 35.",
                35, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }

    @Test
    public void testSCANBounceBottom() {
        AbstractDiskScheduler scheduler = new SCAN();
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(40);
            add(100);
            add(35);
        }};
        ArrayList<Integer> expectedOrder = new ArrayList<Integer>() {{
            add(100);
            add(40);
            add(35);
            add(50);
        }};
        scheduler.setRequestQueue(queue);
        scheduler.currentHeadCylinder = 200;

        scheduler.run();

        queue.clear();
        queue.add(50);
        scheduler.setRequestQueue(queue);

        scheduler.run();

        assertEquals("Expected total head movements to equal 250.",
                250, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal 50.",
                50, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the test expected order.",
                expectedOrder, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
