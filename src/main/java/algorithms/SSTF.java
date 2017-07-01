package algorithms;

import exceptions.EmptyQueueException;

/**
 * The SSTF (Shortest Seek Time First) algorithm selects requests based on which one is closest
 * to the current head position.
 */
public class SSTF extends AbstractDiskScheduler {
    public void run() {
        int nextRequest;
        while (!requestQueue.isEmpty()) {
            nextRequest = requestQueue
                    .stream()
                    .reduce((request1, request2) -> Math.abs(currentHeadCylinder - request1) < Math.abs(currentHeadCylinder - request2) ? request1 : request2)
                    .orElseThrow(() -> new EmptyQueueException(getClass()));

            validateRequest(nextRequest);
            orderProcessed.add(nextRequest);
            totalHeadMovements += Math.abs(currentHeadCylinder - nextRequest);
            currentHeadCylinder = nextRequest;
            requestQueue.remove((Integer) nextRequest);
        }
    }
}