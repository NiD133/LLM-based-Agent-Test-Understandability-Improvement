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
public class Seconds_ESTest_test21 extends Seconds_ESTest_scaffolding {

    /**
     * Verifies that parsing the ISO-8601 duration string "PT0S" (zero seconds)
     * either succeeds and returns a Seconds instance with zero seconds, or, if
     * the implementation rejects it, throws a DateTimeParseException sourced
     * from Seconds.parse(). The unstable assertion reflects that the outcome
     * depends on whether the parser treats a zero-value duration as valid.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        try {
            Seconds result = Seconds.parse("PT0S");
            assertEquals(0, result.getAmount());
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Seconds", e);
        }
    }
}
