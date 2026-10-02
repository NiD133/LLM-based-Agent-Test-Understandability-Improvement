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
public class StrSubstitutor_ESTest_test12 extends StrSubstitutor_ESTest_scaffolding {

    // Used as both the variable prefix and suffix, making prefix == suffix.
    private static final String PREFIX_AND_SUFFIX = "org.apache.cmmons.;ext.lookup.onstantStringLookup";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        // Create a substitutor backed by an empty map with a custom prefix and suffix
        // (both are the same string), leaving no variables resolvable.
        Map<String, String> emptyVariables = new HashMap<>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyVariables, PREFIX_AND_SUFFIX, PREFIX_AND_SUFFIX);
        substitutor.setValueDelimiter('$');

        // Start with a buffer whose content equals the prefix/suffix string,
        // then append '$' in-place (appendCodePoint returns the same StringBuilder instance),
        // and finally self-append the buffer to itself.
        StringBuilder content = new StringBuilder((CharSequence) PREFIX_AND_SUFFIX);
        StringBuilder sameReference = content.appendCodePoint('$');
        content.append((CharSequence) sameReference); // sameReference == content: self-append

        // replaceIn modifies the buffer in-place; with prefix == suffix and an empty variable
        // map the substitution reduces the content to just "$".
        boolean wasReplaced = substitutor.replaceIn(content);

        assertEquals("$", content.toString());
        assertTrue(wasReplaced);
    }
}
