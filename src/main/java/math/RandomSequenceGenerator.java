package math;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.stream.IntStream;

import exceptions.InvalidGeneratorParameterException;

public class RandomSequenceGenerator {
    private static final String DATA_POINTS_ERROR = "dataPoints parameter must be zero or greater.";
    private static final String PEAKS_ERROR = "peaks parameter must be one or greater.";
    private static final String MIN_MAX_ERROR = "min and max values must not overlap or equal";
    private static double gaussianMin = -1.0;
    private static double gaussianMax = 1.0;
    private static double standardDeviation = 1.0;

    private RangeMapper rangeMapper;
    private int min;
    private int max;

    public RandomSequenceGenerator(int min, int max) {
        if (min >= max) {
            throw new InvalidGeneratorParameterException("min &| max", getClass(), MIN_MAX_ERROR);
        }

        this.min = min;
        this.max = max;
        this.rangeMapper = new RangeMapper(gaussianMin, gaussianMax, this.min, this.max);
    }


    /**
     * Generate a uniform pseudo-random sequence of ints.
     *
     * @param seed - math seed for repeatable results.
     * @param numDataPoints - the number of data points to randomly generate.
     * @return
     */
    public ArrayList<Integer> getUniformRandomSequence(int numDataPoints, int seed, boolean dedupeList) {
        if (numDataPoints < 0) {
            throw new InvalidGeneratorParameterException("numDataPoints", getClass(), DATA_POINTS_ERROR);
        }
        Random generator = new Random(seed);
        ArrayList<Integer> sequence = new ArrayList<>();

        do {
            for (int i = 0; i < numDataPoints; i++) {
                sequence.add(generator.nextInt(max - min + 1) + min);
            }
            if (dedupeList) {
                sequence = dedupe(sequence);
            }
        } while(dedupeList && sequence.size() < numDataPoints);

        if (dedupeList && sequence.size() > numDataPoints) {
            sequence = new ArrayList<>(sequence.subList(0, numDataPoints));
        }

        return sequence;
    }

    /**
     * Generate a multi-modal distributed pseudo-random sequence of ints.
     *
     * Generates the sequence by first uniformly randomly generating the available peaks betwee -1.0
     * and 1.0, then uses a uniform random distribution to select said peak, and finally uses a
     * Gaussian distribution to generate an double which is mapped to the int range specified in the
     * constructor; in order to emulate a multi-modal distribution.
     *
     * @param numDataPoints - the number of data points to randomly generate.
     * @param numPeaks - the number of modals in the distribution.
     * @param peakGeneratorSeed - seed value for the math creating the peaks.
     * @param peakSelectorSeed - seed value for math selecting from the generated peaks.
     * @param distributionSeed - the seed value for the Gaussian distribution math.
     * @return
     */
    public ArrayList<Integer> getMultimodalRandomSequence(int numDataPoints, int numPeaks,
                                                          int peakGeneratorSeed, int peakSelectorSeed,
                                                          int distributionSeed, boolean dedupeList) {
        if (numDataPoints < 0) {
            throw new InvalidGeneratorParameterException("numDataPoints", getClass(), DATA_POINTS_ERROR);
        }
        if (numPeaks < 1) {
            throw new InvalidGeneratorParameterException("numPeaks", getClass(), PEAKS_ERROR);
        }

        Random peakGenerator = new Random(peakGeneratorSeed);
        Random peakSelector = new Random(peakSelectorSeed);
        Random gaussianGenerator = new Random(distributionSeed);

        ArrayList<Integer> sequence = new ArrayList<>();
        ArrayList<Double> peaks = new ArrayList<>();

        // Generate randomly distributed peaks between -1.0 & 1.0
        for (int i = 0; i < numPeaks; i++) {
            double peak = gaussianMin + (gaussianMax - gaussianMin) * peakGenerator.nextDouble();
            peaks.add(peak);
        }

        do {
            for (int i = 0; i < numDataPoints; i++) {
                // Use the selected peak as the mean of the distribution
                double selectedPeak = peaks.get(peakSelector.nextInt(peaks.size()));
                double randomPoint = gaussianGenerator.nextGaussian() * standardDeviation + selectedPeak;

                // cut off at -1, 1
                randomPoint = (randomPoint < -1) ? -1 : (randomPoint > 1) ? 1 : randomPoint;

                // Map the random point to the specified int range & add to sequence
                sequence.add(rangeMapper.mapDoubleToInt(randomPoint));
            }
            if (dedupeList) {
                sequence = dedupe(sequence);
            }
        } while(dedupeList && sequence.size() < numDataPoints);

        if (dedupeList && sequence.size() > numDataPoints) {
            sequence = new ArrayList<>(sequence.subList(0, numDataPoints));
        }

        return sequence;
    }

    /**
     * Dedupe a list while maintaining ordering.
     * @param input - list to dedupe.
     * @return deduped input list with the same ordering.
     */
    private static ArrayList<Integer> dedupe(ArrayList<Integer> input) {
        return new ArrayList<>(new LinkedHashSet<>(input));
    }
}
