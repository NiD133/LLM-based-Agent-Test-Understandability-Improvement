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
public class X5455_ExtendedTimestamp_ESTest_test25 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * X5455 stores timestamps as 32-bit signed Unix seconds, so dates before
     * approximately 1901-12-13 (whose epoch value underflows a signed 32-bit int)
     * must be rejected. This test confirms that setAccessJavaTime throws
     * IllegalArgumentException for such an out-of-range date.
     *
     * MockDate(1, 4, 2) constructs a date equivalent to new Date(1, 4, 2),
     * i.e. year 1901, month May (0-indexed), day 2 — a Unix timestamp of
     * -2166998400 seconds, which is smaller than Integer.MIN_VALUE (-2147483648).
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();

        // Represents 1901-05-02, whose Unix epoch value (-2166998400s) is too
        // negative to fit in the signed 32-bit integer that X5455 uses for storage.
        MockDate year1901May2 = new MockDate((byte) 1, (byte) 4, (byte) 2);

        try {
            extendedTimestamp.setAccessJavaTime(year1901May2);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected message includes the offending timestamp value.
            verifyException("org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp", e);
        }
    }
}
