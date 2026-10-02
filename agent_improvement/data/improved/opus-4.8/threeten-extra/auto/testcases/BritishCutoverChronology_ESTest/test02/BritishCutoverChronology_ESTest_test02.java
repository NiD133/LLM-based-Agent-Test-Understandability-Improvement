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
public class BritishCutoverChronology_ESTest_test02 extends BritishCutoverChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the chronology for the valid value range of the
     * YEAR_OF_ERA field returns a (non-null) ValueRange.
     */
    @Test(timeout = 4000)
    public void rangeForYearOfEraReturnsValueRange() throws Throwable {
        BritishCutoverChronology chronology = new BritishCutoverChronology();

        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);

        assertNotNull(yearOfEraRange);
    }
}
