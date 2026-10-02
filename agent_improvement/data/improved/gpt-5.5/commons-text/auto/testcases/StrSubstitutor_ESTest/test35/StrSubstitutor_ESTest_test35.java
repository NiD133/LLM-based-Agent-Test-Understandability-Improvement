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
public class StrSubstitutor_ESTest_test35 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test35() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 0).when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver, prefixAndSuffixMatcher, prefixAndSuffixMatcher, '1');
        StringBuffer source = new StringBuffer((CharSequence) "fYxE");

        boolean wasModified = substitutor.replaceIn(source);

        assertFalse(wasModified);
        assertEquals('1', substitutor.getEscapeChar());
    }
}
