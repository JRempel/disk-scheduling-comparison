package algorithms;

/**
 * The FCFS (First-Come, First-Serve) algorithm simply processes requests in the order which
 * they appear in the request queue.
 */
public class FCFS extends DiskSchedulerImpl {
    public void run() {
        for (int request: requestQueue) {
            validateRequest(request, getClass());
            orderProcessed.add(request);
            totalHeadMovements += Math.abs(currentHeadCylinder - request);
            currentHeadCylinder = request;
        }
    }
}
