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
public class StrSubstitutor_ESTest_test23 extends StrSubstitutor_ESTest_scaffolding {

    private static final char CUSTOM_ESCAPE_CHARACTER = '1';
    private static final int START_POSITION_BEYOND_NULL_INPUT = 1899;
    private static final int REPLACEMENT_LENGTH = 1;

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher variableBoundaryMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());

        StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver,
                variableBoundaryMatcher,
                variableBoundaryMatcher,
                CUSTOM_ESCAPE_CHARACTER);

        substitutor.replace((String) null, START_POSITION_BEYOND_NULL_INPUT, REPLACEMENT_LENGTH);

        assertEquals(CUSTOM_ESCAPE_CHARACTER, substitutor.getEscapeChar());
    }
}
