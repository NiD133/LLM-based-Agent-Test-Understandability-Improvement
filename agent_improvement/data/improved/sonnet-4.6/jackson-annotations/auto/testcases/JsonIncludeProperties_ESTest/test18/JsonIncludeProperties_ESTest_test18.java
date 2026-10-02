package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test18 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIncludeProperties.Value#valueFor()} returns the
     * {@link JsonIncludeProperties} annotation class, and that this class is not an enum.
     */
    @Test(timeout = 4000)
    public void test_valueFor_returnsJsonIncludePropertiesAnnotationClass() throws Throwable {
        // Create a Value with an empty included-properties set and ordered=false
        LinkedHashSet<String> emptyIncludedProperties = new LinkedHashSet<String>();
        Boolean ordered = new Boolean("j:w.BxrN!bO}");
        JsonIncludeProperties.Value value = new JsonIncludeProperties.Value(emptyIncludedProperties, ordered);

        // valueFor() must return the JsonIncludeProperties annotation class
        Class<JsonIncludeProperties> annotationClass = value.valueFor();

        // JsonIncludeProperties is an annotation, not an enum
        assertFalse(annotationClass.isEnum());
    }
}
