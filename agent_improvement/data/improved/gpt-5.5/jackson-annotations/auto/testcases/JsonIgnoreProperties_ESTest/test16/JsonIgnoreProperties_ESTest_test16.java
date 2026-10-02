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
public class JsonIgnoreProperties_ESTest_test16 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        boolean ignoreUnknown = false;
        boolean allowGetters = false;
        boolean allowSetters = false;
        boolean merge = true;
        JsonIgnoreProperties.Value mergeEnabledValue = JsonIgnoreProperties.Value.construct(
                ignoredProperties, ignoreUnknown, allowGetters, allowSetters, merge);

        JsonIgnoreProperties.Value mergeDisabledValue = mergeEnabledValue.withoutMerge();
        JsonIgnoreProperties.Value mergeDisabledAgain = mergeDisabledValue.withoutMerge();

        assertFalse(mergeEnabledValue.getIgnoreUnknown());
        assertFalse(mergeDisabledAgain.getAllowGetters());
        assertFalse(mergeDisabledAgain.getMerge());
        assertFalse(mergeDisabledAgain.getAllowSetters());
        assertFalse(mergeDisabledAgain.getIgnoreUnknown());
    }
}
