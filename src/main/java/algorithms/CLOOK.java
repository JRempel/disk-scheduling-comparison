package algorithms;

/**
 * The C-LOOK (Circular LOOK) algorithm behaves the same as C-SCAN, with the exception that it
 * doesn't travel to the max cylinder if there are no requests between that and it's current
 * position.
 */
public class CLOOK extends AbstractDiskScheduler {
    public void run() {
        Integer nextRequest;
        while (!requestQueue.isEmpty()) {
            nextRequest = getNextRequestUp();
            if (nextRequest == null) {
                totalHeadMovements += MAX_DISK_CYLINDER - currentHeadCylinder;
                currentHeadCylinder = 0;
                nextRequest = getNextRequestUp();
            }
            validateRequest(nextRequest);
            orderProcessed.add(nextRequest);
            totalHeadMovements += nextRequest - currentHeadCylinder;
            currentHeadCylinder = nextRequest;
            requestQueue.remove(nextRequest);
        }
    }
}
