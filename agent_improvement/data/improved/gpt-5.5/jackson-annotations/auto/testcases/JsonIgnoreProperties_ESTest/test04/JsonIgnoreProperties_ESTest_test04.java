package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test04 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonIgnoreProperties annotation = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotation).allowGetters();
        doReturn(false).when(annotation).allowSetters();
        doReturn(false).when(annotation).ignoreUnknown();
        doReturn((String[]) null).when(annotation).value();

        JsonIgnoreProperties.Value valueFromAnnotation = JsonIgnoreProperties.Value.from(annotation);
        Set<String> ignoredForSerialization = valueFromAnnotation.findIgnoredForSerialization();
        JsonIgnoreProperties.Value valueWithSameIgnoredProperties = valueFromAnnotation.withIgnored(ignoredForSerialization);

        boolean valuesAreEqual = valueFromAnnotation.equals(valueWithSameIgnoredProperties);
        assertTrue(valuesAreEqual);
        assertFalse(valueWithSameIgnoredProperties.getMerge());
    }
}
