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
public class BritishCutoverChronology_ESTest_test01 extends BritishCutoverChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01_rangeForYearFieldReturnsNonNullValueRange() throws Throwable {
        BritishCutoverChronology chronology = BritishCutoverChronology.INSTANCE;
        ValueRange yearRange = chronology.range(ChronoField.YEAR);
        assertNotNull(yearRange);
    }
}
