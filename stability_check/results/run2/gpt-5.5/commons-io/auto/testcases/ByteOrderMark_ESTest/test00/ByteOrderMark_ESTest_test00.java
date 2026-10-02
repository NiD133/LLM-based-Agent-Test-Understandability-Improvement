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

    private static final String UTF_16LE_DESCRIPTION = "ByteOrderMark[UTF-16LE: 0xFF,0xFE]";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        int[] emptyBomBytes = new int[8];
        ByteOrderMark customBom = new ByteOrderMark("w-", emptyBomBytes);

        String utf16LittleEndianDescription = customBom.UTF_16LE.toString();

        assertEquals(UTF_16LE_DESCRIPTION, utf16LittleEndianDescription);
    }
}
