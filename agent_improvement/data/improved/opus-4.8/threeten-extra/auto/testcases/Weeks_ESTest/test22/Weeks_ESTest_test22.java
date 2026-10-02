package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test22 extends Weeks_ESTest_scaffolding {

    /**
     * Verifies that {@link Weeks#parse(CharSequence)} rejects text that does not
     * match the expected {@code PnW} pattern. Here the input is a freshly
     * allocated {@link CharBuffer} whose contents are NUL characters, so parsing
     * must fail with a {@link DateTimeParseException}.
     */
    @Test(timeout = 4000)
    public void parseRejectsTextNotMatchingWeeksPattern() throws Throwable {
        CharBuffer unparseableText = CharBuffer.allocate(1749);

        try {
            Weeks.parse(unparseableText);
            fail("Expected a DateTimeParseException because the text is not in 'PnW' format");
        } catch (DateTimeParseException expected) {
            // "Text cannot be parsed to a Weeks"
            verifyException("org.threeten.extra.Weeks", expected);
        }
    }
}
