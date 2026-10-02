package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JulianChronology_ESTest_test01 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that the Julian chronology provides a valid value range
     * for the YEAR field rather than returning null.
     */
    @Test(timeout = 4000)
    public void rangeForYearFieldIsNotNull() throws Throwable {
        JulianChronology julianChronology = JulianChronology.INSTANCE;

        ValueRange yearRange = julianChronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
