package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BritishCutoverChronology_ESTest_test00 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology returns a (non-null) value range for a
     * supported temporal field such as SECOND_OF_DAY.
     */
    @Test(timeout = 4000)
    public void rangeForSecondOfDayReturnsValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        ValueRange secondOfDayRange = chronology.range(ChronoField.SECOND_OF_DAY);

        assertNotNull(secondOfDayRange);
    }
}
