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
public class StringSubstitutor_ESTest_test22 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing on a null TextStringBuilder source returns null,
     * and that the default escape character remains '$' after the call.
     */
    @Test(timeout = 4000)
    public void test_replaceNullTextStringBuilder_returnsNullAndDefaultEscapeCharIsPreserved() throws Throwable {
        // Arrange: substitutor backed by an empty variable map
        HashMap<String, Object> emptyVariableMap = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) emptyVariableMap);

        // Act: replace on a null source with arbitrary offset and length
        String result = substitutor.replace((TextStringBuilder) null, (-25), (-25));

        // Assert: null source produces null result; default escape char '$' is unchanged
        assertNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
