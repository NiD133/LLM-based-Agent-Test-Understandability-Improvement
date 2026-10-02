package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test34 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that calling withGetterVisibility() with the same visibility value
     * already in use produces a Value equal to the original.
     *
     * When all visibilities are NONE, the field visibility is also NONE.
     * Applying withGetterVisibility(NONE) to a value whose getter visibility is
     * already NONE should return an equal (though possibly distinct) Value.
     */
    @Test(timeout = 4000)
    public void test34() throws Throwable {
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;

        // Build a Value with every accessor restricted to NONE visibility
        JsonAutoDetect.Value allNoneValue = JsonAutoDetect.Value.construct(
                noneVisibility, noneVisibility, noneVisibility,
                noneVisibility, noneVisibility, noneVisibility);
        assertNotNull(allNoneValue);

        // Retrieve the field visibility (NONE) and apply it as the getter visibility
        JsonAutoDetect.Visibility fieldVisibility = allNoneValue.getFieldVisibility();
        JsonAutoDetect.Value valueWithRedundantGetterUpdate = allNoneValue.withGetterVisibility(fieldVisibility);

        // Since the getter was already NONE, the updated value should equal the original
        assertTrue(valueWithRedundantGetterUpdate.equals((Object) allNoneValue));
    }
}
