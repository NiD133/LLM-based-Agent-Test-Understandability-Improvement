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
public class StrSubstitutor_ESTest_test39 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the escape character supplied to the full constructor
     * (value map, prefix, suffix, escape, value delimiter) is retained and
     * returned by {@link StrSubstitutor#getEscapeChar()}.
     */
    @Test(timeout = 4000)
    public void constructorStoresEscapeChar() throws Throwable {
        Map<String, HashMap<Object, String>> valueMap = new HashMap<String, HashMap<Object, String>>();
        String prefix = "hlt8O2";
        String suffix = "hlt8O2";
        char escapeChar = 'w';
        String valueDelimiter = null;

        StrSubstitutor substitutor =
                new StrSubstitutor(valueMap, prefix, suffix, escapeChar, valueDelimiter);

        assertEquals('w', substitutor.getEscapeChar());
    }
}
