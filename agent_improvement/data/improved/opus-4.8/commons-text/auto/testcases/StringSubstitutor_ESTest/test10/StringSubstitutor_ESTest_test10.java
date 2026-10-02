package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test10 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the escape character supplied to the map-based constructor
     * is the one returned by {@link StringSubstitutor#getEscapeChar()}.
     */
    @Test(timeout = 4000)
    public void escapeCharPassedToConstructorIsReturnedByGetter() throws Throwable {
        Map<String, HashMap<String, Object>> emptyValueMap = new HashMap<String, HashMap<String, Object>>();
        char escapeChar = 'R';

        StringSubstitutor substitutor = new StringSubstitutor(
                emptyValueMap,
                /* prefix */ "",
                /* suffix */ "",
                escapeChar,
                /* valueDelimiter */ "");

        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
