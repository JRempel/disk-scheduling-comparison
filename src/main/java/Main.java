import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private static final int MIN_CYLINDER = 0;
    private static final int MAX_CYLINDER = 511;
    private static final int SEQUENCE_SIZE = 50;
    private static final int UNIFORM_R_SEED = 3;
    private static final int MULTI_MODAL_SEED = 5;
    private static final int MULTI_MODAL_PEAK_GEN_SEED = 7;
    private static final int MULTI_MODAL_PEAK_SELECTOR_SEED = 13;
    private static final int MULTI_MODAL_PEAK_AMOUNT = 8;
    private static final int MIN_GROUP_SIZE = 2;
    private static final int MAX_GROUP_SIZE = 5;
    private static final int[] SEQUENCE_MULTIPLES = {10};

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

        ArrayList<Integer> uniformRandom = generator.getUniformRandomSequence(SEQUENCE_SIZE, UNIFORM_R_SEED, true);
        ArrayList<Integer> multiModalRandom = generator.getMultimodalRandomSequence(SEQUENCE_SIZE,
                MULTI_MODAL_PEAK_AMOUNT, MULTI_MODAL_PEAK_GEN_SEED, MULTI_MODAL_PEAK_SELECTOR_SEED,
                MULTI_MODAL_SEED, true);


        System.out.println("Uniform Random, Equal Groupings:");
        processEqualSizeGroupings(uniformRandom, SEQUENCE_MULTIPLES, schedulers);
        clearSchedulers(schedulers);

        System.out.println("MultiModal Random, Equal Groupings:");
        processEqualSizeGroupings(multiModalRandom, SEQUENCE_MULTIPLES, schedulers);
        clearSchedulers(schedulers);

        System.out.println("Uniform Random, Random Groupings: ");
        processRandomSizeGroupings(uniformRandom, schedulers);

        System.out.println("MultiModal, Random Groupings");
        processRandomSizeGroupings(multiModalRandom, schedulers);
    }

    private static void processRandomSizeGroupings(ArrayList<Integer> sequence, ArrayList<DiskScheduler> schedulers) {
        List<List<Integer>> sublists = new ArrayList<>();
        Random groupSizeGenerator = new Random(UNIFORM_R_SEED);
        int indexCompleted = 0;

        while (indexCompleted < sequence.size()) {
            int groupSize = groupSizeGenerator.nextInt(MAX_GROUP_SIZE - MIN_GROUP_SIZE + 1) + MIN_GROUP_SIZE;
            List<Integer> subListToAdd = new ArrayList<>();
            for (int currentIndex = indexCompleted; currentIndex < indexCompleted + groupSize; currentIndex++) {
                if (currentIndex < sequence.size()) {
                    subListToAdd.add(sequence.get(currentIndex));
                }
            }

            indexCompleted += groupSize;
            sublists.add(subListToAdd);
        }

        processLists(sublists, schedulers);
    }

    private static void processEqualSizeGroupings(ArrayList<Integer> sequence, int[] multiplesOf, ArrayList<DiskScheduler> schedulers) {
        for (int multiple : multiplesOf) {
            System.out.println("Groupings of " + multiple + ":\n");
            List<List<Integer>> sublists = Lists.partition(sequence, multiple);

            processLists(sublists, schedulers);
        }
    }

    private static void processLists(List<List<Integer>> sublists, ArrayList<DiskScheduler> schedulers) {
        for (DiskScheduler scheduler : schedulers) {
            for (List<Integer> list : sublists) {
                scheduler.setRequestQueue(list);
                scheduler.run();
            }

            scheduler.printResults();
        }

        clearSchedulers(schedulers);
    }

    private static void clearSchedulers(ArrayList<DiskScheduler> schedulers) {
        for (DiskScheduler scheduler : schedulers) {
            scheduler.reset();
        }
    }
}
