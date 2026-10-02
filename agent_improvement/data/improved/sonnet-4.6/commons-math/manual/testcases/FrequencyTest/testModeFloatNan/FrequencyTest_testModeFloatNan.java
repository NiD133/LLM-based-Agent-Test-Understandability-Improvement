package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests that Frequency.getMode() handles Float special values (NaN and infinities) correctly.
 *
 * NaN values cannot be compared with natural ordering, so they are treated as a
 * distinct group. The mode returns values ordered by frequency (descending), with
 * ties broken by natural order. NaN floats appear last in the mode list.
 */
public class FrequencyTest_testModeFloatNan {

    @Test
    public void testModeFloatNan() {
        // Build a frequency table with Float special values.
        // Counts: NaN=3, NEGATIVE_INFINITY=3, POSITIVE_INFINITY=3
        Frequency<Float> frequency = new Frequency<>();

        frequency.addValue(Float.NaN);
        frequency.addValue(Float.NaN);
        frequency.addValue(Float.NaN);

        frequency.addValue(Float.NEGATIVE_INFINITY);
        frequency.addValue(Float.NEGATIVE_INFINITY);
        frequency.addValue(Float.NEGATIVE_INFINITY);

        frequency.addValue(Float.POSITIVE_INFINITY);
        frequency.addValue(Float.POSITIVE_INFINITY);
        frequency.addValue(Float.POSITIVE_INFINITY);

        // All three special-value groups have the same frequency (3), so all appear
        // in the mode list. The natural Float order puts NEGATIVE_INFINITY first,
        // POSITIVE_INFINITY second, and NaN last (NaN is unordered by definition).
        List<Float> mode = frequency.getMode();

        Assert.assertEquals("All three special-value groups should appear in the mode", 3, mode.size());
        Assert.assertEquals("NEGATIVE_INFINITY should be first (lowest finite-comparable value)",
                Float.valueOf(Float.NEGATIVE_INFINITY), mode.get(0));
        Assert.assertEquals("POSITIVE_INFINITY should be second",
                Float.valueOf(Float.POSITIVE_INFINITY), mode.get(1));
        Assert.assertEquals("NaN should be last (unordered, placed after all comparable values)",
                Float.valueOf(Float.NaN), mode.get(2));
    }
}
