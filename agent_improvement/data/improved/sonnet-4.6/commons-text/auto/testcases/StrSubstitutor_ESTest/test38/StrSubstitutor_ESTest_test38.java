package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test38 extends StrSubstitutor_ESTest_scaffolding {

    // A plain string with no ${...} variable placeholders
    private static final String NO_PLACEHOLDER_TEXT = "org.apache.cmmons.;ext.lookup.onstantStringLookup";

    @Test(timeout = 4000)
    public void test_replaceSystemProperties_returnsOriginalString_whenNoPlaceholdersPresent() throws Throwable {
        String result = StrSubstitutor.replaceSystemProperties(NO_PLACEHOLDER_TEXT);
        // No substitution should occur because the input contains no ${...} references
        assertEquals(NO_PLACEHOLDER_TEXT, result);
    }
}
