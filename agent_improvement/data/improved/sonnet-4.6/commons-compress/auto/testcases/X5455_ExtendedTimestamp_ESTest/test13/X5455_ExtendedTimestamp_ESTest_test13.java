package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test13 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that parseFromLocalFileData reads the flags byte from the correct
     * offset in the data buffer. When only 1 byte is available (the flags byte itself),
     * the MODIFY_TIME_BIT flag (value=1) is captured in the flags field even though
     * there is not enough data to store the actual modify timestamp.
     */
    @Test(timeout = 4000)
    public void test_parseFromLocalFileData_preservesFlagsByteWhenTimestampDataIsAbsent() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Buffer with 2 bytes: index 0 unused, index 1 holds flags = MODIFY_TIME_BIT (1)
        byte[] data = new byte[2];
        data[1] = (byte) 1; // flags byte at offset 1: bit0 = MODIFY_TIME_BIT set

        // Parse from offset=1, length=1: only the flags byte is consumed; no room for timestamp
        extendedTimestamp.parseFromLocalFileData(data, (byte) 1, (byte) 1);

        // The raw flags value should equal 1 (MODIFY_TIME_BIT) as stored during parsing
        assertEquals((byte) 1, extendedTimestamp.getFlags());
    }
}
