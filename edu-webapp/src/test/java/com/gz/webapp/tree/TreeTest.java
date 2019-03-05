package com.gz.webapp.tree;

import com.gz.edu.model.tree.TreeNode;
import com.gz.edu.service.tree.Tree;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class TreeTest {

    @Test
    public void test() {
        Tree tree = new Tree();
        tree.add(10);
        tree.add(8);
        tree.add(4);
        tree.add(15);
        tree.add(9);
        tree.add(20);
        tree.add(5);
        tree.add(11);

        TreeNode node = tree.find(15);
        log.info("左孩子节点，{}", node.getLeftChildrenNode().getData().toString());
        log.info("右孩子节点，{}", node.getRightChildrenNode().getData().toString());
    }

}
