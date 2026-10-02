package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test05 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * The UTF-16BE BOM is the two bytes {0xFE, 0xFF}. An array whose leading
     * bytes differ from those should not be reported as a match.
     */
    @Test(timeout = 4000)
    public void matchesReturnsFalseWhenBytesDoNotMatchBom() throws Throwable {
        ByteOrderMark utf16BeBom = ByteOrderMark.UTF_16BE;
        int[] candidateBytes = new int[] { 0 };

        boolean matches = utf16BeBom.matches(candidateBytes);

        assertFalse(matches);
    }
}
