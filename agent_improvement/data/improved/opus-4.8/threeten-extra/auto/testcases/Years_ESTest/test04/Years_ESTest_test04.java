package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test04 extends Years_ESTest_scaffolding {

    /**
     * Verifies that two {@link Years} amounts with different values are not equal.
     * <p>
     * Dividing one year by 498 uses integer division, so 1 / 498 rounds down to
     * zero years. The original "one year" amount must therefore differ from the
     * resulting "zero years" amount, and the inequality must hold in both directions.
     */
    @Test(timeout = 4000)
    public void oneYearIsNotEqualToZeroYearsFromIntegerDivision() throws Throwable {
        Years oneYear = Years.ONE;
        Years zeroYears = oneYear.dividedBy(498);

        assertFalse("One year should not equal zero years", oneYear.equals(zeroYears));
        assertFalse("Equality should be symmetric", zeroYears.equals((Object) oneYear));
    }
}
