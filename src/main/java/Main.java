import java.util.ArrayList;

import algorithms.CLOOK;
import algorithms.CSCAN;
import algorithms.DiskScheduler;
import algorithms.FCFS;
import algorithms.LOOK;
import algorithms.SCAN;
import algorithms.SSTF;

public class Main {
    public static void main(String[] args) {
        ArrayList<DiskScheduler> schedulers = new ArrayList<DiskScheduler>() {{
            add(new FCFS());
            add(new SSTF());
            add(new SCAN());
            add(new CSCAN());
            add(new LOOK());
            add(new CLOOK());
        }};
    }
}
