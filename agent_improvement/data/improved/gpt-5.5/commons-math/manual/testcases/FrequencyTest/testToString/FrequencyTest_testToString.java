package org.apache.commons.math4.legacy.stat;

import java.io.BufferedReader;
import java.io.StringReader;
import org.junit.Assert;
import org.junit.Test;

public class FrequencyTest_testToString {

    private static final long FIRST_VALUE = 1L;
    private static final long SECOND_VALUE = 2L;

    /**
     * Verifies that the string representation contains a header and at least
     * one data row after values have been added.
     */
    @Test
    public void testToString() throws Exception {
        Frequency<Long> frequency = new Frequency<>();
        frequency.addValue(FIRST_VALUE);
        frequency.addValue(SECOND_VALUE);

        String frequencyTable = frequency.toString();
        Assert.assertNotNull(frequencyTable);

        BufferedReader tableReader = new BufferedReader(new StringReader(frequencyTable));

        String headerLine = tableReader.readLine();
        Assert.assertNotNull(headerLine);

        String firstValueLine = tableReader.readLine();
        Assert.assertNotNull(firstValueLine);
    }
}
