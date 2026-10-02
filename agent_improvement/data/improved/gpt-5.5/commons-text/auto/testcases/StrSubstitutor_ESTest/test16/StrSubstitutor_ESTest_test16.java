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
public class StrSubstitutor_ESTest_test16 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        HashMap<String, String> variableValues = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) variableValues,
                "org.apache.commons.text.lookup.ConstantStringLookup",
                "org.apache.commons.text.lookup.ConstantStringLookup");

        boolean replacementPerformed = substitutor.replaceIn((StringBuffer) null, 31, 671);

        assertEquals('$', substitutor.getEscapeChar());
        assertFalse(replacementPerformed);
    }
}
