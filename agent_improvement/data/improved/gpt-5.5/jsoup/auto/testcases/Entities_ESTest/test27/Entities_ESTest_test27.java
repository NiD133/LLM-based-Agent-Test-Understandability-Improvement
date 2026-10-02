package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedOutputStream;
import java.io.PipedOutputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.evosuite.runtime.mock.java.io.MockPrintStream;
import org.jsoup.internal.QuietAppendable;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test27 extends Entities_ESTest_scaffolding {

    private static final String ESCAPED_LESS_THAN_ENTITY = "&lt;";
    private static final String UNESCAPED_LESS_THAN = "<";

    @Test(timeout = 4000)
    public void test27() throws Throwable {
        String unescapedText = Entities.unescape(ESCAPED_LESS_THAN_ENTITY);

        assertEquals(UNESCAPED_LESS_THAN, unescapedText);
    }
}
