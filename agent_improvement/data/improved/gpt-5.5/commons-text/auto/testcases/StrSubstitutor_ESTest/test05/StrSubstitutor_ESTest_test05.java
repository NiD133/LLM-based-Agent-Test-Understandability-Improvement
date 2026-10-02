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
public class StrSubstitutor_ESTest_test05 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final HashMap<String, String> values = new HashMap<String, String>();
        final String repeatedDelimiter = ".@lNt76>6[gcD#%{";
        final StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) values,
                repeatedDelimiter,
                repeatedDelimiter);

        final StringBuilder template = new StringBuilder();
        final StringBuilder escapedTemplate = template.append('$');
        substitutor.setPreserveEscapes(true);
        escapedTemplate.append(repeatedDelimiter);

        final boolean wasModified = substitutor.replaceIn(template);

        assertEquals("$.@lNt76>6[gcD#%{", template.toString());
        assertFalse(wasModified);
    }
}
