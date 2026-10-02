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
public class JsonAutoDetect_ESTest_test04 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility scalarConstructorVisibility = JsonAutoDetect.Visibility.ANY;

        JsonAutoDetect.Value updatedVisibility =
                defaultVisibility.withScalarConstructorVisibility(scalarConstructorVisibility);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY, updatedVisibility.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY, updatedVisibility.getScalarConstructorVisibility());
    }
}
