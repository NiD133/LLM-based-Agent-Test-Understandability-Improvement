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
public class StrSubstitutor_ESTest_test39 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the escape character supplied to the Map-based constructor
     * is stored and returned correctly by getEscapeChar().
     */
    @Test(timeout = 4000)
    public void test_escapeCharIsPreservedWhenConstructedWithMapPrefixSuffixEscapeAndNullDelimiter() throws Throwable {
        // An empty map is sufficient — this test only exercises constructor parameter handling
        HashMap<String, HashMap<Object, String>> emptyValueMap = new HashMap<String, HashMap<Object, String>>();
        char customEscapeChar = 'w';

        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, HashMap<Object, String>>) emptyValueMap,
                "hlt8O2",       // variable prefix
                "hlt8O2",       // variable suffix
                customEscapeChar,
                (String) null   // no value-default delimiter
        );

        assertEquals(customEscapeChar, substitutor.getEscapeChar());
    }
}
