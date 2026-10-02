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
public class JsonIgnoreProperties_ESTest_test00 extends JsonIgnoreProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonIgnoreProperties.Value[] valuesToMerge = new JsonIgnoreProperties.Value[6];

        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        Set<String> ignoredPropertiesFromEmptyValue = emptyValue.getIgnored();
        JsonIgnoreProperties.Value allFlagsEnabledValue = new JsonIgnoreProperties.Value(
                ignoredPropertiesFromEmptyValue, true, true, true, true);

        String[] ignoredPropertyNames = new String[8];
        JsonIgnoreProperties.Value valueFromIgnoredPropertyNames =
                JsonIgnoreProperties.Value.forIgnoredProperties(ignoredPropertyNames);
        assertFalse(valueFromIgnoredPropertyNames.getIgnoreUnknown());
        assertTrue(valueFromIgnoredPropertyNames.getMerge());
        assertFalse(valueFromIgnoredPropertyNames.getAllowSetters());

        valuesToMerge[3] = valueFromIgnoredPropertyNames;
        valuesToMerge[4] = allFlagsEnabledValue;
        JsonIgnoreProperties.Value mergedValue = JsonIgnoreProperties.Value.mergeAll(valuesToMerge);

        assertTrue(mergedValue.getAllowGetters());
        assertTrue(mergedValue.getAllowSetters());
        assertTrue(mergedValue.getIgnoreUnknown());
        assertNotNull(mergedValue);
        assertFalse(mergedValue.equals((Object) allFlagsEnabledValue));
        assertNotSame(mergedValue, allFlagsEnabledValue);
    }
}
