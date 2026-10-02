package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Seconds_ESTest_test26 extends Seconds_ESTest_scaffolding {

    /**
     * Seconds.between delegates to ChronoUnit.SECONDS.between, which rejects null
     * temporals. Passing null for both the start and end temporal must therefore
     * raise a NullPointerException originating from ChronoUnit.
     */
    @Test(timeout = 4000)
    public void between_withNullStartAndEnd_throwsNullPointerException() throws Throwable {
        try {
            Seconds.between((Temporal) null, (Temporal) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception has no message and is thrown from within ChronoUnit.
            verifyException("java.time.temporal.ChronoUnit", e);
        }
    }
}
