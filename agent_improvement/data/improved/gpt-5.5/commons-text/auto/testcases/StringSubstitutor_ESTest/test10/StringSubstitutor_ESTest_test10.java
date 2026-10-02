package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test10 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        final HashMap<String, HashMap<String, Object>> valueMap = new HashMap<String, HashMap<String, Object>>();

        final StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, HashMap<String, Object>>) valueMap, "", "", 'R', "");

        assertEquals('R', substitutor.getEscapeChar());
    }
}
