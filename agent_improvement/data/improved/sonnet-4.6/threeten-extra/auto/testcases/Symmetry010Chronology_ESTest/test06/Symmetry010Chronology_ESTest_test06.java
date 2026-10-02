package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test06 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the EPOCH_DAY range from the Symmetry010 chronology
     * returns a non-null ValueRange, confirming the field is supported.
     */
    @Test(timeout = 4000)
    public void test_range_returnsNonNull_forEpochDayField() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();
        ValueRange epochDayRange = chronology.range(ChronoField.EPOCH_DAY);
        assertNotNull(epochDayRange);
    }
}
