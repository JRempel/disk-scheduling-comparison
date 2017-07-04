package math;

import org.junit.Test;

import exceptions.InvalidRangeMapperParameterException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RangeMapperTest {
    @Test
    public void obviousValuesRangeMapperTest() {
        RangeMapper mapper = new RangeMapper(-1.0, 1.0, 0, 100);

        assertEquals(0, mapper.mapDoubleToInt(-1.0));
        assertEquals(100, mapper.mapDoubleToInt(1.0));
        assertEquals(50, mapper.mapDoubleToInt(0));
        assertEquals(25, mapper.mapDoubleToInt(-0.5));
        assertEquals(75, mapper.mapDoubleToInt(0.5));

        mapper = new RangeMapper(-1.0, 1.0, 0, 1000);

        assertEquals(0, mapper.mapDoubleToInt(-1.0));
        assertEquals(1000, mapper.mapDoubleToInt(1.0));
        assertEquals(500, mapper.mapDoubleToInt(0));
        assertEquals(250, mapper.mapDoubleToInt(-0.5));
        assertEquals(750, mapper.mapDoubleToInt(0.5));
    }

    @Test
    public void invalidInputTest() {
        RangeMapper mapper = null;
        try {
            mapper = new RangeMapper(100, 0, 0, 100);
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidRangeMapperParameterException);
        }

        try {
            mapper = new RangeMapper(0, 100, 100, 0);
        } catch (RuntimeException e) {
            assertTrue(e instanceof InvalidRangeMapperParameterException);
        }

        assertNull(mapper);
    }
}
