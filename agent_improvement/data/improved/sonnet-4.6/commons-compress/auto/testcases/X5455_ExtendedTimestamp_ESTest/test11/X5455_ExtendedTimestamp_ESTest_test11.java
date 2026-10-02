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
public class X5455_ExtendedTimestamp_ESTest_test11 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that parseFromLocalFileData correctly reads the flags byte from
     * the given offset within the buffer and stores it on the field.
     *
     * The flags byte value 2 corresponds to ACCESS_TIME_BIT, meaning the
     * access-time field is present in the extra-field data.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Build a 20-byte buffer with the flags byte placed at index 2.
        // Flags value 2 == ACCESS_TIME_BIT (bit 1 set).
        byte[] localFileData = new byte[20];
        final int dataOffset = 2;
        final byte accessTimeFlagOnly = (byte) 2;
        localFileData[dataOffset] = accessTimeFlagOnly;

        // Parse starting at offset 2 with a length that covers the remainder of the buffer.
        extendedTimestamp.parseFromLocalFileData(localFileData, (byte) dataOffset, 2212);

        // The flags byte read from the buffer should equal the value we placed there.
        assertEquals(accessTimeFlagOnly, extendedTimestamp.getFlags());
    }
}
