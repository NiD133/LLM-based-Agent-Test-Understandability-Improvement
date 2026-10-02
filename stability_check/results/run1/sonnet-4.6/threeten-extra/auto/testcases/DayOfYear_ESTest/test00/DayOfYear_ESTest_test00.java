package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test00 extends DayOfYear_ESTest_scaffolding {

    /**
     * DayOfYear.of() accepts values from 1 to 366 only.
     * Passing a negative value (-408) must throw DateTimeException.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        try {
            DayOfYear.of(-408);
            fail("Expecting exception: DateTimeException");
        } catch (DateTimeException e) {
            verifyException("org.threeten.extra.DayOfYear", e);
        }
    }
}
