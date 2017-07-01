package algorithms;

import java.util.ArrayList;

import exceptions.InvalidRequestException;

public abstract class AbstractDiskScheduler implements DiskScheduler {
    protected ArrayList<Integer> requestQueue;
    protected ArrayList<Integer> orderProcessed = new ArrayList<>();
    protected int totalHeadMovements = 0;
    protected int currentHeadCylinder = 0;
    protected static final int MIN_DISK_CYLINDER = 0;
    protected static final int MAX_DISK_CYLINDER = 200;
    private static final String RESULTS_FORMAT = "%s\nTotal Head Movements: %s\nOrder Processed: %s\n\n";

    public int getTotalHeadMovements() {
        return totalHeadMovements;
    }

    public ArrayList<Integer> getOrderProcessed() {
        return orderProcessed;
    }

    public void setRequestQueue(ArrayList<Integer> queue){
        this.requestQueue = queue;
    }

    public void reset() {
        currentHeadCylinder = 0;
        requestQueue = null;
        orderProcessed = new ArrayList<>();

    }

    public boolean isValidRequestQueue() {
        return requestQueue != null &&
                requestQueue.stream()
                        .allMatch(request -> request >= MIN_DISK_CYLINDER &&request <= MAX_DISK_CYLINDER);
    }

    protected void validateRequest(Integer request) {
        if (request == null || request < MIN_DISK_CYLINDER || request > MAX_DISK_CYLINDER) {
            throw new InvalidRequestException(request, getClass());
        }
    }

    protected Integer getNextRequestUp() {
        return requestQueue
                .stream()
                .filter(request -> request - currentHeadCylinder > 0)
                .reduce((request1, request2) -> Math.min(request1 - currentHeadCylinder, request2 - currentHeadCylinder))
                .orElse(null);
    }

    protected Integer getNextRequestDown() {
        return requestQueue
                .stream()
                .filter(request -> currentHeadCylinder - request > 0)
                .reduce((request1, request2) -> Math.min(currentHeadCylinder - request1, currentHeadCylinder - request2))
                .orElse(null);
    }

    public void printResults() {
        System.out.println(String.format(RESULTS_FORMAT, getClass(), totalHeadMovements, orderProcessed));
    }
}