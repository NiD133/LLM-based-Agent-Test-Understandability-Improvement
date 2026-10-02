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
public class StrSubstitutor_ESTest_test18 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        final HashMap<String, Object> variableValues = new HashMap<String, Object>();
        final String variableBoundary = " [stringLookupMap=";
        final char customEscapeCharacter = ';';

        final StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, Object>) variableValues,
                variableBoundary,
                variableBoundary,
                customEscapeCharacter);

        substitutor.replace((StringBuffer) null, Integer.MAX_VALUE, Integer.MAX_VALUE);

        assertEquals(customEscapeCharacter, substitutor.getEscapeChar());
    }
}
