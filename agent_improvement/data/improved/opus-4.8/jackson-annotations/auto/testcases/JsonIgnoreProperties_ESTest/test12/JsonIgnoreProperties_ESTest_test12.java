package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test12 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * When a Value is built from a null annotation, Value.from returns the
     * shared EMPTY instance, which has no properties to ignore. Therefore the
     * set of properties ignored for deserialization should be empty.
     */
    @Test(timeout = 4000)
    public void fromNullAnnotation_hasNoPropertiesIgnoredForDeserialization() throws Throwable {
        JsonIgnoreProperties.Value valueFromNullAnnotation =
                JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        Set<String> ignoredForDeserialization =
                valueFromNullAnnotation.findIgnoredForDeserialization();

        assertEquals(0, ignoredForDeserialization.size());
    }
}
