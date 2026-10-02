package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PaxChronology_ESTest_test09 extends PaxChronology_ESTest_scaffolding {

    // In the Pax calendar, a year is a leap year when its last two digits are 99.
    // Year 99 satisfies this rule, so isLeapYear(99) must return true.
    @Test(timeout = 4000)
    public void testIsLeapYear_yearEndingIn99_isLeapYear() throws Throwable {
        PaxDate paxDate = PaxDate.ofEpochDay(146096L);
        PaxChronology paxChronology = paxDate.getChronology();
        boolean isLeap = paxChronology.isLeapYear(99L);
        assertTrue(isLeap);
    }
}
