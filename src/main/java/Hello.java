import com.launchdarkly.client.LDClient;
import com.launchdarkly.client.LDUser;
import com.launchdarkly.client.LDUser.Builder;

import java.util.*;

import java.io.IOException;

import static java.util.Collections.singletonList;

public class Hello {

 public static void main(String... args) throws IOException {
   LDClient client = new LDClient("YOUR_SDK_KEY");

   Builder userBuilder = new LDUser.Builder("bob@example.com")
                           .firstName("Bob")
                           .lastName("Loblaw")
                           .customString("groups", singletonList("beta_testers"));

   HashMap<String, List<String>> mapOfCustomAttributes = new HashMap<String, List<String>>();
   mapOfCustomAttributes.put("groups", Arrays.asList("mbs_testers", "testing"));
   mapOfCustomAttributes.put("mbs_traders", Arrays.asList("trader1", "trader2","trader3"));
   mapOfCustomAttributes.put("mbs_special_users", Arrays.asList("mbs_spec_1"));

   for (Map.Entry<String, List<String>> entry : mapOfCustomAttributes.entrySet()) {
     String key = entry.getKey();
     List<String> list = entry.getValue();
     System.out.println("Custom attribute Key: " + key);
     System.out.println("Custom attribute List: " + list);
     userBuilder.customString(key, list);
   }

   LDUser user = userBuilder.build();

   boolean showFeature = client.boolVariation("YOUR_FEATURE_KEY", user, false);

   if (showFeature) {
    System.out.println("Showing your feature");
   } else {
    System.out.println("Not showing your feature");
   }

   client.flush();
   client.close();
 }
}
