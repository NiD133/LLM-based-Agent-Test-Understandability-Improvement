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
public class JsonAutoDetect_ESTest_test00 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that withOverrides() selectively replaces only the accessor visibility
     * corresponding to the override's PropertyAccessor, leaving all others unchanged.
     *
     * Base value: all accessors set to NONE, except creator which is PUBLIC_ONLY.
     * Override value: only SCALAR_CONSTRUCTOR set to PUBLIC_ONLY; all others remain DEFAULT.
     * After merging, scalarConstructor should change to PUBLIC_ONLY while the rest stay as in base.
     */
    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Arrange: define the two visibility levels used in this test
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        // Base value: field=NONE, getter=NONE, isGetter=NONE, setter=NONE, creator=PUBLIC_ONLY, scalarConstructor=NONE
        JsonAutoDetect.Value baseValue = JsonAutoDetect.Value.construct(none, none, none, none, publicOnly, none);

        // Override value: only SCALAR_CONSTRUCTOR accessor set to PUBLIC_ONLY; all others left at DEFAULT
        PropertyAccessor scalarConstructorAccessor = PropertyAccessor.SCALAR_CONSTRUCTOR;
        JsonAutoDetect.Value scalarConstructorOverride = JsonAutoDetect.Value.construct(scalarConstructorAccessor, publicOnly);

        // Act: apply the SCALAR_CONSTRUCTOR override onto the base value
        JsonAutoDetect.Value mergedValue = baseValue.withOverrides(scalarConstructorOverride);

        // Assert: visibilities that were not targeted by the override stay as in the base value
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedValue.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, baseValue.getScalarConstructorVisibility());

        // Assert: the SCALAR_CONSTRUCTOR visibility in the merged result reflects the override
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedValue.getScalarConstructorVisibility());

        // Assert: the override value itself has DEFAULT isGetter visibility (no isGetter was set)
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getIsGetterVisibility());

        // Assert: the merged value differs from the base value (scalarConstructor changed)
        assertFalse(mergedValue.equals((Object) baseValue));

        // Assert: remaining visibilities from base are preserved in the merged value
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedValue.getFieldVisibility());
    }
}
