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
public class StrSubstitutor_ESTest_test13 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * replaceIn(StringBuilder, offset, length) on a null source must report
     * "nothing altered" (false) regardless of the offset/length arguments, and
     * must leave the substitutor's default escape character ('$') untouched.
     */
    @Test(timeout = 4000)
    public void replaceInNullStringBuilderReturnsFalseAndKeepsDefaultEscapeChar() throws Throwable {
        Map<String, Object> emptyLookup = new HashMap<String, Object>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyLookup);

        boolean altered = substitutor.replaceIn((StringBuilder) null, 8192, -424);

        assertFalse("null source should not be altered", altered);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
