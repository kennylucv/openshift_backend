package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;


@RestController
public class GreetingController {

    // @Autowired
    // private final UserRepository userRepository;

    // GreetingController(UserRepository userRepository){
    //     //this.userRepository = userRepository;
    // }

    @RequestMapping("/greeting")
    public ResponseEntity greeting(@RequestParam(value="name", defaultValue="World") String name) {
        // return new Greeting(counter.incrementAndGet(),
        //                     String.format(template, name));
         return new ResponseEntity("greetings",HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/validate", method=RequestMethod.POST)
    public ResponseEntity validate(@RequestBody UserModel login){//, @RequestBody String password) {
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (login.validateUser()){
            return new ResponseEntity("",responseHeaders,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin
    @RequestMapping(value = "api/{userId}", method=RequestMethod.GET)
    public ResponseEntity readBookmarks(@PathVariable String userId){//, @RequestBody String password) {
        UserModel user = new UserModel(userId);
        user.getUserAccounts();
        ArrayList<String> userAccountTypes = user.getAccountTypes();
        ArrayList<String> userAccountAmounts = user.getAccountAmounts();
        JSONObject jResponse = new JSONObject();
        jResponse.put("bulletin", user.getShowBulletin());
        ArrayList<JSONObject> accounts = new ArrayList<JSONObject>();
        for (int i=0; i<userAccountTypes.size(); i++){
            JSONObject account = new JSONObject();
            account.put("type", userAccountTypes.get(i));
            account.put("amount", userAccountAmounts.get(i));
            accounts.add(account);
        }
        jResponse.put("accounts", accounts);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.set("Access-Control-Max-Age", "3600");
        responseHeaders.set("Access-Control-Allow-Headers", "x-requested-with");

        return new ResponseEntity(jResponse,responseHeaders,HttpStatus.OK);
    }

}
