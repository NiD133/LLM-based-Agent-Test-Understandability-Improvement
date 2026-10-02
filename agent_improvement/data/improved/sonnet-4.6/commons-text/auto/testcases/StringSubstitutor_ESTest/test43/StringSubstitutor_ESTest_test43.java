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
public class StringSubstitutor_ESTest_test43 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a substring containing ${...} variable references throws
     * IllegalStateException when the variable resolver returns the substitutor's own
     * toString() (which itself contains ${...} patterns), causing infinite recursion.
     */
    @Test(timeout = 4000)
    public void test43() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        // The interpolator's toString() contains ${...} variable patterns that will
        // trigger further lookups, creating an infinite substitution cycle.
        String interpolatorDescription = interpolator.toString();

        // Set up a mock resolver that always returns the interpolator description,
        // guaranteeing that every resolved variable re-introduces new variables.
        StringLookup cyclicResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(cyclicResolver).toString();
        doReturn(interpolatorDescription).when(cyclicResolver).apply(anyString());
        interpolator.setVariableResolver(cyclicResolver);

        // Replacing a substring of the self-referential description should detect the
        // infinite loop and throw IllegalStateException.
        try {
            interpolator.replace(interpolatorDescription, 7, 662);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
