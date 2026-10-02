package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.stream.Collector;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test53 extends StringUtil_ESTest_scaffolding {

    /**
     * StringUtil.joining(delimiter) should build and return a non-null Collector
     * that joins CharSequence elements using the given delimiter.
     */
    @Test(timeout = 4000)
    public void joiningReturnsNonNullCollector() throws Throwable {
        String delimiter = "?6z";

        Collector<CharSequence, ?, String> joiningCollector = StringUtil.joining(delimiter);

        assertNotNull(joiningCollector);
    }
}
