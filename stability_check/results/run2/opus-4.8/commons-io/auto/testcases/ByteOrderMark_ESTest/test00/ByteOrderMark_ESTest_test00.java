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
     * Verifies the String representation of the predefined UTF-16LE byte order
     * mark. Its bytes are 0xFF and 0xFE, so {@link ByteOrderMark#toString()}
     * should render them as a hexadecimal list prefixed with the charset name.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Build an unrelated instance; the field access below resolves to the
        // static UTF_16LE constant regardless of this receiver.
        int[] customBomBytes = new int[8];
        ByteOrderMark unusedBom = new ByteOrderMark("w-", customBomBytes);

        String utf16leText = unusedBom.UTF_16LE.toString();

        assertEquals("ByteOrderMark[UTF-16LE: 0xFF,0xFE]", utf16leText);
    }
}
