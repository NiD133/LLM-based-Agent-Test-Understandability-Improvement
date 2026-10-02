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
public class DefaultParser_ESTest_test11 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        DefaultParser defaultParser0 = new DefaultParser();
        DefaultParser.NonOptionAction defaultParser_NonOptionAction0 = DefaultParser.NonOptionAction.IGNORE;
        defaultParser0.nonOptionAction = defaultParser_NonOptionAction0;
        defaultParser0.handleUnknownToken("-T");
    }
}
