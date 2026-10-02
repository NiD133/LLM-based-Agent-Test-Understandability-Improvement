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

    /**
     * Verifies that a JsonAutoDetect.Value instance is not equal to a Visibility enum constant,
     * even when the Value was created from an annotation whose visibility methods all return null.
     */
    @Test(timeout = 4000)
    public void test_ValueDoesNotEqualVisibilityEnumConstant() throws Throwable {
        // Create a mock annotation where all visibility accessors return null
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        // Build a Value from the mocked annotation (all stored visibilities will be null)
        JsonAutoDetect.Value valueFromNullVisibilities = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // Comparing a Value to a Visibility enum constant must return false (different types)
        JsonAutoDetect.Visibility protectedAndPublicVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        boolean isEqual = valueFromNullVisibilities.equals(protectedAndPublicVisibility);
        assertFalse(isEqual);
    }
}
