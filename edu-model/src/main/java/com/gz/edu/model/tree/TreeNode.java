package com.gz.edu.model.tree;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 树节点
 */
@Setter
@Getter
public class TreeNode implements Serializable {

    private TreeNode node; //节点

    private Object data; //数据

    private TreeNode leftChildrenNode; //左孩子节点

    private TreeNode rightChildrenNode; //右孩子节点

    public TreeNode(Object data) {
        this.data = data;
    }

}
