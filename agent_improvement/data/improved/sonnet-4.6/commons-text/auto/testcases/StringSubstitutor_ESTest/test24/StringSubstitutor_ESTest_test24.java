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
public class StringSubstitutor_ESTest_test24 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing on a null TextStringBuilder is a no-op and that the
     * default escape character '$' is preserved afterwards.
     */
    @Test(timeout = 4000)
    public void test_replaceNullTextStringBuilder_defaultEscapeCharUnchanged() throws Throwable {
        Map<String, Object> emptyVariables = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyVariables);

        // Replacing on a null source should not throw and should leave substitutor state intact
        substitutor.replace((TextStringBuilder) null);

        // The default escape character must still be '$' after the no-op replace
        assertEquals('$', substitutor.getEscapeChar());
    }
}
