package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test01 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create a substitutor with default settings: escape char '$', prefix "${", suffix "}"
        StringSubstitutor substitutor = new StringSubstitutor();

        // Disable variable default-value resolution by clearing the value delimiter matcher
        substitutor.setValueDelimiterMatcher((StringMatcher) null);

        // Call replace() on a plain-text string that contains no resolvable ${...} variables;
        // the call exercises the replace path but does not alter the substitutor configuration
        substitutor.replace("StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, enableUndefinedVariableException=false, escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], valueDelimiterMatcher=null, variableResolver=null]");

        // The default escape character '$' must be retained after clearing the delimiter and calling replace()
        assertEquals('$', substitutor.getEscapeChar());
    }
}
