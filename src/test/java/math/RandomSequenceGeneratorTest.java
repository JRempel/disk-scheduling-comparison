package math;

import org.junit.Test;

import java.util.ArrayList;

import exceptions.InvalidGeneratorParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RandomSequenceGeneratorTest {
    @Test
    public void invalidInputTest() {
        RandomSequenceGenerator generator = null;
        try {
            generator = new RandomSequenceGenerator(100, 0);
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidGeneratorParameterException);
        }

        assertNull(generator);
    }

    @Test
    public void dedupeTest() {
        RandomSequenceGenerator generator = new RandomSequenceGenerator(0, 50);
        int numDataPoints = 30;

        ArrayList<Integer> duplicateList = generator.getUniformRandomSequence(numDataPoints, 10, false);
        ArrayList<Integer> dedupedList = generator.getUniformRandomSequence(numDataPoints, 10, true);

        assertEquals("Expected list sizes to be equal.", duplicateList.size(), dedupedList.size());
        assertNotEquals("Expected the duplicate list to contain duplicates.", duplicateList.size(), duplicateList.stream().distinct().count());
        assertNotEquals("Expected list contents to be different", duplicateList, dedupedList);

        duplicateList = generator.getMultimodalRandomSequence(numDataPoints, 3, 5, 7, 13, false);
        dedupedList = generator.getMultimodalRandomSequence(numDataPoints, 3, 5, 7, 13, true);
        assertEquals("Expected list sizes to be equal.", duplicateList.size(), dedupedList.size());
        assertNotEquals("Expected the duplicate list to contain duplicates.", duplicateList.size(), duplicateList.stream().distinct().count());
        assertNotEquals("Expected list contents to be different", duplicateList, dedupedList);
    }

    @Test
    public void sameSequenceTest() {
        RandomSequenceGenerator generator = new RandomSequenceGenerator(0, 50);
        int numDataPoints = 30;

        ArrayList<Integer> firstList = generator.getUniformRandomSequence(numDataPoints, 10, false);
        ArrayList<Integer> secondList = generator.getUniformRandomSequence(numDataPoints, 10, false);

        assertEquals("Expected list sizes to be equal.", firstList.size(), secondList.size());
        assertEquals("Expected list contents and ordering to be identical.", firstList, secondList);

        firstList = generator.getMultimodalRandomSequence(numDataPoints, 3, 5, 7, 13, false);
        secondList = generator.getMultimodalRandomSequence(numDataPoints, 3, 5, 7, 13, false);

        assertEquals("Expected list sizes to be equal.", firstList.size(), secondList.size());
        assertEquals("Expected list contents and ordering to be identical.", firstList, secondList);
    }
}
