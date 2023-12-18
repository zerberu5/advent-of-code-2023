package days;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day07Part2 {

    public static int calcTotalWinningsWithJoker(String input) {
        List<Hand> hands = parseListOfHands(input);

        convertToHighestHand(hands);

        List<List<Hand>> groupedHands = sortAndGroupHandsByJoker(hands);
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

    private static List<List<Hand>> sortAndGroupHandsByJoker(List<Hand> hands) {
        List<Hand> handsByFive = groupByFiveByJoker(hands);
        List<Hand> handsByFour = groupByFourByJoker(hands);
        List<Hand> handsByFullHouse = groupByFullHouseByJoker(hands);
        List<Hand> handsByThree = groupByThreeByJoker(hands);
        List<Hand> handsByTwoPair = groupByTwoPairByJoker(hands);
        List<Hand> handsByOnePair = groupByOnePairByJoker(hands);
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
        return groupedHands;
    }

    private static List<Hand> groupByFiveByJoker(List<Hand> hands) {
        List<Hand> handsByFive = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFive(hand.disguisedJokerCards)) {
                handsByFive.add(hand);
            }
        }

        return handsByFive;
    }

    private static List<Hand> groupByFourByJoker(List<Hand> hands) {
        List<Hand> handyByFour = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFour(hand.disguisedJokerCards)) {
                handyByFour.add(hand);
            }
        }

        return handyByFour;
    }

    private static List<Hand> groupByFullHouseByJoker(List<Hand> hands) {
        List<Hand> fullHouses = new ArrayList<>();
        for (Hand hand : hands) {
            if (isFullHouse(hand.disguisedJokerCards)) {
                fullHouses.add(hand);
            }
        }

        return fullHouses;
    }

    private static List<Hand> groupByThreeByJoker(List<Hand> hands) {
        List<Hand> threes = new ArrayList<>();
        for (Hand hand : hands) {
            if (isThree(hand.disguisedJokerCards) && !isFullHouse(hand.disguisedJokerCards)) {
                threes.add(hand);
            }
        }

        return threes;
    }

    private static List<Hand> groupByTwoPairByJoker(List<Hand> hands) {
        List<Hand> twoPairs = new ArrayList<>();
        for (Hand hand : hands) {
            if (isTwoPair(hand.disguisedJokerCards)) {
                twoPairs.add(hand);
            }
        }

        return twoPairs;
    }

    private static List<Hand> groupByOnePairByJoker(List<Hand> hands) {
        List<Hand> onePairs = new ArrayList<>();
        for (Hand hand : hands) {
            if (isOnePair(hand.disguisedJokerCards) && !isFullHouse(hand.disguisedJokerCards)) {
                onePairs.add(hand);
            }
        }

        return onePairs;
    }

    private static void convertToHighestHand(List<Hand> hands) {
        for (Hand hand : hands) {
            if (hand.cards.contains("J")) {
                convertJoker(hand);
            } else {
                // necessary for sorting
                hand.disguisedJokerCards = hand.cards;
            }
        }
    }

    private static void convertJoker(Hand hand) {
        if (isDistinct(hand.cards)) {
            updateOneCard(hand);
        } else if (isOnePair(hand.cards) && !isFullHouse(hand.cards)) {
            updatePair(hand);
        } else if (isTwoPair(hand.cards)) {
            updateTwoPair(hand);
        } else if (isThree(hand.cards) && !isFullHouse(hand.cards)) {
            updateThree(hand);
        } else if (isFullHouse(hand.cards)) {
            updateFullHouse(hand);
        } else if (isFour(hand.cards)) {
            updateFour(hand);
        } else if (isFive(hand.cards)) {
            updateFive(hand);
        }
    }

    private static boolean isOnePair(String cards) {
        return countPairs(cards) == 1;
    }

    private static boolean isTwoPair(String cards) {
        return countPairs(cards) == 2;
    }

    private static int countPairs(String cards) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(cards);
        int count = 0;
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() == 2) {
                count++;
            }
        }

        return count;
    }

    private static boolean isThree(String cards) {
        return mapCharToOccurences(cards).containsValue(3);
    }

    private static boolean isFullHouse(String cards) {
        return mapCharToOccurences(cards).containsValue(3) && mapCharToOccurences(cards).containsValue(2);
    }

    private static boolean isFour(String cards) {
        return mapCharToOccurences(cards).containsValue(4);
    }

    private static boolean isFive(String cards) {
        return mapCharToOccurences(cards).containsValue(5);
    }

    private static void updateFive(Hand hand) {
        if (hand.cards.charAt(0) == 'J') {
            hand.disguisedJokerCards = hand.cards.replace('J', 'A');
        }
    }

    private static void updateFour(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        char highest = ' ';
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (!entry.getKey().equals("J")) {
                highest = entry.getKey().charAt(0);
            }
        }
        hand.disguisedJokerCards = hand.cards.replace('J', highest);
    }

    private static void updateFullHouse(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        Map.Entry<String, Integer> highest = new AbstractMap.SimpleEntry<String, Integer>(" ", 0);
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (getCardToStrengthMap().get(entry.getKey().charAt(0)) > getCardToStrengthMap().get(highest.getKey().charAt(0))) {
                highest = entry;
            }
        }

        hand.disguisedJokerCards = hand.cards.replace('J', highest.getKey().charAt(0));
    }

    private static void updateThree(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        String trippleCard;
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            trippleCard = entry.getKey();
            if (entry.getValue() == 3) {
                if (trippleCard.equals("J")) {
                    char highestValueCard = getHighestValueCard(hand);
                    hand.disguisedJokerCards = hand.cards.replace('J', highestValueCard);
                } else {
                    hand.disguisedJokerCards = hand.cards.replace('J', trippleCard.charAt(0));
                }
                return;
            }
        }
    }

    private static void updateTwoPair(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        Map.Entry<String, Integer> highest = new AbstractMap.SimpleEntry<>(" ", 0);
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() == 2) {
                if (getCardToStrengthMap().get(entry.getKey().charAt(0)) > getCardToStrengthMap().get(highest.getKey().charAt(0))) {
                    highest = entry;
                }
            }
        }

        hand.disguisedJokerCards = hand.cards.replace('J', highest.getKey().charAt(0));
    }

    private static void updatePair(Hand hand) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(hand.cards);
        String pairCard = "";
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() == 2) {
                pairCard = entry.getKey();
                break;
            }
        }
        if (pairCard.equals("J")) {
            char highestValueCard = getHighestValueCard(hand);
            hand.disguisedJokerCards = hand.cards.replace('J', highestValueCard);
        } else {
            hand.disguisedJokerCards = hand.cards.replace("J", pairCard);
        }
    }


    // for distinct and one pair
    private static void updateOneCard(Hand hand) {
        char highestValueCard = getHighestValueCard(hand);
        hand.disguisedJokerCards = hand.cards.replace('J', highestValueCard);
    }


    private static char getHighestValueCard(Hand hand) {
        int highestValue = 0;
        char observedCard;
        char highestCard = ' ';
        for (int i = 0; i < hand.cards.length(); i++) {
            observedCard = hand.cards.charAt(i);
            Integer observedCardValue = getCardToStrengthMap().get(observedCard);
            if (observedCardValue > highestValue) {
                highestValue = observedCardValue;
                highestCard = observedCard;
            }
        }
        return highestCard;
    }

    private static List<Hand> groupByDistinct(List<Hand> hands) {
        List<Hand> distincts = new ArrayList<>();
        for (Hand hand : hands) {
            if (isDistinct(hand.disguisedJokerCards)) {
                distincts.add(hand);
            }
        }

        return distincts;
    }

    private static boolean isDistinct(String cards) {
        Map<String, Integer> charToOccurences = mapCharToOccurences(cards);
        for (Map.Entry<String, Integer> entry : charToOccurences.entrySet()) {
            if (entry.getValue() > 1) {
                return false;
            }
        }

        return true;
    }

    // Stackoverflow
    private static Map<String, Integer> mapCharToOccurences(String input) {
        return Arrays.stream(input.split("")).collect(Collectors.groupingBy(s -> s, LinkedHashMap::new, Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
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
        String disguisedJokerCards;
        int bid;

        @Override
        public int compareTo(Hand otherHand) {

            Map<Character, Integer> cardToStrength = getCardToStrengthMap();

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
    }

    private static Map<Character, Integer> getCardToStrengthMap() {
        Map<Character, Integer> cardToStrength = new HashMap<>();
        cardToStrength.put('A', 14);
        cardToStrength.put('K', 13);
        cardToStrength.put('Q', 12);
        cardToStrength.put('T', 10);
        cardToStrength.put('9', 9);
        cardToStrength.put('8', 8);
        cardToStrength.put('7', 7);
        cardToStrength.put('6', 6);
        cardToStrength.put('5', 5);
        cardToStrength.put('4', 4);
        cardToStrength.put('3', 3);
        cardToStrength.put('2', 2);
        cardToStrength.put('J', 1);
        cardToStrength.put(' ', 0);

        return cardToStrength;
    }
}
