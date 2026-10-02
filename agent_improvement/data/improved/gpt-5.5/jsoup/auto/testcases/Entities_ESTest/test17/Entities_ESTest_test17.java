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
public class Entities_ESTest_test17 extends Entities_ESTest_scaffolding {

    private static final String UPPERCASE_QUOT_ENTITY_NAME = "QUOT";
    private static final String QUOTATION_MARK = "\"";

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        String resolvedEntityValue = Entities.getByName(UPPERCASE_QUOT_ENTITY_NAME);

        assertEquals(QUOTATION_MARK, resolvedEntityValue);
        assertNotNull(resolvedEntityValue);
    }
}
