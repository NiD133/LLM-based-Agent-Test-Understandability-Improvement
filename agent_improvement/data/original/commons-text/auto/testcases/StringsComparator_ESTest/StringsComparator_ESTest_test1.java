package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test1 extends StringsComparator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringsComparator stringsComparator0 = new StringsComparator("yU3P", "org.apache.commons.text.diff.EditScript");
        EditScript<Character> editScript0 = stringsComparator0.getScript();
        assertEquals(43, editScript0.getModifications());
    }
}
