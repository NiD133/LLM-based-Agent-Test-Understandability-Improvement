package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test16 extends TaiInstant_ESTest_scaffolding {

    private static final int EMPTY_PARSE_INPUT_CAPACITY = 706;

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        CharBuffer emptyParseInput = CharBuffer.allocate(EMPTY_PARSE_INPUT_CAPACITY);

        try {
            TaiInstant.parse(emptyParseInput);
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.scale.TaiInstant", e);
        }
    }
}
