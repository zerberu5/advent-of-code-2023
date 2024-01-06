package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class Day08 {

    public static long countStepsToReachEndFromAllAs(String input) {
        List<String> directions = parseDirections(input);
        List<Node> nodes = parseNodes(input);
        List<Node> initialNodes = getInitialNodes(nodes);

        long stepCount = 0;

        // Mach für jeden **A-Node nen Key wie lange er nach **Z braucht und als Value die Steps
        // Finde dann das kgv/lcm um überall nen gleichen stepcount zu haben, damit man sicherstellen kann, dass sie zur gleichen Zeit auf **Z sind
        Map<Node, Long> nodeToCount = new HashMap<>();

        for (int i = 0; i < directions.size(); i++) {
            String direction = directions.get(i);
            overrideNodes(direction, initialNodes);
            stepCount++;

            if (allNodesEndWithZ(initialNodes, nodeToCount, stepCount)) {
                break;
            }

            i = repeatLoopIfNecessary(i, directions);
        }

        List<Long> values = nodeToCount.values().stream().toList();
        return calcLCM(values);
    }

    // maths and programming is fun
    // https://www.matheretter.de/wiki/kgv-mehrere-zahlen
    private static long calcLCM(List<Long> vals) {

        List<Map<Integer, Integer>> valsToPrimes = new ArrayList<>();
        for (Long val : vals) {
            valsToPrimes.add(calcPrimeNumsOfVal(val));
        }

        Map<Integer, Integer> primes = new HashMap<>();
        for (Map<Integer, Integer> valToPrime : valsToPrimes) {
            for (Map.Entry<Integer, Integer> entry : valToPrime.entrySet()) {
                primes.putIfAbsent(entry.getKey(), entry.getValue());
                if (entry.getValue() > primes.get(entry.getKey())) {
                    primes.put(entry.getKey(), entry.getValue());
                }
            }
        }

        long product = 1;
        for (Map.Entry<Integer, Integer> entry : primes.entrySet()) {
            product *= power(entry.getKey(), entry.getValue());
        }

        return product;
    }

    // maths is even more fun. math.pow() isn't
    private static long power(int base, int exponent) {
        long result = 1;
        while (exponent > 0) {
            result *= base;
            exponent--;
        }

        return result;
    }

    private static Map<Integer, Integer> calcPrimeNumsOfVal(Long val) {
        Map<Integer, Integer> primeNumsOfValToCount = new HashMap<>();
        List<Integer> primes = calcPrimesTilVal(val);

        int idx = 0;
        int count = 0;
        while (val > 1) {
            Integer prime = primes.get(idx);
            if (val % prime == 0) {
                val = val / prime;
                primeNumsOfValToCount.put(prime, ++count);
            } else {
                idx++;
                count = 0;
            }
        }

        return primeNumsOfValToCount;
    }

    private static List<Integer> calcPrimesTilVal(Long val) {
        List<Integer> primes = new ArrayList<>();

        for (int i = 2; i <= val; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }

        return primes;
    }

    private static boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        if (num == 2) {
            return true;
        }

        for (int i = 2; i < num / 2; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
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

    private static boolean allNodesEndWithZ(List<Node> nodes, Map<Node, Long> nodeToCount, long stepCount) {
        for (Node node : nodes) {
            if (node.name.endsWith("Z")) {
                nodeToCount.putIfAbsent(node, stepCount);
            }
        }

        return nodeToCount.size() == nodes.size();
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
