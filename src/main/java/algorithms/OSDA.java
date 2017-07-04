package algorithms;

import java.util.Collections;

/**
 * The OSDA (Optimized Disk Scheduling Algorithm) selects the next request by initially jumping to
 * the nearest max or min request cylinder, after which it performs a linear scan, similar to LOOK.
 * As with LOOK, the initial jump is not counted against the total head movements.
 *
 * Referenced from: https://arxiv.org/ftp/arxiv/papers/1403/1403.0334.pdf
 */
public class OSDA extends AbstractDiskScheduler {
    enum Direction {
        UP, DOWN
    }

    private Direction direction;

    public OSDA(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
    }

    protected void preRun() {
        Collections.sort(requestQueue);
        int selection = (Math.abs(currentHeadCylinder - requestQueue.get(0)) > Math.abs(currentHeadCylinder - requestQueue.get(requestQueue.size() - 1))) ? 1 :
                (Math.abs(currentHeadCylinder - requestQueue.get(0)) < Math.abs(currentHeadCylinder - requestQueue.get(requestQueue.size() - 1))) ? -1 : 0;
        switch (selection) {
            case 1:
                currentHeadCylinder = requestQueue.get(requestQueue.size() - 1);
                orderProcessed.add(requestQueue.get(requestQueue.size() - 1));
                requestQueue.remove(requestQueue.get(requestQueue.size() - 1));
                direction = Direction.DOWN;
                break;
            case -1:
                currentHeadCylinder = requestQueue.get(0);
                orderProcessed.add(requestQueue.get(0));
                requestQueue.remove(requestQueue.get(0));
                direction = Direction.UP;
                break;
            default:
                /**
                 * Current head position is equidistant to min and max request cylinders,
                 * start seek from lower request arbitrarily.
                 */
                currentHeadCylinder = requestQueue.get(0);
                direction = Direction.UP;
        }
    }

    protected Integer selectNext() {
        Integer nextRequest = null;
        switch (direction) {
            case UP:
                nextRequest = getNextRequestUp();
                break;
            case DOWN:
                nextRequest = getNextRequestDown();
                break;
        }
        return nextRequest;
    }
}
