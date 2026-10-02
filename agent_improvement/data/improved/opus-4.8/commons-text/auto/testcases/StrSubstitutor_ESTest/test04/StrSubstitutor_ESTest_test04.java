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
public class StrSubstitutor_ESTest_test04 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that {@link StrSubstitutor#replaceIn(StringBuilder)} reports "no change"
     * when no variable can be resolved, and that the escape character keeps its default value.
     *
     * <p>The substitutor is configured with an empty value map and with the same odd token
     * used for both the variable prefix and suffix. The target builder only contains that
     * raw token (twice), so even though a prefix/suffix pair is detected, the enclosed
     * variable name resolves to nothing and the builder is left untouched.</p>
     */
    @Test(timeout = 4000)
    public void replaceInLeavesBuilderUnchangedWhenNoVariableResolves() throws Throwable {
        // Token reused as both the variable prefix and suffix.
        final String prefixAndSuffix = ".@let_E6[gcD#*{";

        // No variable values are available, so nothing can ever be substituted.
        Map<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, prefixAndSuffix, prefixAndSuffix);

        // Empty value delimiter disables default-value resolution.
        substitutor.setValueDelimiter("");

        // Builder holds the token twice, forming a prefix...suffix pair with an empty name.
        StringBuilder target = new StringBuilder(prefixAndSuffix);
        target.append(prefixAndSuffix);

        boolean wasAltered = substitutor.replaceIn(target);

        assertFalse("Builder should be unchanged when no variable resolves", wasAltered);
        assertEquals("Escape character should remain the default", '$', substitutor.getEscapeChar());
    }
}
