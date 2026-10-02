package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test0 extends NodeIterator_ESTest_scaffolding {

    private static final String DOCUMENT_HTML = "g==i<Tb";
    private static final String BASE_URI = "g==i<Tb";
    private static final String APPENDED_ELEMENT_TAG = "g==i<Tb";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        Document document = Parser.parse(DOCUMENT_HTML, BASE_URI);
        document.appendElement(APPENDED_ELEMENT_TAG);

        Class<FormElement> formElementType = FormElement.class;
        NodeIterator<FormElement> formElementIterator = new NodeIterator<FormElement>(document, formElementType);

        boolean hasFormElement = formElementIterator.hasNext();

        assertFalse(hasFormElement);
    }
}
