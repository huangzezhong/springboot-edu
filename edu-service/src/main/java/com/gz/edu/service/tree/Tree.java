package com.gz.edu.service.tree;

import com.gz.edu.model.tree.TreeNode;
import lombok.Getter;
import lombok.Setter;

/**
 * 树
 */
@Getter
@Setter
public class Tree {

    private TreeNode rootNode; //树根节点

    public TreeNode find(Integer data) {
        TreeNode currNode = rootNode;
        while (currNode != null) {
            if (data < (Integer) currNode.getData()) {
                currNode = currNode.getLeftChildrenNode();
            } else if (data > (Integer) currNode.getData()) {
                currNode = currNode.getRightChildrenNode();
            } else {
                return currNode;
            }
        }

        return null;
    }

    public void add(Integer data) {
        TreeNode node = new TreeNode(data);
        //创建根节点
        if (rootNode == null) {
            rootNode = node;
        } else {
            TreeNode currNode = rootNode;
            TreeNode parentNode = null;
            while (currNode != null) {
                parentNode = currNode;
                if (data < (Integer) currNode.getData()) {
                    currNode = currNode.getLeftChildrenNode();
                    if (currNode == null) {
                        parentNode.setLeftChildrenNode(node);
                    }

                } else {
                    currNode = currNode.getRightChildrenNode();
                    if (currNode == null) {
                        parentNode.setRightChildrenNode(node);
                    }
                }
            }

        }
    }

    public void remove(Object treeNode) {

    }

}
