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
public class JsonAutoDetect_ESTest_test39 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that a Value created via noOverrides() reports DEFAULT creator visibility,
     * meaning it defers to whatever the inherited/global default is rather than
     * overriding it with a specific visibility level.
     */
    @Test(timeout = 4000)
    public void test39_noOverridesValue_hasDefaultCreatorVisibility() throws Throwable {
        JsonAutoDetect.Value noOverridesValue = JsonAutoDetect.Value.noOverrides();

        JsonAutoDetect.Visibility creatorVisibility = noOverridesValue.getCreatorVisibility();

        assertEquals(
            "A 'no overrides' Value should have DEFAULT creator visibility",
            JsonAutoDetect.Visibility.DEFAULT,
            creatorVisibility
        );
    }
}
