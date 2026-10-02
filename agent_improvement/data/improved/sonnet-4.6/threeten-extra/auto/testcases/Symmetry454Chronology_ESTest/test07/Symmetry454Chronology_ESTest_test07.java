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
public class Symmetry454Chronology_ESTest_test07 extends Symmetry454Chronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Symmetry454Chronology chronology = new Symmetry454Chronology();
        ChronoField dayOfMonthField = ChronoField.DAY_OF_MONTH;
        ValueRange dayOfMonthRange = chronology.INSTANCE.range(dayOfMonthField);
        assertNotNull(dayOfMonthRange);
    }
}
