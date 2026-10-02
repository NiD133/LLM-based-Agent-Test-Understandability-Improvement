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
public class X5455_ExtendedTimestamp_ESTest_test21 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that two entries with the same flags byte are NOT equal when one has an
     * actual access timestamp stored and the other only has the access-time flag bit set
     * (without a corresponding timestamp value).
     *
     * Also confirms that calling setAccessFileTime() implicitly sets the ACCESS_TIME_BIT
     * flag on the entry that receives the timestamp.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        // entryWithFlagOnly: ACCESS_TIME_BIT (bit 1) is set via setFlags, but no access
        // time value is stored.
        X5455_ExtendedTimestamp entryWithFlagOnly = new X5455_ExtendedTimestamp();
        entryWithFlagOnly.setFlags(X5455_ExtendedTimestamp.ACCESS_TIME_BIT);

        // entryWithAccessTime: access time is set via FileTime (4 ms since epoch).
        // setAccessFileTime() internally sets ACCESS_TIME_BIT in the flags as well.
        X5455_ExtendedTimestamp entryWithAccessTime = new X5455_ExtendedTimestamp();
        FileTime accessFileTime = FileTime.fromMillis((byte) 4);
        entryWithAccessTime.setAccessFileTime(accessFileTime);

        // Even though both entries end up with the same flags byte (ACCESS_TIME_BIT = 2),
        // they differ in their stored access-time value, so equals() must return false.
        boolean areEqual = entryWithFlagOnly.equals(entryWithAccessTime);

        assertEquals(X5455_ExtendedTimestamp.ACCESS_TIME_BIT, entryWithAccessTime.getFlags());
        assertFalse(areEqual);
    }
}
