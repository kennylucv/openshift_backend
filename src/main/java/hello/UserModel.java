package hello;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.*;
import static com.mongodb.client.model.Filters.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserModel {

    private String username;
    private String password; 
    private String dbpw = "";
    private Boolean showBulletin;
    private ArrayList<String> accountTypes = new ArrayList<String>();
    private ArrayList<String> accountAmounts = new ArrayList<String>();
    
    private String  mongoUser       = "";
    private String  mongoPass       = "";
    private String  databaseName    = "sampledb";
    private String  mongoHost       = "";
    private int     mongoPort       = 27017;

    private Properties prop = new Properties();
	private InputStream input = null;

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

        loadConfig();

        // Set credentials      
        MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
        ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
        MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential));         
        
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

    public void loadConfig(){
        try {
            input = new FileInputStream("/config/config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            this.mongoHost = prop.getProperty("dbhostname");
            this.mongoUser = prop.getProperty("dbuser");
            this.mongoPass = prop.getProperty("dbpassword");
        } 
        catch (IOException ex) {
           ex.printStackTrace();
        } 
        finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
