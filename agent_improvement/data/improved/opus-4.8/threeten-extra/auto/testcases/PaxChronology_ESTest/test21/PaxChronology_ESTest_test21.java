package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import java.time.chrono.Era;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test21 extends PaxChronology_ESTest_scaffolding {

    /**
     * {@link PaxChronology#date(Era, int, int, int)} requires the era to be a
     * {@link PaxEra}. Passing a null era cannot be cast to PaxEra, so the call
     * must fail fast with a ClassCastException reading "Era must be PaxEra".
     */
    @Test(timeout = 4000)
    public void dateWithNonPaxEraThrowsClassCastException() throws Throwable {
        PaxChronology paxChronology = PaxChronology.INSTANCE;
        Era nonPaxEra = null;

        try {
            paxChronology.date(nonPaxEra, -70, -70, -70);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Message: "Era must be PaxEra"
            verifyException("org.threeten.extra.chrono.PaxChronology", e);
        }
    }
}
