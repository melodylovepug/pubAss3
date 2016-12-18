/**
 * Created by melody on 12/15/16.
*/

import com.google.cloud.pubsub.*;


public class createSubscriber {
    public static void main(String[] args) throws Exception {
     Long currentTimeStamp = System.currentTimeMillis();
        PubSub pubsub = PubSubOptions.getDefaultInstance().getService();
        for (Integer i = 10; i < Integer.parseInt(args[0]); i++)
        {
            Subscription sub = pubsub.create(SubscriptionInfo.of("news"+i.toString(),"sub"+i));

        }
         Long walltime = System.currentTimeMillis() - currentTimeStamp;
        System.out.println("Totally cost " + walltime.toString() + " ms");
    }


}
