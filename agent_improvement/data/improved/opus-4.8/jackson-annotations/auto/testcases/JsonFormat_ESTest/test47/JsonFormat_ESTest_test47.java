package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test47 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonFormat.Features#equals(Object)} returns {@code false}
     * when compared against {@code null}.
     */
    @Test(timeout = 4000)
    public void emptyFeatures_equals_null_returnsFalse() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        boolean equalsNull = emptyFeatures.equals((Object) null);

        assertFalse(equalsNull);
    }
}
