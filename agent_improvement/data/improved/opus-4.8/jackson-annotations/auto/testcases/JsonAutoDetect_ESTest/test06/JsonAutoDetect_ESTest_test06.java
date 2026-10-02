package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test06 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that changing the field visibility on a {@link JsonAutoDetect.Value}
     * produces a new Value that is no longer equal to the original.
     */
    @Test(timeout = 4000)
    public void withFieldVisibility_changesEquality() throws Throwable {
        // Build an annotation whose every visibility accessor returns null,
        // so the resulting Value has null for all six visibility settings.
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        JsonAutoDetect.Value originalValue = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // Override only the field visibility, leaving the other (null) settings intact.
        JsonAutoDetect.Value valueWithPublicFields =
                originalValue.withFieldVisibility(JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertFalse(valueWithPublicFields.equals(originalValue));
    }
}
