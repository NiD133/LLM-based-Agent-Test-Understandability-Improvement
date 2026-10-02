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
public class JsonIncludeProperties_ESTest_test12 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        includedProperties.add("U 6?8\u007F|");

        JsonIncludeProperties.Value originalValue = new JsonIncludeProperties.Value(includedProperties, (Boolean) null);
        JsonIncludeProperties.Value mergedWithItself = originalValue.withOverrides(originalValue);

        assertNotSame(mergedWithItself, originalValue);
        assertTrue(mergedWithItself.equals((Object) originalValue));
    }
}
