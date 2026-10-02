package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test23 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a zero-length region of a TextStringBuilder returns an empty string,
     * and that the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceZeroLengthRegion_returnsEmptyString() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // Wrap a single-char array in a TextStringBuilder and replace a zero-length slice
        char[] singleChar = new char[1];
        TextStringBuilder builder = TextStringBuilder.wrap(singleChar);
        String result = substitutor.replace(builder, 0, 0);

        assertNotNull(result);
        assertEquals("", result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
