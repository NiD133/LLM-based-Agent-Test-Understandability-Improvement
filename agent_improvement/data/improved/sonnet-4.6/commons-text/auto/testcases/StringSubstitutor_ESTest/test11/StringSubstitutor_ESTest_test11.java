package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
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
     * Verifies that when a StringSubstitutor is constructed with an explicit escape character,
     * {@code getEscapeChar()} returns that same character.
     *
     * The constructor signature exercised here is:
     *   StringSubstitutor(Map valueMap, String prefix, String suffix, char escape, String valueDelimiter)
     */
    @Test(timeout = 4000)
    public void test11_escapeCharIsPreservedAfterConstruction() throws Throwable {
        // An empty map is sufficient — the test focuses on the escape character, not variable lookup.
        Map<String, LinkOption> emptyVariableMap = new HashMap<>();

        // Construct with the default "${" / "}" delimiters, '$' as the escape character,
        // and null for the value delimiter (no default-value syntax).
        StringSubstitutor substitutor = new StringSubstitutor(
                emptyVariableMap,
                "${",   // variable prefix
                "}",    // variable suffix
                '$',    // escape character
                (String) null  // value delimiter (none)
        );

        // The escape character provided at construction time must be returned unchanged.
        assertEquals('$', substitutor.getEscapeChar());
    }
}
