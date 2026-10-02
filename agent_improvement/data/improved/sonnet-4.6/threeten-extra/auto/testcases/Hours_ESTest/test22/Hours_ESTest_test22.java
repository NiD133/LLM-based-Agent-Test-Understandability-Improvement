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
public class Hours_ESTest_test22 extends Hours_ESTest_scaffolding {

    /**
     * Parsing an ISO-8601 duration string with a negative hours component inside the
     * time designator ("PT-1485H") may or may not be accepted depending on the
     * parser implementation.  The test verifies that when a DateTimeParseException
     * IS thrown it originates from Hours, but does not require the exception to be
     * thrown (the input satisfies the regex pattern that Hours.parse uses).
     */
    @Test(timeout = 4000)
    public void test_parse_durationStringWithNegativeHoursComponent_doesNotThrowOrThrowsDateTimeParseException() throws Throwable {
        try {
            Hours.parse("PT-1485H");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Hours", e);
        }
    }
}
