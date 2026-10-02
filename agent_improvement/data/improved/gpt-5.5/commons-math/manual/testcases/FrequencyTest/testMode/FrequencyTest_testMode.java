package org.apache.commons.math4.legacy.stat;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testMode {

    @Test
    public void testMode() {
        Frequency<String> frequency = new Frequency<>();

        List<String> mode = frequency.getMode();
        Assert.assertEquals(0, mode.size());

        frequency.addValue("3");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("3", mode.get(0));

        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(2, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertEquals("3", mode.get(1));

        frequency.addValue("2");
        mode = frequency.getMode();
        Assert.assertEquals(1, mode.size());
        Assert.assertEquals("2", mode.get(0));
        Assert.assertFalse(mode.contains("1"));
        Assert.assertTrue(mode.contains("2"));
    }
}
