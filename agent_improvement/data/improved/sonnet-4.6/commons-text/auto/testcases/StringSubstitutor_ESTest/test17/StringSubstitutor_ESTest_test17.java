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
public class StringSubstitutor_ESTest_test17 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_replaceInNullStringBuilder_returnsFalseAndPreservesEscapeChar() throws Throwable {
        // Set up a mock lookup that always returns null (simulates unresolved variables)
        StringLookup mockLookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(mockLookup).toString();

        // Use the default suffix matcher for prefix, suffix, and value-delimiter roles,
        // and set '^' as the escape character
        StringMatcher defaultSuffix = StringSubstitutor.DEFAULT_SUFFIX;
        StringSubstitutor substitutor = new StringSubstitutor(
                mockLookup, defaultSuffix, defaultSuffix, '^', defaultSuffix);

        // replaceIn(null) should be a no-op and return false
        boolean wasModified = substitutor.replaceIn((StringBuilder) null);

        assertEquals('^', substitutor.getEscapeChar());
        assertFalse(wasModified);
    }
}
