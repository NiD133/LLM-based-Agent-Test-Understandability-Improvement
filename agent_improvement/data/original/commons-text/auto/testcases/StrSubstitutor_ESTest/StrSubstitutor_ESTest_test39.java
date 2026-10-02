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
public class StrSubstitutor_ESTest_test39 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test39() throws Throwable {
        HashMap<String, HashMap<Object, String>> hashMap0 = new HashMap<String, HashMap<Object, String>>();
        StrSubstitutor strSubstitutor0 = new StrSubstitutor((Map<String, HashMap<Object, String>>) hashMap0, "hlt8O2", "hlt8O2", 'w', (String) null);
        assertEquals('w', strSubstitutor0.getEscapeChar());
    }
}
