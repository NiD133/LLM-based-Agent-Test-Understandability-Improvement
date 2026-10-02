package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test06 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A TSV tokenizer created from a null input string should still default to
     * treating empty tokens as empty strings (not as null).
     */
    @Test(timeout = 4000)
    public void tsvInstanceFromNullInputDoesNotTreatEmptyTokensAsNull() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance((String) null);

        assertFalse(tsvTokenizer.isEmptyTokenAsNull());
    }
}
