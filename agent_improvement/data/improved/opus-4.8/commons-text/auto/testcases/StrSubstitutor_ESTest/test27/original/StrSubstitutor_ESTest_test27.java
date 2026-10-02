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
public class StrSubstitutor_ESTest_test27 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        StrLookup<String> strLookup0 = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(strLookup0).toString();
        doReturn("fYxE").when(strLookup0).apply(anyString());
        StrMatcher strMatcher0 = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null).when(strMatcher0).toString();
        doReturn(0, 0, 1, 1, 0).when(strMatcher0).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());
        StrSubstitutor strSubstitutor0 = new StrSubstitutor(strLookup0, strMatcher0, strMatcher0, '1');
        String string0 = strSubstitutor0.replace("fYxE");
        assertEquals("fYfYxE", string0);
        assertEquals('1', strSubstitutor0.getEscapeChar());
    }
}
