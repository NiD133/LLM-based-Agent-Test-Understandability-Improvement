package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.Temporal;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test06 extends Years_ESTest_scaffolding {

    /**
     * When the amount of years is zero, {@link Years#subtractFrom(Temporal)}
     * leaves the temporal untouched and simply returns the argument unchanged.
     *
     * Here {@code Years.ONE.dividedBy(498)} is 1 / 498, which truncates to 0 years.
     * Subtracting that zero amount from a {@code null} temporal therefore returns
     * {@code null} rather than dereferencing it.
     */
    @Test(timeout = 4000)
    public void subtractFromNullWithZeroYearsReturnsNull() throws Throwable {
        Years zeroYears = Years.ONE.dividedBy(498);

        Temporal result = zeroYears.subtractFrom((Temporal) null);

        assertNull(result);
    }
}
