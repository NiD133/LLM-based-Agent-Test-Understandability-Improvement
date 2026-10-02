package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test15 extends Quarter_ESTest_scaffolding {

    /**
     * Quarter.get(TemporalField) must reject a null field with NullPointerException.
     * The null-check is performed via Objects.requireNonNull inside the default
     * TemporalAccessor.get() implementation, with the parameter name "field".
     */
    @Test(timeout = 4000)
    public void test_get_withNullField_throwsNullPointerException() throws Throwable {
        Quarter quarter = Quarter.Q2;
        try {
            quarter.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("java.util.Objects", e);
        }
    }
}
