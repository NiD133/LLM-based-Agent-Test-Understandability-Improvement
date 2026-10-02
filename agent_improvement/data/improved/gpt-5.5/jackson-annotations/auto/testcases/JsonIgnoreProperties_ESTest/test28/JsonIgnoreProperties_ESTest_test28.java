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
public class JsonIgnoreProperties_ESTest_test28 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[6];

        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> emptyIgnoredProperties = emptyValue.getIgnored();
        JsonIgnoreProperties.Value allFlagsEnabled = new JsonIgnoreProperties.Value(
                emptyIgnoredProperties, true, true, true, true);
        valuesToMerge[1] = allFlagsEnabled;

        String[] ignoredPropertyNames = new String[8];
        JsonIgnoreProperties.Value ignoredPropertiesOnly =
                JsonIgnoreProperties.Value.forIgnoredProperties(ignoredPropertyNames);
        assertFalse(ignoredPropertiesOnly.getIgnoreUnknown());
        assertFalse(ignoredPropertiesOnly.getAllowGetters());
        assertFalse(ignoredPropertiesOnly.getAllowSetters());
        valuesToMerge[3] = ignoredPropertiesOnly;

        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);
        assertTrue(mergedValue.getAllowSetters());
        assertNotNull(mergedValue);
        assertNotSame(mergedValue, allFlagsEnabled);
        assertFalse(mergedValue.equals((Object) allFlagsEnabled));
        assertTrue(mergedValue.getAllowGetters());
        assertTrue(mergedValue.getIgnoreUnknown());
    }
}
