package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test38 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * A string that contains no "${...}" variable placeholders should be
     * returned by replaceSystemProperties() exactly as it was supplied.
     */
    @Test(timeout = 4000)
    public void replaceSystemPropertiesLeavesStringWithoutPlaceholdersUnchanged() throws Throwable {
        String inputWithoutPlaceholders = "org.apache.cmmons.;ext.lookup.onstantStringLookup";

        String result = StrSubstitutor.replaceSystemProperties(inputWithoutPlaceholders);

        assertEquals(inputWithoutPlaceholders, result);
    }
}
