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
public class Months_ESTest_test19 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void parse_withUnparsableCharBuffer_throwsDateTimeParseException() throws Throwable {
        // A CharBuffer filled with null characters does not match the ISO-8601 period pattern
        CharBuffer unparsableInput = CharBuffer.allocate(419);
        try {
            Months.parse(unparsableInput);
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Months", e);
        }
    }
}
