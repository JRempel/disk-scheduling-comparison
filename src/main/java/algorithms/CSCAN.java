package algorithms;

/**
 * The C-SCAN (Circular SCAN) algorithm selects requests that are nearest to the current head
 * position and in the direction the head is traveling. C-SCAN effectively treats the disk as
 * a circular list by jumping from the max cylinder to the min without changing directions
 * (as with SCAN).
 *
 * Note: the jump from max to min cylinders isn't counted against head movements.
 */
public class CSCAN extends AbstractDiskScheduler {
    public CSCAN(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
    }

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
