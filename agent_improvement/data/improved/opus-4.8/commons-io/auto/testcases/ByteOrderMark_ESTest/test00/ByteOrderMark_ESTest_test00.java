package org.apache.commons.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ByteOrderMark_ESTest_test00 extends ByteOrderMark_ESTest_scaffolding {

    /**
     * Verifies that toString() on the predefined UTF-16LE constant renders the
     * charset name followed by its two BOM bytes (0xFF, 0xFE) in the documented
     * "ByteOrderMark[charset: 0xAA,0xBB]" format.
     */
    @Test(timeout = 4000)
    public void toString_onUtf16LeConstant_rendersCharsetNameAndHexBytes() throws Throwable {
        // Construct an unrelated instance, mirroring the original generated test.
        int[] bomBytes = new int[8];
        new ByteOrderMark("w-", bomBytes);

        String rendered = ByteOrderMark.UTF_16LE.toString();

        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", rendered);
    }
}
