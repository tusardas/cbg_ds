package com.heytusar.cbg.setup;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.heytusar.cbg.core.models.Card;
import com.heytusar.cbg.core.models.CardAttribute;
import com.heytusar.cbg.core.models.Player;
import com.heytusar.cbg.core.models.User;
import com.heytusar.cbg.core.models.UserRole;
import com.heytusar.cbg.core.models.Role;

/**
 * This program sets up card data and admin user data + two public users for cbg game
 */
public class App {
	// Create an EntityManagerFactory when you start the application.
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence
            .createEntityManagerFactory("cbgPersistenceUnit");
    
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        
        List<Card> cards = readAllCards();
        
        for(Card card : cards) {
        	System.out.println("Deleting card -----> " + card.getId());
        	delete(card.getId());
        }
        
        List<Map<String, String>> cardMaplist = readCsv();
        
        for(Map<String, String> cardMap : cardMaplist) {
        	System.out.println("cardMap -----> " + cardMap.toString());
        	create(cardMap);
        }
        
        String adminRoleName = "ADMIN";
        String publicRoleName = "PUBLIC";
        Role adminRole = getExistingOrNewRole(adminRoleName);
        Role publicRole = getExistingOrNewRole(publicRoleName);
        
        List<Role> adminRoles = new ArrayList<Role>();
        adminRoles.add(adminRole);
        adminRoles.add(publicRole);
        
        List<Role> publicRoles = new ArrayList<Role>();
        publicRoles.add(publicRole);
        
        User admin = getExistingOrNewUser("admin@cbg.com", "click123", adminRoles);
        Player adminPlayer = getExistingOrNewPlayer("admin", admin);
        updateUserWithPlayer(admin, adminPlayer);
        
        User player1 = getExistingOrNewUser("player1@cbg.com", "click123", publicRoles);
        Player player1Obj = getExistingOrNewPlayer("player1", player1);
        updateUserWithPlayer(player1, player1Obj);
        
        User player2 = getExistingOrNewUser("player2@cbg.com", "click123", publicRoles);
        Player player2Obj = getExistingOrNewPlayer("player2", player2);
        updateUserWithPlayer(player2, player2Obj);
        
        List<User> adminUserList = readUsersByRole(adminRoleName);
        System.out.println("adminUserList ----> " + adminUserList);
        for(User adminUser : adminUserList) {
        	System.out.println("This is adminUser -----> " + adminUser.getId());
        }
        
    }
    
    public static List<Map<String, String>> readCsv() {
    	List<Map<String, String>> cardMaplist = new ArrayList<Map<String, String>>();
    	List<String> attributeList = new ArrayList<String>();
        String csvFile = "D:\\cbg_workspace\\cbg\\core\\src\\main\\resources\\Cards.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            int index = 0;
            
            while ((line = br.readLine()) != null) {
                // use comma as separator
            	String[] cardArray = line.split(cvsSplitBy);
            	
            	if(index == 0) {
            		for(int j = 0; j < cardArray.length; j++) {
            			attributeList.add(cardArray[j]);
            		}
            	}
            	else {
	            	Map<String, String> cardMap = new HashMap<String, String>();
	            	for(int k = 0; k < cardArray.length; k++) {
	            		cardMap.put(attributeList.get(k), cardArray[k]);
            		}
	            	cardMaplist.add(cardMap);
            	}
            	index++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return cardMaplist;

    }
    
    
    /**
     * Read all the Cards.
     * 
     * @return a List of Cards
     */
    public static List<Card> readAllCards() {

        List<Card> cards = new ArrayList<Card>();

        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get a List of Cards
            cards = manager.createQuery("SELECT c FROM Card c", Card.class).getResultList();

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } 
        finally {
            // Close the EntityManager
            manager.close();
        }
        return cards;
    }
    
    /**
     * Create a new Card.
     * @param cardMap 
     */
    public static void create(Map<String, String> cardMap) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Create a new Student object
            Card card = new Card();
            card.setIsDeleted(false);
            List<CardAttribute> cardAttributes = new ArrayList<CardAttribute>();
            //using iterators 
            Iterator<Map.Entry<String, String>> itr = cardMap.entrySet().iterator(); 
            while(itr.hasNext()) { 
				Map.Entry<String, String> entry = itr.next(); 
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
				CardAttribute cardAttribute = new CardAttribute();
				cardAttribute.setAttributeKey(entry.getKey());
				cardAttribute.setAttributeValue(entry.getValue());
				cardAttribute.setCard(card);
				cardAttributes.add(cardAttribute);
            } 
            card.setCardAttributes(cardAttributes);
            // Save the card object
            manager.persist(card);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }
    
    /**
     * Delete the existing Card.
     * @param cardId
     */
    public static void delete(Long cardId) {
        // Create an EntityManager
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            // Get a transaction
            transaction = manager.getTransaction();
            // Begin the transaction
            transaction.begin();

            // Get the Card object
            Card card = manager.find(Card.class, cardId);

            // Delete the card
            manager.remove(card);

            // Commit the transaction
            transaction.commit();
        } catch (Exception ex) {
            // If there are any exceptions, roll back the changes
            if (transaction != null) {
                transaction.rollback();
            }
            // Print the Exception
            ex.printStackTrace();
        } finally {
            // Close the EntityManager
            manager.close();
        }
    }
    
    
    /**
     * This function inserts role meta data, or returns existing
     * @param roleName
     * @return Role
     */
    public static Role getExistingOrNewRole(String roleName) {
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    	EntityTransaction et = null;
    	Role newRole = null;
    	try {
    		et = em.getTransaction();
    		et.begin();
    		
    		List<Role> roles = em.createQuery(" SELECT role FROM Role role WHERE role.name = :roleName ", Role.class)
				.setParameter("roleName", roleName)
				.setMaxResults(1)
				.getResultList();
			System.out.println("Existing roles ---> " + roles);
    		if(roles.size() > 0) {
    			newRole = roles.get(0);
    		}
    		else {
	    		newRole = new Role();
	    		newRole.setName(roleName);
	    		em.persist(newRole);
    		}
    		et.commit();
    	}
    	catch (Exception e) {
			if(et != null) {
				et.rollback();
			}
			e.printStackTrace();
		}
    	finally {
    		em.close();
		}
    	return newRole;
    }
    
    
    /**
     * This function inserts user or get existing from db
     * @param email
     * @param password
     * @param roles
     * @return User
     */
    public static User getExistingOrNewUser(String email, String password, List<Role> roles) {
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    	EntityTransaction et = null;
    	User user = null;
    	try {
    		et = em.getTransaction();
    		et.begin();
    		
    		List<User> users = em.createQuery(" SELECT u FROM User u WHERE u.email = :email AND u.password = :password ", User.class)
				.setParameter("email", email)
				.setParameter("password", password)
				.getResultList();
    		System.out.println("Existing users ---> " + users);
    		if(users.size() > 0) {
    			user = users.get(0);
    		}
    		else {
    			System.out.println("roles ---> " + roles);
	    		user = new User();
	    		user.setEmail(email);
	    		user.setPassword(password);
	    		
	    		List<UserRole> userRoles = new ArrayList<UserRole>();
	    		UserRole userRole;
	    		for(Role role : roles) {
	    			userRole = new UserRole();
	    			userRole.setUser(user);
    				userRole.setRole(role);
	    			userRoles.add(userRole);
	    		}
	    		user.setUserRoles(userRoles);
	    		em.persist(user);
    		}
    		et.commit();
    	}
    	catch (Exception e) {
			if(et != null) {
				et.rollback();
			}
			e.printStackTrace();
		}
    	finally {
    		em.close();
		}
    	return user;
    }
    
    /**
     * This function inserts user or get existing from db
     * @param displayName
     * @param user
     * @return Player
     */
    public static Player getExistingOrNewPlayer(String displayName, User user) {
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    	EntityTransaction et = null;
    	Player player = null;
    	try {
    		et = em.getTransaction();
    		et.begin();
    		
    		List<Player> players = em.createQuery(" SELECT p FROM Player p WHERE p.user.id = :userId ", Player.class)
				.setParameter("userId", user.getId())
				.getResultList();
    		System.out.println("Existing players ---> " + players);
    		if(players.size() > 0) {
    			player = players.get(0);
    		}
    		else {
    			player = new Player();
    			player.setUser(user);
    			player.setDisplayName(displayName);
	    		
	    		em.persist(player);
    		}
    		et.commit();
    	}
    	catch (Exception e) {
			if(et != null) {
				et.rollback();
			}
			e.printStackTrace();
		}
    	finally {
    		em.close();
		}
    	return player;
    }
    
    /**
     * This function inserts user or get existing from db
     * @param displayName
     * @param user
     * @return Player
     */
    public static void updateUserWithPlayer(User user, Player player) {
    	EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
    	EntityTransaction et = null;
    	try {
    		et = em.getTransaction();
    		et.begin();
    		user.setPlayerId(player.getId());
    		em.merge(user);
    		et.commit();
    	}
    	catch (Exception e) {
			if(et != null) {
				et.rollback();
			}
			e.printStackTrace();
		}
    	finally {
    		em.close();
		}
    }
    
    /**
     * This function loads all adminUsers from db
     * @param roleName
     * @return List<User>
     */
    public static List<User> readUsersByRole(String roleName) {
    	EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
    	EntityTransaction entityTransaction = null;
    	List<User> adminUsers = new ArrayList<User>();
    	try {
    		entityTransaction = entityManager.getTransaction();
    		entityTransaction.begin();

			// Get a List of Cards
			StringBuilder query = new StringBuilder(" SELECT user FROM User user ");
			query.append(" JOIN user.userRoles userRole ");
			query.append(" JOIN userRole.role role ");
			query.append(" WHERE role.name = :adminRoleName ");
			adminUsers = entityManager.createQuery(query.toString(), User.class)
				.setParameter("adminRoleName", roleName)
				.getResultList();
			
			// Commit the transaction
			entityTransaction.commit();
    	}
    	catch (Exception e) {
    		if(entityTransaction != null) {
    			entityTransaction.rollback();
    		}
    		e.printStackTrace();
		}
    	finally {
    		entityManager.close();
    	}
    	return adminUsers;
    }
}
