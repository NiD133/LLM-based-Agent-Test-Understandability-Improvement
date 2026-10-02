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
public class JsonIgnoreProperties_ESTest_test41 extends JsonIgnoreProperties_ESTest_scaffolding {

    private static final String EMPTY_IGNORALS_DESCRIPTION =
            "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=false,merge=true)";

    @Test(timeout = 4000)
    public void test41() throws Throwable {
        LinkedHashSet<String> ignoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                ignoredProperties,
                false,
                false,
                false,
                true);

        String description = value.toString();

        assertEquals(EMPTY_IGNORALS_DESCRIPTION, description);
    }
}
