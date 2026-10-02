package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DayOfYear_ESTest_test06 extends DayOfYear_ESTest_scaffolding {

    /**
     * Verifies that {@link DayOfYear#query(TemporalQuery)} delegates to a custom
     * (non-built-in) query, returning exactly whatever that query produces.
     * Here a mocked query is stubbed to echo back the same DayOfYear it is asked
     * about, so the result must be that DayOfYear, value 45 (the mocked "today").
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        DayOfYear today = DayOfYear.now();

        // A custom query stubbed to return the DayOfYear it is queried from.
        @SuppressWarnings("unchecked")
        TemporalQuery<Object> echoQuery = mock(TemporalQuery.class, new ViolatedAssumptionAnswer());
        doReturn(today).when(echoQuery).queryFrom(any(TemporalAccessor.class));

        DayOfYear queryResult = (DayOfYear) today.query(echoQuery);

        assertEquals(45, queryResult.getValue());
    }
}
