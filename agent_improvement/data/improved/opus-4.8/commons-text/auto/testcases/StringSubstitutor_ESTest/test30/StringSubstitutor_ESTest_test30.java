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
public class StringSubstitutor_ESTest_test30 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a substring (offset/length view of the source)
     * leaves the substitutor's configuration untouched. In particular, the
     * default escape character stays '$' even after a custom variable resolver
     * has been installed and a replace() call has run.
     */
    @Test(timeout = 4000)
    public void replaceWithOffsetAndLengthKeepsDefaultEscapeChar() throws Throwable {
        // An interpolating substitutor uses the default escape character '$'.
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String sourceText = substitutor.toString();

        // Install a custom resolver that always resolves any variable to "}".
        StringLookup variableResolver = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(variableResolver).toString();
        doReturn("}").when(variableResolver).apply(anyString());
        substitutor.setVariableResolver(variableResolver);

        // Replace within the offset/length window of the source text.
        substitutor.replace(sourceText, 7, 662);

        // The escape character is unaffected by resolver setup or replacement.
        assertEquals('$', substitutor.getEscapeChar());
    }
}
