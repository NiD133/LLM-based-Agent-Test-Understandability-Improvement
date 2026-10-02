package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test37 extends JsonFormat_ESTest_scaffolding {

    /**
     * When {@link JsonFormat.Value#from(JsonFormat)} is given a {@code null}
     * annotation, it returns the shared empty Value, which carries no locale.
     */
    @Test(timeout = 4000)
    public void fromNullAnnotation_hasNoLocale() throws Throwable {
        JsonFormat.Value valueFromNullAnnotation = JsonFormat.Value.from((JsonFormat) null);

        assertFalse(valueFromNullAnnotation.hasLocale());
    }
}
