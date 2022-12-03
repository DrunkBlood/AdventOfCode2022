package drunkblood.day3;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

public class Rucksack {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = Rucksack.class.getResource("rucksacks.txt");
        File dataFile = new File(resource.toURI());
        int errorSum = 0;
        int badgeSum = 0;
        int lineCounter = 0;
        char[][] rucksackBuffer = new char[3][];
        try(BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String currentRucksack;
            while((currentRucksack = reader.readLine()) != null) {
                int totalItems = currentRucksack.length();
                String leftRucksack = currentRucksack.substring(0, totalItems / 2);
                String rightRucksack = currentRucksack.substring(totalItems / 2);
                char[] leftItems = leftRucksack.toCharArray();
                char[] rightItems = rightRucksack.toCharArray();
                Arrays.sort(leftItems);
                Arrays.sort(rightItems);
                int currentLeftItemIndex = 0, currentRightItemIndex = 0;
                char currentLeftItem, currentRightItem = rightItems[currentRightItemIndex];
                while (currentLeftItemIndex < leftRucksack.length()){
                    currentLeftItem = leftItems[currentLeftItemIndex];
                    if(currentLeftItem == currentRightItem){
                        errorSum += scoreItem(currentLeftItem);
                        break;
                    } else if(currentLeftItem > currentRightItem){
                        currentRightItem = rightItems[++currentRightItemIndex];
                    } else {
                        currentLeftItemIndex++;
                    }
                }
                // part 2
                rucksackBuffer[lineCounter] = currentRucksack.toCharArray();
                Arrays.sort(rucksackBuffer[lineCounter]);
                if(++lineCounter == 3){
                    lineCounter = 0;
                    int left = 0, middle = 0, right = 0;
                    char itemLeft, itemMiddle = rucksackBuffer[1][middle], itemRight = rucksackBuffer[2][right];
                    while (left < rucksackBuffer[0].length){
                        itemLeft = rucksackBuffer[0][left];
                        if(itemLeft == itemMiddle){
                            if(itemLeft == itemRight){
                                badgeSum += scoreItem(itemLeft);
                                break;
                            } else if(itemLeft > itemRight){
                                itemRight = rucksackBuffer[2][++right];
                            } else {
                                left++;
                            }
                        } else if(itemLeft > itemMiddle){
                            itemMiddle = rucksackBuffer[1][++middle];
                        } else {
                            left++;
                        }
                    }
                }
            }
        }
        System.out.println(errorSum);
        System.out.println(badgeSum);
    }

    private static int scoreItem(char item){
        return Character.isLowerCase(item) ? item - 'a' + 1 : item - 'A' + 1 + 26;
    }

}
