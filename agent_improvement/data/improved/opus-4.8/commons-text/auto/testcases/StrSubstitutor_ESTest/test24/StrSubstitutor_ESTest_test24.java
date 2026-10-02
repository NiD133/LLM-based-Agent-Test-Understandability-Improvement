package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test24 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * When {@link StrSubstitutor#replace(String, int, int)} is asked to process a
     * zero-length region of the source, no characters are examined, so the result
     * is the empty string regardless of the configured lookup or matchers. The
     * escape character supplied to the constructor is preserved unchanged.
     */
    @Test(timeout = 4000)
    public void replaceOfZeroLengthRegionReturnsEmptyString() throws Throwable {
        StrLookup<String> variableResolver =
                (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher prefixAndSuffixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(prefixAndSuffixMatcher).toString();

        char escapeChar = '1';
        StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver, prefixAndSuffixMatcher, prefixAndSuffixMatcher, escapeChar);

        // offset 1, length 0 -> an empty region is processed.
        String result = substitutor.replace("fYxE", 1, 0);

        assertEquals('1', substitutor.getEscapeChar());
        assertEquals("", result);
    }
}
