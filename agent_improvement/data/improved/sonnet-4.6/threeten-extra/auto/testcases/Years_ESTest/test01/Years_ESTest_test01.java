package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.IsoChronology;
import java.time.chrono.ThaiBuddhistDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.time.chrono.MockThaiBuddhistDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test01 extends Years_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Years.ONE is the constant representing 1 year
        Years oneYear = Years.ONE;
        // Adding -1 to ZERO yields a Years instance representing -1 year
        Years negativeOneYearFromZero = oneYear.ZERO.plus(-1);
        // Years.of(-1) should produce an equivalent -1 year instance
        Years negativeOneYear = Years.of(-1);

        boolean areBothNegativeOne = negativeOneYear.equals(negativeOneYearFromZero);

        assertEquals(-1, negativeOneYearFromZero.getAmount());
        assertTrue(areBothNegativeOne);
        assertFalse(negativeOneYear.equals((Object) oneYear));
        assertFalse(oneYear.equals((Object) negativeOneYear));
    }
}
