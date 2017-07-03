package algorithms;

/**
 * The LOOK algorithm behaves the same as SCAN, with the exception that it doesn't travel to the
 * min or max cylinder if there are no requests between that and it's current position.
 */
public class LOOK extends AbstractDiskScheduler {
    private enum Direction {
        UP, DOWN
    }

    private Direction direction;

    public LOOK(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
    }

    protected void preRun() {
        direction = Direction.UP;
    }

    public Integer selectNext() {
        Integer nextRequest = 0;
        switch (direction) {
            case UP:
                nextRequest = getNextRequestUp();
                if (nextRequest == null) {
                    direction = Direction.DOWN;
                    nextRequest = getNextRequestDown();
                }
                break;
            case DOWN:
                nextRequest = getNextRequestDown();
                if (nextRequest == null) {
                    direction = Direction.UP;
                    nextRequest = getNextRequestUp();
                }
                break;
        }
        return nextRequest;
    }
}
