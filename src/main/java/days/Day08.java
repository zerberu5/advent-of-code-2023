package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Day08 {

    public static long countStepsToReachEndFromAllAs(String input) {
        List<String> directions = parseDirections(input);
        List<Node> nodes = parseNodes(input);
        List<Node> initialNodes = getInitialNodes(nodes);

        long stepCount = 0;

        for (int i = 0; i < directions.size(); i++) {
            String direction = directions.get(i);
            overrideNodes(direction, initialNodes);
            stepCount++;

            if (stepCount % 1_000_000_000 == 0) {
                System.out.println(stepCount);
            }

            if (allNodesEndWithZ(initialNodes)) {
                return stepCount;
            }

            i = repeatLoopIfNecessary(i, directions);
        }

        return 0;
    }

    private static int repeatLoopIfNecessary(int i, List<String> directions) {
        if (i == directions.size() - 1) {
            i = -1;
        }
        return i;
    }

    private static void overrideNodes(String direction, List<Node> nodes) {
        nodes.replaceAll(node -> overrideNode(direction, node));
    }

    private static boolean allNodesEndWithZ(List<Node> nodes) {
        for (Node node : nodes) {
            if (!node.name.endsWith("Z")) {
                return false;
            }
        }

        return true;
    }

    private static List<Node> getInitialNodes(List<Node> nodes) {
        return new ArrayList<>(nodes.stream().filter(node -> node.name.endsWith("A")).toList());
    }

    public static int countStepsToReachEnd(String input) {

        List<String> directions = parseDirections(input);
        List<Node> nodes = parseNodes(input);
        Node node = getInitialNode(nodes);

        int stepCount = 0;
        for (int i = 0; i < directions.size(); i++) {

            String direction = directions.get(i);
            node = overrideNode(direction, node);
            stepCount++;

            if (node.name.equals("ZZZ")) {
                return stepCount;
            }

            i = repeatLoopIfNecessary(i, directions);
        }

        return 0;
    }

    private static Node overrideNode(String direction, Node node) {
        if (direction.equals("L")) {
            node = node.nodeLeft;
        } else {
            node = node.nodeRight;
        }

        return node;
    }

    private static Node getInitialNode(List<Node> nodes) {
        return nodes.stream()
                .filter(node -> "AAA".equals(node.name))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Initial node with name 'AAA' not found"));
    }

    private static List<Node> parseNodes(String input) {
        String[] split = input.split("\n");

        List<Node> allNodes = new ArrayList<>();
        for (int i = 2; i < split.length; i++) {
            String[] rawNodeToInstruction = split[i].split(" = ");
            String nodeName = rawNodeToInstruction[0];
            Node node = new Node(nodeName);
            allNodes.add(node);
        }

        for (int i = 2; i < split.length; i++) {
            String[] rawNodeToInstruction = split[i].split(" = ");
            String instructions = rawNodeToInstruction[1].substring(1, rawNodeToInstruction[1].length() - 1);
            String[] leftRight = instructions.split(", ");

            Node node = allNodes.get(i - 2);

            // left
            String nodeLeftRaw = leftRight[0];
            for (Node nodeLeft : allNodes) {
                if (nodeLeftRaw.equals(nodeLeft.name)) {
                    node.nodeLeft = nodeLeft;
                }
            }

            // right
            String nodeRightRaw = leftRight[1];
            for (Node nodeRight : allNodes) {
                if (nodeRightRaw.equals(nodeRight.name)) {
                    node.nodeRight = nodeRight;
                }
            }
        }

        return allNodes;
    }

    private static List<String> parseDirections(String input) {
        return Arrays.stream(input.split("\n")[0].split("")).toList();
    }

    static class Node {
        String name;
        Node nodeLeft;
        Node nodeRight;

        public Node(String name) {
            this.name = name;
        }
    }
}
