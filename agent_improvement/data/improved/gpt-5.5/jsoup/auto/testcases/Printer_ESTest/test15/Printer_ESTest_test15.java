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

    private static final String BODY_FRAGMENT = "S*NpiP";
    private static final String BASE_URI = "S*NpiP";
    private static final String PREPENDED_TEXT = "S*NpiP";
    private static final String EXPECTED_PRINTED_DOCUMENT =
            "S*NpiP\n"
                    + "<html>\n"
                    + " <head></head>\n"
                    + " <body>S*NpiP</body>\n"
                    + "</html>";

    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Document document = Parser.parseBodyFragment(BODY_FRAGMENT, BASE_URI);

        document.prependText(PREPENDED_TEXT);

        assertEquals(EXPECTED_PRINTED_DOCUMENT, document.toString());
    }
}
