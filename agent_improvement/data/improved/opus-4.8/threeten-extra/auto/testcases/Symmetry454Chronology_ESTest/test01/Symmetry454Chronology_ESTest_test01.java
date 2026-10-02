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
public class Symmetry454Chronology_ESTest_test01 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the supported {@link ChronoField#YEAR}
     * field returns a (non-null) valid value range.
     */
    @Test(timeout = 4000)
    public void rangeForYearFieldReturnsValueRange() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange yearRange = chronology.range(ChronoField.YEAR);

        assertNotNull(yearRange);
    }
}
