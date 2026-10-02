package org.apache.commons.text;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test10 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that calling {@link StrSubstitutor#setValueDelimiter(String)} does not
     * change the escape character, which should remain the default '$' supplied implicitly
     * by the {@code (Map, prefix, suffix)} constructor.
     */
    @Test(timeout = 4000)
    public void settingValueDelimiterKeepsDefaultEscapeChar() throws Throwable {
        Map<String, String> emptyValues = new HashMap<String, String>();
        String prefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String suffix = "org.apache.commons.text.lookup.ConstantStringLookup";
        StrSubstitutor substitutor = new StrSubstitutor(emptyValues, prefix, suffix);

        StrSubstitutor sameSubstitutor = substitutor.setValueDelimiter("j#jGwu");

        assertEquals(StrSubstitutor.DEFAULT_ESCAPE, sameSubstitutor.getEscapeChar());
    }
}
