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
public class Symmetry454Chronology_ESTest_test02 extends Symmetry454Chronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for YEAR_OF_ERA returns a non-null ValueRange.
     * The Symmetry454 chronology maps YEAR_OF_ERA to the same range as proleptic YEAR
     * (i.e. -1_000_000 to 1_000_000).
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);
        assertNotNull(yearOfEraRange);
    }
}
