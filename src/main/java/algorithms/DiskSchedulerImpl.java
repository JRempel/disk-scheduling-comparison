package algorithms;

import java.util.ArrayList;

import exceptions.InvalidRequestException;

public abstract class DiskSchedulerImpl implements DiskScheduler {
    protected ArrayList<Integer> requestQueue;
    protected ArrayList<Integer> orderProcessed = new ArrayList<>();
    protected int totalHeadMovements = 0;
    protected int currentHeadCylinder = 0;
    protected static final int MIN_DISK_CYLINDER = 0;
    protected static final int MAX_DISK_CYLINDER = 200;

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

    protected void validateRequest(Integer request, Class clazz) {
        if (request == null || request < MIN_DISK_CYLINDER || request > MAX_DISK_CYLINDER) {
            throw new InvalidRequestException(request, clazz);
        }
    }
}
