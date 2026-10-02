package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test03 extends StrSubstitutor_ESTest_scaffolding {

    /** The null character, used here as the (non-matching) variable suffix. */
    private static final char NULL_CHAR = (char) 0;

    /**
     * Verifies that enabling substitution-in-variable-names stays enabled after a
     * replace() call, even when the configured prefix/suffix never actually match
     * inside the source text (so no substitution is performed).
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // A multi-character string reused as both the variable prefix and suffix.
        final String prefixAndSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";

        // Empty value map: there are no variables to resolve.
        Map<String, String> emptyValues = new HashMap<String, String>();
        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, prefixAndSuffix, prefixAndSuffix);

        // Override the suffix with the null character and turn on nested-variable
        // substitution before running the replacement.
        substitutor.setVariableSuffix(NULL_CHAR);
        substitutor.setEnableSubstitutionInVariables(true);

        // Build the source text: the prefix/suffix string twice, followed by five
        // null characters.
        StringBuilder source = new StringBuilder((CharSequence) prefixAndSuffix);
        source.append(prefixAndSuffix);
        source.append(new char[5]);

        // Perform the replacement; the flag under test must remain enabled afterwards.
        substitutor.replace((Object) source);

        assertTrue(substitutor.isEnableSubstitutionInVariables());
    }
}
