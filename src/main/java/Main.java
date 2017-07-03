import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

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
    private static int MIN_GROUP_SIZE = 3;
    private static int MAX_GROUP_SIZE = 10;

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

        ArrayList<Integer> pureRandom = generator.getUniformRandomSequence(SEQUENCE_SIZE, UNIFORM_R_SEED);
        ArrayList<Integer> multiModalRandom = generator.getMultimodalRandomSequence(SEQUENCE_SIZE,
                MULTI_MODAL_PEAK_AMOUNT, MULTI_MODAL_PEAK_GEN_SEED, MULTI_MODAL_PEAK_SELECTOR_SEED,
                MULTI_MODAL_SEED);


        for (DiskScheduler scheduler: schedulers) {
            scheduler.setRequestQueue(dedupe(new ArrayList<>(pureRandom)));
            scheduler.run();
            scheduler.printResults();
        }
    }

    // Algorithms expect no duplicates
    private static ArrayList<Integer> dedupe(ArrayList<Integer> input) {
        return new ArrayList<>(new LinkedHashSet<>(input));
    }
}
