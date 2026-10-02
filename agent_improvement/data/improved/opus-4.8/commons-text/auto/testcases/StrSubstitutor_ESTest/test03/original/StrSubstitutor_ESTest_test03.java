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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        HashMap<String, String> hashMap0 = new HashMap<String, String>();
        StrSubstitutor strSubstitutor0 = new StrSubstitutor((Map<String, String>) hashMap0, "org.apache.commons.text.lookup.ConstantStringLookup", "org.apache.commons.text.lookup.ConstantStringLookup");
        StringBuilder stringBuilder0 = new StringBuilder((CharSequence) "org.apache.commons.text.lookup.ConstantStringLookup");
        StringBuilder stringBuilder1 = stringBuilder0.append("org.apache.commons.text.lookup.ConstantStringLookup");
        char[] charArray0 = new char[5];
        strSubstitutor0.setVariableSuffix('\u0000');
        strSubstitutor0.setEnableSubstitutionInVariables(true);
        stringBuilder1.append(charArray0);
        strSubstitutor0.replace((Object) stringBuilder0);
        assertTrue(strSubstitutor0.isEnableSubstitutionInVariables());
    }
}
