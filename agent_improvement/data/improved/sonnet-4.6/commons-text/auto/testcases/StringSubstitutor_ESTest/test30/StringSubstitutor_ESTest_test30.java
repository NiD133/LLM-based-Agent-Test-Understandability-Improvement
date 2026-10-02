package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.text.lookup.StringLookup;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test30 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that after replacing a substring (using the string representation of the interpolator as input),
     * the escape character remains the default '$'.
     *
     * The mock lookup always returns "}" for any variable key, which causes variable resolution to stop
     * immediately (the suffix is matched right away), so the replacement has no net effect on the escape char.
     */
    @Test(timeout = 4000)
    public void test30_escapeCharRemainsDefaultAfterReplaceWithMockedLookup() throws Throwable {
        // Create a substitutor that knows how to resolve many common variable prefixes (env, sys, etc.)
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        // Capture the string representation of the substitutor to use as the source text for replacement
        String substitutorAsText = substitutor.toString();

        // Set up a mock lookup that always resolves any variable key to "}" (the default suffix)
        StringLookup mockLookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(mockLookup).toString();
        doReturn("}").when(mockLookup).apply(anyString());
        substitutor.setVariableResolver(mockLookup);

        // Perform a partial replace on the substitutor's own string representation (offset=7, length=662)
        substitutor.replace(substitutorAsText, 7, 662);

        // The default escape character '$' must not be altered by the replacement operation
        assertEquals('$', substitutor.getEscapeChar());
    }
}
