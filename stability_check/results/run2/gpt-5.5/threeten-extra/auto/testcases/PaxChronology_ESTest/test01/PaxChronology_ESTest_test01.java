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
public class PaxChronology_ESTest_test01 extends PaxChronology_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        PaxDate dateAtEpochDay = PaxDate.ofEpochDay(146096L);
        PaxChronology chronology = dateAtEpochDay.getChronology();
        ChronoField monthOfYear = ChronoField.MONTH_OF_YEAR;

        ValueRange monthRange = chronology.range(monthOfYear);

        assertNotNull(monthRange);
    }
}
