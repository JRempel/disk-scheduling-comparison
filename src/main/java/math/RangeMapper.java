package math;

import exceptions.InvalidRangeMapperParameterException;

public class RangeMapper {
    private static final String MIN_MAX_ERROR_MESSAGE = "min value must be < max value.";

    private double minFrom;
    private double maxFrom;
    private int minTo;
    private int maxTo;

    /**
     * @param minFrom - minimum value of range to map from.
     * @param maxFrom - max value of range to map from.
     * @param minTo - min value of range to map to.
     * @param maxTo - max value of range to map to.
     */
    public RangeMapper(double minFrom, double maxFrom, int minTo, int maxTo) {
        if (minFrom >= maxFrom) {
            throw new InvalidRangeMapperParameterException("min &| max (from)", MIN_MAX_ERROR_MESSAGE);
        } else if (minTo >= maxTo) {
            throw new InvalidRangeMapperParameterException("min &| max (to)", MIN_MAX_ERROR_MESSAGE);
        }

        this.minFrom = minFrom;
        this.maxFrom = maxFrom;
        this.minTo = minTo;
        this.maxTo = maxTo;
    }

    /**
     * Applies a linear transformation mapping a double range onto an int range to some double point.
     *
     * Let f(x) = (x - A)/(B - A) * (D - C) + C
     * If x ϵ [A, B] and f(x) = y, then y ϵ [C, D].
     *
     * @param point - the value to map between ranges.
     * @return - the int equivalent of the {@param point} when mapped to/from the range specified.
     */
    public int mapDoubleToInt(double point) {
        return (int) Math.round((point - minFrom) / (maxFrom - minFrom) * ((double) maxTo - (double) minTo) + (double) minTo);
    }
}
