package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test01 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * When the central-directory data is parsed starting from a byte whose value is 0,
     * the flags byte is read as 0, so none of the timestamp-present bits should be set.
     */
    @Test(timeout = 4000)
    public void parsingZeroFlagsByteLeavesAllTimestampBitsUnset() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // A two-byte buffer of all zeros; parsing starts at offset 1 and reads 1 byte (the flags).
        byte[] data = new byte[2];
        int flagsOffset = 1;
        int length = 1;
        extendedTimestamp.parseFromCentralDirectoryData(data, flagsOffset, length);

        assertEquals("flags byte should be 0", (byte) 0, extendedTimestamp.getFlags());
        assertFalse("access-time bit must be unset", extendedTimestamp.isBit1_accessTimePresent());
        assertFalse("modify-time bit must be unset", extendedTimestamp.isBit0_modifyTimePresent());
    }
}
