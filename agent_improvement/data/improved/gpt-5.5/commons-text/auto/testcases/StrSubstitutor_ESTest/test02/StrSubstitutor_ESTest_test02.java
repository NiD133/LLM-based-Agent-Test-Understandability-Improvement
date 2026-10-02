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
public class StrSubstitutor_ESTest_test02 extends StrSubstitutor_ESTest_scaffolding {

    private static final String CONSTANT_LOOKUP_CLASS_NAME = "org.apache.commons.text.lookup.ConstantStringLookup";
    private static final String EXPECTED_REPLACEMENT =
            "org.apache.commons.text.lookup.ConstantStringLookup$$\u0000\u0000\u0000"
                    + "org.apache.commons.text.lookup.ConstantStringLookup$$\u0000\u0000\u0000";

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        HashMap<String, String> valuesByVariableName = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) valuesByVariableName,
                CONSTANT_LOOKUP_CLASS_NAME,
                CONSTANT_LOOKUP_CLASS_NAME);
        StringBuilder source = new StringBuilder((CharSequence) CONSTANT_LOOKUP_CLASS_NAME);
        char[] escapedPrefixWithNullPadding = new char[5];
        escapedPrefixWithNullPadding[0] = '$';
        escapedPrefixWithNullPadding[1] = '$';

        substitutor.setVariablePrefix('$');
        source.append(escapedPrefixWithNullPadding);
        source.append((CharSequence) source);
        String replacement = substitutor.replace((Object) source);

        assertEquals('$', substitutor.getEscapeChar());
        assertEquals(EXPECTED_REPLACEMENT, replacement);
    }
}
