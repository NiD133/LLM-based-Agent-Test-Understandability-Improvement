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
public class JulianChronology_ESTest_test01 extends JulianChronology_ESTest_scaffolding {

    /**
     * Verifies that querying the value range for the YEAR field on the
     * JulianChronology singleton returns a non-null ValueRange.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JulianChronology chronology = new JulianChronology();
        ChronoField yearField = ChronoField.YEAR;
        ValueRange yearRange = chronology.INSTANCE.range(yearField);
        assertNotNull(yearRange);
    }
}
