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
public class NodeIterator_ESTest_test1 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that iterating over a cloned document containing no FormElements
     * yields hasNext() == false immediately.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        Document parsedDocument = Parser.parse("kv3=Q", "");
        Element clonedElement = parsedDocument.doClone(parsedDocument);
        NodeIterator<FormElement> formElementIterator = new NodeIterator<FormElement>(clonedElement, FormElement.class);

        boolean hasFormElements = formElementIterator.hasNext();

        assertFalse(hasFormElements);
    }
}
