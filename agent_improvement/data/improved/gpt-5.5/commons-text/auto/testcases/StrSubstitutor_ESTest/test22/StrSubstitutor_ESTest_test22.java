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
public class StrSubstitutor_ESTest_test22 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test22() throws Throwable {
        StrLookup<String> variableLookup = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(variableLookup).toString();
        doReturn("fYxE").when(variableLookup).apply(anyString());

        StrMatcher delimiterMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null).when(delimiterMatcher).toString();
        doReturn(0, 0, 1, 5, 1694).when(delimiterMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        StrSubstitutor substitutor = new StrSubstitutor(variableLookup, delimiterMatcher, delimiterMatcher, '1');

        String replacedSubstring = substitutor.replace("Q3N]D,Um> EQt", 1, 5);
        assertEquals("3NfYxE", replacedSubstring);

        StringBuffer unchangedBuffer = new StringBuffer((CharSequence) "fYxE");
        boolean bufferWasChanged = substitutor.replaceIn(unchangedBuffer);

        assertFalse(bufferWasChanged);
        assertEquals('1', substitutor.getEscapeChar());
    }
}
