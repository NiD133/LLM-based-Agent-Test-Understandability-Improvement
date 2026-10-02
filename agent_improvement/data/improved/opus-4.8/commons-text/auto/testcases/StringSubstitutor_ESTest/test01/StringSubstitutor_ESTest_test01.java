package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test01 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Clearing the value-delimiter matcher (setting it to null) must not affect
     * the substitutor's escape character, which stays at its default of '$'.
     */
    @Test(timeout = 4000)
    public void replaceWithNullValueDelimiterMatcherKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        substitutor.setValueDelimiterMatcher((StringMatcher) null);
        substitutor.replace(
            "StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, "
            + "enableUndefinedVariableException=false, escapeChar=$, "
            + "prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], "
            + "preserveEscapes=false, "
            + "suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], "
            + "valueDelimiterMatcher=null, variableResolver=null]");

        assertEquals('$', substitutor.getEscapeChar());
    }
}
