package algorithms;

import org.junit.Test;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FCFSTest {
    @Test
    public void testFCFS() {
        int maxCylinder = 199;
        int minCylinder = 0;
        AbstractDiskScheduler scheduler = new FCFS(minCylinder, maxCylinder);
        ArrayList<Integer> queue = new ArrayList<Integer>() {{
            add(maxCylinder / 2);
            add(maxCylinder / 4);
            add(maxCylinder / 4 * 3);
        }};
        scheduler.setRequestQueue(queue);
        int initialHeadCylinder = scheduler.currentHeadCylinder;
        int expectedHeadMovements = IntStream.range(1, queue.size())
                .map(i -> Math.abs(queue.get(i) - queue.get(i - 1)))
                .reduce((a, b) -> a + b)
                .orElse(-1) +
                Math.abs(queue.get(0) - initialHeadCylinder);

        scheduler.run();

        assertEquals("Expected total head movements to equal the sum of requests in the queue.",
                expectedHeadMovements, scheduler.totalHeadMovements);
        assertEquals("Expected final currentHeadCylinder to be equal to the final request in the queue.",
                (int) queue.get(queue.size() - 1), scheduler.currentHeadCylinder);
        assertEquals("Expected the order processed to be equal to the input queue order.",
                queue, scheduler.orderProcessed);
        assertTrue("Expected request queue to be empty after processing.", scheduler.requestQueue.isEmpty());
    }
}
