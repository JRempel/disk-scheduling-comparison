package algorithms;

import exceptions.EmptyQueueException;

/**
 * The SSTF (Shortest Seek Time First) algorithm selects requests based on which one is closest
 * to the current head position.
 */
public class SSTF extends AbstractDiskScheduler {
    public SSTF(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
    }

    public void run() {
        int nextRequest;
        while (!requestQueue.isEmpty()) {
            nextRequest = requestQueue
                    .stream()
                    .reduce((request1, request2) -> Math.abs(currentHeadCylinder - request1) < Math.abs(currentHeadCylinder - request2) ? request1 : request2)
                    .orElseThrow(() -> new EmptyQueueException(getClass()));

            calculateChanges(nextRequest);
        }
    }
}