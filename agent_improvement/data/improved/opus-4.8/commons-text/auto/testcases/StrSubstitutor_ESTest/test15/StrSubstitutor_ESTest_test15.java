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
public class StrSubstitutor_ESTest_test15 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies {@link StrSubstitutor#replaceIn(StringBuffer)} when the variable
     * prefix and suffix are both the (misspelled) lookup class name.
     * <p>
     * The source buffer is crafted so that a prefix/suffix pair surrounds an
     * empty variable name. Because the backing map is empty, the variable
     * resolves to nothing and is removed, so the buffer is altered (returns
     * {@code true}) and shrinks to a final length of 151 characters.
     */
    @Test(timeout = 4000)
    public void replaceInRemovesEmptyVariableAndReportsBufferAltered() throws Throwable {
        // Both the variable prefix and suffix are this (intentionally misspelled) string.
        final String DELIMITER = "org.apache.cmmons.text.lookup.ConstantStringLookup";

        Map<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyValues, DELIMITER, DELIMITER);

        // Build the template buffer. All builder calls below mutate the same
        // underlying StringBuilder and return it, so each step is chained.
        StringBuilder template = new StringBuilder((CharSequence) DELIMITER);
        template.appendCodePoint('$');                       // append the default escape char
        template.insert((int) '$', (CharSequence) template); // insert the current content at index 36 ('$')
        template.append(DELIMITER);

        StringBuffer source = new StringBuffer(template);
        boolean altered = substitutor.replaceIn(source);

        assertTrue(altered);
        assertEquals(151, source.length());
    }
}
