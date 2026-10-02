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
public class Minutes_ESTest_test21 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parse_withInvalidText_throwsDateTimeParseException() throws Throwable {
        // "~,]:" does not match the ISO-8601 duration pattern (e.g. "PT5M"), so parse must reject it
        try {
            Minutes.parse("~,]:");
            fail("Expecting exception: DateTimeParseException");
        } catch (DateTimeParseException e) {
            verifyException("org.threeten.extra.Minutes", e);
        }
    }
}
