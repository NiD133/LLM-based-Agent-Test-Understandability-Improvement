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
public class StrSubstitutor_ESTest_test16 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer, offset, length) returns false when the
     * target StringBuffer is null, and that the default escape character '$' is
     * preserved regardless of custom prefix/suffix settings.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        // Use custom strings as variable prefix and suffix (no actual variable lookups)
        String customPrefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String customSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";
        Map<String, String> emptyVariables = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyVariables, customPrefix, customSuffix);

        // Attempting to replace in a null StringBuffer should be a no-op and return false
        boolean replacementPerformed = substitutor.replaceIn((StringBuffer) null, 31, 671);

        assertEquals('$', substitutor.getEscapeChar());
        assertFalse(replacementPerformed);
    }
}
