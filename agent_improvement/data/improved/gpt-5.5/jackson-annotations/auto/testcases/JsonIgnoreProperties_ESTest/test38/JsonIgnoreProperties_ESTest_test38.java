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
public class JsonIgnoreProperties_ESTest_test38 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        JsonIgnoreProperties.Value valueWithUnknownsAndGettersAllowed =
                JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[7];
        valuesToMerge[0] = valueWithUnknownsAndGettersAllowed;
        valuesToMerge[1] = valueWithUnknownsAndGettersAllowed;

        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);

        assertTrue(mergedValue.getIgnoreUnknown());
        assertFalse(mergedValue.getAllowSetters());
        assertFalse(mergedValue.getMerge());
        assertNotNull(mergedValue);
        assertTrue(mergedValue.getAllowGetters());
    }
}
