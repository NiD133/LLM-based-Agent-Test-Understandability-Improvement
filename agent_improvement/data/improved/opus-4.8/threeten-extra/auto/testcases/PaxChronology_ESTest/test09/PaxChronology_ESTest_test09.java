package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test09 extends PaxChronology_ESTest_scaffolding {

    /**
     * In the Pax calendar a proleptic year is a leap year when its last two
     * digits are 99. Year 99 satisfies this rule, so isLeapYear must return true.
     */
    @Test(timeout = 4000)
    public void isLeapYearReturnsTrueForYearEndingIn99() throws Throwable {
        PaxChronology paxChronology = PaxDate.ofEpochDay(146096L).getChronology();

        boolean leapYear = paxChronology.isLeapYear(99L);

        assertTrue("Year 99 ends in 99 and must be a Pax leap year", leapYear);
    }
}
