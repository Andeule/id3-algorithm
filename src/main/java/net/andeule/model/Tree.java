package net.andeule.model;

import lombok.Getter;

import java.util.ArrayList;

/**
 * This should represent a generic tree structure
 * @param <T>
 */
public class Tree<T> {
    @Getter
    private Node<T> root;

    public Tree(T rootData) {
        root = new Node<T>();
        root.setData(rootData);
        root.setChildren( new ArrayList<Node<T>>());
        root.setParent(null);
    }


}
