package algorithms;

/**
 * The FCFS (First-Come, First-Serve) algorithm simply processes requests in the order which
 * they appear in the request queue.
 */
public class FCFS extends AbstractDiskScheduler {
    public void run() {
        Integer nextRequest;
        while(!requestQueue.isEmpty()) {
            nextRequest = requestQueue.get(0);
            validateRequest(nextRequest, getClass());
            orderProcessed.add(nextRequest);
            totalHeadMovements += Math.abs(currentHeadCylinder - nextRequest);
            currentHeadCylinder = nextRequest;
            requestQueue.remove(nextRequest);
        }
    }
}
