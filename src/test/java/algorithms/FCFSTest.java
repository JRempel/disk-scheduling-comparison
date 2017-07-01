package algorithms;

import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FCFSTest {
    @Test
    public void testFCFS() {
        AbstractDiskScheduler scheduler = new FCFS();
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(100);
            add(50);
            add(150);
        }};
        scheduler.setRequestQueue(queue);

        scheduler.run();

        assertEquals("Expected total head movements to equal the sum of requests in the queue.",
                250, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal to the final request in the queue.",
                150, scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the input queue order.",
                queue, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
