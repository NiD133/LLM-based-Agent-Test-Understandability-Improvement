package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.chrono.Era;
import java.time.chrono.ThaiBuddhistEra;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AccountingChronology_ESTest_test22 extends AccountingChronology_ESTest_scaffolding {

    /**
     * dateYearDay(Era, yearOfEra, dayOfYear) must reject any era that is not an
     * AccountingEra. Here a ThaiBuddhistEra is passed, so the chronology should
     * raise a ClassCastException complaining that the era must be an AccountingEra.
     */
    @Test(timeout = 4000)
    public void dateYearDay_withNonAccountingEra_throwsClassCastException() throws Throwable {
        AccountingChronology accountingChronology = AccountingChronology.create(
                DayOfWeek.TUESDAY,
                Month.DECEMBER,
                true,
                AccountingYearDivision.QUARTERS_OF_PATTERN_4_5_4_WEEKS,
                1,
                1);

        Era nonAccountingEra = ThaiBuddhistEra.BEFORE_BE;

        try {
            accountingChronology.dateYearDay(nonAccountingEra, 1, 1);
            fail("Expecting exception: ClassCastException");
        } catch (ClassCastException e) {
            // Era must be AccountingEra
            verifyException("org.threeten.extra.chrono.AccountingChronology", e);
        }
    }
}
