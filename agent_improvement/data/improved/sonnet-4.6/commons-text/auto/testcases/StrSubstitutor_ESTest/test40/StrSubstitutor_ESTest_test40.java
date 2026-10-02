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
public class StrSubstitutor_ESTest_test40 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the static replace method converts any object to its string representation
     * when given an empty value map and Unicode prefix/suffix delimiters.
     * Also confirms that the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test40() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        // Use an empty map so no variable substitutions are performed
        Map<String, Object> emptyValueMap = new HashMap<String, Object>();

        // Use a Unicode character as both the variable prefix and suffix delimiter
        String unicodeDelimiter = "৓";

        // The substitutor instance itself is the source: its toString() is used as the text
        String result = StrSubstitutor.replace((Object) substitutor, emptyValueMap, unicodeDelimiter, unicodeDelimiter);

        // Default StrSubstitutor uses '$' as the escape character
        assertEquals('$', substitutor.getEscapeChar());
        assertNotNull(result);
    }
}
