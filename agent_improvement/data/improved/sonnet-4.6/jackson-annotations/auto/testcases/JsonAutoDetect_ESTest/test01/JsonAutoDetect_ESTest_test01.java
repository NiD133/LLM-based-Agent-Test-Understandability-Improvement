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

    /**
     * Verifies that withOverrides() produces a new Value instance when a setter visibility
     * override differs from the base, and that the result equals the override value.
     *
     * The base Value is constructed from a mock annotation returning null for all visibility
     * settings. The override specifies PROTECTED_AND_PUBLIC for setter visibility.
     * After applying the override, the result must be a distinct object that equals the override.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create a mock @JsonAutoDetect annotation where every visibility attribute returns null.
        // This exercises the null-tolerance of Value.from() and serves as a "blank" base.
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        JsonAutoDetect.Value baseValue = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // Build an override that changes only the setter visibility to PROTECTED_AND_PUBLIC.
        JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        JsonAutoDetect.Value overrideValue = baseValue.withSetterVisibility(setterVisibility);

        // Apply the override: the result should differ from the base (different setter visibility).
        JsonAutoDetect.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged result must be a new object (not the same reference as the base)...
        assertNotSame(mergedValue, baseValue);
        // ...and it must be equal in content to the override value.
        assertTrue(mergedValue.equals((Object) overrideValue));
    }
}
