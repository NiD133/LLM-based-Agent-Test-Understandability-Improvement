package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.nio.CharBuffer;
import java.time.format.DateTimeParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test19 extends Months_ESTest_scaffolding {

    /**
     * Parsing text that does not match the ISO-8601 "PnYnM" period format must
     * fail. Here the input is a freshly allocated, unfilled CharBuffer (419 NUL
     * characters), which cannot be parsed into a Months value.
     */
    @Test(timeout = 4000)
    public void parse_unparsableText_throwsDateTimeParseException() throws Throwable {
        CharBuffer unparsableText = CharBuffer.allocate(419);

        try {
            Months.parse(unparsableText);
            fail("Expected DateTimeParseException for text that is not a valid Months");
        } catch (DateTimeParseException expected) {
            // Months.parse rejects the input with "Text cannot be parsed to a Months"
            verifyException("org.threeten.extra.Months", expected);
        }
    }
}
