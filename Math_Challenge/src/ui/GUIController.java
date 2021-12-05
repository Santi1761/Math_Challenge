package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;

import javafx.stage.Stage;
import model.GameManager;
import model.Operation;
import model.User;
import threads.BarThread;
import threads.Timer;


public class GUIController {
	
		private Stage st;
		private Stage godParent;
		private Stage mainStage;
		
		Timer timer;
		BarThread barT;
		
		private Operation currentOperation;
	
	 	@FXML
	    private TableView<User> tv;
	 	
	 	@FXML
	    private Label seconds;

	    @FXML
	    private TableColumn<User, Integer> posTc;

	    @FXML
	    private TableColumn<User, String> nameTc;

	    @FXML
	    private TableColumn<User, Integer> scoreTc;

	    @FXML
	    private Label msgLb;
	    
	    @FXML
	    private Button opt1;

	    @FXML
	    private Button opt2;

	    @FXML
	    private Button opt3;

	    @FXML
	    private Button opt4;

	    @FXML
	    private Label n1;

	    @FXML
	    private Label sign;

	    @FXML
	    private Label n2;

	    @FXML
	    private Label username;

	    @FXML
	    private Rectangle timeBar;
	    
	    GameManager manager;
	    	        
	    
	    public GUIController() {
	    	
	    	manager = new GameManager();	    	
	    }
	    
	    
	    public  void initialize() {
	    	
	    	initializeLeaderBoard();
	    }
	    
	    
	    public void initializeLeaderBoard() {
	    	
	    	if (manager.getRoot() != null) {
	    		
	    		setLeaderBoardTV();	    		
	    	}
	    	if (manager.getCurrent() == null) {
	    		
	    		msgLb.setText("");
	    	}
	    }
	    

	    private void setLeaderBoardTV() {
	    	
	    	ArrayList<User> a = (ArrayList<User>) manager.getUsersList();
	    	ArrayList<User> b = new ArrayList<>();
	    	
	    	if (a.size() < 5) {
	    		
	    		b = a;
	    	}else {
	    		
	    		for (int i = 0; i < 5; i++) {
	    			
		    		a.add(a.get(i));
		    	}
	    	}
	    	
	    	
	    	ObservableList<User> observableList;
	    	observableList = FXCollections.observableArrayList(b);
	    	
	    	tv.setItems(observableList);
	    	nameTc.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
	    	scoreTc.setCellValueFactory(new PropertyValueFactory<User,Integer>("score"));
	    	posTc.setCellValueFactory(new PropertyValueFactory<User,Integer>("position"));					
		}

	    
		@FXML
	    private TextField usernameTextField;

		@FXML
	    public void go(ActionEvent event) throws IOException {			
			
			String res = usernameTextField.getText();
			
			if (!res.isEmpty()) {
				
				manager.setCurrent(new User(res));
				mainStage  = new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPane.fxml"));
				loader.setController(this);
				Parent p = loader.load();
				mainStage.setScene(new Scene(p));
				mainStage.show();
				initializeMainPane(res);
				st.close();
				manager.startNewGame(res);
				timer = new Timer(10,this);
				timer.setDaemon(true);
				barT = new BarThread(this);
				barT.setDaemon(true);
				timer.start();
				barT.start();
				}else {
					
					Alert a = new Alert(AlertType.ERROR, "Username textfield must be not empty!", ButtonType.OK);
					a.setTitle("What?!");
					a.showAndWait();
			}			
	    }
		
		
		public void initializeMainPane(String username) {
			
			this.username.setText(username);
			createOperaion();			
		}
		
		
		public void createOperaion() {
			
			currentOperation = new Operation();
			this.n1.setText(currentOperation.getN1()+"");
			this.n2.setText(currentOperation.getN2()+"");
			this.sign.setText(currentOperation.getSign()+"");
			
			int[] opts = currentOperation.createOptions(currentOperation.getResult());
			opt1.setText(opts[0]+"");
			opt2.setText(opts[1]+"");
			opt3.setText(opts[2]+"");
			opt4.setText(opts[3]+"");
		}

	    
		public void selection(int a) {
			
			if (currentOperation.getResult() == a) manager.getCurrent().answer(true);
			else manager.getCurrent().answer(false);
			createOperaion();
		}

		
	    @FXML
	    public void a(ActionEvent event) {
	    	
	    	selection(Integer.parseInt(opt1.getText()));
	    }

	    
	    @FXML
	    public void b(ActionEvent event) {
	    	
	    	selection(Integer.parseInt(opt2.getText()));
	    }
	    

	    @FXML
	    public void c(ActionEvent event) {
	    	
	    	selection(Integer.parseInt(opt3.getText()));
	    }
	    

	    @FXML
	    public void d(ActionEvent event) {
	    	
	    	selection(Integer.parseInt(opt4.getText()));
	    }
     
	    
	    @FXML
	    public void exitGame(ActionEvent event) throws FileNotFoundException, IOException {
	    	
	    	manager.exportData();
	    	godParent.close();
	    }

	    
	    @FXML
	    public void startNewGame(ActionEvent event) throws IOException {
	    	
	    	manager.exportData();
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("UserData.fxml"));
	    	loader.setController(this);
	    	Parent parent = loader.load();
	    	 st = new Stage();
	    	st.setScene(new Scene(parent));
	    	st.showAndWait();	    	
	    }
	    

		public void updateSeconds() {
			
			int s = Integer.parseInt(seconds.getText());
			seconds.setText((s-1)+"");			
		}
		

		public void closeGame() {
			
			mainStage.close();
			manager.addUser();
			initializeLeaderBoard();
			msgLb.setText("Your position is: "+ manager.getCurrent().getPosition());
		}
		

		public void updateBar(double i) {
			
			this.timeBar.setWidth(i);		
		}
		
		
		@FXML
	    public void deleteCurrentScore(ActionEvent event) {
			
			manager.deleteUser();
			msgLb.setText("");
			initializeLeaderBoard();
	    }
		
		
		public void setGod(Stage s) {
			
			godParent = s;
		}		
}
