package utilities.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static utilities.account.AccountTest.*;

public class InitConnection {
	
	final static Logger logger = LogManager.getLogger(InitConnection.class);
	
    public Connection createConnection() throws SQLException {
        String connectionUrl = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s&loginTimeout=30".formatted(DB_HOST, DB_PORT, DB_DATABASE, DB_USER, DB_PASS);
        return DriverManager.getConnection(connectionUrl);
    }
	public Connection createConnection(String host, String user, String pass) throws SQLException {
		String connectionUrl = "jdbc:postgresql://%s:%s/%s?user=%s&password=%s&loginTimeout=30".formatted(host,DB_PORT, DB_DATABASE, user, pass);
		return DriverManager.getConnection(connectionUrl);
	}
	
    public String getActivationKey(String username) throws SQLException {
    	Connection connection = null;
	    ResultSet resultSet = null;
	    String key = null;
	    
	    //Sometimes it takes longer for the activation code to be generated
	    for (int i=0; i<3; i++) {
		    try {
			    connection = createConnection();
			    
		        String query = "select activation_key from \"gateway-services\".jhi_user ju where login = '%s'".formatted(username.toLowerCase());
		        resultSet = connection.prepareStatement(query).executeQuery();
		        
		        if (resultSet.next()) {
		            key = resultSet.getString("activation_key");
		        }
		        logger.info("Activation key retrieved for '%s': ".formatted(username) + key);
		    } catch (SQLException e) {
		    	throw e;
		    } finally {
		        // Close the resources in reverse order
		        if (resultSet != null) {
		            resultSet.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
		    }
		    if (key != null) break;
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        return key;
    }     
    
    public String getResetKey(String username) throws SQLException {
    	Connection connection = null;
	    ResultSet resultSet = null;
	    String key = null;
	    
	    //Sometimes it takes longer for the activation code to be generated
	    for (int i=0; i<3; i++) {
		    try {
			    connection = createConnection();
			    
		        String query = "select reset_key from \"gateway-services\".jhi_user ju where login = '%s'".formatted(username.toLowerCase());
		        resultSet = connection.prepareStatement(query).executeQuery();
		        
		        if (resultSet.next()) {
		            key = resultSet.getString("reset_key");
		        }
		        logger.info("Reset key retrieved for '%s': ".formatted(username) + key);
		    } catch (SQLException e) {
		    	throw e;
		    } finally {
		        // Close the resources in reverse order
		        if (resultSet != null) {
		            resultSet.close();
		        }
		        if (connection != null) {
		            connection.close();
		        }
		    }
		    if (key != null) break;
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
        return key;
    }     
    
    public String getStoreURL(String storeName) throws SQLException {
    	String query = "SELECT url FROM \"store-services\".store x WHERE name = '%s'".formatted(storeName);
    	ResultSet resultSet = createConnection().prepareStatement(query).executeQuery();
    	String URL = null;
    	while (resultSet.next()) {
    		URL = resultSet.getString("url");
    	}
    	logger.debug("Store to get URL from: " + storeName); 
    	logger.info("Store URL retrieved: " + URL); 
    	return URL;
    }     
    
    public String getStoreDomain(String storeName) throws SQLException {
    	String query = "SELECT * FROM \"store-services\".store x WHERE name = '%s'".formatted(storeName);
    	ResultSet resultSet = createConnection().prepareStatement(query).executeQuery();
    	String domain = null;
    	while (resultSet.next()) {
    		domain = resultSet.getString("domain");
    	}
    	logger.debug("Store to get domain from: " + storeName); 
    	logger.info("Store domain retrieved: " + domain); 
    	return domain;
    }     
    
    public String getStoreGiftCode(String storeName) throws SQLException {
    	String query = "SELECT * FROM \"store-services\".store x WHERE name = '%s'".formatted(storeName);
    	ResultSet resultSet = createConnection().prepareStatement(query).executeQuery();
    	String domain = null;
    	while (resultSet.next()) {
    		domain = resultSet.getString("gift_code");
    	}
    	logger.debug("Store to get store gift code from: " + storeName); 
    	logger.info("Store gift code retrieved: " + domain); 
    	return domain;
    }     

    public String getCountryCode(String country) throws SQLException {
    	String query = "SELECT code FROM \"catalog-services\".country x WHERE out_country = '%s'".formatted(country.replace("'", "''"));
    	ResultSet resultSet = createConnection(DB_HOST_CATALOG, DB_USER_CATALOG, DB_PASS_CATALOG).prepareStatement(query).executeQuery();
    	String code = null;
    	while (resultSet.next()) {
    		code = resultSet.getString("code");
    	}
    	logger.info("Country code retrieved: " + code); 
    	return code;
    }         
    /**
     * Retrieves location code of the user with the specific login info
     * @param username
     * @return location code of the user
     * @throws SQLException
     */
    public String getUserLocationCode(String username) throws SQLException {
    	String query = "select location_code from \"gateway-services\".jhi_user ju where login = '%s'".formatted(username.toLowerCase());
    	ResultSet resultSet = createConnection().prepareStatement(query).executeQuery();
    	String code = null;
    	while (resultSet.next()) {
    		code = resultSet.getString("location_code");
    	}
    	logger.info("Retrieved location code of '%s': %s".formatted(username, code)); 
    	return code;
    }         
    
    @Test
    public void test() throws SQLException {
        Connection connection = createConnection();
        String query = "select activation_key from \"gateway-services\".jhi_user ju where login = '+500:8942531099'";
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("activation_key"));
        }
    }

}
