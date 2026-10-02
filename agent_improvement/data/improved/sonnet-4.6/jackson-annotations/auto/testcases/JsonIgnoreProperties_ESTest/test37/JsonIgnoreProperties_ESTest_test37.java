package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that {@link JsonIgnoreProperties.Value#from} correctly constructs a Value
 * from a {@link JsonIgnoreProperties} annotation with all flags set to false and
 * a null property list, and that the resulting Value does not equal a plain Object.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test37 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        // Build a mock annotation with all flags false and no property names
        JsonIgnoreProperties annotationWithAllFlagsDisabled = mock(JsonIgnoreProperties.class, CALLS_REAL_METHODS);
        doReturn(false).when(annotationWithAllFlagsDisabled).allowGetters();
        doReturn(false).when(annotationWithAllFlagsDisabled).allowSetters();
        doReturn(false).when(annotationWithAllFlagsDisabled).ignoreUnknown();
        doReturn((String[]) null).when(annotationWithAllFlagsDisabled).value();

        // Convert the annotation to its Value representation
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.from(annotationWithAllFlagsDisabled);

        // A Value instance must not equal an unrelated Object
        Object unrelatedObject = new Object();
        boolean equalsUnrelated = value.equals(unrelatedObject);
        assertFalse(equalsUnrelated);

        // The resulting Value should reflect all-false settings from the annotation
        assertFalse("merge should be false when built from annotation (no merge support in annotation)",
                value.getMerge());
        assertFalse("ignoreUnknown should be false as set on mock annotation",
                value.getIgnoreUnknown());
        assertFalse("allowGetters should be false as set on mock annotation",
                value.getAllowGetters());
        assertFalse("allowSetters should be false as set on mock annotation",
                value.getAllowSetters());
    }
}
