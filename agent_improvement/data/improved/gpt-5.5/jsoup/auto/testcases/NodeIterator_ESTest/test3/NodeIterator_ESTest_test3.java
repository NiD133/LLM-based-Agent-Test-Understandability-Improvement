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
public class NodeIterator_ESTest_test3 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        Document document = new Document("Gz-OTe\"24", "Wo\u00028Lgsblf?");
        Element iteratorElement = document.appendElement("org.jsoup.nodes.NodeIterator");
        Class<FormElement> formElementType = FormElement.class;
        NodeIterator<FormElement> formElementIterator = new NodeIterator<FormElement>(iteratorElement, formElementType);

        // Preserve the generated scenario: remove the iterator's current node, then drain the iterator.
        formElementIterator.remove();
        Consumer<Object> remainingNodeConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        formElementIterator.forEachRemaining(remainingNodeConsumer);
    }
}
