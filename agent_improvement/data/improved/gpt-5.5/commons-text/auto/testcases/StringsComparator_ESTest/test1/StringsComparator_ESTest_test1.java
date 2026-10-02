package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test1 extends StringsComparator_ESTest_scaffolding {

    private static final String LEFT_SEQUENCE = "yU3P";
    private static final String RIGHT_SEQUENCE = "org.apache.commons.text.diff.EditScript";
    private static final int EXPECTED_MODIFICATIONS = 43;

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringsComparator comparator = new StringsComparator(LEFT_SEQUENCE, RIGHT_SEQUENCE);

        EditScript<Character> editScript = comparator.getScript();

        assertEquals(EXPECTED_MODIFICATIONS, editScript.getModifications());
    }
}
