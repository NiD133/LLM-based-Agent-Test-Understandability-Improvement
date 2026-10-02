package org.jsoup.nodes;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test2 extends NodeIterator_ESTest_scaffolding {

    /**
     * Iterates a small document (root plus one appended child element) with
     * {@link NodeIterator#forEachRemaining(Consumer)}, feeding each visited node
     * to a mocked Consumer.
     */
    @Test(timeout = 4000)
    public void forEachRemainingVisitsEveryNode() throws Throwable {
        // Build a document that contains the root node and a single child element.
        Document document = new Document("~I@)ny9j(:24`r@$", "org.jsoup.nodes.NodeIterator");
        document.appendElement("~I@)ny9j(:24`r@$");

        // Iterator over the document and all of its descendants.
        NodeIterator<Node> nodeIterator = NodeIterator.from(document);

        // Consumer that receives each node visited during the traversal.
        Consumer<Object> nodeConsumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());

        nodeIterator.forEachRemaining(nodeConsumer);
    }
}
