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
public class JulianChronology_ESTest_test02 extends JulianChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        ValueRange yearOfEraRange = chronology.range(ChronoField.YEAR_OF_ERA);
        assertNotNull(yearOfEraRange);
    }
}
