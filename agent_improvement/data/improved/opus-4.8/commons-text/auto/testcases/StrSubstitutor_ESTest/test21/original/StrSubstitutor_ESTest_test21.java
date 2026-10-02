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
public class StrSubstitutor_ESTest_test21 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        StrSubstitutor strSubstitutor0 = new StrSubstitutor();
        StringBuffer stringBuffer0 = new StringBuffer();
        String string0 = strSubstitutor0.replace(stringBuffer0);
        assertNotNull(string0);
        assertEquals('$', strSubstitutor0.getEscapeChar());
    }
}
