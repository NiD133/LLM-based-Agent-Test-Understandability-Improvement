package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test51 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that {@link StringSubstitutor#setDisableSubstitutionInValues(boolean)}
     * takes effect: once enabled, {@link StringSubstitutor#isDisableSubstitutionInValues()}
     * reports {@code true}, and the flag remains set after performing a substitution.
     */
    @Test(timeout = 4000)
    public void disableSubstitutionInValuesFlagStaysSetAfterReplace() throws Throwable {
        // Build a substitutor backed by an absent (null) value map.
        final Map<String, String> noValues = null;
        StringSubstitutor substitutor = new StringSubstitutor(
                noValues,
                /* prefix         */ "${",
                /* suffix         */ ":-",
                /* escape         */ '$',
                /* valueDelimiter */ "}");

        substitutor.setDisableSubstitutionInValues(true);

        // Replacing any text must not reset the flag we just enabled.
        substitutor.replace("StringSubstitutor [disableSubstitutionInValues=false, "
                + "enableSubstitutionInVariables=false, enableUndefinedVariableException=false, "
                + "escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], "
                + "preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], "
                + "valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@4[\":-\"], "
                + "variableResolver=null]");

        assertTrue(substitutor.isDisableSubstitutionInValues());
    }
}
