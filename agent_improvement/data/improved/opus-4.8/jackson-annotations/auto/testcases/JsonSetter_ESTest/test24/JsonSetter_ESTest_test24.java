package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test24 extends JsonSetter_ESTest_scaffolding {

    /**
     * When a JsonSetter annotation reports {@code null} for both nulls() and
     * contentNulls(), Value.from must normalize those nulls into Nulls.DEFAULT.
     * This verifies getContentNulls() returns Nulls.DEFAULT rather than null.
     */
    @Test(timeout = 4000)
    public void contentNullsDefaultsToDefaultWhenAnnotationReportsNull() throws Throwable {
        JsonSetter annotationWithNullSettings = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(annotationWithNullSettings).nulls();
        doReturn((Nulls) null).when(annotationWithNullSettings).contentNulls();

        JsonSetter.Value value = JsonSetter.Value.from(annotationWithNullSettings);

        assertEquals(Nulls.DEFAULT, value.getContentNulls());
    }
}
