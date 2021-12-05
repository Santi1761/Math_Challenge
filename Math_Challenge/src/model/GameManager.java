package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GameManager implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private User root;
	private User current;
	
	
	
	
	public GameManager() {
		try {
			importData();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void importData() throws FileNotFoundException, IOException, ClassNotFoundException {
		
		File source = new File("data/status.mc");
		
		if (source.exists()) {
			System.out.println("El archi8co existe");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(source));
			GameManager aux = (GameManager) ois.readObject();
			ois.close();
			this.root = aux.root;
			this.current = aux.current;
			
		}else {
			System.out.println("El archi8co no existe");
			root = null;
			current = null;
		}
		
	}
	
	public void exportData() throws FileNotFoundException, IOException {
		File source = new File("data/status.mc");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(source));
		oos.writeObject(this);
		oos.close();
	}
	
	public void close() throws FileNotFoundException, IOException {
		exportData();
	}
	
	public void startNewGame(String username) {
		current = new User(username);
	}
	
	public void addUser() {
		if (root == null ) {
			if (current != null) {
				root = current;
			}
		}else {
			root.add(current);
		}
	}
	
	public List<User> getUsersList() {
		ArrayList<User> usersList = new ArrayList<>();
		if (root != null) {
			usersList = (ArrayList<User>)root.listUsers(usersList);
			Collections.sort(usersList, new Comparator<User>() {

				@Override
				public int compare(User o1, User o2) {
					if (o1.getScore() > o2.getScore()) return -1;
					else if (o1.getScore() < o2.getScore()) return 1;
					
					return 0;
				}
			});
			
			for (int i = 0; i < usersList.size(); i++) {
				usersList.get(i).setPosition(i+1);
			}
		}
		return usersList;
	}
	
	
	 public void deleteUser() {
		 if (current != null) {
			 if (current == root) {
				 root = null;
				 current = null;
			 }else {
				 root.remove(current);
			 }
		 }
	 }





	public User getRoot() {
		return root;
	}





	public void setRoot(User root) {
		this.root = root;
	}





	public User getCurrent() {
		return current;
	}





	public void setCurrent(User current) {
		this.current = current;
	}
	
	
	
}
