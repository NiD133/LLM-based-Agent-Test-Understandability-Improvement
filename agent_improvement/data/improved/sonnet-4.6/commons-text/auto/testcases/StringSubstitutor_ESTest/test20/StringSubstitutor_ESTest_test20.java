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
public class StringSubstitutor_ESTest_test20 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer) returns false and the escape character
     * defaults to '$' when the interpolator substituter is given a null StringBuffer.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Create an interpolator-based substitutor that resolves common variable sources
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Passing null should be a no-op: no replacement is performed
        boolean wasModified = interpolator.replaceIn((StringBuffer) null);
        assertFalse("replaceIn(null) should return false (nothing was replaced)", wasModified);

        // The default escape character for StringSubstitutor is '$'
        assertEquals('$', interpolator.getEscapeChar());
    }
}
