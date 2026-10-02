package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test00 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Prepare a 6-byte local file data buffer.
        // The flags byte sits at index 1 (the parse offset).
        // Setting it to MODIFY_TIME_BIT signals that a modify timestamp is present.
        byte[] localFileData = new byte[6];
        localFileData[1] = X5455_ExtendedTimestamp.MODIFY_TIME_BIT;

        // Parse starting at offset 1 with a declared length of 22.
        // The flags byte at localFileData[1] has bit0 set, so modifyTime is read
        // from the remaining bytes and bit0_modifyTimePresent becomes true.
        extendedTimestamp.parseFromLocalFileData(localFileData, (byte) 1, 22);

        assertTrue(extendedTimestamp.isBit0_modifyTimePresent());
    }
}
