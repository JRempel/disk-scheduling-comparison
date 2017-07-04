import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import algorithms.CLOOK;
import algorithms.CSCAN;
import algorithms.DiskScheduler;
import algorithms.FCFS;
import algorithms.LOOK;
import algorithms.OSDA;
import algorithms.SCAN;
import algorithms.SSTF;
import math.RandomSequenceGenerator;

public class Main {
    private static int MIN_CYLINDER = 0;
    private static int MAX_CYLINDER = 511;
    private static int SEQUENCE_SIZE = 50;
    private static int UNIFORM_R_SEED = 3;
    private static int MULTI_MODAL_SEED = 5;
    private static int MULTI_MODAL_PEAK_GEN_SEED = 7;
    private static int MULTI_MODAL_PEAK_SELECTOR_SEED = 13;
    private static int MULTI_MODAL_PEAK_AMOUNT = 8;
    private static int MIN_GROUP_SIZE = 2;
    private static int MAX_GROUP_SIZE = 5;

    public static void main(String[] args) {
        RandomSequenceGenerator generator = new RandomSequenceGenerator(MIN_CYLINDER, MAX_CYLINDER);
        ArrayList<DiskScheduler> schedulers = new ArrayList<DiskScheduler>() {{
            add(new FCFS(MIN_CYLINDER, MAX_CYLINDER));
            add(new SSTF(MIN_CYLINDER, MAX_CYLINDER));
            add(new SCAN(MIN_CYLINDER, MAX_CYLINDER));
            add(new LOOK(MIN_CYLINDER, MAX_CYLINDER));
            add(new CSCAN(MIN_CYLINDER, MAX_CYLINDER));
            add(new CLOOK(MIN_CYLINDER, MAX_CYLINDER));
            add(new OSDA(MIN_CYLINDER, MAX_CYLINDER));
        }};

        ArrayList<Integer> pureRandom = generator.getUniformRandomSequence(SEQUENCE_SIZE, UNIFORM_R_SEED, true);
        ArrayList<Integer> multiModalRandom = generator.getMultimodalRandomSequence(SEQUENCE_SIZE,
                MULTI_MODAL_PEAK_AMOUNT, MULTI_MODAL_PEAK_GEN_SEED, MULTI_MODAL_PEAK_SELECTOR_SEED,
                MULTI_MODAL_SEED, true);

        int[] multiples = new int[]{5, 10, 25, 50};

        System.out.println("Pure Random:");
        processMultiples(pureRandom, schedulers, multiples);
        clearSchedulers(schedulers);

        System.out.println("MultiModal:");
        processMultiples(multiModalRandom, schedulers, multiples);
        clearSchedulers(schedulers);

    }

    private static void processMultiples(ArrayList<Integer> sequence, ArrayList<DiskScheduler> schedulers, int[] multiples) {
        for (int multiple: multiples) {
            System.out.println("Multiples of " + multiple + ":\n");

            for (DiskScheduler scheduler : schedulers) {

                List<List<Integer>> sublists = Lists.partition(sequence, multiple);
                for (List<Integer> list: sublists) {
                    scheduler.setRequestQueue(list);
                    scheduler.run();
                }

                scheduler.printResults();
            }
        }
    }

    private static void clearSchedulers(ArrayList<DiskScheduler> schedulers) {
        for (DiskScheduler scheduler: schedulers) {
            scheduler.reset();
        }
    }
}
