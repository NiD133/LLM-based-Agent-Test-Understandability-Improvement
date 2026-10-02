package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.fail;
import java.time.temporal.TemporalQuery;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test02 extends Quarter_ESTest_scaffolding {

    /**
     * Verifies that querying a Quarter with a null TemporalQuery throws a
     * NullPointerException (with no detail message).
     */
    @Test(timeout = 4000)
    public void query_withNullQuery_throwsNullPointerException() throws Throwable {
        Quarter quarter = Quarter.of(4);

        try {
            quarter.query((TemporalQuery<Object>) null);
            fail("Expected a NullPointerException when querying with a null query");
        } catch (NullPointerException expected) {
            // The query method rejects a null argument; the exception carries no message.
        }
    }
}
