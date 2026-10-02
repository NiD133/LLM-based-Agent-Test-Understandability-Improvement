package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalUnit;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Days_ESTest_test20 extends Days_ESTest_scaffolding {

    /**
     * Verifies two independent facts about {@link Days}:
     * <ol>
     *   <li>{@code Days.ofWeeks} converts weeks to days by multiplying by 7,
     *       so -4447 weeks becomes -31129 days.</li>
     *   <li>{@code Days.ONE} reports an amount of 1 when queried with the DAYS unit.
     *       The range unit of {@code ChronoField.MILLI_OF_DAY} is DAYS, which is the
     *       only unit {@code Days.get} supports.</li>
     * </ol>
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        Days negativeWeeks = Days.ofWeeks(-4447);
        assertEquals(-31129, negativeWeeks.getAmount());

        TemporalUnit daysUnit = ChronoField.MILLI_OF_DAY.getRangeUnit();
        long amountOfOneDay = Days.ONE.get(daysUnit);
        assertEquals(1L, amountOfOneDay);
    }
}
