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
public class Symmetry454Chronology_ESTest_test10 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * The Symmetry454 chronology should report a valid value range for the
     * DAY_OF_WEEK field.
     */
    @Test(timeout = 4000)
    public void rangeForDayOfWeekIsNotNull() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange dayOfWeekRange = chronology.range(ChronoField.DAY_OF_WEEK);

        assertNotNull(dayOfWeekRange);
    }
}
