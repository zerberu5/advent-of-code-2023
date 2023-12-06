package days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day04 {

    // =================================================================================================================
    // PART 1
    // =================================================================================================================
    public static int calcScratchcardPoints(String input) {
        String[] lines = input.split("\n");

        PileOfCards pileOfCards = parsePileOfCards(lines);

        return calcOverallCount(pileOfCards.cards);
    }

    private static int calcOverallCount(List<Card> cards) {
        int overallCount = 0;
        for (Card card : cards) {
            int gameCount = 0;
            for (Integer num : card.yourNums) {
                if (card.winningNums.contains(num)) {
                    if (gameCount == 0) {
                        gameCount = 1;
                    } else {
                        gameCount *= 2;
                    }
                }
            }
            overallCount += gameCount;
        }
        return overallCount;
    }

    // =================================================================================================================
    // PART 2
    // =================================================================================================================
    public static int calcCardInstances(String input) {
        String[] lines = input.split("\n");

        PileOfCards pileOfCards = parsePileOfCards(lines);

        identifyMatchingNums(pileOfCards);

        identifyFollowingCards(pileOfCards);

        // TODO: refactor because too stupid for recursion
        Map<Card, Integer> cardToCount = new LinkedHashMap<>();
        for (Card card : pileOfCards.cards) {
            cardToCount.put(card, 1);
        }

        for (Card card : pileOfCards.cards) {
            countMatches(card, cardToCount);
        }

        return cardToCount.values().stream().mapToInt(Integer::valueOf).sum();
    }

    private static void countMatches(Card card, Map<Card, Integer> cardToCount) {
        cardToCount.put(card, cardToCount.get(card) + card.followingCards.size());

        for (Card followingCard : card.followingCards) {
            countMatches(followingCard, cardToCount);
        }
    }

    private static void identifyFollowingCards(PileOfCards pileOfCards) {
        for (int i = 0; i < pileOfCards.cards.size(); i++) {
            Card card = pileOfCards.cards.get(i);
            List<Card> followingCards = new ArrayList<>();

            for (int j = i; j < card.matchingNums.size() + i; j++) {
                followingCards.add(pileOfCards.cards.get(j + 1));
            }
            card.followingCards = followingCards;
        }
    }

    private static void identifyMatchingNums(PileOfCards pileOfCards) {
        for (Card card : pileOfCards.cards) {
            List<Integer> yourWinningNums = new ArrayList<>();
            for (Integer num : card.yourNums) {
                if (card.winningNums.contains(num)) {
                    yourWinningNums.add(num);
                }
            }
            card.matchingNums = yourWinningNums;
        }
    }

    private static PileOfCards parsePileOfCards(String[] lines) {
        PileOfCards pileOfCards = new PileOfCards();
        pileOfCards.cards = new ArrayList<>(lines.length);

        for (String line : lines) {
            String[] gameRaw = line.split(":")[1].split("\\|");
            Card card = new Card();
            card.yourNums = parseNums(gameRaw[1]);
            card.winningNums = parseNums(gameRaw[0]);
            pileOfCards.cards.add(card);
        }
        return pileOfCards;
    }

    private static List<Integer> parseNums(String nums) {
        return Arrays.stream(nums.trim().split(" ")).filter(element -> !element.isEmpty()).map(Integer::parseInt).toList();
    }

    static class PileOfCards {
        List<Card> cards;
    }

    static class Card {
        List<Integer> winningNums;
        List<Integer> yourNums;
        List<Integer> matchingNums;
        List<Card> followingCards;
    }

}
