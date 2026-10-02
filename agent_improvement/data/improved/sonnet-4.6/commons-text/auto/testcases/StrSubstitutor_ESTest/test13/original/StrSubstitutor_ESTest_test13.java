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
        HashMap<String, Object> hashMap0 = new HashMap<String, Object>();
        StrSubstitutor strSubstitutor0 = new StrSubstitutor((Map<String, Object>) hashMap0);
        boolean boolean0 = strSubstitutor0.replaceIn((StringBuilder) null, 8192, (-424));
        assertFalse(boolean0);
        assertEquals('$', strSubstitutor0.getEscapeChar());
    }
}
