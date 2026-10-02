package org.apache.commons.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test12 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Uses the same (arbitrary) token as both the variable prefix and suffix, so a
     * "TOKEN ... TOKEN" pair in the source delimits one variable reference. The only
     * text enclosed by that pair is a single value-delimiter character ('$'), which
     * means the reference has an empty name and an empty default value. With an empty
     * value map the reference resolves to the empty string, leaving only the trailing
     * '$' that sits outside the prefix/suffix pair.
     */
    @Test(timeout = 4000)
    public void replaceInResolvesEmptyVariableToEmptyString() throws Throwable {
        final String token = "org.apache.cmmons.;ext.lookup.onstantStringLookup";

        // No variables defined, and the same token marks both the start and end of a reference.
        Map<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, token, token);
        substitutor.setValueDelimiter('$');

        // Build the source: token + '$' + token + '$'
        // -> the first "token ... token" pair encloses just "$".
        StringBuilder source = new StringBuilder(token);
        source.appendCodePoint('$');
        source.append((CharSequence) source);

        boolean replaced = substitutor.replaceIn(source);

        assertTrue(replaced);
        assertEquals("$", source.toString());
    }
}
