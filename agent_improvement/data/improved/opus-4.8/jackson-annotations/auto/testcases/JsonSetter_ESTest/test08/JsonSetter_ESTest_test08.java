package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test08 extends JsonSetter_ESTest_scaffolding {

    /**
     * Builds a JsonSetter.Value with explicit (non-default) value- and content-null
     * handling, then verifies that both null-handling settings are stored and reported
     * back unchanged.
     */
    @Test(timeout = 4000)
    public void valueNullsAndContentNullsArePreservedWhenNonDefault() throws Throwable {
        JsonSetter.Value setterValue =
                JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY, Nulls.AS_EMPTY);

        // Since AS_EMPTY differs from the DEFAULT, the value-nulls setting is reported as-is.
        assertEquals(Nulls.AS_EMPTY, setterValue.nonDefaultValueNulls());

        assertEquals(Nulls.AS_EMPTY, setterValue.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, setterValue.getContentNulls());
    }
}
