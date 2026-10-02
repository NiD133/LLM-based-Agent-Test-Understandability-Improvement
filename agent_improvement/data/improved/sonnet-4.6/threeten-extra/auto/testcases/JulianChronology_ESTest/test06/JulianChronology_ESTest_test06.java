package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests for {@link JulianChronology#isLeapYear(long)}.
 *
 * In the Julian calendar, a proleptic year is a leap year if and only if
 * it is exactly divisible by 4.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test06 extends JulianChronology_ESTest_scaffolding {

    /**
     * -11999988 is evenly divisible by 4 (-11999988 / 4 = -2999997), so
     * {@code isLeapYear} must return {@code true} for this proleptic year.
     */
    @Test(timeout = 4000)
    public void test06_isLeapYear_trueForYearDivisibleByFour() throws Throwable {
        // A proleptic year that is a multiple of 4 must be recognised as a leap year.
        final long prolepticLeapYear = -11999988L; // -11999988 % 4 == 0

        boolean result = JulianChronology.INSTANCE.isLeapYear(prolepticLeapYear);

        assertTrue("Expected proleptic year " + prolepticLeapYear + " to be a leap year (divisible by 4)", result);
    }
}
