package org.threeten.extra.chrono;

import static org.junit.Assert.assertNotNull;

import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry454Chronology_ESTest_test02 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that the chronology can supply a valid value range for the
     * YEAR_OF_ERA field.
     */
    @Test(timeout = 4000)
    public void range_forYearOfEra_returnsNonNullRange() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();

        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);

        assertNotNull(yearOfEraRange);
    }
}
