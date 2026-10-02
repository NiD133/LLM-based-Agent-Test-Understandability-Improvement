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
public class Symmetry454Chronology_ESTest_test04 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testRangeForMonthOfYearFieldIsNotNull() throws Throwable {
        // Instantiate via the deprecated public constructor (required for coverage); INSTANCE is used for the actual query
        new Symmetry454Chronology();
        ValueRange monthOfYearRange = Symmetry454Chronology.INSTANCE.range(ChronoField.MONTH_OF_YEAR);
        assertNotNull(monthOfYearRange);
    }
}
