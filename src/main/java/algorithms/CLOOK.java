package algorithms;

/**
 * The C-LOOK (Circular LOOK) algorithm behaves the same as C-SCAN, with the exception that it
 * doesn't travel to the max cylinder if there are no requests between that and it's current
 * position.
 *
 * Note: the jump from max to min requests isn't counted against head movements.
 */
public class CLOOK extends AbstractDiskScheduler {
    public CLOOK(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
        customCalculateChanges = true;
    }

    private boolean cycle;

    protected void preRun() {
        cycle = false;
    }

    public Integer selectNext() {
        Integer nextRequest;
        nextRequest = getNextRequestUp();
        if (nextRequest == null) {
            currentHeadCylinder = 0;
            nextRequest = getNextRequestUp();
            cycle = true;
        }
        validateRequest(nextRequest);
        orderProcessed.add(nextRequest);
        if (!cycle) {
            totalHeadMovements += nextRequest - currentHeadCylinder;
        } else {
            cycle = false;
        }
        currentHeadCylinder = nextRequest;
        requestQueue.remove(nextRequest);
        return nextRequest;
    }
}
