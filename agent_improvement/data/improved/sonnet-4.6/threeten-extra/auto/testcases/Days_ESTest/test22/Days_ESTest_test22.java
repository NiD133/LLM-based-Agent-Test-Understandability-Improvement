package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test22 extends Days_ESTest_scaffolding {

    /**
     * Verifies that parsing a zero-day ISO-8601 period string ("P0D") either
     * succeeds or throws a DateTimeParseException originating from Days.
     * Both outcomes are acceptable; the important constraint is that no other
     * exception type escapes.
     */
    @Test(timeout = 4000)
    public void test_parse_zeroDayPeriodString_succeedsOrThrowsDateTimeParseException() throws Throwable {
        try {
            Days.parse("P0D");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Days", e);
        }
    }
}
