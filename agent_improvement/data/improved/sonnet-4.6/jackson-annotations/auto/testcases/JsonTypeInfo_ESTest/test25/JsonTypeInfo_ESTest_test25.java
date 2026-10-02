package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test25 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@code withInclusionType} returns the same {@code Value} instance
     * when the requested inclusion type is identical to the one already set.
     * {@code Value.EMPTY} is initialised with {@code As.NOTHING}, so calling
     * {@code withInclusionType(As.NOTHING)} must be a no-op and return {@code this}.
     */
    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // EMPTY is pre-configured with inclusionType = As.NOTHING
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // Requesting the same inclusion type that is already set
        JsonTypeInfo.Value result = emptyValue.withInclusionType(JsonTypeInfo.As.NOTHING);

        // No new object should be created; the original instance must be returned unchanged
        assertSame(result, emptyValue);
    }
}
