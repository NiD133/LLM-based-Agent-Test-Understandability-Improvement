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
public class JsonAutoDetect_ESTest_test34 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Re-applying a Value's existing getter visibility via withGetterVisibility()
     * should leave the configuration unchanged, so the result equals the original.
     */
    @Test(timeout = 4000)
    public void withGetterVisibility_usingExistingVisibility_returnsEqualValue() throws Throwable {
        // Build a Value whose every accessor uses NONE visibility.
        Value allNone = Value.construct(
                Visibility.NONE, Visibility.NONE, Visibility.NONE,
                Visibility.NONE, Visibility.NONE, Visibility.NONE);
        assertNotNull(allNone);

        // The current field visibility is therefore NONE...
        Visibility currentFieldVisibility = allNone.getFieldVisibility();

        // ...and reusing it as the getter visibility (already NONE) is a no-op.
        Value sameValue = allNone.withGetterVisibility(currentFieldVisibility);

        assertEquals(allNone, sameValue);
    }
}
