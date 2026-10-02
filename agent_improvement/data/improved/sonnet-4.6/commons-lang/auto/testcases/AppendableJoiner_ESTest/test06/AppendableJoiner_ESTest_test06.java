package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import org.apache.commons.lang3.function.FailableBiConsumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AppendableJoiner_ESTest_test06 extends AppendableJoiner_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // A no-op element appender that performs no action when rendering each element
        FailableBiConsumer<Appendable, StringBuilder, IOException> noOpAppender = FailableBiConsumer.nop();

        // Build an AppendableJoiner configured with the no-op element appender
        AppendableJoiner<StringBuilder> joiner = AppendableJoiner.<StringBuilder>builder()
                .setElementAppender(noOpAppender)
                .get();

        assertNotNull(joiner);
    }
}
