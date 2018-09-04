package sample;

import java.util.ArrayList;

public class TreeNode {
    private static final int MAX_NUMBER_OF_NODES = 4;
    ArrayList<TreeNode> children;
    //TreeNode children[] = new TreeNode[MAX_NUMBER_OF_NODES];
    private int decision;

    public TreeNode(int decision) {
        this.decision = decision;
        this.children = new ArrayList<>();
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
        if (!root.children.isEmpty()) {
            for (int i = 0; i < root.children.size() / 2; i++) {
                decisionSet.addAll(inOrderTraversal(root.children.get(i)));
            }
            decisionSet.add(root.decision);
            for (int i = root.children.size() / 2; i < root.children.size(); i++) {
                decisionSet.addAll(inOrderTraversal(root.children.get(i)));
            }
        } else {
            decisionSet.add(root.decision);
        }

        return decisionSet;
    }

}
