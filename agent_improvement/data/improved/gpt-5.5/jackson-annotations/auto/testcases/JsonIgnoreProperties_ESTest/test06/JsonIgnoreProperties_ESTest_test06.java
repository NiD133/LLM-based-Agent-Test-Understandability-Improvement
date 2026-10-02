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
public class JsonIgnoreProperties_ESTest_test06 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        JsonIgnoreProperties.Value valueWithNullIgnoredProperties =
                JsonIgnoreProperties.Value.construct((Set<String>) null, true, true, false, false);

        boolean equalsNull = valueWithNullIgnoredProperties.equals((Object) null);

        assertFalse(valueWithNullIgnoredProperties.getMerge());
        assertFalse(valueWithNullIgnoredProperties.getAllowSetters());
        assertTrue(valueWithNullIgnoredProperties.getIgnoreUnknown());
        assertFalse(equalsNull);
        assertTrue(valueWithNullIgnoredProperties.getAllowGetters());
    }
}
