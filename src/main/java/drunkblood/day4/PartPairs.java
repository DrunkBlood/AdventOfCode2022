package drunkblood.day4;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class PartPairs {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL resource = PartPairs.class.getResource("pairs.txt");
        File dataFile = new File(resource.toURI());
        int pairContained = 0;
        int pairOverlap = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String currentPair;
            while((currentPair = reader.readLine()) != null) {
                Pair pair = parsePair(currentPair);
                if(pair.first.start <= pair.second.start && pair.first.end >= pair.second.end ||
                    pair.second.start <= pair.first.start && pair.second.end >= pair.first.end){
                    pairContained++;
                }
                if(pair.first.start <= pair.second.start && pair.first.end >= pair.second.start ||
                    pair.second.start <= pair.first.start && pair.second.end >= pair.first.start){
                    pairOverlap++;
                }
            }
        }
        System.out.println(pairContained);
        System.out.println(pairOverlap);
    }

    private static Pair parsePair(String currentPair) {
        String[] startEnd = currentPair.split("-");
        String[] middle = startEnd[1].split(",");
        Section first = new Section(Integer.parseInt(startEnd[0]), Integer.parseInt(middle[0]));
        Section second = new Section(Integer.parseInt(middle[1]), Integer.parseInt(startEnd[2]));
        return new Pair(first, second);
    }

    record Section(int start, int end){}
    record Pair(Section first, Section second){}
}
