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
public class StringSubstitutor_ESTest_test37 extends StringSubstitutor_ESTest_scaffolding {

    private static final int BUFFER_CAPACITY = '$';
    private static final String EXPECTED_UNCHANGED_BUFFER_CONTENT =
            "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
                    + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
                    + "\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0000";

    @Test(timeout = 4000)
    public void test37() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        CharBuffer emptyBuffer = CharBuffer.allocate(BUFFER_CAPACITY);

        String replacementResult = substitutor.replace((CharSequence) emptyBuffer);

        assertNotNull(replacementResult);
        assertEquals('$', substitutor.getEscapeChar());
        assertEquals(EXPECTED_UNCHANGED_BUFFER_CONTENT, replacementResult);
    }
}
