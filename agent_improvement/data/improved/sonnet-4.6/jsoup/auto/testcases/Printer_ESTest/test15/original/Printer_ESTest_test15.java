package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.mock.java.io.MockFileWriter;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Printer_ESTest_test15 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Document document0 = Parser.parseBodyFragment("S*NpiP", "S*NpiP");
        document0.prependText("S*NpiP");
        String string0 = document0.toString();
        assertEquals("S*NpiP\n<html>\n <head></head>\n <body>S*NpiP</body>\n</html>", string0);
    }
}
