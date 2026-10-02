package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Months_ESTest_test26 extends Months_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hashCode_forOneMonth_returnsMonthCount() throws Throwable {
        Months oneMonth = Months.ONE;
        int hashCode = oneMonth.hashCode();
        assertEquals(1, hashCode);
    }
}
