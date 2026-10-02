package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AmPm_ESTest_test01 extends AmPm_ESTest_scaffolding {

    /**
     * AmPm only supports the AMPM_OF_DAY temporal field. Querying it with
     * an unrelated ChronoField (ERA) via getFrom() must throw
     * UnsupportedTemporalTypeException, with the exception originating from AmPm.
     */
    @Test(timeout = 4000)
    public void test01_getFromUnsupportedField_ERA_throwsUnsupportedTemporalTypeException() throws Throwable {
        AmPm am = AmPm.AM;
        ChronoField eraField = ChronoField.ERA;

        try {
            eraField.getFrom(am);
            fail("Expecting exception: UnsupportedTemporalTypeException");
        } catch (UnsupportedTemporalTypeException e) {
            verifyException("org.threeten.extra.AmPm", e);
        }
    }
}
