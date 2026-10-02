package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests {@link Frequency#getMode()}, which returns the value(s) that appear
 * most often in the distribution.  The mode list is always sorted in natural
 * order and contains every value tied for the highest frequency.
 */
public class FrequencyTest_testMode {

    @Test
    public void testMode() {
        Frequency<String> freq = new Frequency<>();

        // Empty distribution → mode list must be empty.
        List<String> mode = freq.getMode();
        Assert.assertEquals(0, mode.size());

        // After adding "3" once it is the sole mode.
        freq.addValue("3");
        mode = freq.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("3", mode.get(0));

        // "2" is added once; both "2" and "3" have count 1, so both are modes.
        // The list is sorted: "2" comes before "3".
        freq.addValue("2");
        mode = freq.getMode();
        Assert.assertEquals(2, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertEquals("3", mode.get(1));

        // "2" is added a second time; now count("2") = 2 > count("3") = 1,
        // so "2" becomes the unique mode and "3" drops out.
        freq.addValue("2");
        mode = freq.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertFalse(mode.contains("1"));
        Assert.assertTrue(mode.contains("2"));
    }
}
