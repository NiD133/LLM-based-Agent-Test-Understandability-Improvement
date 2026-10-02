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
public class StringSubstitutor_ESTest_test04 extends StringSubstitutor_ESTest_scaffolding {

    // Verifies that after configuring recursive substitution and replacing the DEFAULT_SUFFIX
    // matcher as the variable prefix, the interpolator's escape character remains '$'.
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // setEnableSubstitutionInVariables returns 'this', so both references point to the same instance
        StringSubstitutor interpolatorWithRecursion = interpolator.setEnableSubstitutionInVariables(true);

        // Set the variable prefix matcher to the DEFAULT_SUFFIX ('}') matcher on the returned instance
        interpolatorWithRecursion.setVariablePrefixMatcher(interpolator.DEFAULT_SUFFIX);

        // Perform a replacement using the interpolator's own toString() as input
        String interpolatorDescription = interpolator.toString();
        interpolator.replace(interpolatorDescription);

        // The default escape character '$' should remain unchanged after the above configuration
        assertEquals('$', interpolator.getEscapeChar());
    }
}
