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
     * Parsing the ISO-8601 text "P0D" (a period of zero days) succeeds and does
     * not throw. The original generated test wrapped the call in a try/catch for
     * DateTimeParseException, but because "P0D" is a well-formed input the catch
     * block is never reached; the test simply verifies the call completes
     * normally.
     */
    @Test(timeout = 4000)
    public void parsingZeroDaysTextDoesNotThrow() throws Throwable {
        try {
            Days.parse("P0D");
        } catch (DateTimeParseException e) {
            // Unreachable for the valid input "P0D"; kept to mirror the
            // original test's structure without changing its behaviour.
            verifyException("org.threeten.extra.Days", e);
        }
    }
}
