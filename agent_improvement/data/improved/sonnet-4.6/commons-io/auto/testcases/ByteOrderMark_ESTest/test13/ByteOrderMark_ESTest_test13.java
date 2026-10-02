package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test13 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that the predefined UTF-16LE BOM constant reports its charset name as "UTF-16LE".
     */
    @Test(timeout = 4000)
    public void test_utf16LeBom_getCharsetName_returnsUtf16Le() throws Throwable {
        String charsetName = ByteOrderMark.UTF_16LE.getCharsetName();
        assertEquals("UTF-16LE", charsetName);
    }
}
