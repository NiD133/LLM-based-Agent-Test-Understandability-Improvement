package org.threeten.extra;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.any;
import static org.evosuite.shaded.org.mockito.Mockito.doReturn;
import static org.evosuite.shaded.org.mockito.Mockito.mock;
import static org.evosuite.shaded.org.mockito.Mockito.when;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test03 extends Half_ESTest_scaffolding {

    /**
     * A custom {@link TemporalQuery} (one that is neither the chronology nor the
     * precision query) is delegated by {@link Half#query} to the default
     * {@code TemporalAccessor} behaviour, which simply invokes the query's
     * {@code queryFrom} method. This verifies that whatever the query returns
     * (here, {@code null}) is passed straight back to the caller.
     */
    @Test(timeout = 4000)
    public void queryWithCustomQueryReturnsQueryResult() throws Throwable {
        Half half = Half.H2;

        // A custom query stubbed to return null when applied to any temporal.
        @SuppressWarnings("unchecked")
        TemporalQuery<Object> customQuery =
                (TemporalQuery<Object>) mock(TemporalQuery.class, new ViolatedAssumptionAnswer());
        doReturn(null).when(customQuery).queryFrom(any(TemporalAccessor.class));

        Object result = half.query(customQuery);

        org.junit.Assert.assertNull(result);
    }
}
