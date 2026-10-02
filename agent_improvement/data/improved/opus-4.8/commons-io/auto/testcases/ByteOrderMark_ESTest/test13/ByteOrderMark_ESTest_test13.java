package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test13 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that the predefined UTF-16LE byte order mark reports
     * "UTF-16LE" as its charset name.
     */
    @Test(timeout = 4000)
    public void getCharsetName_forUtf16LeBom_returnsUtf16LeName() throws Throwable {
        ByteOrderMark utf16LeBom = ByteOrderMark.UTF_16LE;

        String charsetName = utf16LeBom.getCharsetName();

        assertEquals("UTF-16LE", charsetName);
    }
}
