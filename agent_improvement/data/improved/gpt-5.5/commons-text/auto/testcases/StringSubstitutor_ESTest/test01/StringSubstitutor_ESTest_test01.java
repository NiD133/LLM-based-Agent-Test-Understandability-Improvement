package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test01 extends StringSubstitutor_ESTest_scaffolding {

    private static final String STRING_SUBSTITUTOR_DESCRIPTION =
            "StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, "
                    + "enableUndefinedVariableException=false, escapeChar=$, "
                    + "prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], "
                    + "preserveEscapes=false, "
                    + "suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], "
                    + "valueDelimiterMatcher=null, variableResolver=null]";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        substitutor.setValueDelimiterMatcher((StringMatcher) null);
        substitutor.replace(STRING_SUBSTITUTOR_DESCRIPTION);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
