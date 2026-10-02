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
public class X5455_ExtendedTimestamp_ESTest_test18 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that getCentralDirectoryData() does not modify the flags field.
     *
     * When only ACCESS_TIME_BIT (bit1) is set, the central directory contains
     * only the flags byte (no modify-time payload), because central data never
     * stores access or create timestamps. After the call, getFlags() must still
     * return the original ACCESS_TIME_BIT value.
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Set only the access-time bit; modify-time and create-time bits remain clear.
        extendedTimestamp.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);

        // Retrieve central directory bytes. With no modify-time bit set, this
        // produces a single-byte array containing just the flags byte.
        extendedTimestamp.getCentralDirectoryData();

        // Flags must be unchanged after getCentralDirectoryData() is called.
        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, extendedTimestamp.getFlags());
    }
}
