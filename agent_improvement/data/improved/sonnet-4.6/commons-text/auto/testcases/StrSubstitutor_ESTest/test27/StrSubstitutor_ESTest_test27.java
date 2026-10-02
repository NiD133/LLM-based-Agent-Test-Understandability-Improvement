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

    /**
     * Verifies that StrSubstitutor correctly performs variable substitution when
     * a custom prefix/suffix matcher and variable resolver are provided.
     *
     * The mock StrMatcher returns no match for the first two positions, then
     * matches at position 2 (prefix) and position 3 (suffix), effectively
     * identifying a variable region within "fYxE". The resolver always returns
     * "fYxE" for any variable name, producing the result "fYfYxE":
     * - "fY" is kept as literal text (no prefix match at positions 0 and 1)
     * - the matched variable is replaced with the resolved value "fYxE"
     *
     * Also confirms that the escape character '1' is stored on the substitutor.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        // Arrange: resolver that always returns "fYxE" for any variable name
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null).when(variableResolver).toString();
        doReturn("fYxE").when(variableResolver).apply(anyString());

        // Arrange: matcher used for both prefix and suffix detection.
        // isMatch returns: 0 (no match), 0 (no match), 1 (prefix found), 1 (suffix found), 0 (no more matches)
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null).when(prefixAndSuffixMatcher).toString();
        doReturn(0, 0, 1, 1, 0).when(prefixAndSuffixMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());

        char escapeChar = '1';
        StrSubstitutor substitutor = new StrSubstitutor(variableResolver, prefixAndSuffixMatcher, prefixAndSuffixMatcher, escapeChar);

        // Act: replace variables in the source string
        String result = substitutor.replace("fYxE");

        // Assert: "fY" is preserved literally, the detected variable is replaced with the resolved "fYxE"
        assertEquals("fYfYxE", result);
        assertEquals('1', substitutor.getEscapeChar());
    }
}
