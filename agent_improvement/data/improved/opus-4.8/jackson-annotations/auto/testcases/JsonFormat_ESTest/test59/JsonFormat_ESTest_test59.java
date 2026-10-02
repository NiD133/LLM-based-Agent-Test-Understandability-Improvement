package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test59 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that overriding an empty {@link JsonFormat.Features} with a
     * {@code null} override leaves the original instance unchanged: per
     * {@code withOverrides}, a null override returns {@code this} verbatim.
     */
    @Test(timeout = 4000)
    public void withOverridesNullReturnsSameInstance() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        JsonFormat.Features result = emptyFeatures.withOverrides((JsonFormat.Features) null);

        assertSame(emptyFeatures, result);
    }
}
