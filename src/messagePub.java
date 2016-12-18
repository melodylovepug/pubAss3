/**
 * Created by melody on 12/15/16.
 */
import com.google.cloud.pubsub.*;
import java.util.Queue;
import java.util.LinkedList;
/*
public class messagePub {
    public static void main(String[] args) throws Exception {
        Long currentTimeStamp = System.currentTimeMillis();
        PubSub pubsub = PubSubOptions.getDefaultInstance().getService();
        for(int i = 0; i<1000;i++) {
            Message message = Message.of("payloadnew" + i);
            String messageId = pubsub.publish("news1", message);
        }
        Long walltime = System.currentTimeMillis() - currentTimeStamp;
        System.out.println("Totally cost " + walltime.toString() + " ms");

    }
}*/

 class messagePubthread extends Thread {

    public void run() {
        try {


            PubSub pubsub = PubSubOptions.getDefaultInstance().getService();

            for (int i = 0; i < 20; i++) {
                for (int x = 0; x < 500; x++)
                {
                    Message message = Message.of("payloadnew" + i+x);
                String messageId = pubsub.publish("news"+i, message);
            }
        }

        }
        catch (Exception e) {
            System.err.println(" exception: " + e.toString());
            e.printStackTrace();
        }
}}

public class messagePub {

    private messagePub(){}
    public static void main(String[] args) {
        Long currentTimeStamp = System.currentTimeMillis();
        Queue<Thread> threadQueue = new LinkedList<>();
        for (int x = 0; x < 20; x++) {

            Thread t = new messagePubthread();
            threadQueue.add(t);
            t.start();
        }


        for (int i = 0; i < 20; i++) {
            Thread t = threadQueue.poll();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Long walltime = System.currentTimeMillis() - currentTimeStamp;
        System.out.println("Totally cost " + walltime.toString() + " ms");
    }}
