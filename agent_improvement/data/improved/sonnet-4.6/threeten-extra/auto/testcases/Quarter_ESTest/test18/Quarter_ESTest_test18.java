package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.ZoneOffset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test18 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.from() must throw DateTimeException when given a ZoneOffset,
     * because ZoneOffset carries no quarter-of-year information.
     */
    @Test(timeout = 4000)
    public void test_from_zoneOffset_throwsDateTimeException() throws Throwable {
        // ZoneOffset.MIN is UTC-18:00 — it has no concept of a calendar quarter
        ZoneOffset minOffset = ZoneOffset.MIN;

        try {
            Quarter.from(minOffset);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.Quarter", e);
        }
    }
}
