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
public class StrSubstitutor_ESTest_test13 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        HashMap<String, Object> emptyVariables = new HashMap<String, Object>();
        StrSubstitutor substitutor = new StrSubstitutor((Map<String, Object>) emptyVariables);

        boolean replacedNullBuilder = substitutor.replaceIn((StringBuilder) null, 8192, (-424));

        assertFalse(replacedNullBuilder);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
