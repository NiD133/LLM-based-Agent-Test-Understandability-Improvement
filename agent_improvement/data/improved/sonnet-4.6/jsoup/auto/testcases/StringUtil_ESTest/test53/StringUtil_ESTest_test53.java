package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.stream.Collector;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test53 extends StringUtil_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void joining_withArbitraryDelimiter_returnsNonNullCollector() throws Throwable {
        Collector<CharSequence, ?, String> joiningCollector = StringUtil.joining("?6z");
        assertNotNull(joiningCollector);
    }
}
