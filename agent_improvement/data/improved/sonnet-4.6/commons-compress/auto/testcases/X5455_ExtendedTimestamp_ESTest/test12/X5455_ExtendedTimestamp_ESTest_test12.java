package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test12 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that the raw flags byte is preserved after parsing local file data
     * even when the data buffer is too short to contain the timestamp indicated by the flags.
     *
     * Setup:
     *   - byte[3] with ACCESS_TIME_BIT (0x02) placed at index 2
     *   - parseFromLocalFileData called with offset=2 and length=2
     *   - The parser reads the flags byte at data[2] = 0x02 (ACCESS_TIME_BIT set)
     *   - Only 1 byte remains after the flags byte, which is insufficient for a 4-byte timestamp
     *   - The access-time-present boolean is cleared, but the flags field itself stays at 0x02
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Place ACCESS_TIME_BIT in the byte that will be read as the flags byte (index 2).
        byte[] localFileData = new byte[3];
        localFileData[2] = X5455_ExtendedTimestamp.ACCESS_TIME_BIT; // 0x02

        // Parse starting at offset 2 with length 2: only data[2] and data[3] would be in scope,
        // but data[3] is out of bounds — effectively only 1 byte of content (the flags) is present.
        extendedTimestamp.parseFromLocalFileData(localFileData, (byte) 2, (byte) 2);

        // Although the access timestamp itself could not be read (insufficient data),
        // the raw flags byte should still reflect the value that was stored in the buffer.
        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
