package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test21 extends Minutes_ESTest_scaffolding {

    /**
     * Verifies that parsing text that does not match the expected ISO-8601
     * period format (e.g. the garbage input "~,]:") throws a
     * DateTimeParseException reporting "Text cannot be parsed to Minutes".
     */
    @Test(timeout = 4000)
    public void parseRejectsTextThatDoesNotMatchPeriodFormat() throws Throwable {
        String invalidPeriodText = "~,]:";

        try {
            Minutes.parse(invalidPeriodText);
            fail("Expected a DateTimeParseException because the text is not a valid period");
        } catch (DateTimeParseException expected) {
            // The exception is thrown from within Minutes.parse(...)
            verifyException("org.threeten.extra.Minutes", expected);
        }
    }
}
