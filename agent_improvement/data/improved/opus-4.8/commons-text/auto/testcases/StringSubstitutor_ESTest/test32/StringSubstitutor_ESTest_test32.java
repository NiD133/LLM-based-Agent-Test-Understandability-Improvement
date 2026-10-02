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

    /**
     * Verifies that replacing a string while using a custom variable resolver
     * does not change the substitutor's escape character, which remains the
     * default '$'.
     */
    @Test(timeout = 4000)
    public void replaceWithCustomResolverKeepsDefaultEscapeChar() throws Throwable {
        // The interpolator factory builds a substitutor with the default '$' escape char.
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        // Use the substitutor's own toString() output as the text to process.
        String textToReplace = substitutor.toString();

        // Stub a variable resolver: it returns "}" for any variable name it is asked to resolve.
        StringLookup variableResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(variableResolver).toString();
        doReturn("}").when(variableResolver).apply(anyString());

        // Installing a new resolver returns the same substitutor instance for chaining.
        StringSubstitutor sameSubstitutor = substitutor.setVariableResolver(variableResolver);
        sameSubstitutor.replace(textToReplace);

        // The replace operation must not alter the default escape character.
        assertEquals('$', sameSubstitutor.getEscapeChar());
    }
}
