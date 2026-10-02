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
public class StringSubstitutor_ESTest_test02 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();

        StringBuilder builder = new StringBuilder("}");
        char[] paddingWithTrailingEscape = new char[5];
        paddingWithTrailingEscape[4] = '$';

        StringBuilder afterPadding = builder.append(paddingWithTrailingEscape);
        StringBuilder afterFirstVariablePrefix = afterPadding.append((CharSequence) "${");
        afterFirstVariablePrefix.append("${");
        afterFirstVariablePrefix.append("}");
        StringBuilder selfAppendedBuilder = afterFirstVariablePrefix.append((Object) builder);

        boolean wasModified = substitutor.replaceIn(selfAppendedBuilder);

        String expectedAfterReplacement = "}\u0000\u0000\u0000\u0000${${}}\u0000\u0000\u0000\u0000${${}";
        assertEquals(expectedAfterReplacement, selfAppendedBuilder.toString());
        assertTrue(wasModified);
    }
}
