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
public class Symmetry010Chronology_ESTest_test06 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid range of the
     * EPOCH_DAY field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForEpochDayFieldReturnsValueRange() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        ValueRange epochDayRange = chronology.range(ChronoField.EPOCH_DAY);

        assertNotNull(epochDayRange);
    }
}
