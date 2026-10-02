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
public class StrSubstitutor_ESTest_test25 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * When the prefix and suffix are configured explicitly (here both set to an
     * arbitrary marker string), the escape character is still left at its
     * default value of '$'. Performing a replace() on input that contains no
     * variable markers must not change that default.
     */
    @Test(timeout = 4000)
    public void escapeCharStaysAtDefaultAfterReplace() throws Throwable {
        Map<String, String> emptyValues = new HashMap<String, String>();
        String prefixAndSuffix = "org.apache.commons.text.lookup.ConstantStringLookup";

        StrSubstitutor substitutor =
                new StrSubstitutor(emptyValues, prefixAndSuffix, prefixAndSuffix);

        substitutor.replace(" R f.B0.CP^L'^h_{");

        assertEquals('$', substitutor.getEscapeChar());
    }
}
