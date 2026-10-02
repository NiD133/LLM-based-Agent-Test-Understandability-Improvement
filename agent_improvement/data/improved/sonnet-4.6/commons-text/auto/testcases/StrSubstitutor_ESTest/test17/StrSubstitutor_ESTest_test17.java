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

    @Test(timeout = 4000)
    public void test_replaceInNullStringBuffer_returnsFalseAndPreservesEscapeChar() throws Throwable {
        StrLookup<Object> mockLookup = (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        char escapeChar = '`';
        StrSubstitutor substitutor = new StrSubstitutor(mockLookup, "Variable prefix matcher must not be null!", "Variable prefix matcher must not be null!", escapeChar);

        boolean result = substitutor.replaceIn((StringBuffer) null);

        assertFalse(result);
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
