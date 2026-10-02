package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test05 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that a {@link JsonIncludeProperties.Value} is never considered
     * equal to {@code null}.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToNull() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedProperties, Boolean.TRUE);

        boolean equalsNull = value.equals((Object) null);

        assertFalse(equalsNull);
    }
}
