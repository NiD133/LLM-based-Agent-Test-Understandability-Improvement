package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NullReader_ESTest_test13 extends NullReader_ESTest_scaffolding {

    /**
     * The default constructor emulates a size-0 reader that supports marking.
     * Verifies that such a reader reports a size of 0 and that mark is supported.
     */
    @Test(timeout = 4000)
    public void defaultReaderHasZeroSizeAndSupportsMark() throws Throwable {
        NullReader nullReader = new NullReader();

        assertEquals(0L, nullReader.getSize());
        assertTrue(nullReader.markSupported());
    }
}
