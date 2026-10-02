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
public class StrSubstitutor_ESTest_test12 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        final String prefixAndSuffix = "org.apache.cmmons.;ext.lookup.onstantStringLookup";
        HashMap<String, String> valuesByName = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor((Map<String, String>) valuesByName, prefixAndSuffix, prefixAndSuffix);
        StringBuilder template = new StringBuilder((CharSequence) prefixAndSuffix);

        substitutor.setValueDelimiter('$');
        StringBuilder templateAfterDelimiterAppend = template.appendCodePoint('$');
        template.append((CharSequence) templateAfterDelimiterAppend);

        boolean wasModified = substitutor.replaceIn(template);

        assertEquals("$", template.toString());
        assertTrue(wasModified);
    }
}
