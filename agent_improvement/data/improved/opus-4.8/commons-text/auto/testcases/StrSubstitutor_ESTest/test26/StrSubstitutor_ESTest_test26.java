package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test26 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Builds a StrSubstitutor with a custom (and unusual) prefix/suffix pair and
     * verifies that:
     *   1. replacing a null source returns without error, and
     *   2. supplying only a prefix and suffix leaves the escape character at its
     *      default value ('$').
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        Map<String, String> emptyValues = new HashMap<String, String>();
        String variablePrefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String variableSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";

        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, variablePrefix, variableSuffix);

        // Replacing a null source is a no-op and must not throw.
        substitutor.replace((String) null);

        // Only prefix and suffix were customized, so the escape char stays default.
        assertEquals('$', substitutor.getEscapeChar());
    }
}
