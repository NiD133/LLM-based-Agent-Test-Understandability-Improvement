package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test32 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Builds a Value whose every accessor visibility is set to NONE and verifies
     * that the is-getter visibility is reported back as NONE.
     */
    @Test(timeout = 4000)
    public void isGetterVisibilityReflectsConstructedNoneValue() throws Throwable {
        JsonAutoDetect.Visibility none = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNoneValue = JsonAutoDetect.Value.construct(
                none, none, none, none, none, none);

        // valueFor() simply identifies the annotation type backing this Value.
        allNoneValue.valueFor();

        assertEquals(JsonAutoDetect.Visibility.NONE, allNoneValue.getIsGetterVisibility());
    }
}
