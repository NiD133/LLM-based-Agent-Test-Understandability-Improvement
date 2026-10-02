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

    /**
     * Verifies that replacing a zero-length substring returns an empty string
     * and that the escape character configured at construction time is preserved.
     */
    @Test(timeout = 4000)
    public void test_replaceZeroLengthSubstring_returnsEmptyStringAndPreservesEscapeChar() throws Throwable {
        // Use mocked matchers as prefix and suffix delimiters
        StrLookup<String> variableLookup = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher delimiterMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(delimiterMatcher).toString();

        // Construct substitutor with custom escape char '1' and the same mock as both prefix and suffix matcher
        StrSubstitutor substitutor = new StrSubstitutor(variableLookup, delimiterMatcher, delimiterMatcher, '1');

        // Replacing a zero-length region (offset=1, length=0) in "fYxE" should yield an empty string
        String result = substitutor.replace("fYxE", 1, 0);

        assertEquals("", result);
        assertEquals('1', substitutor.getEscapeChar());
    }
}
