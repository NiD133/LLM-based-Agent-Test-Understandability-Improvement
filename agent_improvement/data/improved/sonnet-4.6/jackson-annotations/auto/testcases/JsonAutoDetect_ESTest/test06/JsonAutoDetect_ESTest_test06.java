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
public class JsonAutoDetect_ESTest_test06 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that applying withFieldVisibility(PUBLIC_ONLY) to a Value constructed from
     * an annotation with all-null visibilities produces a Value that is not equal to the original.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Build a mock @JsonAutoDetect annotation where every visibility accessor returns null
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        // Construct a Value from the mock annotation (all visibility fields will be null)
        JsonAutoDetect.Value originalValue = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // Produce a new Value that differs only in field visibility (set to PUBLIC_ONLY)
        JsonAutoDetect.Value valueWithPublicFieldVisibility =
                originalValue.withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY);

        // Changing the field visibility must yield a Value that is not equal to the original
        assertFalse(valueWithPublicFieldVisibility.equals((Object) originalValue));
    }
}
