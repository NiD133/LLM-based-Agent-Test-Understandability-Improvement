package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.apache.commons.text.lookup.StringLookup;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test32 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test32() throws Throwable {
        // Create an interpolator substitutor and capture its string representation as the template
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        String interpolatorAsTemplate = interpolator.toString();

        // Set up a mock lookup that always returns "}" for any variable name.
        // Using ViolatedAssumptionAnswer so unexpected interactions fail fast.
        StringLookup mockLookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(mockLookup).toString();
        doReturn("}").when(mockLookup).apply(anyString());

        // Replace variables in the interpolator's own string representation using the mock lookup
        StringSubstitutor substitutorWithMock = interpolator.setVariableResolver(mockLookup);
        substitutorWithMock.replace(interpolatorAsTemplate);

        // The default escape character must remain '$' after substitution
        assertEquals('$', substitutorWithMock.getEscapeChar());
    }
}
