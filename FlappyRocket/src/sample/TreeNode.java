package sample;

import java.util.ArrayList;
import java.util.Random;

public class TreeNode {

    private ArrayList<TreeNode> children;
    private int decision;

    public TreeNode(int decision) {
        this.decision = decision;
        this.children = new ArrayList<>();
    }

    public void addChild() {

        Random rnd = new Random();
        switch (this.decision) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    this.children.add(new TreeNode(rnd.nextInt(7)));
                }
                break;
            case 3:
                for (int i = 0; i < 2; i++) {
                    this.children.add(new TreeNode(rnd.nextInt(7)));
                }
                break;
            case 4:
                for (int i = 0; i < 2; i++) {
                    this.children.add(new TreeNode(rnd.nextInt(7)));
                }
                break;
            case 5:
                for (int i = 0; i < 2; i++) {
                    this.children.add(new TreeNode(rnd.nextInt(7)));
                }
                break;
            case 6:
                for (int i = 0; i < 3; i++) {
                    this.children.add(new TreeNode(rnd.nextInt(7)));
                }
                break;
            default:
                break;
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

}
