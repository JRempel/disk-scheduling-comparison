import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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
    private static int SEQUENCE_MULTIPLES = 1;
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


        System.out.println("Pure Random:");
        processGroupings(pureRandom, SEQUENCE_MULTIPLES, schedulers);
        clearSchedulers(schedulers);

        System.out.println("MultiModal:");
        processGroupings(multiModalRandom, SEQUENCE_MULTIPLES, schedulers);
        clearSchedulers(schedulers);

    }

    private static void processGroupings(ArrayList<Integer> sequence, int multiplesOf, ArrayList<DiskScheduler> schedulers) {
        IntStream.range(1, sequence.size() + 1).forEachOrdered( n -> {
            System.out.println("Groupings of " + n + ":\n");

            if (n % multiplesOf == 0) {
                for (DiskScheduler scheduler : schedulers) {

                    List<List<Integer>> sublists = Lists.partition(sequence, n);
                    for (List<Integer> list : sublists) {
                        scheduler.setRequestQueue(list);
                        scheduler.run();
                    }

                    scheduler.printResults();
                }

                clearSchedulers(schedulers);
            }
        });
    }

    private static void clearSchedulers(ArrayList<DiskScheduler> schedulers) {
        for (DiskScheduler scheduler : schedulers) {
            scheduler.reset();
        }
    }
}
