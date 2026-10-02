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
public class StrSubstitutor_ESTest_test24 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        final StrLookup<String> variableLookup = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        final StrMatcher sharedPrefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(sharedPrefixAndSuffixMatcher).toString();

        final char escapeCharacter = '1';
        final StrSubstitutor substitutor = new StrSubstitutor(
                variableLookup,
                sharedPrefixAndSuffixMatcher,
                sharedPrefixAndSuffixMatcher,
                escapeCharacter);

        final String source = "fYxE";
        final int offset = 1;
        final int length = 0;
        final String replacedText = substitutor.replace(source, offset, length);

        assertEquals(escapeCharacter, substitutor.getEscapeChar());
        assertEquals("", replacedText);
    }
}
