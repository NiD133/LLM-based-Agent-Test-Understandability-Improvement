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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        HashMap<String, String> hashMap0 = new HashMap<String, String>();
        StrSubstitutor strSubstitutor0 = new StrSubstitutor((Map<String, String>) hashMap0, ".@let_E6[gcD#*{", ".@let_E6[gcD#*{");
        StringBuilder stringBuilder0 = new StringBuilder(".@let_E6[gcD#*{");
        strSubstitutor0.setValueDelimiter("");
        StringBuilder stringBuilder1 = stringBuilder0.append(".@let_E6[gcD#*{");
        boolean boolean0 = strSubstitutor0.replaceIn(stringBuilder1);
        assertFalse(boolean0);
        assertEquals('$', strSubstitutor0.getEscapeChar());
    }
}
