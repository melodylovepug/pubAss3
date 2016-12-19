/*
Created by melody on 12/16/16.

*/

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.cloud.pubsub.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
/*public class messageRec {

    public static void main(String[] args) throws Exception {
        Long currentTimeStamp = System.currentTimeMillis();
        PubSub pubsub = PubSubOptions.getDefaultInstance().getService();
        int i =0;
        Iterator<ReceivedMessage> messages = pubsub.pull("sub1", 1000);
// Ack deadline is renewed until the message is consumed
        while (messages.hasNext()) {
            ReceivedMessage message = messages.next();
           // System.out.println(message.getPayloadAsString());
            // do something with message and ack/nack it
            message.ack(); // or message.nack()
            i++;
        }
        Long walltime = System.currentTimeMillis() - currentTimeStamp;
        System.out.println("Totally cost " + walltime.toString() + " ms"+"return i:" + i);
    }
}*/
class messageRecthread extends Thread {

    public void run() {
        try {
            PubSub pubsub = PubSubOptions.getDefaultInstance().getService();
            for(int i = 0; i < 20; i++)
            {
            Iterator<ReceivedMessage> messages = pubsub.pull("sub"+i, 499);
                com.google.appengine.api.taskqueue.Queue queue = QueueFactory.getDefaultQueue();
// Ack deadline is renewed until the message is consumed
            while (messages.hasNext()) {
                ReceivedMessage message = messages.next();
                // System.out.println(message.getPayloadAsString());
                // do something with message and ack/nack it
                String newMessage = message.getPayloadAsString();
                String content = "content";
                String process = "/process";
                queue.add(TaskOptions.Builder.withUrl(process).param(content, newMessage));
                message.ack(); // or message.nack()

            }
            }
        }

            catch (Exception e) {
                System.err.println(" exception: " + e.toString());
                e.printStackTrace();
            }
        }}

public class messageRec {

    private messageRec(){}
    public static void main(String[] args) {
        Long currentTimeStamp = System.currentTimeMillis();
        Queue<Thread> threadQueue = new LinkedList<>();
        for (int x = 0; x < 20; x++) {

            Thread t = new messageRecthread();
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
