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
        switch (this.decision) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                for (int i = 0; i < 3; i++) {
                    this.children.add(createNode());
                }
                break;
            case 3:
                for (int i = 0; i < 2; i++) {
                    this.children.add(createNode());
                }
                break;
            case 4:
                for (int i = 0; i < 2; i++) {
                    this.children.add(createNode());
                }
                break;
            case 5:
                for (int i = 0; i < 2; i++) {
                    this.children.add(createNode());
                }
                break;
            case 6:
                for (int i = 0; i < 3; i++) {
                    this.children.add(createNode());
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


    /* TODO change it so it tries to split the tree and return it - need for spawning new children */
    /* TODO geneticY and geneticX need major rework, possibly should implement heaps. */
   /* public TreeNode geneticY(TreeNode root) {

        Random rnd = new Random();
        double cutProbs = rnd.nextDouble();
        TreeNode geneticTree = null;

        if (cutProbs < 0.2) {
            geneticTree = root;
            return geneticTree;
        } else if (root.children != null && !root.children.isEmpty()) {
            *//* Using while loop to terminate it after first occurence of valid
            * genetic tree - i.e. when cutProbs < 0.2 *//*
            int i = 0;
            while (geneticTree == null && i < root.children.size()) {
                geneticTree = geneticY(root.getChild(i));
                i++;
            }
            return geneticTree;

        } else {
            return null;
        }

    }

    public TreeNode geneticX(TreeNode root, TreeNode geneticY) {
        if (root.hasChildren() && !root.children.isEmpty()) {
            for (TreeNode child : root.children) {
                if (tryInsert()) {
                    child = geneticY;
                    break; // Shouldn't use that ehhh...
                }
            }
        }
        return root;
    }

*/

    public boolean tryInsert() {
        Random rnd = new Random();
        double insertProb = rnd.nextDouble();

        return insertProb < 0.35;
    }

    public boolean hasChildren() {
        return this.children != null;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public int getNumberOfChildren() {
        return children.size();
    }

    private TreeNode createNode() {
        TreeNode node;
        Random rnd = new Random();
        double decisionPicker = rnd.nextDouble();

        /* Creating decision picker to assign higher decision probability for terminal functions
        * (jump and waitForNext eg. 0 and 1). Using this hack because trees can grow large and
        * cause stackOverflowExceptions. */

        if (decisionPicker < 0.2) {
            node = new TreeNode(0);
        } else if (decisionPicker >= 0.2 && decisionPicker < 0.6) {
            node = new TreeNode(1);
        } else if (decisionPicker >= 0.6 && decisionPicker < 0.7) {
            node = new TreeNode(2);
        } else if (decisionPicker >= 0.7 && decisionPicker < 0.8) {
            node = new TreeNode(3);
        } else if (decisionPicker >= 0.8 && decisionPicker < 0.9) {
            node = new TreeNode(4);
        } else if (decisionPicker >= 0.9 && decisionPicker < 0.95) {
            node = new TreeNode(5);
        } else {
            node = new TreeNode(6);
        }
        return node;
    }

}
