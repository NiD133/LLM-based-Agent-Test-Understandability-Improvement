package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test17 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that merging Value.DEFAULT with itself preserves PUBLIC_ONLY getter visibility.
     * When the same value is used as both base and overrides, the merge should be idempotent.
     */
    @Test(timeout = 4000)
    public void test_mergeDefaultWithItself_preservesPublicOnlyGetterVisibility() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;

        JsonAutoDetect.Value mergedVisibility = JsonAutoDetect.Value.merge(defaultVisibility, defaultVisibility);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedVisibility.getGetterVisibility());
    }
}
