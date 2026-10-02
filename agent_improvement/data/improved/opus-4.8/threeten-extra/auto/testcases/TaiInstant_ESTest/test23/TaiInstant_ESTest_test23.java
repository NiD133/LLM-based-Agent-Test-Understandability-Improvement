package org.threeten.extra.scale;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TaiInstant_ESTest_test23 extends TaiInstant_ESTest_scaffolding {

    /**
     * Converting a null {@link UtcInstant} to a {@link TaiInstant} must fail.
     * The {@code TaiInstant.of(UtcInstant)} factory delegates to the system
     * {@code UtcRules}, which rejects the null argument with a
     * {@link NullPointerException} (no message).
     */
    @Test(timeout = 4000)
    public void of_nullUtcInstant_throwsNullPointerException() throws Throwable {
        try {
            TaiInstant.of((UtcInstant) null);
            fail("Expected NullPointerException for a null UtcInstant");
        } catch (NullPointerException expected) {
            // Thrown from within UtcRules while dereferencing the null instant.
            verifyException("org.threeten.extra.scale.UtcRules", expected);
        }
    }
}
