package hello;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;

public class UserModel {

    private String username;
    private String password; 
    private String dbpw = "";
    private Boolean showBulletin;
    private ArrayList<String> accountTypes = new ArrayList<String>();
    private ArrayList<String> accountAmounts = new ArrayList<String>();
    
    private String  mongoUser       = "admin";
    private String  mongoPass       = "admin";
    private String  databaseName    = "sampledb";
    private String  mongoHost       = "database";
    private int     mongoPort       = 27017;


    public UserModel(){
    }


    public UserModel(String username){
        this.username = username;
        this.password = "";
    }

    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return this.username;
    }

    public String getUsername(){
        return this.username;
    }

    public ArrayList<String> getAccountTypes(){
        return this.accountTypes;
    }

    public ArrayList<String> getAccountAmounts(){
        return this.accountAmounts;
    }

    public Boolean getShowBulletin(){
        return this.showBulletin;
    }

    public Boolean validateUser(){

        // Set credentials      
        MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 
        
        //MongoClient mongoClient = new MongoClient("localhost",27017);
        
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection("users");
        collection.find(eq("username", this.username)).forEach(validatePrintBlock);
        
        Boolean match = this.dbpw.equals(this.password);
        this.dbpw = "";

        return match;
    }

    public void getUserAccounts(){

        // Set credentials      
        MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);

        // Mongo Client
        MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 
        
        //MongoClient mongoClient = new MongoClient("localhost",27017);
        
        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection("users");
        collection.find(eq("username", this.username)).forEach(getAccountsPrintBlock);
    }

    public Block<Document> validatePrintBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			// parse result obtained from collection

			String username = (String) document.get("username");
			String password = (String) document.get("password");
			dbpw = password;
		}
	};

    public Block<Document> getAccountsPrintBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			// parse result obtained from collection

			Collection<Document> accounts = (Collection) document.get("accounts");
            showBulletin = (Boolean) document.get("bulletins");
            for (int i=0; i<accounts.size(); i++){
                ArrayList accountsArray = new ArrayList<Document>(accounts);
                Document accountDoc = (Document) accountsArray.get(i);
                String type =   (String) accountDoc.get("type");
                String amount = (String) accountDoc.get("amount");
                accountTypes.add(type);
                accountAmounts.add(amount); 
            }
		}
	};
}
