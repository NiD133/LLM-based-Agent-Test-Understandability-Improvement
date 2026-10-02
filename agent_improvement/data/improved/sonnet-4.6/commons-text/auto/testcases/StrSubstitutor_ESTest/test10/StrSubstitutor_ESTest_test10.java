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
public class StrSubstitutor_ESTest_test10 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that setting a value delimiter string does not change the escape character,
     * which should remain the default '$'.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Build a substitutor with an empty variable map and non-default prefix/suffix strings
        HashMap<String, String> emptyVariableMap = new HashMap<String, String>();
        String customPrefix = "org.apache.commons.text.lookup.ConstantStringLookup";
        String customSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) emptyVariableMap, customPrefix, customSuffix);

        // Setting a value delimiter returns 'this', allowing method chaining;
        // the escape character must remain the default '$' regardless
        StrSubstitutor substitutorAfterDelimiterSet = substitutor.setValueDelimiter("j#jGwu");
        assertEquals('$', substitutorAfterDelimiterSet.getEscapeChar());
    }
}
