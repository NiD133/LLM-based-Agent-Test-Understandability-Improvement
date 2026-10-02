package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Instant;
import java.util.Date;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test06 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * Verifies that setting a non-null modify time via {@code setModifyJavaTime}
     * causes {@code getFlags()} to report bit 0 (MODIFY_TIME_BIT) as set,
     * and that {@code toString()} executes without error when modify time is present.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Build a Date from a small epoch offset (4 ms) to use as the modify time
        Instant modifyInstant = MockInstant.ofEpochMilli((byte) 4);
        Date modifyDate = Date.from(modifyInstant);
        extendedTimestamp.setModifyJavaTime(modifyDate);

        // Exercise toString() to confirm it handles a set modify time without throwing
        extendedTimestamp.toString();

        // Setting a non-null modify time must activate MODIFY_TIME_BIT (bit 0) in flags
        assertEquals(X5455_ExtendedTimestamp.MODIFY_TIME_BIT, extendedTimestamp.getFlags());
    }
}
