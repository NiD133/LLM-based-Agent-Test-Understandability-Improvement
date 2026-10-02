package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test14 extends PaxChronology_ESTest_scaffolding {

    // Month -1093 is far outside the valid Pax range of 1–13 (or 1–14 in leap years).
    private static final int INVALID_MONTH = -1093;

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // Calling date() with an out-of-range month must throw DateTimeException,
        // detected by ValueRange during field validation.
        try {
            PaxChronology.INSTANCE.date(INVALID_MONTH, INVALID_MONTH, 0);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("java.time.temporal.ValueRange", e);
        }
    }
}
