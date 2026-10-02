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
public class StringSubstitutor_ESTest_test43 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test43() throws Throwable {
        StringSubstitutor stringSubstitutor0 = StringSubstitutor.createInterpolator();
        String string0 = stringSubstitutor0.toString();
        StringLookup stringLookup0 = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(stringLookup0).toString();
        doReturn(string0).when(stringLookup0).apply(anyString());
        stringSubstitutor0.setVariableResolver(stringLookup0);
        // Undeclared exception!
        try {
            stringSubstitutor0.replace(string0, 7, 662);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Infinite loop in property interpolation of ubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, enableUndefinedVariableException=false, escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@14[\"${\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@15['}'], valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@16[\":-\"], variableResolver=org.apache.commons.text.lookup.InterpolatorStringLookup@1 [stringLookupMap={date=org.apache.commons.text.lookup.DateStringLookup@2, localhost=org.apache.commons.text.lookup.InetAddressStringLookup@: \"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@15['
            //
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
