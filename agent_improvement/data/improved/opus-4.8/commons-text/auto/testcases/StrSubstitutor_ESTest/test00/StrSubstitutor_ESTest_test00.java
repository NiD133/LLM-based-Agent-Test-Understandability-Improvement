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
public class StrSubstitutor_ESTest_test00 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a zero-length region of a character array should yield an empty
     * string, regardless of the array's contents, and must leave the
     * substitutor's default escape character ('$') untouched.
     */
    @Test(timeout = 4000)
    public void replaceEmptyRegionReturnsEmptyString() throws Throwable {
        Map<String, String> emptyVariables = new HashMap<String, String>();
        String variablePrefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String variableSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyVariables, variablePrefix, variableSuffix);

        char[] source = new char[1];
        // length == 0 means no characters are processed.
        String result = substitutor.replace(source, 0, 0);

        assertNotNull(result);
        assertEquals("", result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
