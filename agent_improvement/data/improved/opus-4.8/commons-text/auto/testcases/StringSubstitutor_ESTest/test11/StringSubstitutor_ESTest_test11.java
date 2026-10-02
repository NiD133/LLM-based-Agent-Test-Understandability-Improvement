package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test11 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the escape character passed to the
     * (Map, prefix, suffix, escape, valueDelimiter) constructor is retained and
     * returned by {@link StringSubstitutor#getEscapeChar()}.
     */
    @Test(timeout = 4000)
    public void escapeCharGivenToConstructorIsReturnedByGetter() throws Throwable {
        Map<String, LinkOption> emptyValueMap = new HashMap<String, LinkOption>();
        char escapeChar = '$';

        StringSubstitutor substitutor = new StringSubstitutor(
                emptyValueMap, "${", "}", escapeChar, (String) null);

        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
