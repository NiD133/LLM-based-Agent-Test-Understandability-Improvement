package org.threeten.extra.scale;

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
public class TaiInstant_ESTest_test16 extends TaiInstant_ESTest_scaffolding {

    /**
     * Verifies that {@link TaiInstant#parse(CharSequence)} rejects text that does not
     * match the required {@code {seconds}.{nanosOfSecond}s(TAI)} format.
     * <p>
     * Here the input is an unwritten {@link CharBuffer} (706 NUL characters), which
     * does not match the parser pattern, so a {@link DateTimeParseException} is thrown.
     */
    @Test(timeout = 4000)
    public void parse_withUnparsableText_throwsDateTimeParseException() throws Throwable {
        CharBuffer unparsableText = CharBuffer.allocate(706);

        try {
            TaiInstant.parse(unparsableText);
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            // The text could not be parsed.
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
