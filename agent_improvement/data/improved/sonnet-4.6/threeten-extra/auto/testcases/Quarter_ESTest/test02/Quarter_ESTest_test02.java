package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.time.temporal.TemporalQuery;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Quarter_ESTest_test02 extends Quarter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_query_withNullQuery_throwsNullPointerException() throws Throwable {
        Quarter q4 = Quarter.of(4);

        try {
            q4.query((TemporalQuery<Object>) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // query(null) must throw NullPointerException per TemporalAccessor contract
        }
    }
}
