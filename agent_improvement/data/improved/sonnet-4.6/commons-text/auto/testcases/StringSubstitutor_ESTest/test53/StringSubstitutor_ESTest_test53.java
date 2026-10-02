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
public class StringSubstitutor_ESTest_test53 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that calling setVariableSuffix() does not change the escape character.
     * The default escape character '$' should remain unchanged after modifying the suffix.
     */
    @Test(timeout = 4000)
    public void test_setVariableSuffix_doesNotAffectEscapeChar() throws Throwable {
        // Arrange: create a substitutor backed by an empty variable map
        Map<String, Object> emptyVariables = new HashMap<>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyVariables);

        // Act: change the variable suffix to 'r' (returns the same substitutor instance)
        StringSubstitutor substitutorAfterSuffixChange = substitutor.setVariableSuffix('r');

        // Assert: the default escape character '$' is still in effect
        assertEquals('$', substitutorAfterSuffixChange.getEscapeChar());
    }
}
