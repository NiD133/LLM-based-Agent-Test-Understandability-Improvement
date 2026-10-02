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
public class Years_ESTest_test00 extends Years_ESTest_scaffolding {

    /**
     * Verifies that parsing text that does not match the ISO-8601 'PnY' pattern
     * fails with a DateTimeParseException. Here the input is a CharBuffer of eight
     * NUL characters, which is not a valid Years representation.
     */
    @Test(timeout = 4000)
    public void parse_unparseableText_throwsDateTimeParseException() throws Throwable {
        CharSequence unparseableText = CharBuffer.wrap(new char[8]);

        try {
            Years.parse(unparseableText);
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            // Message: "Text cannot be parsed to a Years"
            verifyException("org.threeten.extra.Years", e);
        }
    }
}
