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

    // A CharBuffer filled with null characters does not match the ISO-8601 "PnW" pattern.
    private static final int CHAR_BUFFER_CAPACITY = 1749;

    @Test(timeout = 4000)
    public void testParseThrowsDateTimeParseExceptionForNonISO8601CharBuffer() throws Throwable {
        CharBuffer invalidWeeksText = CharBuffer.allocate(CHAR_BUFFER_CAPACITY);
        try {
            Weeks.parse(invalidWeeksText);
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
