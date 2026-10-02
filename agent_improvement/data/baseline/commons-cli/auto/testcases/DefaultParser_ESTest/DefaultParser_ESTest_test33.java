package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test33 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test33() throws Throwable {
        DefaultParser.Builder defaultParser_Builder0 = DefaultParser.builder();
        Consumer<Option> consumer0 = CommandLine.Builder.DEPRECATED_HANDLER;
        DefaultParser.Builder defaultParser_Builder1 = defaultParser_Builder0.setDeprecatedHandler(consumer0);
        assertSame(defaultParser_Builder1, defaultParser_Builder0);
    }
}
