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
     * The predefined UTF-16LE constant carries the little-endian BOM bytes
     * 0xFF, 0xFE, so its String form should describe exactly those bytes.
     */
    @Test(timeout = 4000)
    public void toStringDescribesCharsetNameAndBomBytes() throws Throwable {
        // Build an unrelated BOM instance; the constant accessed below is static,
        // so this instance does not affect the expected result.
        int[] bomBytes = new int[8];
        ByteOrderMark customBom = new ByteOrderMark("w-", bomBytes);

        String utf16LeText = customBom.UTF_16LE.toString();

        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", utf16LeText);
    }
}
