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
public class InternationalFixedChronology_ESTest_test03 extends InternationalFixedChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for the YEAR_OF_ERA field
     * returns a non-null range from the International Fixed chronology.
     */
    @Test(timeout = 4000)
    public void rangeForYearOfEraReturnsNonNullValueRange() throws Throwable {
        InternationalFixedChronology chronology = InternationalFixedChronology.INSTANCE;

        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);

        assertNotNull(yearOfEraRange);
    }
}
