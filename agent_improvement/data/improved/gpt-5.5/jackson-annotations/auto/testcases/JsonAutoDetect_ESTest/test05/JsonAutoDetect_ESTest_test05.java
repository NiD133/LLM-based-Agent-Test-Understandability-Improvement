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
public class JsonAutoDetect_ESTest_test05 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility updatedIsGetterVisibility =
                JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        JsonAutoDetect.Value visibilityWithProtectedIsGetters =
                defaultVisibility.withIsGetterVisibility(updatedIsGetterVisibility);

        assertEquals(JsonAutoDetect.Visibility.ANY,
                visibilityWithProtectedIsGetters.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithProtectedIsGetters.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE,
                visibilityWithProtectedIsGetters.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithProtectedIsGetters.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithProtectedIsGetters.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC,
                visibilityWithProtectedIsGetters.getIsGetterVisibility());
    }
}
