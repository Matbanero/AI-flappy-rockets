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
    public TreeNode geneticY(TreeNode root) {

        Random rnd = new Random();
        double cutProbs = rnd.nextDouble();
        TreeNode geneticTree = null;

        if (cutProbs < 0.2) {
            geneticTree = root;
            return geneticTree;
        } else if (root.children != null && !root.children.isEmpty()) {
            /* Using while loop to terminate it after first occurence of valid
            * genetic tree - i.e. when cutProbs < 0.2 */
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

    public boolean tryInsert() {
        Random rnd = new Random();
        double insertProb = rnd.nextDouble();

        return insertProb < 0.35;
    }

    /* Can optimize it with heap??? */
    public int findSpace(TreeNode tree) {
        if (tree == null) {
            return -1;
        } else {
            TreeNode temp = tree;
            int j = 0;
            while (temp.hasChildren()) {
                for (int i = 0; i < temp.children.size(); i++) {
                    if (temp.children.get(i) == null) {
                        return i;
                    }
                }
                temp = temp.getChild(j);
                j++;
            }
            return -2;
        }
    }

    public boolean hasChildren() {
        return this.children != null;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    private TreeNode createNode() {
        TreeNode node;
        Random rnd = new Random();
        double decisionPicker = rnd.nextDouble();

        /* Creating decision picker to assign higher decision probability for terminal functions
        * (jump and waitForNext eg. 0 and 1). Using this hack because trees can grow large and
        * cause stackOverflowExceptions. */

        if (decisionPicker < 0.3) {
            node = new TreeNode(0);
        } else if (decisionPicker >= 0.3 && decisionPicker < 0.6) {
            node = new TreeNode(1);
        } else if (decisionPicker >= 0.84 && decisionPicker < 0.92) {
            node = new TreeNode(2);
        } else if (decisionPicker >= 0.6 && decisionPicker < 0.72) {
            node = new TreeNode(3);
        } else if (decisionPicker >= 0.72 && decisionPicker < 0.84) {
            node = new TreeNode(4);
        } else if (decisionPicker >= 0.92 && decisionPicker < 0.96) {
            node = new TreeNode(5);
        } else {
            node = new TreeNode(6);
        }
        return node;
    }

}
