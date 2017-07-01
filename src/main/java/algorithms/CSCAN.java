package algorithms;

/**
 * The C-SCAN (Circular SCAN) algorithm selects requests that are nearest to the current head
 * position and in the direction the head is traveling. C-SCAN effectively treats the disk as
 * a circular list by jumping from the max cylinder to the min without changing directions
 * (as with SCAN).
 */
public class CSCAN extends AbstractDiskScheduler {
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
