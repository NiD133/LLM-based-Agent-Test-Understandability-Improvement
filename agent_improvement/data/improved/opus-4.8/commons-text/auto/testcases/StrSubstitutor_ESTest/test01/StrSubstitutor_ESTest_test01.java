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
public class StrSubstitutor_ESTest_test01 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Uses the same (unusual) string as the variable prefix and suffix, then verifies
     * that {@link StrSubstitutor#replaceIn(StringBuilder)} reports that it changed the
     * buffer. A "variable" is formed because the text contains the prefix marker, a name
     * ('$'), and the suffix marker. The configuration is also checked to confirm that the
     * fluent setters and {@code setDisableSubstitutionInValues} are applied to the instance.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // This odd token doubles as both the variable prefix and the variable suffix.
        final String prefixAndSuffix = ".@let_E6[gkIcD#*{";

        // No variables are defined; matched variables therefore resolve to empty/unknown values.
        Map<String, String> emptyVariables = new HashMap<String, String>();
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyVariables, prefixAndSuffix, prefixAndSuffix);

        // Build "<prefix>$<suffix>", i.e. a single variable named "$" wrapped by the markers.
        StringBuilder text = new StringBuilder(prefixAndSuffix);
        text.append('$');
        text.append(prefixAndSuffix);

        // setValueDelimiter returns the same instance, allowing fluent configuration.
        StrSubstitutor sameSubstitutor = substitutor.setValueDelimiter('$');
        sameSubstitutor.setDisableSubstitutionInValues(true);

        boolean changed = substitutor.replaceIn(text);

        assertTrue(substitutor.isDisableSubstitutionInValues());
        assertTrue(changed);
    }
}
