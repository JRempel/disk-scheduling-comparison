package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import javax.swing.text.html.Option;

import exceptions.InvalidRequestException;

public abstract class AbstractDiskScheduler implements DiskScheduler {
    private static final String RESULTS_FORMAT = "%s\nTotal Head Movements: %s\nOrder Processed: %s\n";

    protected ArrayList<Integer> requestQueue;
    protected ArrayList<Integer> orderProcessed = new ArrayList<>();
    protected ArrayList<Predicate<AbstractDiskScheduler>> pauseConditions = new ArrayList<>();
    protected int totalHeadMovements = 0;
    protected int currentHeadCylinder = 0;
    protected int requestsServiced = 0;
    protected boolean customCalculateChanges = false;
    protected final int MIN_DISK_CYLINDER;
    protected final int MAX_DISK_CYLINDER;

    protected AbstractDiskScheduler(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        this.MIN_DISK_CYLINDER = MIN_DISK_CYLINDER;
        this.MAX_DISK_CYLINDER = MAX_DISK_CYLINDER;
    }

    public void run() {
        Integer next;
        // Run setup of algorithm if implemented.
        preRun();
        while (!requestQueue.isEmpty()) {
            // Check conditions for pausing the scheduler
            Predicate<AbstractDiskScheduler> pauseCondition = pauseConditions.stream()
                    .filter(p -> p.test(this))
                    .findFirst()
                    .orElse(null);

            if (pauseCondition != null) {
                pauseConditions.remove(pauseCondition);
                return;
            }

            next = selectNext();
            // Don't calculate changes if algorithm has implemented a custom solution.
            if (!customCalculateChanges) {
                calculateChanges(next);
            }
        }
    }

    // Require subclasses implement a preRun setup method and method of selecting the next cylinder
    protected abstract void preRun();
    protected abstract Integer selectNext();

    public boolean setRequestQueue(ArrayList<Integer> queue){
        if (isValidRequestQueue(queue)) {
            this.requestQueue = new ArrayList<>(queue);
            return true;
        }
        return false;
    }

    public boolean addToRequestQueue(ArrayList<Integer> queue) {
        if (isValidRequestQueue(queue) && requestQueue != null) {
            queue.forEach(x -> requestQueue.add(x));
            return true;
        }
        return false;
    }

    public void addPauseConditions(Predicate... predicates) {
        Arrays.stream(predicates)
                .forEach(pauseConditions::add);
    }

    public void clearPauseConditions() {
        pauseConditions.clear();
    }

    public void reset() {
        totalHeadMovements = 0;
        currentHeadCylinder = 0;
        requestsServiced = 0;
        requestQueue = null;
        orderProcessed = new ArrayList<>();
        pauseConditions = new ArrayList<>();
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

    /**
     * Update properties based on the next selected cylinder.
     * @param nextRequest - the request chosen to be serviced next.
     */
    void calculateChanges(Integer nextRequest) {
        validateRequest(nextRequest);
        orderProcessed.add(nextRequest);
        totalHeadMovements += Math.abs(currentHeadCylinder - nextRequest);
        currentHeadCylinder = nextRequest;
        requestQueue.remove(nextRequest);
        requestsServiced++;
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