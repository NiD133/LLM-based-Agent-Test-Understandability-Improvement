package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test21 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A freshly created TSV tokenizer has not advanced past any token yet,
     * so its previous-token index should be -1 (one before the first position).
     */
    @Test(timeout = 4000)
    public void previousIndexIsMinusOneBeforeAnyIteration() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance((char[]) null);

        int previousIndex = tsvTokenizer.previousIndex();

        assertEquals(-1, previousIndex);
    }
}
