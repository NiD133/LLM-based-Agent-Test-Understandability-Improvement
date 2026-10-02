package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.MockYearMonth;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test30 extends Minutes_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        Minutes negativeEightHours = Minutes.ofHours((-8));
        Minutes absoluteMinutes = negativeEightHours.abs();

        assertEquals(480, absoluteMinutes.getAmount());
        assertEquals((-480), negativeEightHours.getAmount());
    }
}
