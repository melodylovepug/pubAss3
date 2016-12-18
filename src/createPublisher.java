/*
  Copyright 2016, Google, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/





// [START pubsub_quickstart]
// Imports the Google Cloud client library
import com.google.cloud.pubsub.*;

public class createPublisher {
    public static void main(String[] args) throws Exception {
        // Instantiates a client
        //have 100 topic each publish 100 messages
        PubSub pubsub = PubSubOptions.getDefaultInstance().getService();
        Long currentTimeStamp = System.currentTimeMillis();
        for (Integer x = 0; x < Integer.parseInt(args[0]); x++) {

            String topicName = "news" + x.toString();

            // Creates the new topic
            Topic topic = pubsub.create(TopicInfo.of(topicName));
            for (Integer y = 0; y < Integer.parseInt(args[1]); y++) {
                Message mes = Message.of("news" + x.toString() + "id" + y.toString());
                topic.publishAsync(mes);
            }
          //  System.out.printf("Topic %s created.%n", topic.getName());
        }
        Long walltime = System.currentTimeMillis() - currentTimeStamp;
        System.out.println("Totally cost " + walltime.toString() + " ms");

    }
}
// [END pubsub_quickstart]
