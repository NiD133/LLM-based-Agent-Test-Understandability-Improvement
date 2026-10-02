package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test18 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Value.construct(PropertyAccessor.ALL, visibility) applies the given
     * visibility to every accessor type. This verifies that the creator
     * accessor in particular receives the PUBLIC_ONLY visibility.
     */
    @Test(timeout = 4000)
    public void construct_withAllAccessors_setsCreatorVisibility() throws Throwable {
        JsonAutoDetect.Value value =
                JsonAutoDetect.Value.construct(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, value.getCreatorVisibility());
    }
}
