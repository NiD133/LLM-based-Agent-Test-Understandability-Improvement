package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test10 extends AccountingChronology_ESTest_scaffolding {

    /**
     * AccountingChronology.equals(Object) must return false when compared against
     * an object that is not an AccountingChronology (here, a plain Object).
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForNonAccountingChronologyObject() throws Throwable {
        AccountingChronology chronology = AccountingChronology.create(
                DayOfWeek.WEDNESDAY,
                Month.APRIL,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                2,
                2);

        boolean isEqual = chronology.equals(new Object());

        assertFalse(isEqual);
    }
}
