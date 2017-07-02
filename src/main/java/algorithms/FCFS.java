package algorithms;

/**
 * The FCFS (First-Come, First-Serve) algorithm simply processes requests in the order which
 * they appear in the request queue.
 */
public class FCFS extends AbstractDiskScheduler {
    public FCFS(int MIN_DISK_CYLINDER, int MAX_DISK_CYLINDER) {
        super(MIN_DISK_CYLINDER, MAX_DISK_CYLINDER);
    }

    public void run() {
        Integer nextRequest;
        while(!requestQueue.isEmpty()) {
            nextRequest = requestQueue.get(0);
            calculateChanges(nextRequest);
        }
    }
}
