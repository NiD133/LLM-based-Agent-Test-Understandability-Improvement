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
public class Symmetry010Chronology_ESTest_test11 extends Symmetry010Chronology_ESTest_scaffolding {

    // Verifies that range() returns a non-null ValueRange for the DAY_OF_WEEK field,
    // which in Symmetry010 maps to 1–7 (one per weekday, same as ISO).
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;
        ValueRange dayOfWeekRange = chronology.range(ChronoField.DAY_OF_WEEK);
        assertNotNull(dayOfWeekRange);
    }
}
