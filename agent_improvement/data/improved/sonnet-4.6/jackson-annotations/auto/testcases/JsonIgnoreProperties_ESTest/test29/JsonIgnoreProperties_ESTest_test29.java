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
        // Value.from(null) returns the EMPTY singleton (no ignored fields, all flags false, merge=true)
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.from((JsonIgnoreProperties) null);

        // Construct a Value that ignores the empty-string property, with merge=true
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();
        ignoredProperties.add("");
        JsonIgnoreProperties.Value valueWithIgnoredProperty = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                /*ignoreUnknown=*/ false,
                /*allowGetters=*/ false,
                /*allowSetters=*/ false,
                /*merge=*/ true);

        // Applying a mergeable override onto the EMPTY base yields a new Value equal to the override
        JsonIgnoreProperties.Value mergedValue = emptyValue.withOverrides(valueWithIgnoredProperty);

        assertNotSame(mergedValue, valueWithIgnoredProperty);
        assertFalse(valueWithIgnoredProperty.getAllowSetters());
        assertFalse(emptyValue.equals((Object) valueWithIgnoredProperty));
        assertTrue(mergedValue.equals((Object) valueWithIgnoredProperty));
    }
}
