package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import java.time.Month;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Half_ESTest_test04 extends Half_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void firstMonth_H1_returnsJanuary() throws Throwable {
        Half firstHalf = Half.H1;
        Month firstMonth = firstHalf.firstMonth();
        assertEquals(Month.JANUARY, firstMonth);
    }
}
