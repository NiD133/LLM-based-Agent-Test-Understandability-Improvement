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
public class X5455_ExtendedTimestamp_ESTest_test10 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that flags retain the CREATE_TIME_BIT value (4) after parsing a buffer
     * where the flags byte is at offset 1 but there is insufficient remaining data
     * (only 1 byte available at that offset) to actually read the create timestamp.
     * The flags field preserves the raw parsed byte even when the timestamp itself
     * cannot be populated.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Build a 2-byte buffer: index 0 is unused padding, index 1 holds the flags byte.
        // Setting the flags byte to CREATE_TIME_BIT (4) signals that a create timestamp
        // is present, but no 4-byte timestamp follows because the buffer is too short.
        byte[] buffer = new byte[2];
        buffer[1] = X5455_ExtendedTimestamp.CREATE_TIME_BIT; // == (byte) 4

        // Parse starting at offset 1 (the flags byte) with a declared length of 4.
        // Only 1 byte is actually available from offset 1, so no timestamp data can
        // be read. The flags field still captures the raw byte value of 4.
        extendedTimestamp.parseFromLocalFileData(buffer, (byte) 1, (byte) 4);

        assertEquals(X5455_ExtendedTimestamp.CREATE_TIME_BIT, extendedTimestamp.getFlags());
    }
}
