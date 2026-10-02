package com.fasterxml.jackson.annotation;

import java.lang.reflect.Modifier;
import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test26 extends JsonSetter_ESTest_scaffolding {

    // JsonSetter is a public @interface: PUBLIC | INTERFACE | ABSTRACT | ACC_ANNOTATION (JVM flag 0x2000)
    private static final int ANNOTATION_TYPE_MODIFIERS =
            Modifier.PUBLIC | Modifier.INTERFACE | Modifier.ABSTRACT | 0x2000;

    @Test(timeout = 4000)
    public void test_valueForReturnsJsonSetterAnnotationType() throws Throwable {
        JsonSetter.Value value = JsonSetter.Value.forContentNulls(Nulls.DEFAULT);
        Class<JsonSetter> annotationType = value.valueFor();
        assertEquals(ANNOTATION_TYPE_MODIFIERS, annotationType.getModifiers());
    }
}
