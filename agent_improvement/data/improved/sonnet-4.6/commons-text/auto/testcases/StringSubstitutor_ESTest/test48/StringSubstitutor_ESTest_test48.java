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
public class StringSubstitutor_ESTest_test48 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that when the custom variable prefix ("}") does not appear in the source
     * string ("${"), no substitution occurs and the source is returned unchanged.
     * The empty value map means no variables are defined regardless.
     */
    @Test(timeout = 4000)
    public void test_replaceWithCustomPrefix_sourceContainsNoMatchingPrefix_returnsSourceUnchanged() throws Throwable {
        Map<String, LinkOption> emptyValueMap = new HashMap<>();
        String customPrefix = "}";
        String customSuffix = "}";
        String sourceWithNoCustomPrefix = "${";

        String result = StringSubstitutor.replace((Object) sourceWithNoCustomPrefix, emptyValueMap, customPrefix, customSuffix);

        assertEquals(sourceWithNoCustomPrefix, result);
    }
}
