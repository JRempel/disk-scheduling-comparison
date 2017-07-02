package algorithms;

import java.util.ArrayList;

import exceptions.InvalidRequestException;

public abstract class AbstractDiskScheduler implements DiskScheduler {
    protected ArrayList<Integer> requestQueue;
    protected ArrayList<Integer> orderProcessed = new ArrayList<>();
    protected int totalHeadMovements = 0;
    protected int currentHeadCylinder = 0;
    protected final int MIN_DISK_CYLINDER;
    protected final int MAX_DISK_CYLINDER;
    private static final String RESULTS_FORMAT = "%s\nTotal Head Movements: %s\nOrder Processed: %s\n";

    protected AbstractDiskScheduler(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        this.MIN_DISK_CYLINDER = MIN_DISK_CYLINDER;
        this.MAX_DISK_CYLINDER = MAX_DISK_CYLINDER;
    }

    public boolean setRequestQueue(ArrayList<Integer> queue){
        if (isValidRequestQueue(queue)) {
            this.requestQueue = new ArrayList<>(queue);
            return true;
        }
        return false;
    }

    public void reset() {
        totalHeadMovements = 0;
        currentHeadCylinder = 0;
        requestQueue = null;
        orderProcessed = new ArrayList<>();

    }

    private boolean isValidRequestQueue(ArrayList<Integer> queue) {
        return queue != null &&
                queue.stream().allMatch(request -> request >= MIN_DISK_CYLINDER &&request <= MAX_DISK_CYLINDER) &&
                queue.stream().distinct().count() == queue.size();
    }

    void validateRequest(Integer request) {
        if (request == null || request < MIN_DISK_CYLINDER || request > MAX_DISK_CYLINDER) {
            throw new InvalidRequestException(request, getClass());
        }
    }

    Integer getNextRequestUp() {
        return requestQueue
                .stream()
                .filter(request -> request - currentHeadCylinder > 0)
                .reduce((request1, request2) -> request1 - currentHeadCylinder < request2 - currentHeadCylinder ? request1 : request2)
                .orElse(null);
    }

    Integer getNextRequestDown() {
        return requestQueue
                .stream()
                .filter(request -> currentHeadCylinder - request > 0)
                .reduce((request1, request2) -> currentHeadCylinder - request1 < currentHeadCylinder - request2 ? request1 : request2)
                .orElse(null);
    }

    public void printResults() {
        System.out.println(String.format(RESULTS_FORMAT, getClass(), totalHeadMovements, orderProcessed));
    }
}