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
public class Printer_ESTest_test04 extends Printer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        String bodyFragment = "S*NpiP";
        String baseUri = "S*NpiP";
        String expectedOutlineHtml = "<html>\n <head></head>\n <body>S*NpiP</body>\n</html>";

        Document document = Parser.parseBodyFragment(bodyFragment, baseUri);
        Document.OutputSettings outputSettings = new Document.OutputSettings();
        Document.OutputSettings outlineOutputSettings = outputSettings.outline(true);
        document.outputSettings(outlineOutputSettings);

        String actualHtml = document.toString();
        assertEquals(expectedOutlineHtml, actualHtml);
    }
}
