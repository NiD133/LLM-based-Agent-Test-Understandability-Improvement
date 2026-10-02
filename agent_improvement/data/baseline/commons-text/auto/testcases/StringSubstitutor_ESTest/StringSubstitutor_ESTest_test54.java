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
public class StringSubstitutor_ESTest_test54 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test54() throws Throwable {
        StringSubstitutor stringSubstitutor0 = StringSubstitutor.createInterpolator();
        stringSubstitutor0.setEnableUndefinedVariableException(true);
        String string0 = stringSubstitutor0.toString();
        // Undeclared exception!
        try {
            stringSubstitutor0.replace(string0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Cannot resolve variable '\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@15['' (enableSubstitutionInVariables=false).
            //
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
