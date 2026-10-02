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
public class StrSubstitutor_ESTest_test25 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test25() throws Throwable {
        // Use an unusual string as both prefix and suffix so no variable in the input matches.
        // The escape character should still be the default '$'.
        HashMap<String, String> emptyVariableMap = new HashMap<String, String>();
        String unusualDelimiter = "org.apache.commons.text.lookup.ConstantStringLookup";
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) emptyVariableMap, unusualDelimiter, unusualDelimiter);

        // The input contains no variable patterns matching the unusual delimiter, so replace() is a no-op.
        substitutor.replace(" R f.B0.CP^L'^h_{");

        assertEquals('$', substitutor.getEscapeChar());
    }
}
