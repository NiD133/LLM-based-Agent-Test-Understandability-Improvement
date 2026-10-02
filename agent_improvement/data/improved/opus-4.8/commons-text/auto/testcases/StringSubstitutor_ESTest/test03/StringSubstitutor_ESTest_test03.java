package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test03 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that enabling substitution within variable names is retained after the
     * flag is set, the variable prefix is changed, and a replacement is performed on a
     * source that contains no actual variable references.
     */
    @Test(timeout = 4000)
    public void enableSubstitutionInVariablesFlagSurvivesReplace() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.setEnableSubstitutionInVariables(true);
        substitutor.setVariablePrefix('$');

        // A plain string with no resolvable variables; replace should leave the flag intact.
        String sourceWithoutVariables = "StringSubstitutor [disableSubstitutionInValues=false, "
                + "enableSubstitutionInVariables=true, enableUndefinedVariableException=false, escapeChar=$, "
                + "prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@6[\"${\"], "
                + "preserveEscapes=false, "
                + "suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@7['}'], "
                + "valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@8[\":-\"], "
                + "variableResolver=null]";
        substitutor.replace((Object) sourceWithoutVariables);

        assertTrue(substitutor.isEnableSubstitutionInVariables());
    }
}
