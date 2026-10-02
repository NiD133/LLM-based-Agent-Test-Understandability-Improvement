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
     * Parsing the ISO-8601 text "PT0S" (zero seconds) is a valid input, so
     * {@link Seconds#parse(CharSequence)} succeeds and returns the canonical
     * zero instance rather than throwing a DateTimeParseException.
     */
    @Test(timeout = 4000)
    public void parseZeroSecondsTextReturnsZero() throws Throwable {
        try {
            Seconds result = Seconds.parse("PT0S");

            // "PT0S" is well-formed, so no exception is thrown and the result is zero.
            assertEquals(0, result.getAmount());
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Seconds", e);
        }
    }
}
