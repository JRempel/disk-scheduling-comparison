package algorithms;

import java.util.ArrayList;

import exceptions.InvalidRequestException;

public interface DiskScheduler {
    /**
     * Set the request queue before simulating the algorithm.
     * @param queue - queue of cylinder requests.
     */
    void setRequestQueue(ArrayList<Integer> queue);

    /**
     * Ensure that the set request queue only contains valid requests.
     * @throws InvalidRequestException
     */
    boolean isValidRequestQueue();

    /**
     * Execute the scheduler with the assigned input request queue.
     */
    void run() throws InvalidRequestException;

    /**
     * @return - the total number of head movements after running the scheduler.
     */
    int getTotalHeadMovements();
}
