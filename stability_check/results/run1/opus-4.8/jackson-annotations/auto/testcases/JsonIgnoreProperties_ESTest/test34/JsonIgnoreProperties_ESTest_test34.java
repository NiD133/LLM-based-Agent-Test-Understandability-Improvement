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
public class JsonIgnoreProperties_ESTest_test34 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * A Value built from an empty list of ignored property names should keep
     * the default "allowGetters" setting, which is false.
     */
    @Test(timeout = 4000)
    public void forIgnoredProperties_withNoPropertyNames_leavesAllowGettersDisabled() throws Throwable {
        String[] noIgnoredProperties = new String[0];

        JsonIgnoreProperties.Value value =
                JsonIgnoreProperties.Value.forIgnoredProperties(noIgnoredProperties);

        assertFalse(value.getAllowGetters());
    }
}
