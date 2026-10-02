package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.MockInstant;
import org.evosuite.runtime.mock.java.time.MockZonedDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test20 extends Days_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        int negativeWeeks = -4447;
        int expectedDays = -31129;

        Days daysFromWeeks = Days.ofWeeks(negativeWeeks);
        TemporalUnit daysUnit = ChronoField.MILLI_OF_DAY.getRangeUnit();

        long oneDayAmount = Days.ONE.get(daysUnit);

        assertEquals(expectedDays, daysFromWeeks.getAmount());
        assertEquals(1L, oneDayAmount);
    }
}
