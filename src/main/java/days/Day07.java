package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day07 {

    public static int calcTotalWinnings(String input) {
        List<Hand> hands = parseListOfHands(input);

        List<Hand> handsByFive = groupByFive(hands);
        List<Hand> handsByFour = groupByFour(hands);
        List<Hand> handsByFullHouse = groupByFullHouse(hands);
        List<Hand> handsByThree = groupByThree(hands);
        List<Hand> handsByTwoPair = groupByTwoPair(hands);
        List<Hand> handsByOnePair = groupByOnePair(hands);
        List<Hand> handsByDistinct = groupByDistinct(hands);

        Collections.sort(handsByFive);
        Collections.sort(handsByFour);
        Collections.sort(handsByFullHouse);
        Collections.sort(handsByThree);
        Collections.sort(handsByTwoPair);
        Collections.sort(handsByOnePair);
        Collections.sort(handsByDistinct);

        List<List<Hand>> groupedHands = new ArrayList<>();
        groupedHands.add(handsByFive);
        groupedHands.add(handsByFour);
        groupedHands.add(handsByFullHouse);
        groupedHands.add(handsByThree);
        groupedHands.add(handsByTwoPair);
        groupedHands.add(handsByOnePair);
        groupedHands.add(handsByDistinct);


        List<Integer> handToWinnings = new ArrayList<>();
        int totalRankings = hands.size();
        for (List<Hand> sortedHands : groupedHands) {
            for (Hand sortedHand : sortedHands) {
                handToWinnings.add(sortedHand.bid * totalRankings);
                totalRankings--;
            }
        }

        return handToWinnings.stream().mapToInt(Integer::intValue).sum();
    }

    private static List<Hand> groupByDistinct(List<Hand> hands) {
        List<Hand> distincts = new ArrayList<>();
        for (Hand hand : hands) {
            if (isDistinct(hand)) {
                distincts.add(hand);
            }
        }

        return distincts;
    }

    private static boolean isDistinct(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() > 1) {
                return false;
            }
        }

        return true;
    }

    private static List<Hand> groupByOnePair(List<Hand> hands) {
        List<Hand> onePairs = new ArrayList<>();
        for (Hand hand : hands) {
            if (isOnePair(hand) && !isFullHouse(hand)) {
                onePairs.add(hand);
            }
        }

        return onePairs;
    }

    private static boolean isOnePair(Hand hand) {
        return countPairs(hand) == 1;
    }

    private static List<Hand> groupByTwoPair(List<Hand> hands) {
        List<Hand> twoPairs = new ArrayList<>();
        for (Hand hand : hands) {
            if (isTwoPair(hand)) {
                twoPairs.add(hand);
            }
        }

        return twoPairs;
    }

    private static boolean isTwoPair(Hand hand) {
        return countPairs(hand) == 2;
    }

    private static int countPairs(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        int count = 0;
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() == 2) {
                count++;
            }
        }

        return count;
    }

    private static List<Hand> groupByThree(List<Hand> hands) {
        List<Hand> threes = new ArrayList<>();
        for (Hand hand : hands) {
            if (isThree(hand) && !isFullHouse(hand)) {
                threes.add(hand);
            }
        }

        return threes;
    }

    private static boolean isThree(Hand hand) {
        return mapCharToOccurences(hand.cards).containsValue(3);
    }

    private static List<Hand> groupByFullHouse(List<Hand> hands) {
        List<Hand> fullHouses = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFullHouse(hand)) {
                fullHouses.add(hand);
            }
        }

        return fullHouses;
    }

    private static boolean isFullHouse(Hand hand) {
        return mapCharToOccurences(hand.cards).containsValue(3) && mapCharToOccurences(hand.cards).containsValue(2);
    }

    private static List<Hand> groupByFour(List<Hand> hands) {
        List<Hand> handyByFour = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFour(hand)) {
                handyByFour.add(hand);
            }
        }

        return handyByFour;
    }

    private static boolean isFour(Hand hand) {
        return mapCharToOccurences(hand.cards).containsValue(4);
    }

    private static List<Hand> groupByFive(List<Hand> hands) {
        List<Hand> handsByFive = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFive(hand)) {
                handsByFive.add(hand);
            }
        }

        return handsByFive;
    }

    private static boolean isFive(Hand hand) {
        return mapCharToOccurences(hand.cards).containsValue(5);
    }

    // Stackoverflow
    private static Map<String, Integer> mapCharToOccurences(String input) {
        return Arrays.stream(input.split(""))
                .collect(Collectors.groupingBy(s -> s, LinkedHashMap::new, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
    }

    private static List<Hand> parseListOfHands(String input) {
        String[] handsToBidsRaw = input.split("\n");

        List<Hand> hands = new ArrayList<>();
        for (String handToBid : handsToBidsRaw) {
            Hand hand = new Hand();
            String[] handAndBidsRaw = handToBid.split(" ");

            hand.cards = handAndBidsRaw[0];
            hand.bid = Integer.parseInt(handAndBidsRaw[1]);
            hands.add(hand);
        }

        return hands;
    }

    static class Hand implements Comparable<Hand> {
        String cards;
        int bid;

        @Override
        public int compareTo(Hand otherHand) {

            Map<Character, Integer> cardToStrength = getCharacterIntegerMap();

            for (int i = 0; i < cards.length(); i++) {
                char thisCard = this.cards.charAt(i);
                char otherCard = otherHand.cards.charAt(i);

                int thisStrength = cardToStrength.get(thisCard);
                int otherStrength = cardToStrength.get(otherCard);

                if (thisStrength != otherStrength) {
                    return Integer.compare(otherStrength, thisStrength);
                }
            }

            return 0;
        }

        private Map<Character, Integer> getCharacterIntegerMap() {
            Map<Character, Integer> cardToStrength = new HashMap<>();
            cardToStrength.put('A', 14);
            cardToStrength.put('K', 13);
            cardToStrength.put('Q', 12);
            cardToStrength.put('J', 11);
            cardToStrength.put('T', 10);
            cardToStrength.put('9', 9);
            cardToStrength.put('8', 8);
            cardToStrength.put('7', 7);
            cardToStrength.put('6', 6);
            cardToStrength.put('5', 5);
            cardToStrength.put('4', 4);
            cardToStrength.put('3', 3);
            cardToStrength.put('2', 2);
            return cardToStrength;
        }
    }
}
