package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Verifies {@link Frequency#getMode()}, which returns the most frequently
 * occurring value(s). When several values share the highest count, every one
 * of them is returned in natural (sorted) order.
 */
public class FrequencyTest_testMode {

    @Test
    public void testMode() {
        Frequency<String> frequency = new Frequency<>();

        // No values have been added yet, so there is no mode.
        List<String> mode = frequency.getMode();
        Assert.assertEquals(0, mode.size());

        // "3" is the only value, so it is the sole mode.
        frequency.addValue("3");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("3", mode.get(0));

        // "2" and "3" now each occur once, so both are modes, sorted naturally.
        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(2, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertEquals("3", mode.get(1));

        // A second "2" makes it the unique most frequent value.
        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertFalse(mode.contains("1"));
        Assert.assertTrue(mode.contains("2"));
    }
}
