package org.threeten.extra.chrono;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Symmetry010Chronology_ESTest_test02 extends Symmetry010Chronology_ESTest_scaffolding {

    /**
     * Verifies that the Symmetry010 chronology provides a valid (non-null) value
     * range for the YEAR_OF_ERA field.
     */
    @Test(timeout = 4000)
    public void rangeForYearOfEraFieldIsNotNull() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);

        assertNotNull(yearOfEraRange);
    }
}
