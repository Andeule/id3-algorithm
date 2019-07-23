package net.andeule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Node<T> {

    public Node(T data, Node<T> parent, String branch) {
        this.data = data;
        this.parent = parent;
        this.branch = branch;
    }
    public Node(Node<T> parent, String branch) {
        this.parent = parent;
        this.branch = branch;
    }

    private String branch;
    private T data;
    private Node<T> parent;
    private List<Node<T>> children = new ArrayList<>();

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        String spaceAsLongAsBranch = "       ";
        for(int i = 0; i < branch.toCharArray().length; i++){
            spaceAsLongAsBranch += " ";
        }
        System.out.println(prefix + (isTail ? "└── " : "├── ") + branch + "───"+ data);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? spaceAsLongAsBranch : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ? spaceAsLongAsBranch : "│   "), true);
        }
    }
}