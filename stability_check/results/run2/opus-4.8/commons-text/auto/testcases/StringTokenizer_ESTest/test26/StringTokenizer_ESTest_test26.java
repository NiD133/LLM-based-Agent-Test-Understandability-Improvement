package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test26 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly created CSV tokenizer should report a next index of 0,
     * since no tokens have been consumed yet.
     */
    @Test(timeout = 4000)
    public void nextIndexOnNewCsvTokenizerIsZero() throws Throwable {
        char[] emptyPaddedInput = new char[7];
        StringTokenizer csvTokenizer = StringTokenizer.getCSVInstance(emptyPaddedInput);

        int nextIndex = csvTokenizer.nextIndex();

        assertEquals(0, nextIndex);
    }
}
