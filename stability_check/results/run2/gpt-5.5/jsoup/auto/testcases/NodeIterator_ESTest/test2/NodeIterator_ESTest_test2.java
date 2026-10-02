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
public class NodeIterator_ESTest_test2 extends NodeIterator_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        String documentLocation = "~I@)ny9j(:24`r@$";
        String namespace = "org.jsoup.nodes.NodeIterator";
        String elementName = "~I@)ny9j(:24`r@$";

        Document document = new Document(documentLocation, namespace);
        document.appendElement(elementName);

        NodeIterator<Node> iteratorOverDocument = NodeIterator.from(document);
        Consumer<Object> remainingNodeConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        iteratorOverDocument.forEachRemaining(remainingNodeConsumer);
    }
}
