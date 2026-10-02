package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test26 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#valueFor()} reports the annotation type
     * it carries information for, namely the {@link JsonSetter} annotation interface.
     *
     * The expected modifiers value (9729) is the {@code java.lang.reflect.Modifier}
     * bitmask that the JVM assigns to the {@code JsonSetter} annotation type:
     * public (0x0001) + final (0x0010) + interface (0x0200) + abstract (0x0400)
     * + annotation (0x2000) = 9729.
     */
    @Test(timeout = 4000)
    public void valueFor_returnsJsonSetterAnnotationType() throws Throwable {
        JsonSetter.Value contentNullsValue = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);

        Class<JsonSetter> annotationType = contentNullsValue.valueFor();

        int expectedModifiers = 9729;
        assertEquals(expectedModifiers, annotationType.getModifiers());
    }
}
