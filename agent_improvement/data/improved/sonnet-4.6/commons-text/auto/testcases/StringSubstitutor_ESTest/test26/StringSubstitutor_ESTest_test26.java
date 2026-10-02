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
public class StringSubstitutor_ESTest_test26 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test26_replaceOnNullBufferPreservesDefaultEscapeChar() throws Throwable {
        // Construct a substitutor backed by an empty variable map
        Map<String, Object> emptyVariables = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyVariables);

        // Replacing within a null StringBuffer (offset 36, length 36) should not throw
        substitutor.replace((StringBuffer) null, 36, 36);

        // Default escape character must remain '$' regardless of the replace call
        assertEquals('$', substitutor.getEscapeChar());
    }
}
