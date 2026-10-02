package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test20 extends Weeks_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withUnsupportedUnit_throwsUnsupportedTemporalTypeException() throws Throwable {
        Weeks oneWeek = Weeks.ONE;
        ChronoUnit unsupportedUnit = ChronoUnit.ERAS;

        try {
            oneWeek.get(unsupportedUnit);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Weeks", e);
        }
    }
}
