package algorithms;

import static algorithms.SCAN.Direction.DOWN;
import static algorithms.SCAN.Direction.UP;

/**
 * The SCAN algorithm selects requests that are nearest to the current head position and in
 * the direction the head is traveling. When it hits the maximum or minimum cylinder, it
 * repeats the process while moving in the opposite direction.
 */
public class SCAN extends AbstractDiskScheduler {
    enum Direction {
        UP, DOWN
    }

    private Direction currentDirection;

    public SCAN(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
        currentDirection = UP;
    }

    public void run() {
        Integer nextRequest = null;
        while (!requestQueue.isEmpty()) {
            switch (currentDirection) {
                case UP:
                    nextRequest = getNextRequestUp();
                    if (nextRequest == null) {
                        currentDirection = DOWN;
                        totalHeadMovements += (MAX_DISK_CYLINDER - currentHeadCylinder) * 2;
                        nextRequest = getNextRequestDown();
                    }
                    break;
                case DOWN:
                    nextRequest = getNextRequestDown();
                    if (nextRequest == null) {
                        currentDirection = UP;
                        totalHeadMovements += (currentHeadCylinder - MIN_DISK_CYLINDER) * 2;
                        nextRequest = getNextRequestUp();
                    }
                    break;
            }
            calculateChanges(nextRequest);
        }
    }
}
