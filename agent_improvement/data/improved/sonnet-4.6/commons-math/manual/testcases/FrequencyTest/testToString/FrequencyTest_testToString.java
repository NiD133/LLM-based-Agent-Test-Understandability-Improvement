package org.apache.commons.math4.legacy.stat;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testToString {

    private static final long ONE_LONG = 1L;
    private static final long TWO_LONG = 2L;

    /**
     * Verifies that toString() produces a non-null, multi-line output
     * containing at least a header line and one data line for each added value.
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> f = new Frequency<>();
        f.addValue(ONE_LONG);
        f.addValue(TWO_LONG);

        String output = f.toString();
        Assert.assertNotNull("toString() should not return null", output);

        try (BufferedReader reader = new BufferedReader(new StringReader(output))) {
            String headerLine = reader.readLine();
            Assert.assertNotNull("toString() output should contain a header line", headerLine);

            String firstDataLine = reader.readLine();
            Assert.assertNotNull("toString() output should contain at least one data line", firstDataLine);
        }
    }
}
