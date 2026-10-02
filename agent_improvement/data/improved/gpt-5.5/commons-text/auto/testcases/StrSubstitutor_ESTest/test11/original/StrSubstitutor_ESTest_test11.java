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
public class StrSubstitutor_ESTest_test11 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        StrSubstitutor strSubstitutor0 = new StrSubstitutor();
        StrSubstitutor strSubstitutor1 = strSubstitutor0.setVariableSuffix(".@let_E6[gcD#*{");
        strSubstitutor1.setVariablePrefix(".@let_E6[gcD#*{");
        StringBuilder stringBuilder0 = new StringBuilder(".@let_E6[gcD#*{");
        stringBuilder0.append(".@let_E6[gcD#*{");
        boolean boolean0 = strSubstitutor0.replaceIn(stringBuilder0);
        assertFalse(boolean0);
        assertEquals('$', strSubstitutor0.getEscapeChar());
    }
}
