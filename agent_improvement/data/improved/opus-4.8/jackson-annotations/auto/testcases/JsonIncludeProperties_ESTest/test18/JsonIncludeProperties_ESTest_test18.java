package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test18 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Value.valueFor() should always report the annotation type it describes
     * ({@link JsonIncludeProperties}), regardless of the included-property names
     * or ordering flag passed to the constructor. That annotation type is an
     * interface, so the returned Class must not be reported as an enum.
     */
    @Test(timeout = 4000)
    public void valueForReturnsAnnotationTypeThatIsNotEnum() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        // Non-"true" text parses to Boolean.FALSE; exact flag value is irrelevant here.
        Boolean ordered = new Boolean("j:w.BxrN!bO}");
        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        Class<JsonIncludeProperties> annotationType = value.valueFor();

        assertFalse(annotationType.isEnum());
    }
}
