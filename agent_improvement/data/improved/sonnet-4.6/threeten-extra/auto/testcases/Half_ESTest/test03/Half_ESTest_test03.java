package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.chrono.MinguoDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Locale;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.evosuite.runtime.mock.java.time.MockOffsetDateTime;
import org.evosuite.runtime.mock.java.time.chrono.MockMinguoDate;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test03 extends Half_ESTest_scaffolding {

    /**
     * Verifies that querying Half.H2 with a TemporalQuery that returns null
     * completes without throwing an exception.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Half secondHalf = Half.H2;

        // A query that always returns null regardless of the temporal accessor
        TemporalQuery<ChronoField> nullReturningQuery =
                (TemporalQuery<ChronoField>) mock(TemporalQuery.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(nullReturningQuery).queryFrom(any(java.time.temporal.TemporalAccessor.class));

        // Querying H2 with a null-returning query should not throw
        secondHalf.query(nullReturningQuery);
    }
}
