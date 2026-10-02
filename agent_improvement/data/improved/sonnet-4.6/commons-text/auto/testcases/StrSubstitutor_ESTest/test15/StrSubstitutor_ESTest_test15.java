package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test15 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Tests that replaceIn(StringBuffer) returns true and updates the buffer length
     * when using a custom (identical) prefix and suffix with an empty variable map.
     * The substitution prefix and suffix are both set to the same long class-name
     * string, and the buffer content is built by inserting and appending copies of
     * that string so that the substitutor finds a variable pattern and processes it.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        // Prefix and suffix are intentionally the same long string
        final String customDelimiter = "org.apache.cmmons.text.lookup.ConstantStringLookup";

        // Empty map: no variable values are defined, but substitution still runs
        Map<String, String> emptyVariableMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyVariableMap, customDelimiter, customDelimiter);

        // Build the source buffer by inserting the delimiter string into itself
        StringBuilder builder = new StringBuilder((CharSequence) customDelimiter);
        // Append '$' (code point 36) to the builder
        StringBuilder builderAfterAppend = builder.appendCodePoint('$');
        // Insert the current builder content at position 36 (the '$' code point value)
        StringBuilder builderAfterInsert = builder.insert((int) '$', (CharSequence) builderAfterAppend);
        // Append another copy of the delimiter string
        StringBuilder builderAfterFinalAppend = builderAfterInsert.append(customDelimiter);

        StringBuffer sourceBuffer = new StringBuffer(builderAfterFinalAppend);

        boolean wasAltered = substitutor.replaceIn(sourceBuffer);

        assertEquals(151, sourceBuffer.length());
        assertTrue(wasAltered);
    }
}
