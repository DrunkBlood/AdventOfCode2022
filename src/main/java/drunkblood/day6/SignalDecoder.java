package drunkblood.day6;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class SignalDecoder {
    static class Chain{
        private Chain next;
        private Chain prev;
        private final Character data;

        public Chain(Chain next, Chain prev, Character data){
            this.next = next;
            this.prev = prev;
            this.data = data;
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Chain other){
                return data == other.data;
            }
            return false;
        }

        public void link(Chain other){
            next = other;
            other.prev = this;
        }

        public void unlink(){
            if(next != null){
                next.prev = null;
                next = null;
            }
            if(prev != null){
                prev.next = null;
                prev = null;
            }
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("[" + data);
            Chain current = next;
            while (current != null){
                if(current.data != null){
                    result.append(", ").append(current.data);
                }
                current = current.next;
            }
            return result + "]";
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = SignalDecoder.class.getResource("secret.txt");
        File file = new File(resource.toURI());
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String data = reader.readLine();
            char[] inputStream = data.toCharArray();
            int streamReaderPos = 0;
            Chain end = new Chain(null, null, null);
            Chain head = end;
            boolean notPrinted = true;
            for (int i = 0; i < 13; i++) {
                Chain newHead = new Chain(head, null, inputStream[streamReaderPos++]);
                newHead.link(head);
                head = newHead;
            }
            for (int i = 0; i < inputStream.length - 13; i++) {
                Chain toInsert = new Chain(end, null, inputStream[streamReaderPos++]);
                toInsert.link(head);
                head = toInsert;
                Chain compareItem = head;
                for (int j = 0; j < 4; j++) {
                    compareItem = compareItem.next;
                }
                boolean fourDifferent = compareOrBreak(compareItem, toInsert);
                boolean allDifferent = compareOrBreak(end, toInsert);
                if(fourDifferent && notPrinted) {
                    System.out.println("start_of_packet_marker: " + streamReaderPos);
                    notPrinted = false;
                }
                if(allDifferent){
                    System.out.println("start_of_messsage_marker: " + streamReaderPos);
                    return;
                }
                Chain lastElement = end.prev.prev;
                end.prev.unlink();
                lastElement.link(end);
            }
        }
    }

    private static boolean compareOrBreak(Chain end, Chain compareItem) {
        while (compareItem.next != end){
            Chain currentChain = compareItem.next;
            while (currentChain != end){
                if(compareItem.equals(currentChain)) return false;
                currentChain = currentChain.next;
            }
            compareItem = compareItem.next;
        }
        return true;
    }
}
