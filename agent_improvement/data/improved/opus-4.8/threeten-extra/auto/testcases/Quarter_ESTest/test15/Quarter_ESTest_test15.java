package org.threeten.extra;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.time.temporal.TemporalField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test15 extends Quarter_ESTest_scaffolding {

    /**
     * Calling {@link Quarter#get(TemporalField)} with a null field must fail fast
     * with a NullPointerException raised by the {@code java.util.Objects} null-check.
     */
    @Test(timeout = 4000)
    public void get_withNullField_throwsNullPointerException() throws Throwable {
        Quarter quarter = Quarter.Q2;

        try {
            quarter.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null "field" argument is rejected by java.util.Objects.
            verifyException("java.util.Objects", e);
        }
    }
}
