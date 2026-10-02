package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test73 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that the default {@link JsonFormat.Value} (created via its
     * no-argument constructor) renders its full state through {@code toString()}:
     * an empty pattern, the {@code ANY} shape, no leniency/locale/timezone,
     * an empty feature set, and the default radix of -1.
     */
    @Test(timeout = 4000)
    public void defaultValueToStringShowsAllDefaults() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        String description = defaultValue.toString();

        assertEquals(
                "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=EMPTY,radix=-1)",
                description);
    }
}
