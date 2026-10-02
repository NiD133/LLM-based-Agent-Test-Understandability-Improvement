package org.apache.commons.math4.legacy.stat;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testToString {

    private static final long FIRST_VALUE = 1L;

    private static final long SECOND_VALUE = 2L;

    /**
     * Verifies that toString() produces a non-null, multi-line summary:
     * a header line followed by at least one line describing a recorded value.
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(FIRST_VALUE);
        frequency.addValue(SECOND_VALUE);

        String summary = frequency.toString();
        Assert.assertNotNull(summary);

        BufferedReader reader = new BufferedReader(new StringReader(summary));

        String headerLine = reader.readLine();
        Assert.assertNotNull(headerLine);

        String firstValueLine = reader.readLine();
        Assert.assertNotNull(firstValueLine);
    }
}
