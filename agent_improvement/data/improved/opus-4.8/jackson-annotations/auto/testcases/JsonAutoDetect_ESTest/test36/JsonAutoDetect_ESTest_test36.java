package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test36 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that:
     *  1. withCreatorVisibility(...) only changes the creator visibility and leaves
     *     every other accessor at the DEFAULT value's settings.
     *  2. Applying withOverrides(DEFAULT) on that customized value collapses the
     *     creator visibility back to DEFAULT's PUBLIC_ONLY, which makes the result
     *     the very same shared DEFAULT instance.
     */
    @Test(timeout = 4000)
    public void creatorOverrideThenMergeWithDefaultReturnsDefault() throws Throwable {
        Value defaultValue = Value.DEFAULT;

        // Start from DEFAULT and override only the creator visibility.
        Value customCreatorValue = defaultValue.withCreatorVisibility(Visibility.NON_PRIVATE);
        assertNotNull(customCreatorValue);

        // Only the creator visibility changed; all other accessors keep DEFAULT's settings.
        assertEquals(Visibility.NON_PRIVATE, customCreatorValue.getCreatorVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, customCreatorValue.getFieldVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, customCreatorValue.getGetterVisibility());
        assertEquals(Visibility.PUBLIC_ONLY, customCreatorValue.getIsGetterVisibility());
        assertEquals(Visibility.ANY, customCreatorValue.getSetterVisibility());
        assertEquals(Visibility.NON_PRIVATE, customCreatorValue.getScalarConstructorVisibility());

        // Overriding with DEFAULT restores DEFAULT's creator visibility, so the merge
        // yields the shared DEFAULT instance itself.
        Value mergedValue = customCreatorValue.withOverrides(defaultValue);
        assertSame(defaultValue, mergedValue);
    }
}
