package sample;

import java.util.ArrayList;

public class TreeNode {
    private static final int MAX_NUMBER_OF_NODES = 4;
    private ArrayList<TreeNode> children;
    private int decision;

    public TreeNode(int decision) {
        this.decision = decision;
        if (decision > 2) {
            this.children = new ArrayList<>();
        } else {
            this.children = null;
        }
    }

    public void addChild(TreeNode child) {
        if (children.size() <= MAX_NUMBER_OF_NODES) {
            children.add(child);
        }
    }

    public TreeNode getChild(int index) throws ArrayIndexOutOfBoundsException {
        if (index > 3) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return this.children.get(index);
        }
    }

    public int getDecision() {
        return decision;
    }

    public ArrayList<Integer> inOrderTraversal(TreeNode root) {

        ArrayList<Integer> decisionSet = new ArrayList<>();
        if (root.children != null && !root.children.isEmpty()) {
            decisionSet.add(root.decision);
            for (int i = 0; i < root.children.size(); i++) {
                decisionSet.addAll(inOrderTraversal(root.children.get(i)));
            }
            /*for (int i = root.children.size() / 2; i < root.children.size(); i++) {
                decisionSet.addAll(inOrderTraversal(root.children.get(i)));
            }*/
        } else {
            decisionSet.add(root.decision);
        }

        return decisionSet;
    }

    public boolean hasChildren() {
        return this.children != null;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public static int getMaxNumberOfNodes() {
        return MAX_NUMBER_OF_NODES;
    }
}
