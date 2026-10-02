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
public class StrSubstitutor_ESTest_test04 extends StrSubstitutor_ESTest_scaffolding {

    private static final String SHARED_PREFIX_AND_SUFFIX = ".@let_E6[gcD#*{";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        HashMap<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor((Map<String, String>) emptyValues, SHARED_PREFIX_AND_SUFFIX, SHARED_PREFIX_AND_SUFFIX);

        StringBuilder source = new StringBuilder(SHARED_PREFIX_AND_SUFFIX);
        substitutor.setValueDelimiter("");

        StringBuilder sourceWithUnresolvedVariable = source.append(SHARED_PREFIX_AND_SUFFIX);
        boolean wasReplaced = substitutor.replaceIn(sourceWithUnresolvedVariable);

        assertFalse(wasReplaced);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
