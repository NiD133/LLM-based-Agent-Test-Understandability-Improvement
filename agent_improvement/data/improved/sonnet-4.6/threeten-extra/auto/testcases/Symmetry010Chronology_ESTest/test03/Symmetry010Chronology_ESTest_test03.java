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
public class Symmetry010Chronology_ESTest_test03 extends Symmetry010Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Symmetry010Chronology chronology = new Symmetry010Chronology();

        // Verify that querying the range for PROLEPTIC_MONTH returns a valid (non-null) ValueRange
        ValueRange prolepticMonthRange = chronology.range(ChronoField.PROLEPTIC_MONTH);
        assertNotNull(prolepticMonthRange);
    }
}
