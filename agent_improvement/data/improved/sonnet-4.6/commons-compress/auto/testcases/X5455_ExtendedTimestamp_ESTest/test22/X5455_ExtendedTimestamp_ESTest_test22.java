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
public class X5455_ExtendedTimestamp_ESTest_test22 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a flags byte with bit 2 set (CREATE_TIME_BIT = 0x04)
     * marks create-time as present, and that two instances with different flags
     * are not considered equal.
     *
     * The flags value -76 (0xB4 = 1011 0100) has its lower 3 bits as 100,
     * meaning only CREATE_TIME_BIT (bit 2) is set among the meaningful flag bits.
     * The default-constructed instance has flags = 0x00, so the two differ.
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        // Flags byte 0xB4 (-76): lower 3 bits = 100, so only bit 2 (CREATE_TIME_BIT) is set
        byte flagsWithCreateTimeBit = (byte) (-76);
        X5455_ExtendedTimestamp timestampWithCreateFlag = new X5455_ExtendedTimestamp();
        timestampWithCreateFlag.setFlags(flagsWithCreateTimeBit);

        // Default instance has flags = 0x00, so no timestamp bits are set
        X5455_ExtendedTimestamp defaultTimestamp = new X5455_ExtendedTimestamp();

        // Instances with different lower-3-bit flag values must not be equal
        boolean areEqual = timestampWithCreateFlag.equals(defaultTimestamp);
        assertFalse(areEqual);

        // Setting bit 2 in flags must mark create time as present
        assertTrue(timestampWithCreateFlag.isBit2_createTimePresent());
    }
}
