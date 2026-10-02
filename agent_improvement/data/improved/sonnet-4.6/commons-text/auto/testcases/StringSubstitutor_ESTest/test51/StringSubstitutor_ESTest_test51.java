package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.LinkOption;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test51 extends StringSubstitutor_ESTest_scaffolding {

    // A toString() snapshot of a StringSubstitutor instance, used as a realistic replacement source.
    // The string contains variable-like patterns (e.g. "@2") but no substitution keys, so replace() is a no-op.
    private static final String SUBSTITUTOR_TOSTRING =
        "StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, "
        + "enableUndefinedVariableException=false, escapeChar=$, "
        + "prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], "
        + "preserveEscapes=false, "
        + "suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], "
        + "valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@4[\":-\"], "
        + "variableResolver=null]";

    @Test(timeout = 4000)
    public void test51_disableSubstitutionInValues_persistsAfterReplace() throws Throwable {
        // Build a substitutor with a null variable map, custom prefix "${", value delimiter ":-",
        // escape char '$', and suffix "}".
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, LinkOption>) null, "${", ":-", '$', "}");

        substitutor.setDisableSubstitutionInValues(true);

        // Calling replace() should not affect the disableSubstitutionInValues flag.
        substitutor.replace(SUBSTITUTOR_TOSTRING);

        assertTrue(substitutor.isDisableSubstitutionInValues());
    }
}
