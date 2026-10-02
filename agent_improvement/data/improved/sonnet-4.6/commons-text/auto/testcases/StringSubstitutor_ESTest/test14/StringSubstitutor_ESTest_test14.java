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
public class StringSubstitutor_ESTest_test14 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(TextStringBuilder) returns false when passed null,
     * and that the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        // Passing null should be a no-op and return false
        boolean wasReplaced = substitutor.replaceIn((TextStringBuilder) null);
        assertFalse(wasReplaced);

        // The default escape character should be '$'
        assertEquals('$', substitutor.getEscapeChar());
    }
}
