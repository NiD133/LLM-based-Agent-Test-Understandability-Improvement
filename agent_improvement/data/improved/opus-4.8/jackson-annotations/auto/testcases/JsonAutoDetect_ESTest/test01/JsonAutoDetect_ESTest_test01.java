package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test01 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonAutoDetect.Value#withOverrides} produces a new,
     * distinct instance that is nonetheless equal to the override source when the
     * overrides only differ from the base in the setter visibility.
     *
     * Setup: a mock annotation whose every visibility accessor returns null, so the
     * base Value carries null visibilities for all accessors. Applying a setter
     * visibility override then yields a Value that equals the override but is a
     * different object from the base.
     */
    @Test(timeout = 4000)
    public void overridesYieldNewInstanceEqualToOverrideSource() throws Throwable {
        // Build a JsonAutoDetect annotation whose visibility accessors all report null.
        JsonAutoDetect annotationWithNullVisibilities = mock(JsonAutoDetect.class, CALLS_REAL_METHODS);
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).creatorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).fieldVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).getterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).isGetterVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).scalarConstructorVisibility();
        doReturn((JsonAutoDetect.Visibility) null).when(annotationWithNullVisibilities).setterVisibility();

        // Base Value derived from the annotation: every accessor visibility is null.
        JsonAutoDetect.Value baseValue = JsonAutoDetect.Value.from(annotationWithNullVisibilities);

        // Override that sets only the setter visibility to PROTECTED_AND_PUBLIC.
        JsonAutoDetect.Visibility setterVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        JsonAutoDetect.Value setterOverride = baseValue.withSetterVisibility(setterVisibility);

        // Merge the override onto the base.
        JsonAutoDetect.Value mergedValue = baseValue.withOverrides(setterOverride);

        // The merge returns a brand-new instance, not the original base.
        assertNotSame(mergedValue, baseValue);
        // ...but it is value-equal to the override that was applied.
        assertTrue(mergedValue.equals((Object) setterOverride));
    }
}
