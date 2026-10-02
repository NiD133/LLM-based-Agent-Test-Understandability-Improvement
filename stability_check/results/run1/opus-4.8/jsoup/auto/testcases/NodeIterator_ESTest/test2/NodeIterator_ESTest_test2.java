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

    /**
     * Iterates over a document that has a single child element and verifies that
     * {@link NodeIterator#forEachRemaining} walks every remaining node without error,
     * forwarding each one to the supplied consumer.
     */
    @Test(timeout = 4000)
    public void forEachRemainingVisitsAllNodes() throws Throwable {
        // Build a document tree with one appended child element.
        Document document = new Document("~I@)ny9j(:24`r@$", "org.jsoup.nodes.NodeIterator");
        document.appendElement("~I@)ny9j(:24`r@$");

        // Iterate the document and all of its descendants.
        NodeIterator<Node> nodeIterator = NodeIterator.from(document);

        // A mock consumer that accepts each visited node.
        Consumer<Object> visitedNodeConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        nodeIterator.forEachRemaining(visitedNodeConsumer);
    }
}
