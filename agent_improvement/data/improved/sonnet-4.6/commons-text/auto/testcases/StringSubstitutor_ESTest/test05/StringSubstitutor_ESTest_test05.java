package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test05 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn returns false and the default escape character is '$'
     * when preserveEscapes is enabled and the buffer contains an escaped variable
     * prefix ("$$") followed by an unclosed variable reference ("${").
     *
     * The buffer content after setup is: "}\0\0\0\0$${", which has no resolvable
     * variable, so no substitution occurs.
     */
    @Test(timeout = 4000)
    public void test_replaceInWithPreserveEscapesAndUnclosedVariable_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // Build the input buffer: start with "}", then append null chars with a
        // trailing '$', then append "${" — resulting in "}\0\0\0\0$${".
        StringBuilder buffer = new StringBuilder("}");
        char[] charsWithTrailingDollar = new char[5];
        substitutor.setPreserveEscapes(true);
        charsWithTrailingDollar[4] = '$';
        buffer.append(charsWithTrailingDollar);
        buffer.append((CharSequence) "${");

        // No variable can be resolved, so replaceIn must report no substitution.
        boolean substitutionOccurred = substitutor.replaceIn(buffer);
        assertFalse(substitutionOccurred);

        // The default escape character for StringSubstitutor is '$'.
        assertEquals('$', substitutor.getEscapeChar());
    }
}
