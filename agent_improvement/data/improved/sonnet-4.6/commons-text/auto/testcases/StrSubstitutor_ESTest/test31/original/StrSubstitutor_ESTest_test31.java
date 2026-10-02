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
public class StrSubstitutor_ESTest_test31 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        StrSubstitutor strSubstitutor0 = new StrSubstitutor();
        String string0 = strSubstitutor0.replace((CharSequence) "$org.apache.cmmons.");
        assertEquals('$', strSubstitutor0.getEscapeChar());
        assertNotNull(string0);
        assertEquals("$org.apache.cmmons.", string0);
    }
}
