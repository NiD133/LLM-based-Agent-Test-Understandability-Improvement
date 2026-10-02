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
public class JsonIgnoreProperties_ESTest_test30 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        JsonIgnoreProperties.Value baseValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
        assertTrue(baseValue.getIgnoreUnknown());
        assertTrue(baseValue.getAllowGetters());
        assertFalse(baseValue.getAllowSetters());
        assertFalse(baseValue.getMerge());

        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[7];
        valuesToMerge[0] = baseValue;

        JsonIgnoreProperties.Value overridingValue = JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, true, true);
        valuesToMerge[6] = overridingValue;

        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);
        assertTrue(mergedValue.equals((Object) overridingValue));
        assertNotNull(mergedValue);
        assertTrue(mergedValue.getAllowSetters());
        assertTrue(mergedValue.getMerge());
    }
}
