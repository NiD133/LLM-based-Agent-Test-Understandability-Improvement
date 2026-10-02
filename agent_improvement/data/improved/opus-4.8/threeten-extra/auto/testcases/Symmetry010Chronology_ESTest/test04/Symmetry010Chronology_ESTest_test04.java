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
public class Symmetry010Chronology_ESTest_test04 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that the Symmetry010 chronology provides a valid value range
     * for the MONTH_OF_YEAR field.
     */
    @Test(timeout = 4000)
    public void rangeOfMonthOfYearIsAvailable() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        ValueRange monthOfYearRange = chronology.range(ChronoField.MONTH_OF_YEAR);

        assertNotNull(monthOfYearRange);
    }
}
