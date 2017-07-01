package algorithms;

/**
 * The SCAN algorithm selects requests that are nearest to the current head position and in
 * the direction the head is traveling. When it hits the maximum or minimum cylinder, it
 * repeats the process while moving in the opposite direction.
 */
public class SCAN extends AbstractDiskScheduler {
    private enum Direction {
        UP, DOWN
    }
    public void run() {
        Integer nextRequest = 0;
        Direction direction = Direction.UP;
        while (!requestQueue.isEmpty()) {
            switch (direction) {
                case UP:
                    nextRequest = getNextRequestUp();
                    if (nextRequest == null) {
                        direction = Direction.DOWN;
                        totalHeadMovements += (MAX_DISK_CYLINDER - currentHeadCylinder) * 2;
                        nextRequest = getNextRequestDown();
                    }
                    break;
                case DOWN:
                    nextRequest = getNextRequestDown();
                    if (nextRequest == null) {
                        direction = Direction.UP;
                        totalHeadMovements += (currentHeadCylinder - MIN_DISK_CYLINDER) * 2;
                        nextRequest = getNextRequestUp();
                    }
                    break;
            }
            validateRequest(nextRequest, getClass());
            orderProcessed.add(nextRequest);
            totalHeadMovements += Math.abs(currentHeadCylinder - nextRequest);
            currentHeadCylinder = nextRequest;
            requestQueue.remove(nextRequest);
        }
    }

    private Integer getNextRequestUp() {
        return requestQueue
                .stream()
                .filter(request -> request - currentHeadCylinder > 0)
                .reduce((request1, request2) -> Math.min(request1 - currentHeadCylinder, request2 - currentHeadCylinder))
                .orElse(null);
    }

    private Integer getNextRequestDown() {
        return requestQueue
                .stream()
                .filter(request -> currentHeadCylinder - request > 0)
                .reduce((request1, request2) -> Math.min(currentHeadCylinder - request1, currentHeadCylinder - request2))
                .orElse(null);
    }
}
