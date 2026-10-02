package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test20 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_get_withUnsupportedChronoField_throwsUnsupportedTemporalTypeException() throws Throwable {
        Half secondHalf = Half.from(Month.OCTOBER);
        try {
            secondHalf.get(ChronoField.DAY_OF_YEAR);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.Half", e);
        }
    }
}
