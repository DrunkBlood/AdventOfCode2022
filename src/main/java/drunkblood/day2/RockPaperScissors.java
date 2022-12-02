package drunkblood.day2;

import drunkblood.day1.ElfCalories;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class RockPaperScissors {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = RockPaperScissors.class.getResource("rock_paper_scissors.txt");
        File dataFile = new File(resource.toURI());
        int scorePartOne = 0, scorePartTwo = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String currentRound;
            while ((currentRound = reader.readLine()) != null){
                scorePartOne += switch (currentRound){
                    case "A X" -> 3 + 1;
                    case "A Y" -> 6 + 2;
                    case "A Z" -> 0 + 3;
                    case "B X" -> 0 + 1;
                    case "B Y" -> 3 + 2;
                    case "B Z" -> 6 + 3;
                    case "C X" -> 6 + 1;
                    case "C Y" -> 0 + 2;
                    case "C Z" -> 3 + 3;
                    default -> print(currentRound);
                };
                scorePartTwo += switch (currentRound){
                    case "A X" -> 0 + 3;
                    case "A Y" -> 3 + 1;
                    case "A Z" -> 6 + 2;
                    case "B X" -> 0 + 1;
                    case "B Y" -> 3 + 2;
                    case "B Z" -> 6 + 3;
                    case "C X" -> 0 + 2;
                    case "C Y" -> 3 + 3;
                    case "C Z" -> 6 + 1;
                    default -> print(currentRound);
                };
            }
        }
        System.out.println(scorePartOne);
        System.out.println(scorePartTwo);
    }
    private static int print(String data){
        System.out.println("default case");
        System.out.println(data);
        return 0;
    }
}
