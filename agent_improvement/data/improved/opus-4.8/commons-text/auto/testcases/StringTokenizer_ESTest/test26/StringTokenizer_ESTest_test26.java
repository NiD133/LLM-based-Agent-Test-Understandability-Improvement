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
     * A freshly created tokenizer should report 0 as the index of the next
     * token, because no tokens have been consumed yet.
     */
    @Test(timeout = 4000)
    public void nextIndexOnNewTokenizerIsZero() throws Throwable {
        char[] emptyContent = new char[7];
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance(emptyContent);

        int nextTokenIndex = tokenizer.nextIndex();

        assertEquals(0, nextTokenIndex);
    }
}
