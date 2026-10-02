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
public class JsonAutoDetect_ESTest_test16 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_merge_bothNull_returnsNull() throws Throwable {
        // When base is null, merge returns overrides directly; so merge(null, null) == null
        JsonAutoDetect.Value mergedValue = JsonAutoDetect.Value.merge(null, null);
        assertNull(mergedValue);
    }
}
