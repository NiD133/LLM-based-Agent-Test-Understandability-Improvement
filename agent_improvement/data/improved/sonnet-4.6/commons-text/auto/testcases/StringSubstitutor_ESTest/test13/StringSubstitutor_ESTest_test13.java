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
public class StringSubstitutor_ESTest_test13 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        char[] singleCharArray = new char[1];
        TextStringBuilder builder = TextStringBuilder.wrap(singleCharArray);

        // A negative length means no characters are in range, so no substitution occurs
        boolean wasModified = substitutor.replaceIn(builder, 0, (-2281));

        assertEquals('$', substitutor.getEscapeChar());
        assertFalse(wasModified);
    }
}
