package drunkblood.day5;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class SupplyStacks {
    public static void main(String[] args) throws URISyntaxException, IOException {
        URL url = SupplyStacks.class.getResource("stack.txt");
        File file = new File(url.toURI());
        try(BufferedReader reader =  new BufferedReader(new FileReader(file))){
            ArrayList<String> stackInput = new ArrayList<>();
            String data;
            while ((data = reader.readLine()) != null && !data.isEmpty()){
                stackInput.add(data);
            }
            char[] stackNumberInput = stackInput.get(stackInput.size() - 1).strip().toCharArray();
            stackInput.remove(stackInput.size() - 1);
            int numStacks = Integer.parseInt(String.valueOf(stackNumberInput[stackNumberInput.length - 1]));
            List<Deque<String>> craneMover9000 = new ArrayList<>();
            List<Deque<String>> craneMover9001 = new ArrayList<>();
            Deque<String> craneMover9001Buffer = new LinkedList<>();
            for (int i = 0; i < numStacks; i++) {
                craneMover9000.add(new LinkedList<>());
                craneMover9001.add(new LinkedList<>());
            }
            ListIterator<String> it = stackInput.listIterator(stackInput.size());
            while (it.hasPrevious()){
                data = it.previous() + " ";
                for (int i = 0; i < numStacks; i++) {
                    String stackValue = data.substring(i * 4 + 1, i * 4 + 2);
                    if(!stackValue.isBlank()){
                        craneMover9000.get(i).push(stackValue);
                        craneMover9001.get(i).push(stackValue);
                    }
                }
            }
            while ((data = reader.readLine()) != null){
                String[] dataSplit = data.split(" ");
                int amount = Integer.parseInt(dataSplit[1]);
                int from = Integer.parseInt(dataSplit[3]) - 1;
                int to = Integer.parseInt(dataSplit[5]) - 1;
                // emulate 9000
                for (int i = 0; i < amount; i++) {
                    String item = craneMover9000.get(from).pop();
                    craneMover9000.get(to).push(item);
                }
                // emulate 9001
                for (int i = 0; i < amount; i++) {
                    String item = craneMover9001.get(from).pop();
                    craneMover9001Buffer.push(item);
                }
                for (int i = 0; i < amount; i++) {
                    String item = craneMover9001Buffer.pop();
                    craneMover9001.get(to).push(item);
                }
            }
            System.out.println("9000: ");
            for (int i = 0; i < numStacks; i++) {
                System.out.print(craneMover9000.get(i).pop());
            }
            System.out.println("\n9001: ");
            for (int i = 0; i < numStacks; i++) {
                System.out.print(craneMover9001.get(i).pop());
            }
        }
    }
}
