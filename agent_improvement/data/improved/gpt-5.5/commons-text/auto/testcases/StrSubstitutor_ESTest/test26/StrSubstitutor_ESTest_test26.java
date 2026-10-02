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
public class StrSubstitutor_ESTest_test26 extends StrSubstitutor_ESTest_scaffolding {

    private static final String CUSTOM_VARIABLE_MARKER = "org.apache.commons.text.lookup.ConstantStringLookup";

    @Test(timeout = 4000)
    public void test26() throws Throwable {
        HashMap<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) emptyValues,
                CUSTOM_VARIABLE_MARKER,
                CUSTOM_VARIABLE_MARKER);

        String replacement = substitutor.replace((String) null);

        assertNull(replacement);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
