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
     * Verifies that substitution aborts with an IllegalStateException when variable
     * resolution never terminates.
     *
     * <p>The text being substituted is the interpolator's own {@code toString()}, which
     * contains {@code ${...}} placeholders. The variable resolver is stubbed to always
     * return that same placeholder-bearing text, so every resolved value introduces new
     * variables to resolve. StringSubstitutor detects this never-ending expansion and
     * throws an IllegalStateException reporting an "Infinite loop in property
     * interpolation".</p>
     */
    @Test(timeout = 4000)
    public void replaceThrowsWhenVariableResolutionLoopsForever() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        // This text contains "${...}" placeholders, making it a self-referential input.
        String textWithPlaceholders = substitutor.toString();

        // A resolver that always hands back placeholder-bearing text, so substitution
        // can never reach a fixed point.
        StringLookup loopingResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(loopingResolver).toString();
        doReturn(textWithPlaceholders).when(loopingResolver).apply(anyString());
        substitutor.setVariableResolver(loopingResolver);

        try {
            substitutor.replace(textWithPlaceholders, 7, 662);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Thrown by StringSubstitutor once it detects the unbounded interpolation.
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
