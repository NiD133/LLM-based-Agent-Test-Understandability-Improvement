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
public class JsonIgnoreProperties_ESTest_test03 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        JsonIgnoreProperties.Value emptyBaseValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);
        LinkedHashSet<String> ignoredPropertyNames = new LinkedHashSet<String>();
        JsonIgnoreProperties.Value mergingOverride = JsonIgnoreProperties.Value.construct(ignoredPropertyNames, false, true, true, true);

        JsonIgnoreProperties.Value mergedValue = emptyBaseValue.withOverrides(mergingOverride);

        assertTrue(mergedValue.getAllowGetters());
        assertNotSame(mergedValue, mergingOverride);
        assertTrue(mergedValue.equals((Object) mergingOverride));
        assertFalse(mergedValue.getIgnoreUnknown());
        assertTrue(mergingOverride.getAllowSetters());
    }
}
