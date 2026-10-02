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
public class JsonIgnoreProperties_ESTest_test08 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value mergeEnabledValue = JsonIgnoreProperties.Value.construct(
                ignoredProperties, false, false, false, true);

        JsonIgnoreProperties.Value mergeDisabledValue = mergeEnabledValue.withoutMerge();

        LinkedHashSet<Object> membershipProbe = new LinkedHashSet<Object>();
        membershipProbe.contains(mergeDisabledValue);

        assertFalse(mergeEnabledValue.getIgnoreUnknown());
        assertFalse(mergeDisabledValue.getIgnoreUnknown());
        assertFalse(mergeDisabledValue.getMerge());
        assertFalse(mergeDisabledValue.getAllowSetters());
        assertFalse(mergeDisabledValue.getAllowGetters());
        assertFalse(mergeEnabledValue.equals((Object) mergeDisabledValue));
    }
}
