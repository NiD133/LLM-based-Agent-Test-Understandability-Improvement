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
public class JsonAutoDetect_ESTest_test01 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        JsonAutoDetect annotationWithNullVisibilities = mockAnnotationWithNullVisibilities();

        JsonAutoDetect.Value valueFromAnnotation = JsonAutoDetect.Value.from(annotationWithNullVisibilities);
        JsonAutoDetect.Visibility setterOverride = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        JsonAutoDetect.Value valueWithSetterOverride = valueFromAnnotation.withSetterVisibility(setterOverride);

        JsonAutoDetect.Value mergedValue = valueFromAnnotation.withOverrides(valueWithSetterOverride);

        assertNotSame(mergedValue, valueFromAnnotation);
        assertTrue(mergedValue.equals((Object) valueWithSetterOverride));
    }

    private JsonAutoDetect mockAnnotationWithNullVisibilities() {
        JsonAutoDetect annotation = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotation).setterVisibility();
        return annotation;
    }
}
