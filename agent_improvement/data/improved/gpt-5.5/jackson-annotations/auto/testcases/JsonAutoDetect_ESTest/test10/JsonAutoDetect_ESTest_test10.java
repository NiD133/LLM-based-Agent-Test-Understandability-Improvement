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
public class JsonAutoDetect_ESTest_test10 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        JsonAutoDetect autoDetectAnnotation = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        JsonAutoDetect.Visibility nullVisibility = (JsonAutoDetect.Visibility) null;

        doReturn(nullVisibility).when(autoDetectAnnotation).creatorVisibility();
        doReturn(nullVisibility).when(autoDetectAnnotation).fieldVisibility();
        doReturn(nullVisibility).when(autoDetectAnnotation).getterVisibility();
        doReturn(nullVisibility).when(autoDetectAnnotation).isGetterVisibility();
        doReturn(nullVisibility).when(autoDetectAnnotation).scalarConstructorVisibility();
        doReturn(nullVisibility).when(autoDetectAnnotation).setterVisibility();

        JsonAutoDetect.Value valueFromAnnotation = JsonAutoDetect.Value.from(autoDetectAnnotation);
        JsonAutoDetect.Visibility otherType = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        boolean equalsVisibilityEnum = valueFromAnnotation.equals(otherType);

        assertFalse(equalsVisibilityEnum);
    }
}
