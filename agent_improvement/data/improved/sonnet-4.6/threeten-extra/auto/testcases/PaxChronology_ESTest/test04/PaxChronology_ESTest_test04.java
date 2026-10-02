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
public class PaxChronology_ESTest_test04 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_range_dayOfYear_returnsNonNullValueRange() throws Throwable {
        PaxChronology paxChronology = new PaxChronology();
        ValueRange dayOfYearRange = paxChronology.range(ChronoField.DAY_OF_YEAR);
        assertNotNull(dayOfYearRange);
    }
}
