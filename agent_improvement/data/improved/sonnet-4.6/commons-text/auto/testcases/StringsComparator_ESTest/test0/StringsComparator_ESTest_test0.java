package org.apache.commons.text.diff;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringsComparator_ESTest_test0 extends StringsComparator_ESTest_scaffolding {

    // The two strings share the package prefix "org.apache.commons.text.diff."
    // but differ in the class name suffix ("StringsComparator" vs "DeleteCommand"),
    // producing 20 character-level modifications in the edit script.
    private static final String LEFT_CLASS_NAME  = "org.apache.commons.text.diff.StringsComparator";
    private static final String RIGHT_CLASS_NAME = "org.apache.commons.text.diff.DeleteCommand";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        StringsComparator comparator = new StringsComparator(LEFT_CLASS_NAME, RIGHT_CLASS_NAME);
        EditScript<Character> editScript = comparator.getScript();
        assertEquals(20, editScript.getModifications());
    }
}
