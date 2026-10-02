package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.TemporalField;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test12 extends Half_ESTest_scaffolding {

    /**
     * Calling {@link Half#get(TemporalField)} with a null field must fail fast
     * with a NullPointerException raised by the internal null-check in
     * {@code java.util.Objects}.
     */
    @Test(timeout = 4000)
    public void getWithNullFieldThrowsNullPointerException() throws Throwable {
        Half secondHalf = Half.H2;

        try {
            secondHalf.get((TemporalField) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expected) {
            // The null field argument is rejected by java.util.Objects.
            verifyException("java.util.Objects", expected);
        }
    }
}
