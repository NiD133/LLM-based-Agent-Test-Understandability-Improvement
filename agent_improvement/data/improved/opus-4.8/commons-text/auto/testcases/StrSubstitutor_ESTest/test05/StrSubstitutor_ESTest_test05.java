package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test05 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * When the source text contains no variable placeholder (the prefix never
     * appears followed by a matching suffix), replaceIn() leaves the text
     * unchanged and reports that no substitution happened.
     */
    @Test(timeout = 4000)
    public void replaceIn_withNoMatchingPlaceholder_returnsFalseAndLeavesTextUnchanged() throws Throwable {
        // Use the same arbitrary token for both the variable prefix and suffix.
        final String variableMarker = ".@lNt76>6[gcD#%{";

        Map<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, variableMarker, variableMarker);
        substitutor.setPreserveEscapes(true);

        // Source text: a literal '$' followed by the marker, which alone is not
        // a complete prefix+suffix placeholder, so nothing should be replaced.
        StringBuilder source = new StringBuilder();
        source.append('$');
        source.append(variableMarker);

        boolean substituted = substitutor.replaceIn(source);

        assertFalse(substituted);
        assertEquals("$" + variableMarker, source.toString());
    }
}
