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
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        LinkedHashSet<String> overrideIncludedNames = new LinkedHashSet<String>();
        overrideIncludedNames.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue = new JsonIncludeProperties.Value(overrideIncludedNames, (Boolean) null);

        OptBoolean defaultOrdering = OptBoolean.DEFAULT;
        String[] annotationIncludedNames = new String[1];
        JsonIncludeProperties annotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(defaultOrdering).when(annotation).order();
        doReturn(annotationIncludedNames).when(annotation).value();

        JsonIncludeProperties.Value annotationValue = JsonIncludeProperties.Value.from(annotation);
        JsonIncludeProperties.Value mergedValue = annotationValue.withOverrides(overrideValue);

        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) annotationValue));
        assertNotSame(mergedValue, annotationValue);
    }
}
