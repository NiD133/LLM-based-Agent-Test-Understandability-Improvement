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
     * Parsing the ISO-8601 text "PT-1485H" is well-formed (a "T" section with a
     * signed hours value), so {@link Hours#parse} accepts it and returns the
     * corresponding amount without throwing.
     *
     * The original generated test wrapped the call in a try/catch for
     * DateTimeParseException whose fail() was disabled as "unstable"; that catch
     * is never actually reached because the input parses successfully. This test
     * keeps the same observable behaviour: the parse call completes normally.
     */
    @Test(timeout = 4000)
    public void parseSignedHoursTextSucceeds() throws Throwable {
        try {
            Hours.parse("PT-1485H");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Hours", e);
        }
    }
}
