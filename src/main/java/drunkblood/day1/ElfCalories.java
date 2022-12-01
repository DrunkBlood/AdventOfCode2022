package drunkblood.day1;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class ElfCalories {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = ElfCalories.class.getResource("elfCalories.txt");
        File dataFile = new File(resource.toURI());
        int secondMaxCalories = -1, thirdMaxCalories = -1;
        int maxCalories = Integer.MIN_VALUE;
        int calorieSum = 0;
        try(BufferedReader dataReader = new BufferedReader(new FileReader(dataFile))){
            String currentData;
            while ((currentData = dataReader.readLine()) != null){
                if(currentData.isEmpty()){
                    // next elf
                    if(maxCalories <= calorieSum){
                        thirdMaxCalories = secondMaxCalories;
                        secondMaxCalories = maxCalories;
                        maxCalories = calorieSum;
                        System.out.println(calorieSum);
                    } else if (secondMaxCalories <= calorieSum){
                        thirdMaxCalories = secondMaxCalories;
                        secondMaxCalories = calorieSum;
                    } else {
                        thirdMaxCalories = Math.max(thirdMaxCalories, calorieSum);
                    }
                    calorieSum = 0;
                } else {
                    calorieSum += Integer.parseInt(currentData);
                }
            }
        }
        System.out.println();
        // part 1 output
        System.out.println(maxCalories);
        // part 2 output
        System.out.println(secondMaxCalories);
        System.out.println(thirdMaxCalories);
        System.out.println(maxCalories + secondMaxCalories + thirdMaxCalories);

    }
}
