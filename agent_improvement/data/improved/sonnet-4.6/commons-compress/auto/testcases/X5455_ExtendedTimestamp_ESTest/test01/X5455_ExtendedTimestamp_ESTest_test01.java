package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test01 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that parsing central directory data whose flags byte is 0
     * results in no timestamp bits being set.
     *
     * The 2-byte buffer is all zeros. We parse with offset=1 and length=1,
     * so the single byte read is buffer[1]=0, which sets the flags to 0 and
     * clears all timestamp-present bits.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        X5455_ExtendedTimestamp timestamp = new X5455_ExtendedTimestamp();

        // A 2-byte all-zero buffer; parsing at offset=1 with length=1 reads one flags byte (0).
        byte[] buffer = new byte[2];
        timestamp.parseFromCentralDirectoryData(buffer, (byte) 1, (byte) 1);

        // Flags byte of 0 means none of the three timestamp bits are set.
        assertEquals((byte) 0, timestamp.getFlags());
        assertFalse(timestamp.isBit1_accessTimePresent());
        assertFalse(timestamp.isBit0_modifyTimePresent());
    }
}
