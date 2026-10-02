package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.util.MockDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class X5455_ExtendedTimestamp_ESTest_test25 extends X5455_ExtendedTimestamp_ESTest_scaffolding {

    /**
     * X5455 timestamps are stored as signed 32-bit integers (seconds since the
     * Unix epoch). Setting an access time from a date that predates the lowest
     * representable value must be rejected with an IllegalArgumentException.
     *
     * The MockDate(1, 4, 2) below corresponds to the year 1901, which converts
     * to -2166998400 seconds since the epoch - too far in the past to fit.
     */
    @Test(timeout = 4000)
    public void setAccessJavaTimeRejectsDateTooFarInThePast() throws Throwable {
        X5455_ExtendedTimestamp extendedTimestamp = new X5455_ExtendedTimestamp();
        MockDate dateBeforeRepresentableRange = new MockDate((byte) 1, (byte) 4, (byte) 2);

        try {
            extendedTimestamp.setAccessJavaTime(dateBeforeRepresentableRange);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message: "X5455 timestamps must fit in a signed 32 bit integer: -2166998400"
            verifyException("org.apache.commons.compress.archivers.zip.X5455_ExtendedTimestamp", e);
        }
    }
}
