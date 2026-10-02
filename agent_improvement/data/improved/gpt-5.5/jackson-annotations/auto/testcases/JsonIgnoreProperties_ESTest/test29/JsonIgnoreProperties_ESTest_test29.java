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
public class JsonIgnoreProperties_ESTest_test29 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test29() throws Throwable {
        JsonIgnoreProperties.Value defaultsFromNullAnnotation = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();
        ignoredProperties.add("");

        JsonIgnoreProperties.Value mergingOverride = JsonIgnoreProperties.Value.construct(
                ignoredProperties, false, false, false, true);
        JsonIgnoreProperties.Value mergedValue = defaultsFromNullAnnotation.withOverrides(mergingOverride);

        assertNotSame(mergedValue, mergingOverride);
        assertFalse(mergingOverride.getAllowSetters());
        assertFalse(defaultsFromNullAnnotation.equals((Object) mergingOverride));
        assertTrue(mergedValue.equals((Object) mergingOverride));
    }
}
