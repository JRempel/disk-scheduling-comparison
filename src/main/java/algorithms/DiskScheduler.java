package algorithms;

import java.util.List;

import exceptions.InvalidRequestException;

public interface DiskScheduler {
    /**
     * Set the request queue before simulating the algorithm.
     * @param queue - queue of cylinder requests.
     */
    boolean setRequestQueue(List<Integer> queue);

    /**
     * Execute the scheduler with the assigned input request queue.
     */
    void run() throws InvalidRequestException;

    /**
     * List the Algorithm name, total head movements, and order processed
     */
    void printResults();

    /**
     * Clear the request queue, and reset variables to their default state.
     */
    void reset();
}
