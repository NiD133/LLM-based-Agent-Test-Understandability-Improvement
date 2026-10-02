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
public class StringSubstitutor_ESTest_test34 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null object returns null,
     * and that the interpolator substitutors default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test_replaceNullObject_returnsNullAndDefaultEscapeCharIsDollarSign() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        String result = interpolator.replace((Object) null);

        assertNull("Replacing a null object should return null", result);
        assertEquals("Default escape character should be '$'", '$', interpolator.getEscapeChar());
    }
}
