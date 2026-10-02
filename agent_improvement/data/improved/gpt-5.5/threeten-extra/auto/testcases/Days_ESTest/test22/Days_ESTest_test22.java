package org.threeten.extra;

import org.junit.Test;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.format.DateTimeParseException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test22 extends Days_ESTest_scaffolding {

    private static final String ZERO_DAYS_TEXT = "P0D";

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        try {
            Days.parse(ZERO_DAYS_TEXT);
        } catch (DateTimeParseException exception) {
            verifyException("org.threeten.extra.Days", exception);
        }
    }
}
