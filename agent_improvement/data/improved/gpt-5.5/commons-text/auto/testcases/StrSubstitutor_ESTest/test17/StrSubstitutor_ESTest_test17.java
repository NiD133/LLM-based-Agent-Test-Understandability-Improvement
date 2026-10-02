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
public class StrSubstitutor_ESTest_test17 extends StrSubstitutor_ESTest_scaffolding {

    private static final String VARIABLE_MATCHER_TEXT = "Variable prefix matcher must not be null!";
    private static final char ESCAPE_CHARACTER = '`';

    @Test(timeout = 4000)
    @SuppressWarnings("unchecked")
    public void test17() throws Throwable {
        StrLookup<Object> variableResolver =
                (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver,
                VARIABLE_MATCHER_TEXT,
                VARIABLE_MATCHER_TEXT,
                ESCAPE_CHARACTER);
        StringBuffer nullBuffer = null;

        boolean wasModified = substitutor.replaceIn(nullBuffer);

        assertFalse(wasModified);
        assertEquals(ESCAPE_CHARACTER, substitutor.getEscapeChar());
    }
}
