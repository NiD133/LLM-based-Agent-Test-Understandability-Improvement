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
public class StrSubstitutor_ESTest_test00 extends StrSubstitutor_ESTest_scaffolding {

    private static final String CUSTOM_PREFIX = "org.apache.commons.text.lookup.ConstantStringLookup";
    private static final String CUSTOM_SUFFIX = "org.apache.commons.text.lookup.ConstantStringLookup";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        HashMap<String, String> emptyVariables = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) emptyVariables,
                CUSTOM_PREFIX,
                CUSTOM_SUFFIX);
        char[] source = new char[1];

        String replacedEmptyRange = substitutor.replace(source, 0, 0);

        assertEquals("", replacedEmptyRange);
        assertNotNull(replacedEmptyRange);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
