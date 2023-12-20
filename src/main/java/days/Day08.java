package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day08 {

    public static int countStepsToReachEnd(String input) {

        List<String> directions = parseDirections(input);
        List<Node> nodesToLeftRight = parseNodes(input);

        int start = 0;
        int end = nodesToLeftRight.size() - 1;
        int count = 0;
        int directionsCount = 0;
        Node node = nodesToLeftRight.get(start);
        while (start != end) {
            String direction = directions.get(directionsCount);
            if (direction.equals("L")) {
                start = getIndexOfNodeString(nodesToLeftRight, node.instruction.left);
                node = nodesToLeftRight.get(start);
            } else if (direction.equals("R")) {
                start = getIndexOfNodeString(nodesToLeftRight, node.instruction.right);
                node = nodesToLeftRight.get(start);
            }
            count++;
            directionsCount++;
            if (directionsCount == directions.size()) {
                directionsCount = 0;
            }
        }

        return count;
    }

    private static int getIndexOfNodeString(List<Node> nodesToLeftRight, String nodeStr) {
        int index = 0;
        for (int i = 0; i < nodesToLeftRight.size(); i++) {
            if (nodesToLeftRight.get(i).location.equals(nodeStr)) {
                index = i;
            }
        }
        return index;
    }

    private static List<Node> parseNodes(String input) {
        String[] split = input.split("\n");

        List<Node> allNodes = new ArrayList<>();
        for (int i = 2; i < split.length; i++) {
            String[] rawNodeToInstruction = split[i].split(" = ");
            String location = rawNodeToInstruction[0];
            String instructions = rawNodeToInstruction[1].substring(1, rawNodeToInstruction[1].length() - 1);
            String[] leftRight = instructions.split(", ");
            String left = leftRight[0];
            String right = leftRight[1];
            Instruction instruction = new Instruction(left, right);
            allNodes.add(new Node(location, instruction));
        }

        return allNodes;
    }

    private static List<String> parseDirections(String input) {
        return Arrays.stream(input.split("\n")[0].split("")).toList();
    }

    static class Node {
        String location;
        Instruction instruction;

        public Node(String location, Instruction instruction) {
            this.location = location;
            this.instruction = instruction;
        }
    }

    static class Instruction {
        String left;
        String right;

        public Instruction(String left, String right) {
            this.left = left;
            this.right = right;
        }
    }
}
