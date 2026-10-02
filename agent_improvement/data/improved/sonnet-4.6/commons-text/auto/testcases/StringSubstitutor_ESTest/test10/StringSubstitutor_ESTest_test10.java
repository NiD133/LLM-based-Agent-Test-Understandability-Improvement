package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test10 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testGetEscapeCharReturnsCharacterSetInMapConstructor() throws Throwable {
        // Use the 5-argument map-based constructor with empty prefix/suffix/valueDelimiter and 'R' as escape char
        Map<String, HashMap<String, Object>> variableMap = new HashMap<>();
        StringSubstitutor substitutor = new StringSubstitutor(variableMap, "", "", 'R', "");
        assertEquals('R', substitutor.getEscapeChar());
    }
}
