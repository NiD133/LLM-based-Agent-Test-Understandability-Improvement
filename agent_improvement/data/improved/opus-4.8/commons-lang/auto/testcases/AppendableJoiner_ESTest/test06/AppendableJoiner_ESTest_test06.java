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

    /**
     * Verifies that a builder configured with a custom element appender
     * produces a non-null AppendableJoiner instance.
     */
    @Test(timeout = 4000)
    public void builderWithElementAppender_buildsJoiner() throws Throwable {
        // A no-op appender that writes nothing for each element.
        FailableBiConsumer<Appendable, StringBuilder, IOException> noopAppender = FailableBiConsumer.nop();

        AppendableJoiner.Builder<StringBuilder> builder = AppendableJoiner.builder();
        builder.setElementAppender(noopAppender);
        AppendableJoiner<StringBuilder> joiner = builder.get();

        assertNotNull(joiner);
    }
}
