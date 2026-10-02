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
public class X5455_ExtendedTimestamp_ESTest_test20 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Tests that two X5455_ExtendedTimestamp instances with differing create times
     * are not considered equal, even when their flags bytes share the same
     * CREATE_TIME_BIT (bit 2).
     *
     * - entryWithFlagsOnly has flags = -76 (0xB4), whose lower 3 bits equal 4
     *   (CREATE_TIME_BIT set), but no create-time value assigned.
     * - entryWithCreateTime has its create time set via setCreateTime(), which
     *   implicitly sets CREATE_TIME_BIT in its flags and stores DD_SIG as the
     *   create-time ZipLong.
     *
     * equals() compares both the lower 3 flag bits and the actual timestamp fields,
     * so the two instances are not equal because createTime differs (null vs DD_SIG).
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Set up first entry: flags byte -76 (0xB4) whose lower 3 bits = 4 (CREATE_TIME_BIT),
        // but no timestamp values are stored.
        X5455_ExtendedTimestamp entryWithFlagsOnly = new X5455_ExtendedTimestamp();
        entryWithFlagsOnly.setFlags((byte) (-76));

        // Set up second entry: assign DD_SIG as the create time, which also sets
        // CREATE_TIME_BIT in the flags and marks isBit2_createTimePresent() as true.
        X5455_ExtendedTimestamp entryWithCreateTime = new X5455_ExtendedTimestamp();
        ZipLong createTimeValue = ZipLong.DD_SIG;
        entryWithCreateTime.setCreateTime(createTimeValue);

        // The two entries differ in their createTime field (null vs DD_SIG),
        // so equals() must return false.
        boolean areEqual = entryWithFlagsOnly.equals(entryWithCreateTime);

        assertTrue(entryWithCreateTime.isBit2_createTimePresent());
        assertFalse(areEqual);
    }
}
