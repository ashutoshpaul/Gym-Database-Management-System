import javafx.application.Application;

import java.io.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

import java.util.regex.Pattern;

import org.controlsfx.control.textfield.TextFields;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.*;
import javafx.scene.effect.*;
import javafx.scene.control.Alert.AlertType;

import java.text.DateFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.geometry.Orientation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;

import java.awt.Desktop;
import java.net.URI;


public class GymDriver extends Application{
	
	int width = 900;
	int height = 540;
	
	//menubar
	Menu fileMenu = new Menu("File");
	MenuItem homeMenuItem = new MenuItem("Go to Home");
	MenuItem gymDetailsMenuItem = new MenuItem("Gym Details");
	MenuItem exitMenuItem = new MenuItem("Exit");
	
	Menu newMenu = new Menu("New");
	MenuItem newMemberMenuItem = new MenuItem("Enroll New Member");
	MenuItem newTrainerMenuItem = new MenuItem("Add New Trainer");
	MenuItem newEquipmentMenuItem = new MenuItem("Install New Equipment");
	MenuItem newMembershipMenuItem = new MenuItem("Create New Membership");
	
	Menu viewMenu = new Menu("View");
	MenuItem viewMemberMenuItem = new MenuItem("View Members Enrolled");
	MenuItem viewTrainerMenuItem = new MenuItem("View Trainers");
	MenuItem viewEquipmentMenuItem = new MenuItem("View Equipments Present");
	
	Menu helpMenu = new Menu("Help");
	MenuItem aboutAppMenuItem = new MenuItem("About App");
	MenuItem howToUseMenuItem = new MenuItem("How to Use");
	MenuItem aboutTheDevMenuItem = new MenuItem("About the Developers");
	
	MenuBar menuBar = new MenuBar();
	
	//settingsImage
	Button settings = new Button();
	FileInputStream inputSettingsImage;
	Image settingsImage;
	//searchImage
	Button searchIcon = new Button();
	FileInputStream inputSearchImage;
	Image searchImage;
	
	CustomTextField search = (CustomTextField) TextFields.createClearableTextField();
	
	//database
	static Connection connection = null;
	static String databaseName = "";
	static String url = "jdbc:mysql://localhost:3307/" + databaseName;
	
	static String username = "codebuddy";
	static String password = "dbmsproject";
	
	//settings
	Label settingsLabel = new Label("Settings");
	String space = "\t\t\t\t\t\t\t\t\t\t\t\t\t";
	Label headingUnderline = new Label(space);
	Label adminLabel = new Label("Admin Name:");
	TextField admin = new TextField();
	Label phoneLabel = new Label("Admin Phone Number:");
	TextField phone = new TextField();
	TextField altPhone = new TextField();
	Label emailLabel = new Label("Admin Email ID:");
	TextField email = new TextField();
	CheckBox gymDetailsCB = new CheckBox("Edit Gym Details");
	String space2 = "\t\t\t\t\t\t\t\t\t" + "  ";
	Label gymDetailsUnderline = new Label(space2);
	ObservableList<String> prefix = FXCollections.observableArrayList("Mr." , "Mrs." , "Miss");
	ComboBox<String> prefixOwnerName = new ComboBox<String>(prefix);
	TextField ownerName = new TextField();
	TextField gymName = new TextField();
	TextField gymPhone = new TextField();
	TextField gymAltPhone = new TextField();
	TextField gymAddress = new TextField();
	TextField ref = new TextField();
	TextField gymPin = new TextField();
	Label invalid = new Label("Incorrect data entered!");
	ObservableList<String> hh = FXCollections.observableArrayList("1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "10" , "11" , "12");
	ObservableList<String> mm = FXCollections.observableArrayList("00" , "15" , "30" , "45");
	ObservableList<String> meridian = FXCollections.observableArrayList("AM" , "PM");
	ComboBox<String> openHH = new ComboBox<String>(hh);
	ComboBox<String> openMM = new ComboBox<String>(mm);
	ComboBox<String> openMeridian = new ComboBox<String>(meridian);
	ComboBox<String> closeHH = new ComboBox<String>(hh);
	ComboBox<String> closeMM = new ComboBox<String>(mm);
	ComboBox<String> closeMeridian = new ComboBox<String>(meridian);
	Label openTime = new Label("Opens at");
	Label closeTime = new Label("Closes at");
	
	//settings string data
	String enteredAdmin = "";
	String enteredPhone = "";
	String enteredAltPhone = "";
	String enteredEmail = "";
	String enteredOwnerName = "";
	String enteredGymName = "";
	String enteredGymPhone = "";
	String enteredGymAltPhone = "";
	String enteredGymAddress = "";
	String enteredGymPin = "";
	String enteredPrefixOwnerName = "Mr.";
	String enteredOpenHH = "1";
	String enteredOpenMM = "00";
	String enteredOpenMeridian = "AM";
	String enteredCloseHH = "1";
	String enteredCloseMM = "00";
	String enteredCloseMeridian = "AM";
	
	//backButton
	Button back = new Button();
	FileInputStream inputBackImage;
	Image backImage;
	
	//table
	TableView membersTable = new TableView();
	TableColumn<String , MembersListClass> column1 = new TableColumn<>("ID");
	TableColumn<String , MembersListClass> column2 = new TableColumn<>("Name");
	TableColumn<String , MembersListClass> column3 = new TableColumn<>("Membership");
	
	TableView trainersTable = new TableView();
	TableColumn<String , TrainersListClass> trainersColumn1 = new TableColumn<>("ID");
	TableColumn<String , TrainersListClass> trainersColumn2 = new TableColumn<>("Name");
	
	TableView equipmentsTable = new TableView();
	TableColumn<String , EquipmentsListClass> equipmentsColumn1 = new TableColumn<>("Name");
	TableColumn<String , EquipmentsListClass> equipmentsColumn2 = new TableColumn<>("No. of Unit(s)");
	TableColumn<String , EquipmentsListClass> equipmentsColumn3 = new TableColumn<>("Cost (in Rs.)");
	TableColumn<String , EquipmentsListClass> equipmentsColumn4 = new TableColumn<>("Availability");
	TableColumn<String , EquipmentsListClass> equipmentsColumn5 = new TableColumn<>("Installation Date");
	
	TableView membershipsTable = new TableView();
	TableColumn<String , MembershipsListClass> membershipsColumn1 = new TableColumn<>("Name");
	TableColumn<String , MembershipsListClass> membershipsColumn2 = new TableColumn<>("Duration");
	TableColumn<String , MembershipsListClass> membershipsColumn3 = new TableColumn<>("Amount (in Rs.)");
	
	TableView membersTableX = new TableView();
	TableColumn<String , MembersListClass> column1X = new TableColumn<>("ID");
	TableColumn<String , MembersListClass> column2X = new TableColumn<>("Name");
	
	TableView trainersTableX = new TableView();
	TableColumn<String , MembersListClass> columnT1X = new TableColumn<>("ID");
	TableColumn<String , MembersListClass> columnT2X = new TableColumn<>("Name");
	
	TableView feeTable = new TableView();
	TableColumn<String , FeeListClass> feeColumn1 = new TableColumn<>("ID");
	TableColumn<String , FeeListClass> feeColumn2 = new TableColumn<>("Name");
	TableColumn<String , FeeListClass> feeColumn3 = new TableColumn<>("Membership");
	TableColumn<String , FeeListClass> feeColumn4 = new TableColumn<>("Total Amount");
	TableColumn<String , FeeListClass> feeColumn5 = new TableColumn<>("Pending Amount");
	TableColumn<String , FeeListClass> feeColumn6 = new TableColumn<>("Receipt Number");
	TableColumn<String , FeeListClass> feeColumn7 = new TableColumn<>("Transaction ID");
	TableColumn<String , FeeListClass> feeColumn8 = new TableColumn<>("Date of Payment");
	TableColumn<String , FeeListClass> feeColumn9 = new TableColumn<>("Amount Payed");
	
	TableView transactionHistoryTable = new TableView();
	TableColumn<String , TransactionHistoryTable> thColumn1 = new TableColumn<>("Date of Payment");
	TableColumn<String , TransactionHistoryTable> thColumn2 = new TableColumn<>("Amount Payed (in Rs.)");
	TableColumn<String , TransactionHistoryTable> thColumn3 = new TableColumn<>("Receipt Number");
	TableColumn<String , TransactionHistoryTable> thColumn4 = new TableColumn<>("Transaction ID");
	
	TableView exTrainersTable = new TableView();
	TableColumn<String , ExTrainerListClass> exColumn1 = new TableColumn<>("Name");
	TableColumn<String , ExTrainerListClass> exColumn2 = new TableColumn<>("Address");
	
	TableView filterMemberTable = new TableView();
	TableColumn<String , FilterMemberClass> filterColumn1 = new TableColumn<>("ID");
	TableColumn<String , FilterMemberClass> filterColumn2 = new TableColumn<>("Name");
	TableColumn<String , FilterMemberClass> filterColumn3 = new TableColumn<>("Contact Number");
	TableColumn<String , FilterMemberClass> filterColumn4 = new TableColumn<>("Amount");
	
	//addNewMember(Stage s)
	TextField newID = new TextField();
	TextField newName = new TextField();
	ObservableList<String> sex = FXCollections.observableArrayList("Male" , "Female" , "Non-Binary");
	ComboBox<String> selectedSex = new ComboBox<String>(sex);
	Label weightLabel = new Label("Weight:");
	TextField newWeight = new TextField();
	Label kgLabel = new Label("Kg.");
	DatePicker newDOB = new DatePicker();
	Label ageLabel = new Label("Age:");
	Label newAge = new Label("Null");
	TextField newPhoneNumber = new TextField();
	TextField newAltPhoneNumber = new TextField();
	TextField newEmail = new TextField();
	TextField newOccupation = new TextField();
	TextField newAddress = new TextField();
	TextField newNationality = new TextField();
	TextField newPin = new TextField();
	TextField newCity = new TextField();
	Label invalidWizard = new Label("Enter minimum data");
	Label prefixID = new Label("MBR");
	
	//addNewMember2(Stage s)
	ObservableList<String> savedMemberships = FXCollections.observableArrayList();
	ComboBox<String> availableMemberships = new ComboBox<String>(savedMemberships);
	String aboutContext = "Null";
	TextArea aboutDescription = new TextArea();
	Label savedDuration = new Label("Null");
	Label savedGymKit = new Label("Not Included");
	Label savedAmount = new Label("Rs. 0.00");
	
	//addNewMember3(Stage s)
	TextArea userNotes = new TextArea();
	
	//addNewTrainer(Stage s)
	TextField newTrainerID = new TextField();
	TextField newTrainerName = new TextField();
	ComboBox<String> selectedTrainerSex = new ComboBox<String>(sex);
	Label salaryLabel = new Label("Salary:");
	TextField newTrainerSalary = new TextField();
	Label monthLabel = new Label("/ month");
	DatePicker newTrainerDOB = new DatePicker();
	Label ageTrainerLabel = new Label("Age:");
	Label newTrainerAge = new Label("Null");
	TextField newTrainerPhoneNumber = new TextField();
	TextField newTrainerAltPhoneNumber = new TextField();
	TextField newTrainerEmail = new TextField();
	TextField newTrainerOccupation = new TextField();
	TextField newTrainerAddress = new TextField();
	TextField newTrainerNationality = new TextField();
	TextField newTrainerPin = new TextField();
	TextField newTrainerCity = new TextField();
	Label invalidTrainerWizard = new Label("Enter minimum data");
	Label dojLabel = new Label("Date of Joining:");
	Label membershipLabel = new Label("Membership alloted:");
	ObservableList<String> membershipTrainer = FXCollections.observableArrayList();
	ComboBox<String> membershipToTrainer = new ComboBox<String>(membershipTrainer);
	
	ObservableList<String> designationsAvailable = FXCollections.observableArrayList("Jr. Trainer" , "Sr. Trainer");
	ComboBox<String> newTrainerDesignation = new ComboBox<String>(designationsAvailable);
	
	ObservableList<String> kitOptionsAvailable = FXCollections.observableArrayList("Included" , "Not Included");
	ComboBox<String> newGymKit = new ComboBox<String>(kitOptionsAvailable);
	
	//Tooltip
	String toolStyle = "-fx-background-color: white;"
						+ "-fx-text-fill: black;"
						+ "-fx-font-family: 'Arial';"
						+ "-fx-font-size: 11px;";
	//tooltips
	Tooltip toolSettings = new Tooltip("Settings");
	Tooltip toolSearch = new Tooltip("Search");
	Tooltip toolBack = new Tooltip("Go Back");
	
	//copyright
	Label copyright = new Label();
	
	public GymDriver() throws Exception{
		copyright.setText((char)169 + " All Rights Reserved by Ashutosh Paul and team.");
		copyright.setStyle("-fx-text-fill: '#4d4d4d';");
		
		//table
		membersTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		column1.setCellValueFactory(new PropertyValueFactory<>("memberID"));
		column1.setMinWidth(100.0);
		column1.setMaxWidth(100.0);
		column2.setCellValueFactory(new PropertyValueFactory<>("memberName"));
		column2.setMinWidth(400.0);
		column2.setMaxWidth(400.0);
		column3.setCellValueFactory(new PropertyValueFactory<>("memberMembership"));
		column3.setMinWidth(195.0);
		column3.setMaxWidth(195.0);
		membersTable.getColumns().addAll(column1 , column2 , column3);
		
		trainersTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		trainersColumn1.setCellValueFactory(new PropertyValueFactory<>("trainerID"));
		trainersColumn1.setMinWidth(100.0);
		trainersColumn1.setMaxWidth(100.0);
		trainersColumn2.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
		trainersColumn2.setMinWidth(590.0);
		trainersColumn2.setMaxWidth(590.0);
		trainersTable.getColumns().addAll(trainersColumn1 , trainersColumn2);
		
		equipmentsTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		equipmentsColumn1.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
		equipmentsColumn1.setMinWidth(260.0);
		equipmentsColumn1.setMaxWidth(400.0);
		equipmentsColumn2.setCellValueFactory(new PropertyValueFactory<>("equipmentNum"));
		equipmentsColumn2.setMinWidth(160.0);
		equipmentsColumn2.setMaxWidth(160.0);
		equipmentsColumn3.setCellValueFactory(new PropertyValueFactory<>("equipmentCost"));
		equipmentsColumn3.setMinWidth(140.0);
		equipmentsColumn3.setMaxWidth(140.0);
		equipmentsColumn4.setCellValueFactory(new PropertyValueFactory<>("equipmentAvailability"));
		equipmentsColumn4.setMinWidth(160.0);
		equipmentsColumn4.setMaxWidth(160.0);
		equipmentsColumn5.setCellValueFactory(new PropertyValueFactory<>("equipmentDOI"));
		equipmentsColumn5.setMinWidth(0.0);
		equipmentsColumn5.setMaxWidth(0.0);
		equipmentsColumn5.setVisible(false);
		
		equipmentsTable.getColumns().addAll(equipmentsColumn1 , equipmentsColumn2 , equipmentsColumn3 , equipmentsColumn4 , equipmentsColumn5);
		
		membershipsTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		membershipsColumn1.setCellValueFactory(new PropertyValueFactory<>("membershipName"));
		membershipsColumn1.setMinWidth(385.0);
		membershipsColumn1.setMaxWidth(385.0);
		membershipsColumn2.setCellValueFactory(new PropertyValueFactory<>("membershipDuration"));
		membershipsColumn2.setMinWidth(200.0);
		membershipsColumn2.setMaxWidth(200.0);
		membershipsColumn3.setCellValueFactory(new PropertyValueFactory<>("membershipAmount"));
		membershipsColumn3.setMinWidth(140.0);
		membershipsColumn3.setMaxWidth(140.0);
		
		membershipsTable.getColumns().addAll(membershipsColumn1 , membershipsColumn2 , membershipsColumn3);
		
		membersTableX.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		column1X.setCellValueFactory(new PropertyValueFactory<>("memberID"));
		column1X.setMinWidth(90.0);
		column1X.setMaxWidth(90.0);
		column2X.setCellValueFactory(new PropertyValueFactory<>("memberName"));
		column2X.setMinWidth(190.0);
		column2X.setMaxWidth(190.0);
		membersTableX.getColumns().addAll(column1X , column2X);
		
		trainersTableX.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		columnT1X.setCellValueFactory(new PropertyValueFactory<>("trainerID"));
		columnT1X.setMinWidth(90.0);
		columnT1X.setMaxWidth(90.0);
		columnT2X.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
		columnT2X.setMinWidth(190.0);
		columnT2X.setMaxWidth(190.0);
		trainersTableX.getColumns().addAll(columnT1X , columnT2X);
		
		feeTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		feeColumn1.setCellValueFactory(new PropertyValueFactory<>("ID"));
		feeColumn1.setMinWidth(85.0);
		feeColumn1.setMaxWidth(85.0);
		feeColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
		feeColumn2.setMinWidth(180.0);
		feeColumn2.setMaxWidth(180.0);
		feeColumn3.setCellValueFactory(new PropertyValueFactory<>("membership"));
		feeColumn3.setMinWidth(160.0);
		feeColumn3.setMaxWidth(160.0);
		feeColumn4.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
		feeColumn4.setMinWidth(150.0);
		feeColumn4.setMaxWidth(150.0);
		feeColumn5.setCellValueFactory(new PropertyValueFactory<>("pendingAmount"));
		feeColumn5.setMinWidth(150.0);
		feeColumn5.setMaxWidth(150.0);
		
		feeTable.getColumns().addAll(feeColumn1 , feeColumn2 , feeColumn3 , feeColumn4 , feeColumn5);
		
		transactionHistoryTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		thColumn1.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));
		thColumn1.setMinWidth(130.0);
		thColumn1.setMaxWidth(130.0);
		thColumn2.setCellValueFactory(new PropertyValueFactory<>("amountPayed"));
		thColumn2.setMinWidth(160.0);
		thColumn2.setMaxWidth(160.0);
		thColumn3.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
		thColumn3.setMinWidth(190.0);
		thColumn3.setMaxWidth(190.0);
		thColumn4.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
		thColumn4.setMinWidth(190.0);
		thColumn4.setMaxWidth(190.0);
		
		transactionHistoryTable.getColumns().addAll(thColumn1 , thColumn2 , thColumn3 , thColumn4);
		
		exTrainersTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		exColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
		exColumn1.setMinWidth(230.0);
		exColumn1.setMaxWidth(300.0);
		exColumn2.setCellValueFactory(new PropertyValueFactory<>("address"));
		exColumn2.setMinWidth(300.0);
		exColumn2.setMaxWidth(600.0);
		
		exTrainersTable.getColumns().addAll(exColumn1 , exColumn2);
		
		filterMemberTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		filterColumn1.setCellValueFactory(new PropertyValueFactory<>("memberID"));
		filterColumn1.setMinWidth(125.0);
		filterColumn1.setMaxWidth(125.0);
		filterColumn2.setCellValueFactory(new PropertyValueFactory<>("memberName"));
		filterColumn2.setMinWidth(210.0);
		filterColumn2.setMaxWidth(210.0);
		filterColumn3.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		filterColumn3.setMinWidth(160.0);
		filterColumn3.setMaxWidth(160.0);
		filterColumn4.setCellValueFactory(new PropertyValueFactory<>("amount"));
		filterColumn4.setMinWidth(190.0);
		filterColumn4.setMaxWidth(190.0);
		
		filterMemberTable.getColumns().addAll(filterColumn1 , filterColumn2 , filterColumn3 , filterColumn4);
		
		//settings
		inputSettingsImage = new FileInputStream("resources/logos/Settings Logo.png");
		settingsImage = new Image(inputSettingsImage);
		settings.setGraphic(new ImageView(settingsImage));
		settings.setMaxHeight(20.0);
		settings.setMaxWidth(20.0);
		settings.setStyle("-fx-background-color: '#ccffe6';");
		
		//searchIcon
		inputSearchImage = new FileInputStream("resources/logos/search.png");
		searchImage = new Image(inputSearchImage);
		searchIcon.setGraphic(new ImageView(searchImage));
		searchIcon.setMaxHeight(20.0);
		searchIcon.setMaxWidth(20.0);
		searchIcon.setStyle("-fx-background-color: '#ccffe6';");
		
		//tooltips
		toolSettings.setStyle(toolStyle);
		toolSearch.setStyle(toolStyle);
		toolBack.setStyle(toolStyle);
		
		settings.setTooltip(toolSettings);
		searchIcon.setTooltip(toolSearch);
		back.setTooltip(toolBack);
		
		search.setPromptText("Search Member, Trainer, Receipt, etc.");
		//backButton
		try {
			inputBackImage = new FileInputStream("resources/logos/back.png");
			backImage = new Image(inputBackImage);
			back.setGraphic(new ImageView(backImage));
			back.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		
	}
	
	public void connectingToDB() throws InstantiationException , IllegalAccessException , ClassNotFoundException , SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(url , username , password);
	}
	
	public void createMenuBar() {
		fileMenu.getItems().addAll(homeMenuItem , gymDetailsMenuItem , exitMenuItem);
		newMenu.getItems().addAll(newMemberMenuItem , newTrainerMenuItem , newEquipmentMenuItem , newMembershipMenuItem);
		viewMenu.getItems().addAll(viewMemberMenuItem , viewTrainerMenuItem , viewEquipmentMenuItem);
		helpMenu.getItems().addAll(aboutAppMenuItem , howToUseMenuItem , aboutTheDevMenuItem);
		
		menuBar.getMenus().addAll(fileMenu , newMenu , viewMenu , helpMenu);
		
		menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
		menuBar.setMinWidth(width);
	}
	
	public boolean savePressed() {
		final int REQUIRED = 18;
		int flag = 0;
		
		//reading data
		enteredAdmin = admin.getText();
		enteredPhone = phone.getText();
		enteredAltPhone = altPhone.getText();
		enteredEmail = email.getText();
		enteredOwnerName = ownerName.getText();
		enteredGymName = gymName.getText();
		enteredGymPhone = gymPhone.getText();
		enteredGymAltPhone = gymAltPhone.getText();
		enteredGymAddress = gymAddress.getText();
		enteredGymPin = gymPin.getText();
		enteredPrefixOwnerName = prefixOwnerName.getValue().toString();
		enteredOpenHH = openHH.getValue().toString();
		enteredOpenMM = openMM.getValue().toString();
		enteredOpenMeridian = openMeridian.getValue().toString();
		enteredCloseHH = closeHH.getValue().toString();
		enteredCloseMM = closeMM.getValue().toString();
		enteredCloseMeridian = closeMeridian.getValue().toString();
		
		//checking null values
		if(enteredAdmin.equals("") == true) {
			admin.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			admin.setStyle(ref.getStyle());
		}
		if(enteredPhone.equals("") == true) {
			phone.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			phone.setStyle(ref.getStyle());
		}
		if(enteredAltPhone.equals("") == true) {
			altPhone.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			altPhone.setStyle(ref.getStyle());
		}
		if(enteredEmail.equals("") == true) {
			email.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			email.setStyle(ref.getStyle());
		}
		if(enteredOwnerName.equals("") == true) {
			ownerName.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			ownerName.setStyle(ref.getStyle());
		}
		if(enteredGymName.equals("") == true) {
			gymName.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			gymName.setStyle(ref.getStyle());
		}
		if(enteredGymPhone.equals("") == true) {
			gymPhone.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			gymPhone.setStyle(ref.getStyle());
		}
		if(enteredGymAltPhone.equals("") == true) {
			gymAltPhone.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			gymAltPhone.setStyle(ref.getStyle());
		}
		if(enteredGymAddress.equals("") == true) {
			gymAddress.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			gymAddress.setStyle(ref.getStyle());
		}
		if(enteredGymPin.equals("") == true) {
			gymPin.setStyle("-fx-border-color: 'red';");
		}else {
			flag++;
			gymPin.setStyle(ref.getStyle());
		}
		
		//verification
		int i;
		//enteredEmail
		if(enteredEmail.contains("@") == false) {
			email.setStyle("-fx-border-color: 'red';");
			invalid.setVisible(true);
		}else {
			flag++;
			email.setStyle(ref.getStyle());
		}
		//enteredGymPin
		String pin = enteredGymPin;
		if(pin.length() == 6) {
			for(i = 0 ; i < pin.length() ; i++) {
				char ch = pin.charAt(i);
				if(ch =='0' || ch =='1' || ch =='2' || ch =='3' || ch =='4' || ch =='5' || ch =='6' || ch =='7' || ch =='8' || ch =='9') {
					gymPin.setStyle(ref.getStyle());
					continue;
				}else {
					invalid.setVisible(true);
					gymPin.setStyle("-fx-border-color: 'red';");
					break;
				}
			}
			if(i == pin.length())
				flag++;
		}else {
			invalid.setVisible(true);
			gymPin.setStyle("-fx-border-color: 'red';");
		}
		//enteredPhone
		pin = enteredPhone;
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if(ch == '+' || ch =='0' || ch =='1' || ch =='2' || ch =='3' || ch =='4' || ch =='5' || ch =='6' || ch =='7' || ch =='8' || ch =='9') {
				if(ch == '+' && i != 0) {
					invalid.setVisible(true);
					phone.setStyle("-fx-border-color: 'red';");
					break;
				}
				phone.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				phone.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		//enteredAltPhone
		pin = enteredAltPhone;
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if(ch == '+' || ch =='0' || ch =='1' || ch =='2' || ch =='3' || ch =='4' || ch =='5' || ch =='6' || ch =='7' || ch =='8' || ch =='9') {
				if(ch == '+' && i != 0) {
					invalid.setVisible(true);
					altPhone.setStyle("-fx-border-color: 'red';");
					break;
				}
				altPhone.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				altPhone.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		//enteredGymPhone
		pin = enteredGymPhone;
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if(ch == '+' || ch =='0' || ch =='1' || ch =='2' || ch =='3' || ch =='4' || ch =='5' || ch =='6' || ch =='7' || ch =='8' || ch =='9') {
				if(ch == '+' && i != 0) {
					invalid.setVisible(true);
					gymPhone.setStyle("-fx-border-color: 'red';");
					break;
				}
				gymPhone.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				gymPhone.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		//enteredGymAltPhone
		pin = enteredGymAltPhone;
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if(ch == '+' || ch =='0' || ch =='1' || ch =='2' || ch =='3' || ch =='4' || ch =='5' || ch =='6' || ch =='7' || ch =='8' || ch =='9') {
				if(ch == '+' && i != 0) {
					invalid.setVisible(true);
					gymAltPhone.setStyle("-fx-border-color: 'red';");
					break;
				}
				gymAltPhone.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				gymAltPhone.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		//admin
		pin = enteredAdmin;
		pin = pin.toUpperCase();
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if((ch >= 'A' && ch <= 'Z') || ch == ' ') {
				admin.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				admin.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		//ownerName
		//admin
		pin = enteredOwnerName;
		pin = pin.toUpperCase();
		for(i = 0 ; i < pin.length() ; i++) {
			char ch = pin.charAt(i);
			if((ch >= 'A' && ch <= 'Z') || ch == ' ') {
				ownerName.setStyle(ref.getStyle());
				continue;
			}else {
				invalid.setVisible(true);
				ownerName.setStyle("-fx-border-color: 'red';");
				break;
			}
		}
		if(i == pin.length())
			flag++;
		
		if(flag == REQUIRED)
			return true;
		else
			return false;
		
		
	}
	public void saveToFile(Stage s) {
		try {
			String fileData = "";
			fileData = enteredAdmin.trim() + "~" + enteredPhone.trim() + "~" + enteredAltPhone.trim() + "~" +
					enteredEmail.trim() + "~" + enteredOwnerName.trim() + "~" + enteredGymName.trim() +
					"~" + enteredGymPhone.trim() + "~" + enteredGymAltPhone.trim() + "~" +
					enteredGymAddress.trim() + "~" + enteredGymPin.trim() + "~" + enteredPrefixOwnerName +
					"~" + enteredOpenHH + "~" + enteredOpenMM + "~" + enteredOpenMeridian +
					"~" + enteredCloseHH + "~" + enteredCloseMM + "~" + enteredCloseMeridian;
			
			FileOutputStream out = new FileOutputStream("resources/data files/"+ "Settings Data" +".txt");
			out.write(fileData.getBytes());
			out.close();
			
		}catch(Exception e) {}
	}
	public void loadSettings() {
		//load text file
		try {
			File file = new File("resources/data files/" + "Settings Data" + ".txt"); 
			BufferedReader br = new BufferedReader(new FileReader(file)); 
			String a = "";
			String complete = ""; 
			while ((a = br.readLine()) != null) 
				complete = complete.concat(a);	//Eg.: AnandKurkoti~+9123445~00000...
			
			StringTokenizer st = new StringTokenizer(complete , "~");
			admin.setText(st.nextToken());
			phone.setText(st.nextToken());
			altPhone.setText(st.nextToken());
			email.setText(st.nextToken());
			ownerName.setText(st.nextToken());
			gymName.setText(st.nextToken());
			gymPhone.setText(st.nextToken());
			gymAltPhone.setText(st.nextToken());
			gymAddress.setText(st.nextToken());
			gymPin.setText(st.nextToken());
			prefixOwnerName.getSelectionModel().select(st.nextToken());
			openHH.getSelectionModel().select(st.nextToken());
			openMM.getSelectionModel().select(st.nextToken());
			openMeridian.getSelectionModel().select(st.nextToken());
			closeHH.getSelectionModel().select(st.nextToken());
			closeMM.getSelectionModel().select(st.nextToken());
			closeMeridian.getSelectionModel().select(st.nextToken());
			
		}catch(Exception e) {}
				
	}
	public void loadAdminDetails() {
		try {
			PreparedStatement psx = connection.prepareStatement("SELECT * FROM `studentdatabase`.`admins` WHERE `Email` ='" + currentAdminEmail + "';;");
			ResultSet da = psx.executeQuery();
			while(da.next()) {
				admin.setText(da.getString(1));
				phone.setText(da.getString(2));
				altPhone.setText(da.getString(3));
				email.setText(da.getString(4));
				address.setText("Starting Date: " + da.getString(8));
			}
		}catch(Exception e) {}
	}
	
	Label address = new Label();
	
	public void settingsScreen(Stage s){
		menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
		try {
			inputBackImage = new FileInputStream("resources/logos/back.png");
			backImage = new Image(inputBackImage);
			back.setGraphic(new ImageView(backImage));
			updateBackButton(currentTheme);
			back.setStyle("-fx-background-color: '" + currentBackground + "';");
		}catch(Exception e) {}
		Button power = new Button();
		FileInputStream inputPowerImage;
		Image powerImage;
		try {
			inputPowerImage = new FileInputStream("resources/logos/power.png");
			powerImage = new Image(inputPowerImage);
			power.setGraphic(new ImageView(powerImage));
			power.setMaxHeight(20.0);
			power.setMaxWidth(20.0);
			power.setStyle("-fx-background-color: 'black';");//#ccffe6
		}catch(Exception e) {}
		
		String[] powerList = {"Logout" , "Delete Account"};
		ChoiceDialog<String> powerOptions = new ChoiceDialog<String>(powerList[0] , powerList);
		powerOptions.setTitle("Ask");
		powerOptions.setHeaderText("Choose option");
		
		//controls
		back.setOnAction(e -> {
			updateBackButton(currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			homeScreen(s);
		});
		AdminSQL delxx = new AdminSQL();
		power.setOnAction(e -> {
			Optional<String> selected = powerOptions.showAndWait();
			if(selected.isPresent() == true) {
				String getData = powerOptions.getSelectedItem().toString();
				if(getData.equalsIgnoreCase("Logout") == true) {
					currentTheme = 0;
					currentBackground = theme00;
					currentDarkColor = theme01;
					menuBar.setStyle("-fx-background-color: '" + theme01 + "';");
					updateBackButton(0);
					updateSettingsButton(0);
					loginScreen(s);
				}else if(getData.equalsIgnoreCase("Delete Account") == true) {
					Alert ask = new Alert(AlertType.CONFIRMATION);
					ask.setTitle("Confirmation");
					ask.setHeaderText("Delete Admin Account?");
					ask.setContentText("Admin Account will be deleted. Once "
							+ "action done cannot be undone. You will no longer "
							+ "be able to access the Gym data. Are you sure?\n\n\n");
					Optional<ButtonType> what =  ask.showAndWait();
					if(what.get() == ButtonType.OK) {
						delxx.deleteAccout(connection, currentAdminEmail);
						Alert deleted = new Alert(AlertType.INFORMATION);
						deleted.setTitle("Deleted");
						deleted.setHeaderText("Admin Deleted");
						deleted.setContentText("Admin was successfully removed.\n\n\n");
						deleted.show();
						currentTheme = 0;
						currentBackground = theme00;
						currentDarkColor = theme01;
						menuBar.setStyle("-fx-background-color: '" + theme01 + "';");
						updateBackButton(0);
						updateSettingsButton(0);
						loginScreen(s);
					}
				}
			}
		});
		
		//reset all textFields
		admin.setText("");
		phone.setText("");
		altPhone.setText("");
		email.setText("");
		ownerName.setText("");
		gymName.setText("");
		gymPhone.setText("");
		gymAltPhone.setText("");
		gymAddress.setText("");
		gymPin.setText("");
		
		loadSettings();
		
		loadAdminDetails();
		
		settingsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 40px;");
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.6);
		adminLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 15px");
		admin.setPromptText("Enter Administrator Name");
		phoneLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 15px");
		phone.setPromptText("Contact Number");
		altPhone.setPromptText("Alternate Contact Number");
		emailLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 15px");
		email.setPromptText("Enter Email");
		gymDetailsCB.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 15px");
		gymDetailsUnderline.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 24px;");
		gymDetailsUnderline.setUnderline(true);
		gymDetailsUnderline.setOpacity(0.55);
		prefixOwnerName.getSelectionModel().selectFirst();
		ownerName.setPromptText("Owner Name");
		gymName.setPromptText("Gym Name");
		gymPhone.setPromptText("Phone Number");
		gymAltPhone.setPromptText("Alternate Phone Number");
		gymAddress.setPromptText("Address");
		gymPin.setPromptText("PIN Code");
		invalid.setStyle("-fx-font-family: 'Marlett';" + "-fx-font-size: 12px;" + "-fx-text-fill: red;");
		openTime.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 12px");
		closeTime.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 12px");
		openHH.getSelectionModel().selectFirst();
		openMM.getSelectionModel().selectFirst();
		openMeridian.getSelectionModel().selectFirst();
		closeHH.getSelectionModel().selectFirst();
		closeMM.getSelectionModel().selectFirst();
		closeMeridian.getSelectionModel().selectFirst();
		
		Button deleteMembers = new Button("Delete All Members");
		Button deleteTrainers = new Button("Delete All Trainers");
		Button deleteMemberships = new Button("Delete All Memberships");
		Button save = new Button("Save");
		Label saveUnderline = new Label("\t\t\t");
		saveUnderline.setStyle("-fx-text-fill: '#0073e6';"+ "-fx-font-size: 19.5px;");
		saveUnderline.setUnderline(true);
		saveUnderline.setVisible(false);
		
		deleteMembers.setPrefSize(150.0, 30.0);
		deleteTrainers.setPrefSize(150.0, 30.0);
		deleteMemberships.setPrefSize(170.0, 30.0);
		save.setPrefSize(130.0, 30.0);
		
		deleteMembers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		deleteTrainers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		deleteMemberships.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		
		save.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.2px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;");
		
		deleteMembers.setOnMouseExited(e -> deleteMembers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		deleteTrainers.setOnMouseExited(e -> deleteTrainers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		deleteMemberships.setOnMouseExited(e -> deleteMemberships.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		save.setOnMouseExited(e -> saveUnderline.setVisible(false));
		
		deleteMembers.setOnMouseReleased(e -> deleteMembers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		deleteTrainers.setOnMouseReleased(e -> deleteTrainers.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		deleteMemberships.setOnMouseReleased(e -> deleteMemberships.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		save.setOnMouseReleased(e -> saveUnderline.setVisible(false));
		
		deleteMembers.setOnMouseEntered(e -> deleteMembers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		deleteTrainers.setOnMouseEntered(e -> deleteTrainers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		deleteMemberships.setOnMouseEntered(e -> deleteMemberships.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		save.setOnMouseEntered(e -> saveUnderline.setVisible(true));
		
		deleteMembers.setOnMousePressed(e -> deleteMembers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		deleteTrainers.setOnMousePressed(e -> deleteTrainers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		deleteMemberships.setOnMousePressed(e -> deleteMemberships.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		save.setOnMousePressed(e -> saveUnderline.setVisible(true));
		
		//initially
		gymDetailsCB.setSelected(false);
		gymDetailsUnderline.setOpacity(0.3);
		prefixOwnerName.setDisable(true);
		ownerName.setDisable(true);
		gymName.setDisable(true);
		gymPhone.setDisable(true);
		gymAltPhone.setDisable(true);
		gymAddress.setDisable(true);
		gymPin.setDisable(true);
		openHH.setDisable(true);
		openMM.setDisable(true);
		openMeridian.setDisable(true);
		closeHH.setDisable(true);
		closeMM.setDisable(true);
		closeMeridian.setDisable(true);
		openTime.setOpacity(0.6);
		closeTime.setOpacity(0.6);
		invalid.setVisible(false);
		
		admin.setStyle(ref.getStyle());
		phone.setStyle(ref.getStyle());
		altPhone.setStyle(ref.getStyle());
		email.setStyle(ref.getStyle());
		ownerName.setStyle(ref.getStyle());
		gymName.setStyle(ref.getStyle());
		gymPhone.setStyle(ref.getStyle());
		gymAltPhone.setStyle(ref.getStyle());
		gymAddress.setStyle(ref.getStyle());
		gymPin.setStyle(ref.getStyle());
		
		Button changeTheme = new Button("Change Theme");
		changeTheme.setPrefSize(120.0 , 30.0);
		
		changeTheme.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-weight: normal;");
		changeTheme.setOnMouseExited(e -> changeTheme.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-weight: normal;"));
		changeTheme.setOnMouseReleased(e -> changeTheme.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-weight: normal;"));
		changeTheme.setOnMouseEntered(e -> changeTheme.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-weight: bold;"));
		
		//controls
		gymDetailsCB.setOnAction(e -> {
			if(gymDetailsCB.isSelected() == true ) {
				gymDetailsUnderline.setOpacity(0.55);
				prefixOwnerName.setDisable(false);
				ownerName.setDisable(false);
				gymName.setDisable(false);
				gymPhone.setDisable(false);
				gymAltPhone.setDisable(false);
				gymAddress.setDisable(false);
				gymPin.setDisable(false);
				openHH.setDisable(false);
				openMM.setDisable(false);
				openMeridian.setDisable(false);
				closeHH.setDisable(false);
				closeMM.setDisable(false);
				closeMeridian.setDisable(false);
				openTime.setOpacity(1.0);
				closeTime.setOpacity(1.0);
			}else {
				gymDetailsUnderline.setOpacity(0.3);
				prefixOwnerName.setDisable(true);
				ownerName.setDisable(true);
				gymName.setDisable(true);
				gymPhone.setDisable(true);
				gymAltPhone.setDisable(true);
				gymAddress.setDisable(true);
				gymPin.setDisable(true);
				openHH.setDisable(true);
				openMM.setDisable(true);
				openMeridian.setDisable(true);
				closeHH.setDisable(true);
				closeMM.setDisable(true);
				closeMeridian.setDisable(true);
				openTime.setOpacity(0.6);
				closeTime.setOpacity(0.6);
				
			}
		});
		save.setOnAction(e -> {
			if(savePressed() == true && checkPhoneNumber(gymAltPhone.getText()) == true && checkPhoneNumber(gymPhone.getText()) == true && checkPhoneNumber(phone.getText()) == true && checkPhoneNumber(altPhone.getText()) == true) {
				invalid.setVisible(false);
				Alert saveYN = new Alert(AlertType.CONFIRMATION);
				saveYN.setTitle("Save");
				saveYN.setHeaderText("Do you wish to Save?");
				saveYN.setContentText("Changes will be made in Settings. Do you wish to continue?\n\n");
				Optional<ButtonType> pressedButton = saveYN.showAndWait();
				
				//confirmation to change Settings
				if(pressedButton.get() == ButtonType.OK) {
					AdminSQL upd = new AdminSQL();
					boolean isUpdatedDone = upd.updateData(connection, admin.getText().trim(), phone.getText().trim(), altPhone.getText().trim(), email.getText().toString().trim() , currentAdminEmail);
					if(isUpdatedDone == true) {
						
						saveToFile(s);
						this.currentAdminEmail = email.getText().toString().trim();
						Alert sup = new Alert(AlertType.INFORMATION);
						sup.setTitle("Message");
						sup.setHeaderText("Updated Successfully");
						sup.setContentText("Settings has been successfully updated.\n\n\n");
						sup.show();
						homeScreen(s);
					}else {
						Alert nsup = new Alert(AlertType.ERROR);
						nsup.setTitle("Error Occured");
						nsup.setHeaderText("Failed");
						nsup.setContentText("Settings was not updated.\n\n\n");
						nsup.show();
					}
				}
			}else {
				Alert saveYN = new Alert(AlertType.WARNING);
				saveYN.setTitle("Warning");
				saveYN.setHeaderText("Some fields contain invalid data");
				saveYN.setContentText("Please check the fields and try again.\n\n\n");
				saveYN.show();
			}
			
		});
		deleteMembers.setOnAction(e -> {
			Alert delM = new Alert(AlertType.CONFIRMATION);
			delM.setTitle("Delete Confirmation");
			delM.setHeaderText("Remove All Members?");
			delM.setContentText("All the Gym Members data along with their names and IDs will\n"
							  + "be removed. Action once done cannot be undone. Are you sure?\n\n\n");
			Optional<ButtonType> pressedButton = delM.showAndWait();
			
			//confirmation to delete all members
			if(pressedButton.get() == ButtonType.OK) {
				try {
					PreparedStatement r = connection.prepareStatement("DROP TABLE `studentdatabase`.`gym_members`;");
					r.execute();
					
					PreparedStatement r1 = connection.prepareStatement("DROP TABLE `studentdatabase`.`gym_members_connectivity`;");
					r1.execute();
					Alert doneM = new Alert(AlertType.INFORMATION);
					doneM.setTitle("Successful");
					doneM.setHeaderText("Members Removed");
					doneM.setContentText("All the gym members have been removed. Press OK to "
							+ "continue.\n\n\n");
					doneM.show();
				}catch(Exception ee) {}
			}
		});
		deleteTrainers.setOnAction(e -> {
			Alert delT = new Alert(AlertType.CONFIRMATION);
			delT.setTitle("Delete Confirmation");
			delT.setHeaderText("Remove All Trainers?");
			delT.setContentText("All the Gym Trainers data along with their names and IDs as\n"
							  + "well as their all other details will be removed. Action once\n"
							  + "done cannot be undone. Are you sure?\n\n\n");
			Optional<ButtonType> pressedButton = delT.showAndWait();
			
			//confirmation to delete all trainers
			if(pressedButton.get() == ButtonType.OK) {
				try {
					PreparedStatement t = connection.prepareStatement("DROP TABLE `studentdatabase`.`gym_trainers`;");
					t.execute();
					
					PreparedStatement t1 = connection.prepareStatement("DROP TABLE `studentdatabase`.`gym_trainers_connectivity`;");
					t1.execute();
					Alert doneT = new Alert(AlertType.INFORMATION);
					doneT.setTitle("Successful");
					doneT.setHeaderText("Trainers Removed");
					doneT.setContentText("All the gym trainers have been removed. Press OK to "
							+ "continue.\n\n\n");
					doneT.show();
				}catch(Exception ee1) {}
			}
		});
		deleteMemberships.setOnAction(e -> {
			Alert delMS = new Alert(AlertType.CONFIRMATION);
			delMS.setTitle("Delete Confirmation");
			delMS.setHeaderText("Remove All Memberships?");
			delMS.setContentText("All the mentioned Gym Membership(s) will be removed. Are\n"
							  + "you sure?\n\n\n");
			Optional<ButtonType> pressedButton = delMS.showAndWait();
			
			//confirmation to delete all memberships
			if(pressedButton.get() == ButtonType.OK) {
				Alert notMS = new Alert(AlertType.ERROR);
				try {
					PreparedStatement m = connection.prepareStatement("DROP TABLE `studentdatabase`.`gym_memberships`;");
					m.execute();
					Alert doneMS = new Alert(AlertType.INFORMATION);
					doneMS.setTitle("Successful");
					doneMS.setHeaderText("Memberships Removed");
					doneMS.setContentText("All the gym memberships have been removed. Press OK to "
							+ "continue.\n\n\n");
					doneMS.show();
				}catch(Exception ee2) {
					notMS.setTitle("Failed");
					notMS.setHeaderText("Memberships Not Removed");
					notMS.setContentText("Gym memberships was not removed. Some members and "
							+ "trainers are still using these memberships. First remove the "
							+ "trainers and members and try again.\n\n\n");
					notMS.show();
				}
			}
		});
		changeTheme.setOnAction(e -> {
			updateBackButton(currentTheme);
			themeScreen(s);
		});
		
		AnchorPane root = new AnchorPane();
		//settingsLabel
		AnchorPane.setTopAnchor(settingsLabel, 70.0 - 10.0);
		AnchorPane.setLeftAnchor(settingsLabel, 100.0);
		//headingUnderline
		AnchorPane.setTopAnchor(headingUnderline, 99.0 - 10.0);
		AnchorPane.setLeftAnchor(headingUnderline, 70.0);
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//adminLabel
		AnchorPane.setTopAnchor(adminLabel, 170.0 - 10.0);
		AnchorPane.setLeftAnchor(adminLabel, 200.0);
		//admin
		AnchorPane.setTopAnchor(admin, 167.0 - 10.0);
		AnchorPane.setLeftAnchor(admin, 330.0);
		AnchorPane.setRightAnchor(admin, 200.0);
		//phoneLabel
		AnchorPane.setTopAnchor(phoneLabel, 170.0 + 32.0 - 10.0);
		AnchorPane.setLeftAnchor(phoneLabel, 200.0);
		//phone
		AnchorPane.setTopAnchor(phone, 167.0 + 33.0 - 10.0);
		AnchorPane.setLeftAnchor(phone, 395.0);
		AnchorPane.setRightAnchor(phone, 200.0 + 165.0);
		//altPhone
		AnchorPane.setTopAnchor(altPhone, 167.0 + 33.0 - 10.0);
		AnchorPane.setLeftAnchor(altPhone, 395.0 + 145.0);
		AnchorPane.setRightAnchor(altPhone, 200.0);
		//emailLabel
		AnchorPane.setTopAnchor(emailLabel, 170.0 + 32.0 + 32.0 - 10.0);
		AnchorPane.setLeftAnchor(emailLabel, 200.0);
		//email
		AnchorPane.setTopAnchor(email, 167.0 + 33.0 + 33.0 - 10.0);
		AnchorPane.setLeftAnchor(email, 350.0);
		AnchorPane.setRightAnchor(email, 200.0);
		//gymDetailsLabel
		AnchorPane.setTopAnchor(gymDetailsCB, 230.0 + 32.0 + 32.0 - 10.0);
		AnchorPane.setLeftAnchor(gymDetailsCB, 150.0);
		//gymDetailsUnderline
		AnchorPane.setTopAnchor(gymDetailsUnderline, 230.0 + 32.0 + 32.0 - 10.0);
		AnchorPane.setLeftAnchor(gymDetailsUnderline, 140.0);
		//gymName
		AnchorPane.setTopAnchor(gymName, 230.0 + 32.0 + 32.0 + 45.0 - 10.0);
		AnchorPane.setLeftAnchor(gymName, 160.0);
		AnchorPane.setRightAnchor(gymName, 160.0);
		//prefixOwnerName
		AnchorPane.setTopAnchor(prefixOwnerName, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 - 10.0);
		AnchorPane.setLeftAnchor(prefixOwnerName, 160.0);
		AnchorPane.setRightAnchor(prefixOwnerName, 670.0);
		//ownerName
		AnchorPane.setTopAnchor(ownerName, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 - 10.0);
		AnchorPane.setLeftAnchor(ownerName, 235.0);
		AnchorPane.setRightAnchor(ownerName, 460.0);
		//gymPhone
		AnchorPane.setTopAnchor(gymPhone, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 - 10.0);
		AnchorPane.setLeftAnchor(gymPhone, 445.0);
		AnchorPane.setRightAnchor(gymPhone, 320.0);
		//gymAltPhone
		AnchorPane.setTopAnchor(gymAltPhone, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 - 10.0);
		AnchorPane.setLeftAnchor(gymAltPhone, 585.0);
		AnchorPane.setRightAnchor(gymAltPhone, 160.0);
		//openTime
		AnchorPane.setTopAnchor(openTime, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 40.0 - 10.0);
		AnchorPane.setLeftAnchor(openTime, 170.0);
		//openHH
		AnchorPane.setTopAnchor(openHH, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setLeftAnchor(openHH, 233.0);
		AnchorPane.setRightAnchor(openHH, 610.0);
		//openMM
		AnchorPane.setTopAnchor(openMM, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setLeftAnchor(openMM, 295.0);
		AnchorPane.setRightAnchor(openMM, 540.0);
		//openMeridian
		AnchorPane.setTopAnchor(openMeridian, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setLeftAnchor(openMeridian, 365.0);
		AnchorPane.setRightAnchor(openMeridian, 470.0);
		//closeTime
		AnchorPane.setTopAnchor(closeTime, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 40.0 - 10.0);
		AnchorPane.setLeftAnchor(closeTime, 180.0 + 287.0);
		//closeHH
		AnchorPane.setTopAnchor(closeHH, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setRightAnchor(closeHH, 315.0);
		AnchorPane.setLeftAnchor(closeHH, 530.0);
		//closeMM
		AnchorPane.setTopAnchor(closeMM, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setRightAnchor(closeMM, 245.0);
		AnchorPane.setLeftAnchor(closeMM, 590.0);
		//closeMeridian
		AnchorPane.setTopAnchor(closeMeridian, 230.0 + 32.0 + 32.0 + 40.0 + 38.0 + 35.0 - 10.0);
		AnchorPane.setRightAnchor(closeMeridian, 170.0);
		AnchorPane.setLeftAnchor(closeMeridian, 660.0);
		//gymAddress
		AnchorPane.setTopAnchor(gymAddress, 230.0 + 32.0 + 32.0 + 147.0 - 10.0);
		AnchorPane.setLeftAnchor(gymAddress, 160.0);
		AnchorPane.setRightAnchor(gymAddress, 335.0);
		//gymPin
		AnchorPane.setTopAnchor(gymPin, 230.0 + 32.0 + 32.0 + 147.0 - 10.0);
		AnchorPane.setLeftAnchor(gymPin, 570.0);
		AnchorPane.setRightAnchor(gymPin, 160.0);
		//deleteMembers
		AnchorPane.setLeftAnchor(deleteMembers, 50.0 - 25.0 - 10.0);
		AnchorPane.setBottomAnchor(deleteMembers, 21.0);
		//deleteTrainers
		AnchorPane.setLeftAnchor(deleteTrainers, 50 + 150.0 + 10 - 25.0 - 10.0);
		AnchorPane.setBottomAnchor(deleteTrainers, 21.0);
		//deleteMemberships
		AnchorPane.setLeftAnchor(deleteMemberships, 50 + 150.0 + 10.0 + 150.0 + 10.0 - 25.0 - 10.0);
		AnchorPane.setBottomAnchor(deleteMemberships, 21.0);
		//save
		AnchorPane.setRightAnchor(save, 40.0);
		AnchorPane.setBottomAnchor(save, 21.0);
		//saveUnderline
		AnchorPane.setRightAnchor(saveUnderline, 40.0);
		AnchorPane.setBottomAnchor(saveUnderline, 10.0);
		//invalid
		AnchorPane.setRightAnchor(invalid, 40.0);
		AnchorPane.setBottomAnchor(invalid, 58.0);
		//power
		AnchorPane.setTopAnchor(power, 37.0);
		AnchorPane.setRightAnchor(power, 20.0);
		//changeTheme
		AnchorPane.setTopAnchor(changeTheme, 37.0);
		AnchorPane.setRightAnchor(changeTheme, 67.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , settingsLabel , headingUnderline , back , adminLabel , admin ,
								saveUnderline , save , deleteMembers , deleteTrainers , deleteMemberships ,
								phoneLabel , phone , altPhone , emailLabel , email , gymDetailsUnderline ,
								gymDetailsCB , prefixOwnerName , ownerName , gymName , gymPhone ,
								gymAltPhone , openTime , openHH , openMM , openMeridian ,closeTime ,
								closeHH , closeMM , closeMeridian , gymAddress, gymPin , invalid ,
								power , changeTheme);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label savedTotalMembers = new Label("0");
	Label savedTotalTrainers = new Label("0");
	Label savedTotalMemberships = new Label("0");
	
	public void calculateTotalMTMs() {
		savedTotalMembers.setText("0");
		savedTotalTrainers.setText("0");
		savedTotalMemberships.setText("0");
		try {
			PreparedStatement count = connection.prepareStatement("SELECT COUNT(*) FROM `studentdatabase`.`gym_members`;");
			ResultSet c1 = count.executeQuery();
			c1.next();
			savedTotalMembers.setText(c1.getString(1));
		}catch(Exception e) {}
		try {
			PreparedStatement count = connection.prepareStatement("SELECT COUNT(*) FROM `studentdatabase`.`gym_trainers`;");
			ResultSet c2 = count.executeQuery();
			c2.next();
			savedTotalTrainers.setText(c2.getString(1));
		}catch(Exception e) {}
		try {
			PreparedStatement count = connection.prepareStatement("SELECT COUNT(*) FROM `studentdatabase`.`gym_memberships`;");
			ResultSet c3 = count.executeQuery();
			c3.next();
			savedTotalMemberships.setText(c3.getString(1));
		}catch(Exception e) {}
	}
	
	public void gymDetailsScreen(Stage s) {
		Button change = new Button("Change");
		change.setPrefSize(120.0, 30.0);
		
		change.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		change.setOnMouseExited(e -> change.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		change.setOnMouseReleased(e -> change.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.5px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		change.setOnMouseEntered(e -> change.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.3px;"));
		change.setOnMousePressed(e -> change.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		
		//controls
		back.setOnAction(e -> homeScreen(s));
		change.setOnAction(e -> settingsScreen(s));
		
		Label gymNameLabel = new Label(" Null");
		gymNameLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 27px;");
		if(gymName.getText().toString().equals("") == false) {
			gymNameLabel.setText("  ".concat(gymName.getText().toString()));
		}
		String underlineSpace = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "    ";
		Label underline = new Label(underlineSpace);
		underline.setUnderline(true);
		Label ownerNameLabel = new Label("Owner Name:");
		Label gymContactLabel = new Label("Contact Number(s):");
		Label opensAtLabel = new Label("Opening Time:");
		Label closesAtLabel = new Label("Closing Time:");
		
		ownerNameLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 13px;");
		gymContactLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 13px;");
		opensAtLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 13px;");
		closesAtLabel.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 13px;");
		
		Label savedOwnerName = new Label("Null");
		savedOwnerName.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 14px;");
		if(ownerName.getText().toString().equals("") == false) {
			String stringOwnerName = prefixOwnerName.getValue() + " " + ownerName.getText().toString();
			savedOwnerName.setText(stringOwnerName);
		}
		Label savedContactNumber = new Label("Null , Null");
		savedContactNumber.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 14px;");
		if(gymPhone.getText().toString().equals("") == false) {
			savedContactNumber.setText(gymPhone.getText().toString().concat(" , " + gymAltPhone.getText().toString()));
		}
		Label openingTime = new Label("1:00 AM");
		openingTime.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 14px;");
		String storedOpenTime = openHH.getValue() + ":" + openMM.getValue() + " " + openMeridian.getValue();
		openingTime.setText(storedOpenTime);
		Label closingTime = new Label("1:00 AM");
		closingTime.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 14px;");
		String storedCloseTime = closeHH.getValue() + ":" + closeMM.getValue() + " " + closeMeridian.getValue();
		closingTime.setText(storedCloseTime);
		Label otherDetails = new Label("Other Details");
		otherDetails.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 15px;");
		String underlineSpace2 = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "  ";
		Label underline2 = new Label(underlineSpace2);
		underline2.setUnderline(true);
		underline2.setOpacity(0.7);
		Label totalMembers = new Label("Total Members Enrolled:");
		Label totalTrainers = new Label("Total Trainers Present:");
		Label totalMemberships = new Label("Membership(s) Available:");
		totalMembers.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 12px;");
		totalTrainers.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 12px;");
		totalMemberships.setStyle("-fx-font-family: 'MS Reference Sans Serif';" + "-fx-font-size: 12px;");
		
		savedTotalMembers.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		savedTotalTrainers.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		savedTotalMemberships.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		
		calculateTotalMTMs();
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//gymNameLabel
		AnchorPane.setTopAnchor(gymNameLabel, 80.0);
		AnchorPane.setLeftAnchor(gymNameLabel, 135.0);
		//underline
		AnchorPane.setTopAnchor(underline, 102.0);
		AnchorPane.setRightAnchor(underline, 135.0);
		AnchorPane.setLeftAnchor(underline, 135.0);
		//ownerNameLabel
		AnchorPane.setTopAnchor(ownerNameLabel, 150.0);
		AnchorPane.setRightAnchor(ownerNameLabel, 580.0);
		//gymContactLabel
		AnchorPane.setTopAnchor(gymContactLabel, 180.0);
		AnchorPane.setRightAnchor(gymContactLabel, 580.0);
		//opensAtLabel
		AnchorPane.setTopAnchor(opensAtLabel, 210.0);
		AnchorPane.setRightAnchor(opensAtLabel, 580.0);
		//closesAtLabel
		AnchorPane.setTopAnchor(closesAtLabel, 240.0);
		AnchorPane.setRightAnchor(closesAtLabel, 580.0);
		//savedOwnerName
		AnchorPane.setTopAnchor(savedOwnerName, 149.0);
		AnchorPane.setLeftAnchor(savedOwnerName, 350.0);
		//savedContactNumber
		AnchorPane.setTopAnchor(savedContactNumber, 179.0);
		AnchorPane.setLeftAnchor(savedContactNumber, 350.0);
		//openingTime
		AnchorPane.setTopAnchor(openingTime, 209.0);
		AnchorPane.setLeftAnchor(openingTime, 350.0);
		//closingTime
		AnchorPane.setTopAnchor(closingTime, 239.0);
		AnchorPane.setLeftAnchor(closingTime, 350.0);
		//otherDetails
		AnchorPane.setTopAnchor(otherDetails, 310.0);
		AnchorPane.setLeftAnchor(otherDetails, 200.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 317.0);
		AnchorPane.setRightAnchor(underline2, 190.0);
		AnchorPane.setLeftAnchor(underline2, 190.0);
		//totalMembers
		AnchorPane.setTopAnchor(totalMembers, 350.0);
		AnchorPane.setRightAnchor(totalMembers, 500.0);
		//totalTrainers
		AnchorPane.setTopAnchor(totalTrainers, 375.0);
		AnchorPane.setRightAnchor(totalTrainers, 500.0);
		//totalMemberships
		AnchorPane.setTopAnchor(totalMemberships, 400.0);
		AnchorPane.setRightAnchor(totalMemberships, 500.0);
		//savedTotalMembers
		AnchorPane.setTopAnchor(savedTotalMembers, 348.0);
		AnchorPane.setLeftAnchor(savedTotalMembers, 420.0);
		//savedTotalTrainers
		AnchorPane.setTopAnchor(savedTotalTrainers, 373.0);
		AnchorPane.setLeftAnchor(savedTotalTrainers, 420.0);
		//savedTotalMemberships
		AnchorPane.setTopAnchor(savedTotalMemberships, 398.0);
		AnchorPane.setLeftAnchor(savedTotalMemberships, 420.0);
		//change
		AnchorPane.setRightAnchor(change, 30.0);
		AnchorPane.setBottomAnchor(change, 20.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , underline, gymNameLabel , ownerNameLabel ,
				gymContactLabel , opensAtLabel , closesAtLabel , savedOwnerName, savedContactNumber ,
				openingTime , closingTime , underline2 , otherDetails , totalMembers , totalTrainers ,
				totalMemberships , savedTotalMembers , savedTotalTrainers , savedTotalMemberships , change);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void prepareMembersTable() {
		loadAllPotentialSearchElements();
		
		try {
			try {
				//check whether GYM_MEMBERS_CONNECTIVITY TABLE is present or not
				PreparedStatement test0 = connection.prepareStatement("select * from `studentdatabase`.`GYM_MEMBERS_CONNECTIVITY`;");  
			    ResultSet run = test0.executeQuery();
			}catch(Exception e) {
				//create GYM_MEMBERS_CONNECTIVITY TABLE
				String code1 = "CREATE TABLE `studentdatabase`.GYM_MEMBERS_CONNECTIVITY (PhoneNumberOne VARCHAR(13),"
						+ "PhoneNumberTwo VARCHAR(13) , Email VARCHAR(50) , Address VARCHAR(200) , CITY VARCHAR(100) ,"
						+ "COUNTRY VARCHAR(100) , PinCode VARCHAR(20) , ID VARCHAR(10) PRIMARY KEY);";
				PreparedStatement first = connection.prepareStatement(code1);
				first.execute();
			}
			try {
				//check whether GYM_MEMBERS TABLE is present or not
				PreparedStatement test1 = connection.prepareStatement("select * from `studentdatabase`.`GYM_MEMBERS`;");  
			    ResultSet run1 = test1.executeQuery();
			}catch(Exception e) {
				String code2 = "CREATE TABLE `studentdatabase`.GYM_MEMBERS (ID VARCHAR(10) PRIMARY KEY , Name VARCHAR(30) ,"
						+ "Gender VARCHAR(15) , Weight INT , DOB VARCHAR(12) , Age INT , PhoneNumOne VARCHAR(13) ,"
						+ "Membership_Type VARCHAR(20) , Amount INT , Note VARCHAR(300) , Date_Of_Joining VARCHAR(12) , Occupation VARCHAR(50)"
						+ ", FOREIGN KEY (ID) REFERENCES `studentdatabase`.`GYM_MEMBERS_CONNECTIVITY` (ID)"
						+ ", FOREIGN KEY (Membership_Type) REFERENCES `studentdatabase`.`GYM_MEMBERSHIPS` (Name));";
				PreparedStatement second = connection.prepareStatement(code2);
				second.execute();
			}
			//load 
			String className = "";
			try {
				membersTable.getItems().clear();
				PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_MEMBERS`;");
			    ResultSet rs = command.executeQuery();
			    while(rs.next()){  
			    	String memberID = rs.getString(1);
			    	String memberName = rs.getString(2);
			    	String memberMembership = rs.getString(8);
			    	
			    	membersTable.getItems().add(new MembersListClass(memberID , memberName , memberMembership));
			    }
			}catch(Exception e) {}
			
			
			
		}catch(Exception e) {}
		
	}
	
	Label viewMemberIDLabel = new Label("ID:");
	Label viewMemberNameLabel = new Label("Name:");
	Label viewMemberGenderLabel = new Label("Gender:");
	Label viewMemberDOBLabel = new Label("DOB:");
	Label viewMemberPhNoLabel = new Label("Contact Number(s):");
	Label viewMemberEmailLabel = new Label("Email:");
	Label viewMemberAddressLabel = new Label("Address:");
	Label viewMemberCityLabel = new Label("City:");
	Label viewMemberPinLabel = new Label("PIN Code:");
	Label viewMemberCountryLabel = new Label("Country:");
	Label viewMemberOccupationLabel = new Label("Occupation:");
	Label viewMemberMembershipLabel = new Label("Membership chosen:");
	Label viewMemberAgeLabel = new Label("Age:");
	Label viewMemberDOJLabel = new Label("Date of Joining:");
	Label viewMemberAmountLabel = new Label("Amount:   Rs.");
	Label viewMemberNoteLabel = new Label("Note:");
	Label viewMemberWeightLabel = new Label("Weight:");
	
	Label viewMemberID = new Label("");
	Label viewMemberName = new Label("");
	Label viewMemberGender = new Label("");
	Label viewMemberDOB = new Label("");
	Label viewMemberAge = new Label("");
	Label viewMemberOccupation = new Label("");
	TextArea viewMemberAddress = new TextArea("");
	Label viewMemberCity = new Label("");
	Label viewMemberCountry = new Label("");
	Label viewMemberPin = new Label("");
	Label viewMemberPhNo1 = new Label("");
	Label viewMemberPhNo2 = new Label("");
	Label viewMemberEmail = new Label("");
	Label viewMemberMembership = new Label("");
	Label viewMemberAmount = new Label("");
	TextArea viewMemberNote = new TextArea("");
	Label viewMemberDOJ = new Label("");
	Label viewMemberWeight = new Label("");
	
	public void loadMemberDataFromDB(String selectedMemberID) {
		viewMemberIDLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberNameLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberGenderLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberDOBLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberPhNoLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberEmailLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberAddressLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberCityLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberPinLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberCountryLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberOccupationLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberMembershipLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberAgeLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberDOJLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberAmountLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberNoteLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewMemberWeightLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		
		viewMemberID.setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");
		viewMemberName.setStyle("-fx-font-size: 15px;");
		viewMemberGender.setStyle("-fx-font-size: 15px;");
		viewMemberDOB.setStyle("-fx-font-size: 15px;");
		viewMemberAge.setStyle("-fx-font-size: 15px;");
		viewMemberOccupation.setStyle("-fx-font-size: 15px;");
		viewMemberCity.setStyle("-fx-font-size: 15px;");
		viewMemberCountry.setStyle("-fx-font-size: 15px;");
		viewMemberPin.setStyle("-fx-font-size: 15px;");
		viewMemberPhNo1.setStyle("-fx-font-size: 15px;");
		viewMemberPhNo2.setStyle("-fx-font-size: 15px;");
		viewMemberEmail.setStyle("-fx-font-size: 15px;");
		viewMemberMembership.setStyle("-fx-font-size: 15px;");
		viewMemberAmount.setStyle("-fx-font-size: 15px;");
		viewMemberDOJ.setStyle("-fx-font-size: 15px;");
		viewMemberWeight.setStyle("-fx-font-size: 15px;");
		
		viewMemberAddress.setWrapText(true);
		viewMemberNote.setWrapText(true);
		viewMemberAddress.setEditable(false);
		viewMemberNote.setEditable(false);
		
		viewMemberID.setText("");
		viewMemberName.setText("");
		viewMemberGender.setText("");
		viewMemberDOB.setText("");
		viewMemberAge.setText("");
		viewMemberOccupation.setText("");
		viewMemberAddress.setText("");
		viewMemberCity.setText("");
		viewMemberCountry.setText("");
		viewMemberPin.setText("");
		viewMemberPhNo1.setText("");
		viewMemberPhNo2.setText("");
		viewMemberEmail.setText("");
		viewMemberMembership.setText("");
		viewMemberAmount.setText("");
		viewMemberNote.setText("");
		viewMemberDOJ.setText("");
		viewMemberWeight.setText("");
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_MEMBERS` WHERE ID = '" + selectedMemberID + "';");
			ResultSet result = ps.executeQuery();
			
			result.next();
			viewMemberID.setText(result.getString(1));	//id
			viewMemberName.setText(result.getString(2));	//name
			viewMemberGender.setText(result.getString(3));
			viewMemberWeight.setText(result.getString(4) + " kg.");
			viewMemberDOB.setText(result.getString(5));
			viewMemberAge.setText(result.getString(6));
			//store current age
			String storedDOB = viewMemberDOB.getText();
			StringTokenizer sst= new StringTokenizer(storedDOB , "-");
			
			String inputYear = sst.nextToken();
		    String inputMonth = sst.nextToken();
		    
		    //calculating
		    Date date = new Date();
		    String thisYear = date.toString().substring(date.toString().lastIndexOf(" ") + 1 , date.toString().length());
		    String thisMonth = date.toString().substring(date.toString().indexOf(" ") + 1 , date.toString().indexOf(" ", date.toString().indexOf(" ") + 1)).toUpperCase();
		    int intThisMonth = 0;
		    switch(thisMonth) {
		    case "JAN":
		    	intThisMonth = 1;
		    break;
		    case "FEB":
		    	intThisMonth = 2;
		    break;
		    case "MAR":
		    	intThisMonth = 3;
		    break;
		    case "APR":
		    	intThisMonth = 4;
		    break;
		    case "MAY":
		    	intThisMonth = 5;
		    break;
		    case "JUN":
		    	intThisMonth = 6;
		    break;
		    case "JUL":
		    	intThisMonth = 7;
		    break;
		    case "AUG":
		    	intThisMonth = 8;
		    break;
		    case "SEP":
		    	intThisMonth = 9;
		    break;
		    case "OCT":
		    	intThisMonth = 10;
		    break;
		    case "NOV":
		    	intThisMonth = 11;
		    break;
		    case "DEC":
		    	intThisMonth = 12;
		    break;
		    default:
		    	intThisMonth = 0;
		    }
		    int yearGap = Integer.parseInt(thisYear) - Integer.parseInt(inputYear);
		    if(intThisMonth >= Integer.parseInt(inputMonth)) {
		    	//nothing
		    }else {
		    	yearGap--;
		    }
		    String calculatedAge = Integer.toString(yearGap);
		    viewMemberAge.setText(calculatedAge);
			
			
			viewMemberPhNo1.setText(result.getString(7));
			viewMemberMembership.setText(result.getString(8));
			viewMemberAmount.setText(result.getString(9) + " only");
			viewMemberNote.setText(result.getString(10));
			viewMemberDOJ.setText(result.getString(11));
			viewMemberOccupation.setText(result.getString(12));
			
			try {
				PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_MEMBERS_CONNECTIVITY` WHERE ID = '" + viewMemberID.getText().toString() + "';");
				ResultSet result2 = ps2.executeQuery();
				
				result2.next();
				viewMemberPhNo2.setText(result2.getString(2));
				viewMemberEmail.setText(result2.getString(3));
				viewMemberAddress.setText(result2.getString(4));
				viewMemberCity.setText(result2.getString(5));
				viewMemberCountry.setText(result2.getString(6));
				viewMemberPin.setText(result2.getString(7));
				//result2.getString(8) : holds ID, no need to load ID
				
			}catch(Exception e) {}
		}catch(Exception e) {}
	}
	
	Label viewMemberModifyID = new Label("");
	TextField viewMemberModifyName = new TextField("");
	ComboBox<String> viewMemberModifyGender = new ComboBox<String>(sex);
	TextField viewMemberModifyDOB = new TextField("");
	Label viewMemberModifyAge = new Label("");
	TextField viewMemberModifyOccupation = new TextField("");
	TextArea viewMemberModifyAddress = new TextArea("");
	TextField viewMemberModifyCity = new TextField("");
	TextField viewMemberModifyCountry = new TextField("");
	TextField viewMemberModifyPin = new TextField("");
	TextField viewMemberModifyPhNo1 = new TextField("");
	TextField viewMemberModifyPhNo2 = new TextField("");
	TextField viewMemberModifyEmail = new TextField("");
	ComboBox<String> viewMemberModifyMembership = new ComboBox<String>(savedMemberships);
	TextField viewMemberModifyAmount = new TextField("");
	TextArea viewMemberModifyNote = new TextArea("");
	TextField viewMemberModifyDOJ = new TextField("");
	TextField viewMemberModifyWeight = new TextField("");
	Label weightModifyLabel = new Label("kg.");
	Label dateFormatLabel0 = new Label("(yyyy-mm-dd)");
	Label dateFormatLabel1 = new Label("(yyyy-mm-dd)");
	
	public boolean checkDBMSDateFormat(String input) {
		String regex = "[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]";		//yyyy-mm-dd
		String data = input;
		return data.matches(regex);
	}
	
	public boolean checkAmountDigits(String input) {
		String regex = "[0-9][0-9]*";
		String data = input;
		return data.matches(regex);
	}
	
	public void verifyMemberModifiedData(Stage s) {
		if(viewMemberModifyName.getText().equals("") == true || viewMemberModifyDOB.getText().equals("") == true || viewMemberModifyWeight.getText().equals("") == true || viewMemberModifyPhNo1.getText().equals("") == true || viewMemberModifyDOJ.getText().equals("") == true) {
			Alert ent = new Alert(AlertType.WARNING);
			ent.setTitle("Missing Data");
			ent.setHeaderText("Cannot Update Data");
			ent.setContentText("Some of the field(s) is/are empty. Please fill in Name, DOB , Weight , Phone Number and Date of Joining to continue.\n\n\n");
			ent.show();
		}else {
			//check for valid DOB , DOJ and Amount entered if INVALID 'else' VALID
			if(checkDBMSDateFormat(viewMemberModifyDOB.getText()) == false || checkDBMSDateFormat(viewMemberModifyDOJ.getText()) == false || checkAmountDigits(viewMemberModifyAmount.getText()) == false) {
				Alert ent1 = new Alert(AlertType.WARNING);
				ent1.setTitle("Incorrect Date or Amount");
				ent1.setHeaderText("Cannot Update Data");
				ent1.setContentText("DOB or Date of Joining entered Date format is invalid. Please "
						+ "enter in yyyy-mm-dd format. Or Amount entered is invalid.\n\n\n");
				ent1.show();
			}else{
				try {
					PreparedStatement p0 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Name` = '" + viewMemberModifyName.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p0.execute();
					
					PreparedStatement p1 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Gender` = '" + viewMemberModifyGender.getSelectionModel().getSelectedItem().toString() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p1.execute();
					
					PreparedStatement p2 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Weight` = '" + viewMemberModifyWeight.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p2.execute();
					
					PreparedStatement p3 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `DOB` = '" + viewMemberModifyDOB.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p3.execute();
					
					PreparedStatement p4 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Age` = '" + viewMemberModifyAge.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p4.execute();
					
					PreparedStatement p5 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Membership_Type` = '" + viewMemberModifyMembership.getSelectionModel().getSelectedItem().toString() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p5.execute();
					
					PreparedStatement p6 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Amount` = '" + viewMemberModifyAmount.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p6.execute();
					
					PreparedStatement p7 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Note` = '" + viewMemberModifyNote.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p7.execute();
					
					PreparedStatement p8 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Date_Of_Joining` = '" + viewMemberModifyDOJ.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p8.execute();
					
					PreparedStatement p9 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `Occupation` = '" + viewMemberModifyOccupation.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p9.execute();
					
					PreparedStatement p10 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members` SET `PhoneNumOne` = '" + viewMemberModifyPhNo1.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
					p10.execute();
					
					//now UPDATE PhoneNumberOne row in Member_Connectivity Table WHERE ID =ID of Selected Member
					try {
						PreparedStatement pp0 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `PhoneNumberOne` = '" + viewMemberModifyPhNo1.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp0.execute();
						
						PreparedStatement pp1 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `PhoneNumberTwo` = '" + viewMemberModifyPhNo2.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp1.execute();
						
						PreparedStatement pp2 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `Email` = '" + viewMemberModifyEmail.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp2.execute();
						
						PreparedStatement pp3 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `Address` = '" + viewMemberModifyAddress.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp3.execute();
						
						PreparedStatement pp4 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `CITY` = '" + viewMemberModifyCity.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp4.execute();
						
						PreparedStatement pp5 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `COUNTRY` = '" + viewMemberModifyCountry.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp5.execute();
						
						PreparedStatement pp6 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_members_connectivity` SET `PinCode` = '" + viewMemberModifyPin.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pp6.execute();
						
						//update Total_Amount , Membership_Type in Fee table
						PreparedStatement pF = connection.prepareStatement("UPDATE `studentdatabase`.`gym_fee` SET `Total_Amount` = '" + viewMemberModifyAmount.getText().toString().trim() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pF.execute();
						
						PreparedStatement pF1 = connection.prepareStatement("UPDATE `studentdatabase`.`gym_fee` SET `Membership_Type` = '" + viewMemberModifyMembership.getSelectionModel().getSelectedItem().toString() + "' WHERE (`ID` = '" + viewMemberModifyID.getText().toString() + "');");
						pF1.execute();
						
						Alert done = new Alert(AlertType.INFORMATION);
						done.setTitle("Update Message");
						done.setHeaderText("Update Successful");
						done.setContentText("Member detail(s) has been successfully modified. Press OK "
								+ "to continue.\n\n\n");
						Optional<ButtonType> go = done.showAndWait();
						if(go.get() == ButtonType.OK) {
							membersScreen(s);
						}
						
					}catch(Exception e) {}
				}catch(Exception f){}
				
			}
		}
	}
	
	public void loadMemberModifyTextFieldsAndAreas() {
		//load available Memberships Names
		savedMemberships.clear();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from `studentdatabase`.`gym_memberships`;");  
		    ResultSet rs2 = stmt.executeQuery();  
		    while(rs2.next()){  
		    	savedMemberships.add(rs2.getString(1).toString());
		    }
		}catch(Exception e) {}
		viewMemberModifyMembership.getSelectionModel().select(viewMemberMembership.getText());
		
		viewMemberModifyID.setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");
		weightModifyLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		viewMemberModifyAge.setStyle("-fx-font-size: 15px;");
		
		viewMemberModifyAmount.setText("");
		
		dateFormatLabel0.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		dateFormatLabel1.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		
		viewMemberModifyAddress.setWrapText(true);
		viewMemberModifyNote.setWrapText(true);
		
		viewMemberModifyID.setText(viewMemberID.getText());
		viewMemberModifyName.setText(viewMemberName.getText());
		viewMemberModifyDOB.setText(viewMemberDOB.getText());
		viewMemberModifyAge.setText(viewMemberAge.getText());
		viewMemberModifyOccupation.setText(viewMemberOccupation.getText());
		viewMemberModifyAddress.setText(viewMemberAddress.getText());
		viewMemberModifyCity.setText(viewMemberCity.getText());
		viewMemberModifyCountry.setText(viewMemberCountry.getText());
		viewMemberModifyPin.setText(viewMemberPin.getText());
		viewMemberModifyPhNo1.setText(viewMemberPhNo1.getText());
		viewMemberModifyPhNo2.setText(viewMemberPhNo2.getText());
		viewMemberModifyEmail.setText(viewMemberEmail.getText());
		viewMemberModifyAmount.setText(viewMemberAmount.getText().substring(0 , viewMemberAmount.getText().indexOf(" ")));
		viewMemberModifyNote.setText(viewMemberNote.getText());
		viewMemberModifyDOJ.setText(viewMemberDOJ.getText());
		viewMemberModifyWeight.setText(viewMemberWeight.getText().substring(0 , viewMemberWeight.getText().indexOf(" ")));
		
		viewMemberModifyGender.getSelectionModel().select(viewMemberGender.getText());
		
	}
	
	public void temporarilyUpdateMemberMembershipAmount(){
		try {
			String name = viewMemberModifyMembership.getSelectionModel().getSelectedItem().toString();
			PreparedStatement p = connection.prepareStatement("SELECT Amount FROM `studentdatabase`.`GYM_MEMBERSHIPS` WHERE Name = '" + name + "';");
			ResultSet r = p.executeQuery();
			r.next();
			viewMemberModifyAmount.setText(r.getString(1) + "");
		}catch(Exception e) {}
	}
	
	public void updateMemberDetailsScreen(Stage s , String selectedMemberID){
		loadMemberModifyTextFieldsAndAreas();
		
		Button cancelModifications = new Button("Cancel");
		Button saveModifications = new Button("Save Changes");
		
		cancelModifications.setPrefSize(140.0, 32.0);
		saveModifications.setPrefSize(140.0, 32.0);
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
    			"-fx-font-weight: bold;" +
    			"-fx-font-family: 'Arial';" +
    			"-fx-font-size: 13px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		
		cancelModifications.setStyle(delN);
		cancelModifications.setOnMouseMoved(e-> cancelModifications.setStyle(delH));		//hover
		cancelModifications.setOnMouseReleased(e-> cancelModifications.setStyle(delN));		//normal
		cancelModifications.setOnMousePressed(e-> cancelModifications.setStyle(delP));		//pressed
		cancelModifications.setOnMouseExited(e-> cancelModifications.setStyle(delN));		//normal
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		
		saveModifications.setStyle(goN);
		saveModifications.setOnMouseMoved(e-> saveModifications.setStyle(goH));			//hover
		saveModifications.setOnMouseReleased(e-> saveModifications.setStyle(goN));		//normal
		saveModifications.setOnMousePressed(e-> saveModifications.setStyle(goP));		//pressed
		saveModifications.setOnMouseExited(e-> saveModifications.setStyle(goN));		//normal
		
		Label membersLabel = new Label(" Modify Member Detail(s)");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		
		//controls
		back.setOnAction(e -> viewMemberDetailsScreen(s , selectedMemberID));
		cancelModifications.setOnAction(e -> membersScreen(s));
		
		CheckDuplicateEmails cdcd = new CheckDuplicateEmails();
		
		saveModifications.setOnAction(e -> {
			this.isCorrect = true;
			
			if(viewMemberModifyPin.getText().trim().equals("") == false) {
				if(checkPin(viewMemberModifyPin.getText().trim()) == false) {
					this.isCorrect = false;
				}
			}
			if(viewMemberModifyEmail.getText().trim().equals("") == false) {
				if(viewMemberModifyEmail.getText().trim().contains("@") == false) {
					this.isCorrect = false;
				}
			}
			if(Integer.parseInt(viewMemberModifyAge.getText().trim()) <= 0) {
				this.isCorrect = false;
			}
			if(viewMemberModifyPhNo1.getText().trim().equals("") == false) {
				if(checkPhoneNumber(viewMemberModifyPhNo1.getText().trim()) == false) {
					this.isCorrect = false;
				}
			}
			if(viewMemberModifyPhNo2.getText().trim().equals("") == false) {
				if(checkPhoneNumber(viewMemberModifyPhNo2.getText().trim()) == false) {
					this.isCorrect = false;
				}
			}
			
			if(this.isCorrect == true) {
				if(viewMemberModifyEmail.getText().trim().equals("") == true) {
					verifyMemberModifiedData(s);
				}else {
					if(cdcd.checkIfEmailPresentInTrainersTable(connection, viewMemberModifyEmail.getText().trim()) == true && cdcd.checkIfDuplicateEmail(connection, "member", viewMemberModifyID.getText().trim() , viewMemberModifyEmail.getText().trim()) == true) {
						verifyMemberModifiedData(s);
					}else {
						Alert duplicateEmail = new Alert(AlertType.WARNING);
						duplicateEmail.setTitle("Warning");
						duplicateEmail.setHeaderText("Change Email");
						duplicateEmail.setContentText("Entered email is already in use. Change "
								+ "your email and try again.\n\n\n");
						duplicateEmail.show();
					}
				}
			}else {
				Alert al = new Alert(AlertType.WARNING);
				al.setTitle("Warning");
				al.setHeaderText("Invalid Data Entered");
				al.setContentText("Some of the entered data might be invalid. "
						+ "Make sure that phone number is of ten digits and email entered "
						+ "is valid.\nMake sure that age is positive number and "
						+ "PIN Code consists of only six digits.\nCheck and try again.\n\n\n");
				al.show();
			}
		});
		viewMemberModifyMembership.setOnAction(e -> {		//update Amount
			temporarilyUpdateMemberMembershipAmount();
		});
		viewMemberModifyDOB.setOnKeyReleased(e -> {
			if(checkDBMSDateFormat(viewMemberModifyDOB.getText().toString()) == true) {		//DOB regex
				calculateCurrentAge(viewMemberModifyDOB.getText().toString() , "updateMember");
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0 - 20.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//cancelModifications
		AnchorPane.setRightAnchor(cancelModifications, 27.0  + 100.0 + 17.0 + 40.0);
		AnchorPane.setBottomAnchor(cancelModifications, 22.0);
		//saveModifications
		AnchorPane.setRightAnchor(saveModifications, 27.0);
		AnchorPane.setBottomAnchor(saveModifications, 22.0);
		//viewMemberIDLabel
		AnchorPane.setTopAnchor(viewMemberIDLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberIDLabel, 60.0);
		//viewMemberNameLabel
		AnchorPane.setTopAnchor(viewMemberNameLabel, 160.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberNameLabel, 60.0);
		//viewMemberGenderLabel
		AnchorPane.setTopAnchor(viewMemberGenderLabel, 190.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberGenderLabel, 60.0);
		//viewMemberDOBLabel
		AnchorPane.setTopAnchor(viewMemberDOBLabel, 220.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOBLabel, 60.0);
		//viewMemberAgeLabel
		AnchorPane.setTopAnchor(viewMemberAgeLabel, 250.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAgeLabel, 60.0);
		//viewMemberWeightLabel
		AnchorPane.setTopAnchor(viewMemberWeightLabel, 280.0 + 25.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberWeightLabel, 60.0);
		//weightModifyLabel
		AnchorPane.setTopAnchor(weightModifyLabel, 280.0 + 25.0 - 20.0);
		AnchorPane.setLeftAnchor(weightModifyLabel, 290.0);
		//viewMemberOccupationLabel
		AnchorPane.setTopAnchor(viewMemberOccupationLabel, 310.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberOccupationLabel, 60.0);
		//viewMemberAddressLabel
		AnchorPane.setTopAnchor(viewMemberAddressLabel, 340.0 + 35.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAddressLabel, 60.0);
		//viewMemberCityLabel
		AnchorPane.setTopAnchor(viewMemberCityLabel, 370.0 + 40.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCityLabel, 60.0);
		//viewMemberCountryLabel
		AnchorPane.setTopAnchor(viewMemberCountryLabel, 400.0 + 45.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCountryLabel, 60.0);
		//viewMemberPinLabel
		AnchorPane.setTopAnchor(viewMemberPinLabel, 430.0 + 50.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPinLabel, 60.0);
		//viewMemberPhNoLabel
		AnchorPane.setTopAnchor(viewMemberPhNoLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPhNoLabel, 460.0);
		//viewMemberEmailLabel
		AnchorPane.setTopAnchor(viewMemberEmailLabel, 190.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberEmailLabel, 460.0);
		//viewMemberMembershipLabel
		AnchorPane.setTopAnchor(viewMemberMembershipLabel, 220.0 + 21.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberMembershipLabel, 460.0);
		//viewMemberAmountLabel
		AnchorPane.setTopAnchor(viewMemberAmountLabel, 250.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAmountLabel, 460.0);
		//viewMemberNoteLabel
		AnchorPane.setTopAnchor(viewMemberNoteLabel, 280.0 + 35.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberNoteLabel, 460.0);
		//viewMemberDOJLabel
		AnchorPane.setTopAnchor(viewMemberDOJLabel, 370.0 + 56.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOJLabel, 460.0);
		//viewMemberModifyID
		AnchorPane.setTopAnchor(viewMemberModifyID, 129.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyID, 90.0);
		//viewMemberModifyName
		AnchorPane.setTopAnchor(viewMemberModifyName, 159.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyName, 120.0);
		AnchorPane.setRightAnchor(viewMemberModifyName, 480.0);
		//viewMemberModifyGender
		AnchorPane.setTopAnchor(viewMemberModifyGender, 189.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyGender, 125.0);
		AnchorPane.setRightAnchor(viewMemberModifyGender, 640.0);
		//viewMemberModifyDOB
		AnchorPane.setTopAnchor(viewMemberModifyDOB, 219.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyDOB, 110.0);
		//dateFormatLabel0
		AnchorPane.setTopAnchor(dateFormatLabel0, 219.0 + 17.0 - 20.0);
		AnchorPane.setLeftAnchor(dateFormatLabel0, 265.0);
		//viewMemberModifyAge
		AnchorPane.setTopAnchor(viewMemberModifyAge, 249.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyAge, 110.0);
		//viewMemberModifyOccupation
		AnchorPane.setTopAnchor(viewMemberModifyOccupation, 309.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyOccupation, 160.0);
		AnchorPane.setRightAnchor(viewMemberModifyOccupation, 480.0);
		//viewMemberModifyAddress
		AnchorPane.setTopAnchor(viewMemberModifyAddress, 339.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyAddress, 130.0);
		AnchorPane.setRightAnchor(viewMemberModifyAddress, 480.0);
		AnchorPane.setBottomAnchor(viewMemberModifyAddress, 120.0 + 20.0);
		//viewMemberModifyCity
		AnchorPane.setTopAnchor(viewMemberModifyCity, 369.0 + 40.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyCity, 110.0);
		AnchorPane.setRightAnchor(viewMemberModifyCity, 480.0);
		//viewMemberModifyCountry
		AnchorPane.setTopAnchor(viewMemberModifyCountry, 399.0 + 45.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyCountry, 130.0);
		AnchorPane.setRightAnchor(viewMemberModifyCountry, 480.0);
		//viewMemberModifyPin
		AnchorPane.setTopAnchor(viewMemberModifyPin, 430.0 + 50.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyPin, 140.0);
		//viewMemberModifyPhNo1
		AnchorPane.setTopAnchor(viewMemberModifyPhNo1, 129.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyPhNo1, 600.0);
		//viewMemberModifyPhNo2
		AnchorPane.setTopAnchor(viewMemberModifyPhNo2, 159.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyPhNo2, 600.0);
		//viewMemberModifyEmail
		AnchorPane.setTopAnchor(viewMemberModifyEmail, 189.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyEmail, 520.0);
		AnchorPane.setRightAnchor(viewMemberModifyEmail, 55.0);
		//viewMemberModifyMembership
		AnchorPane.setTopAnchor(viewMemberModifyMembership, 219.0 + 21.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyMembership, 610.0);
		AnchorPane.setRightAnchor(viewMemberModifyMembership, 100.0);
		//viewMemberModifyAmount
		AnchorPane.setTopAnchor(viewMemberModifyAmount, 247.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyAmount, 550.0);
		AnchorPane.setRightAnchor(viewMemberModifyAmount, 260.0);
		//viewMemberModifyNote
		AnchorPane.setTopAnchor(viewMemberModifyNote, 279.0 + 32.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyNote, 510.0);
		AnchorPane.setRightAnchor(viewMemberModifyNote, 55.0);
		AnchorPane.setBottomAnchor(viewMemberModifyNote, 120.0 + 20.0);
		//viewMemberModifyDOJ
		AnchorPane.setTopAnchor(viewMemberModifyDOJ, 369.0 + 56.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyDOJ, 580.0);
		//dateFormatLabel1
		AnchorPane.setTopAnchor(dateFormatLabel1, 370.0 + 56.0 + 14.0 + 2.0 - 20.0);
		AnchorPane.setLeftAnchor(dateFormatLabel1, 736.0);
		//viewMemberModifyWeight
		AnchorPane.setTopAnchor(viewMemberModifyWeight, 279.0 + 24.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberModifyWeight, 130.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, underline , membersLabel ,
				viewMemberIDLabel , viewMemberNameLabel , viewMemberGenderLabel , viewMemberDOBLabel ,
				viewMemberPhNoLabel , viewMemberEmailLabel , viewMemberAddressLabel , viewMemberCityLabel ,
				viewMemberPinLabel , viewMemberCountryLabel , viewMemberOccupationLabel ,
				viewMemberMembershipLabel , viewMemberAgeLabel , viewMemberDOJLabel , viewMemberAmountLabel ,
				viewMemberNoteLabel , viewMemberModifyID , viewMemberModifyName , viewMemberModifyGender , viewMemberModifyDOB ,
				viewMemberModifyAge , viewMemberModifyOccupation , viewMemberModifyAddress , viewMemberModifyCity ,
				viewMemberModifyCountry , viewMemberModifyPin , viewMemberModifyPhNo1 , viewMemberModifyPhNo2 , viewMemberModifyEmail ,
				viewMemberModifyMembership , viewMemberModifyAmount , viewMemberModifyNote , viewMemberModifyDOJ , viewMemberModifyWeight ,
				viewMemberWeightLabel , weightModifyLabel , dateFormatLabel0 , dateFormatLabel1 ,
				cancelModifications , saveModifications);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label idTX = new Label("");
	Label nameTX = new Label("");
	Label genderTX = new Label("");
	Label ratingTX = new Label("");
	
	public void prepareTrainersTableX(String selectedMemberMembership) {
		loadAllPotentialSearchElements();
		
		try {
			try {
				//check whether GYM_TRAINERS_CONNECTIVITY TABLE is present or not
				PreparedStatement test0 = connection.prepareStatement("select * from `studentdatabase`.`GYM_TRAINERS_CONNECTIVITY`;");  
			    ResultSet run = test0.executeQuery();
			}catch(Exception e) {
				//create GYM_TRAINERS_CONNECTIVITY TABLE
				String code1 = "CREATE TABLE `studentdatabase`.GYM_TRAINERS_CONNECTIVITY (PhoneNumberOne VARCHAR(13),"
						+ "PhoneNumberTwo VARCHAR(13) , Email VARCHAR(50) , Address VARCHAR(200) , CITY VARCHAR(100) ,"
						+ "COUNTRY VARCHAR(100) , PinCode VARCHAR(20) , ID VARCHAR(10) PRIMARY KEY);";
				PreparedStatement first = connection.prepareStatement(code1);
				first.execute();
			}
			try {
				//check whether GYM_TRAINERS TABLE is present or not
				PreparedStatement test1 = connection.prepareStatement("select * from `studentdatabase`.`GYM_TRAINERS`;");  
			    ResultSet run1 = test1.executeQuery();
			}catch(Exception e) {
				String code2 = "CREATE TABLE `studentdatabase`.GYM_TRAINERS (ID VARCHAR(10) PRIMARY KEY , Name VARCHAR(30) ,"
						+ "Gender VARCHAR(15) , Salary INT , DOB VARCHAR(12) , Age INT , PhoneNumOne VARCHAR(13) ,"
						+ "Membership_Alloted VARCHAR(20) , Trainer_Type VARCHAR(30) , Date_Of_Joining VARCHAR(12) , RATING VARCHAR(5)"
						+ ", FOREIGN KEY (ID) REFERENCES `studentdatabase`.`GYM_TRAINERS_CONNECTIVITY` (ID)"
						+ ", FOREIGN KEY (Membership_Alloted) REFERENCES `studentdatabase`.`GYM_MEMBERSHIPS` (Name));";
				PreparedStatement second = connection.prepareStatement(code2);
				second.execute();
			}
			String className = "";
			try {
				trainersTableX.getItems().clear();
				PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_TRAINERS` WHERE `Membership_Alloted` = '" + selectedMemberMembership + "';");
			    ResultSet rs = command.executeQuery();
			    while(rs.next()){  
			    	String trainerID = rs.getString(1);
			    	String trainerName = rs.getString(2);
			    	
			    	trainersTableX.getItems().add(new TrainersListClass(trainerID , trainerName));
			    }
			}catch(Exception e) {}
		}catch(Exception e) {}
	}
	
	int previousStar = 0;
	
	public void loadTrainerViewData(String tID) {
		stars = "0";
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_trainers` WHERE `ID` = '" + tID + "';");
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			idTX.setText(rs.getString(1));
			nameTX.setText(rs.getString(2));
			genderTX.setText(rs.getString(3));
			ratingTX.setText(rs.getString(11));
			if(ratingTX.getText().equalsIgnoreCase("NULL") == true) {
				ratingTX.setText("No ratings yet.");
				star1button.setVisible(false);
				star2button.setVisible(false);
				star3button.setVisible(false);
				star4button.setVisible(false);
				star5button.setVisible(false);
			}else {
				String memberCount = "";
				try {
					PreparedStatement ps1 = connection.prepareStatement("SELECT COUNT(`Member_ID`) AS 'Members' FROM `studentdatabase`.`rating_" + tID + "`;");
					ResultSet rs1 = ps1.executeQuery();
					rs1.next();
					memberCount = rs1.getString(1);
					String fabricated = ratingTX.getText() + " by " + memberCount + " members.";
					ratingTX.setText(fabricated);
					//load rating star images
					if(Double.parseDouble(rs.getString(11)) >= 0.1 && Double.parseDouble(rs.getString(11)) < 2.0) {
						star1button.setVisible(true);
						star2button.setVisible(false);
						star3button.setVisible(false);
						star4button.setVisible(false);
						star5button.setVisible(false);
					}else if(Double.parseDouble(rs.getString(11)) >= 2.0 && Double.parseDouble(rs.getString(11)) < 3.0) {
						star1button.setVisible(false);
						star2button.setVisible(true);
						star3button.setVisible(false);
						star4button.setVisible(false);
						star5button.setVisible(false);
					}else if(Double.parseDouble(rs.getString(11)) >= 3.0 && Double.parseDouble(rs.getString(11)) < 4.0) {
						star1button.setVisible(false);
						star2button.setVisible(false);
						star3button.setVisible(true);
						star4button.setVisible(false);
						star5button.setVisible(false);
					}else if(Double.parseDouble(rs.getString(11)) >= 4.0 && Double.parseDouble(rs.getString(11)) < 5.0) {
						star1button.setVisible(false);
						star2button.setVisible(false);
						star3button.setVisible(false);
						star4button.setVisible(true);
						star5button.setVisible(false);
					}else if(Double.parseDouble(rs.getString(11)) >= 5.0) {
						star1button.setVisible(false);
						star2button.setVisible(false);
						star3button.setVisible(false);
						star4button.setVisible(false);
						star5button.setVisible(true);
					}
				}catch(Exception e) {}
			}
			try {
				PreparedStatement ps2 = connection.prepareStatement("SELECT `Rating` FROM `studentdatabase`.`rating_" + idTX.getText() + "` WHERE `Member_ID` = '" + viewMemberID.getText() + "';");
				ResultSet rs2 = ps2.executeQuery();
				boolean flag = false;
				previousStar = 0;
				while(rs2.next()) {
					flag = true;
					if(rs2.getString(1).equals("1") == true) {
						previousStar = 1;
					}else if(rs2.getString(1).equals("2") == true) {
						previousStar = 2;
					}else if(rs2.getString(1).equals("3") == true) {
						previousStar = 3;
					}else if(rs2.getString(1).equals("4") == true) {
						previousStar = 4;
					}else if(rs2.getString(1).equals("5") == true) {
						previousStar = 5;
					}else {
						previousStar = 0;
					}
				}
			}catch(Exception e) {}
			
		}catch(Exception e) {}
	}
	
	//star5button
	Button star5button = new Button();
	FileInputStream inputStar5Image;
	Image star5Image;
	//star4button
	Button star4button = new Button();
	FileInputStream inputStar4Image;
	Image star4Image;
	//star3button
	Button star3button = new Button();
	FileInputStream inputStar3Image;
	Image star3Image;
	//star2button
	Button star2button = new Button();
	FileInputStream inputStar2Image;
	Image star2Image;
	//star1button
	Button star1button = new Button();
	FileInputStream inputStar1Image;
	Image star1Image;
	
	//pseudo5button
	Button pseudo5button = new Button();
	//pseudo4button
	Button pseudo4button = new Button();
	//pseudo3button
	Button pseudo3button = new Button();
	//pseudo2button
	Button pseudo2button = new Button();
	//pseudo1button
	Button pseudo1button = new Button();
	
	//rate5button
	Button rate5button = new Button();
	FileInputStream inputRate5Image;
	Image rate5Image;
	//rate4button
	Button rate4button = new Button();
	FileInputStream inputRate4Image;
	Image rate4Image;
	//rate3button
	Button rate3button = new Button();
	FileInputStream inputRate3Image;
	Image rate3Image;
	//rate2button
	Button rate2button = new Button();
	FileInputStream inputRate2Image;
	Image rate2Image;
	//rate1button
	Button rate1button = new Button();
	FileInputStream inputRate1Image;
	Image rate1Image;
	//rate0button
	Button rate0button = new Button();
	FileInputStream inputRate0Image;
	Image rate0Image;
	
	Button inv1 = new Button("1 ");
	Button inv2 = new Button("2 ");
	Button inv3 = new Button("3 ");
	Button inv4 = new Button("4 ");
	Button inv5 = new Button("5 ");
	
	public void loadRatingStars() {
		try {
			inputStar5Image = new FileInputStream("resources/logos/stars/onlyFive.jpg");
			star5Image = new Image(inputStar5Image);
			star5button.setGraphic(new ImageView(star5Image));
			star5button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputStar4Image = new FileInputStream("resources/logos/stars/onlyFour.jpg");
			star4Image = new Image(inputStar4Image);
			star4button.setGraphic(new ImageView(star4Image));
			star4button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputStar3Image = new FileInputStream("resources/logos/stars/onlyThree.jpg");
			star3Image = new Image(inputStar3Image);
			star3button.setGraphic(new ImageView(star3Image));
			star3button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputStar2Image = new FileInputStream("resources/logos/stars/onlyTwo.jpg");
			star2Image = new Image(inputStar2Image);
			star2button.setGraphic(new ImageView(star2Image));
			star2button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputStar1Image = new FileInputStream("resources/logos/stars/onlyOne.jpg");
			star1Image = new Image(inputStar1Image);
			star1button.setGraphic(new ImageView(star1Image));
			star1button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		
		star1button.setVisible(false);
		star2button.setVisible(false);
		star3button.setVisible(false);
		star4button.setVisible(false);
		star5button.setVisible(false);
		
		pseudo1button.setVisible(false);
		pseudo2button.setVisible(false);
		pseudo3button.setVisible(false);
		pseudo4button.setVisible(false);
		pseudo5button.setVisible(false);
		
		try {
			inputRate5Image = new FileInputStream("resources/logos/stars/rateFive.jpg");
			rate5Image = new Image(inputRate5Image);
			rate5button.setGraphic(new ImageView(rate5Image));
			rate5button.setStyle("-fx-background-color: '#ccffe6';");
			
			pseudo5button.setGraphic(new ImageView(rate5Image));
			pseudo5button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRate4Image = new FileInputStream("resources/logos/stars/rateFour.jpg");
			rate4Image = new Image(inputRate4Image);
			rate4button.setGraphic(new ImageView(rate4Image));
			rate4button.setStyle("-fx-background-color: '#ccffe6';");
			
			pseudo4button.setGraphic(new ImageView(rate4Image));
			pseudo4button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRate3Image = new FileInputStream("resources/logos/stars/rateThree.jpg");
			rate3Image = new Image(inputRate3Image);
			rate3button.setGraphic(new ImageView(rate3Image));
			rate3button.setStyle("-fx-background-color: '#ccffe6';");
			
			pseudo3button.setGraphic(new ImageView(rate3Image));
			pseudo3button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRate2Image = new FileInputStream("resources/logos/stars/rateTwo.jpg");
			rate2Image = new Image(inputRate2Image);
			rate2button.setGraphic(new ImageView(rate2Image));
			rate2button.setStyle("-fx-background-color: '#ccffe6';");
			
			pseudo2button.setGraphic(new ImageView(rate2Image));
			pseudo2button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRate1Image = new FileInputStream("resources/logos/stars/rateOne.jpg");
			rate1Image = new Image(inputRate1Image);
			rate1button.setGraphic(new ImageView(rate1Image));
			rate1button.setStyle("-fx-background-color: '#ccffe6';");
			
			pseudo1button.setGraphic(new ImageView(rate1Image));
			pseudo1button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRate0Image = new FileInputStream("resources/logos/stars/rateZero.jpg");
			rate0Image = new Image(inputRate0Image);
			rate0button.setGraphic(new ImageView(rate0Image));
			rate0button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		
		rate0button.setVisible(false);
		rate1button.setVisible(false);
		rate2button.setVisible(false);
		rate3button.setVisible(false);
		rate4button.setVisible(false);
		rate5button.setVisible(false);
		
		inv1.setOpacity(0.0);
		inv2.setOpacity(0.0);
		inv3.setOpacity(0.0);
		inv4.setOpacity(0.0);
		inv5.setOpacity(0.0);
		
		inv1.setVisible(false);
		inv2.setVisible(false);
		inv3.setVisible(false);
		inv4.setVisible(false);
		inv5.setVisible(false);
		
	}
	
	String stars = "0";
	boolean isInvPressed = false;
	public void setIsInvPressed(boolean input) {
		this.isInvPressed = input;
	}
	
	public void setStars0() {
		stars = "0";
	}
	public void setStars1() {
		stars = "1";
	}
	public void setStars2() {
		stars = "2";
	}
	public void setStars3() {
		stars = "3";
	}
	public void setStars4() {
		stars = "4";
	}
	public void setStars5() {
		stars = "5";
	}
	
	public void giveTrainerRatingScreen(Stage s , String selectedMemberID , String selectedMemberMembership) {
		loadRatingStars();
		stars = "0";
		
		prepareTrainersTableX(selectedMemberMembership);
		
		idTX.setText("");
		nameTX.setText("");
		genderTX.setText("");
		ratingTX.setText("");
		
		pseudo1button.setVisible(false);
		pseudo2button.setVisible(false);
		pseudo3button.setVisible(false);
		pseudo4button.setVisible(false);
		pseudo5button.setVisible(false);
		
		Button submit = new Button("Submit");
		
		submit.setPrefSize(120.0, 32.0);
		
		submit.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;");
		submit.setOnMouseExited(e -> submit.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		submit.setOnMouseReleased(e -> submit.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		submit.setOnMouseEntered(e -> submit.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		submit.setOnMousePressed(e -> submit.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 13px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		Label idLabel = new Label("ID:");
		Label nameLabel = new Label("Name:");
		Label genderLabel = new Label("Sex:");
		Label ratingGivenLabel = new Label("Rating given:");
		Label giveRatingLabel = new Label(" Give your Rating");
		Label underline2 = new Label("\t\t\t\t\t\t\t\t\t\t\t\t\t      ");
		Label myRatingLabel = new Label("My Rating:");
		
		idLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		nameLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		idLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		genderLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		ratingGivenLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		giveRatingLabel.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: '595959';");
		underline2.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		underline2.setUnderline(true);
		myRatingLabel.setStyle("-fx-font-size: 16px;" + "-fx-text-fill: '595959';");
		
		idTX.setStyle("-fx-font-size: 16.5px;" + "-fx-font-weight: bold;");
		nameTX.setStyle("-fx-font-size: 16.5px;");
		genderTX.setStyle("-fx-font-size: 16.5px;");
		ratingTX.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		
		//controls
		back.setOnAction(e -> {
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			updateBackButton(currentTheme);
			viewMemberDetailsScreen(s , selectedMemberID);
		});
		trainersTableX.setOnMousePressed(e -> {
			if(trainersTableX.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass tlc = (TrainersListClass) trainersTableX.getSelectionModel().getSelectedItem();
				String tID = tlc.getTrainerID();
				loadTrainerViewData(tID);
				isInvPressed = false;
				rate0button.setVisible(true);
				inv1.setVisible(true);
				inv2.setVisible(true);
				inv3.setVisible(true);
				inv4.setVisible(true);
				inv5.setVisible(true);
				
				if(previousStar == 1) {
					pseudo1button.setVisible(true);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 2) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(true);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 3) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(true);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 4) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(true);
					pseudo5button.setVisible(false);
				}else if(previousStar == 5) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(true);
				}else {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}
			}
		});
		trainersTableX.setOnKeyReleased(e -> {
			if(trainersTableX.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass tlc = (TrainersListClass) trainersTableX.getSelectionModel().getSelectedItem();
				String tID = tlc.getTrainerID();
				loadTrainerViewData(tID);
				isInvPressed = false;
				rate0button.setVisible(true);
				inv1.setVisible(true);
				inv2.setVisible(true);
				inv3.setVisible(true);
				inv4.setVisible(true);
				inv5.setVisible(true);
				
				if(previousStar == 1) {
					pseudo1button.setVisible(true);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 2) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(true);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 3) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(true);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}else if(previousStar == 4) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(true);
					pseudo5button.setVisible(false);
				}else if(previousStar == 5) {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(true);
				}else {
					pseudo1button.setVisible(false);
					pseudo2button.setVisible(false);
					pseudo3button.setVisible(false);
					pseudo4button.setVisible(false);
					pseudo5button.setVisible(false);
				}
			}
		});
		RatingEntry rt = new RatingEntry();
		
		submit.setOnAction(e -> {
			if(trainersTableX.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass tlc = (TrainersListClass) trainersTableX.getSelectionModel().getSelectedItem();
				String tID = tlc.getTrainerID();
				if(stars.equals("0") == false) {
					boolean isRated = rt.makeNewRatingEntry(connection , tID , viewMemberID.getText() , stars);
					
					if(true) {
						Alert rated = new Alert(AlertType.INFORMATION);
						rated.setTitle("Message");
						rated.setHeaderText("Thank you for Rating");
						rated.setContentText("Trainer has been rated successfully. Thank you.\n\n\n");
						Optional<ButtonType> op = rated.showAndWait();
						if(op.get() == ButtonType.OK) {
							menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
							updateBackButton(currentTheme);
							viewMemberDetailsScreen(s , viewMemberID.getText());
						}
					}
				}
			}
		});
		
		this.isInvPressed = false;
		inv1.setOnAction(e -> {
			setStars1();
			setIsInvPressed(true);
			rate0button.setVisible(false);
			rate1button.setVisible(true);
			rate2button.setVisible(false);
			rate3button.setVisible(false);
			rate4button.setVisible(false);
			rate5button.setVisible(false);
		});
		inv2.setOnAction(e -> {
			setStars2();
			setIsInvPressed(true);
			rate0button.setVisible(false);
			rate1button.setVisible(false);
			rate2button.setVisible(true);
			rate3button.setVisible(false);
			rate4button.setVisible(false);
			rate5button.setVisible(false);
		});
		inv3.setOnAction(e -> {
			setStars3();
			setIsInvPressed(true);
			rate0button.setVisible(false);
			rate1button.setVisible(false);
			rate2button.setVisible(false);
			rate3button.setVisible(true);
			rate4button.setVisible(false);
			rate5button.setVisible(false);
		});
		inv4.setOnAction(e -> {
			setStars4();
			setIsInvPressed(true);
			rate0button.setVisible(false);
			rate1button.setVisible(false);
			rate2button.setVisible(false);
			rate3button.setVisible(false);
			rate4button.setVisible(true);
			rate5button.setVisible(false);
		});
		inv5.setOnAction(e -> {
			setStars5();
			setIsInvPressed(true);
			rate0button.setVisible(false);
			rate1button.setVisible(false);
			rate2button.setVisible(false);
			rate3button.setVisible(false);
			rate4button.setVisible(false);
			rate5button.setVisible(true);
		});
		
		inv1.setOnMouseEntered(e -> {
			if(isInvPressed == false) {
				rate0button.setVisible(false);
				rate1button.setVisible(true);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv2.setOnMouseEntered(e -> {
			if(isInvPressed == false) {
				rate0button.setVisible(false);
				rate1button.setVisible(false);
				rate2button.setVisible(true);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv3.setOnMouseEntered(e -> {
			if(isInvPressed == false) {
				rate0button.setVisible(false);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(true);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv4.setOnMouseEntered(e -> {
			if(isInvPressed == false) {
				rate0button.setVisible(false);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(true);
				rate5button.setVisible(false);
			}
		});
		inv5.setOnMouseEntered(e -> {
			if(isInvPressed == false) {
				rate0button.setVisible(false);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(true);
			}
		});
		
		inv1.setOnMouseExited(e -> {
			if(isInvPressed == false) {
				setStars0();
				rate0button.setVisible(true);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv2.setOnMouseExited(e -> {
			if(isInvPressed == false) {
				setStars0();
				rate0button.setVisible(true);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv3.setOnMouseExited(e -> {
			if(isInvPressed == false) {
				setStars0();
				rate0button.setVisible(true);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv4.setOnMouseExited(e -> {
			if(isInvPressed == false) {
				setStars0();
				rate0button.setVisible(true);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		inv5.setOnMouseExited(e -> {
			if(isInvPressed == false) {
				setStars0();
				rate0button.setVisible(true);
				rate1button.setVisible(false);
				rate2button.setVisible(false);
				rate3button.setVisible(false);
				rate4button.setVisible(false);
				rate5button.setVisible(false);
			}
		});
		pseudo1button.setOnMouseEntered(e -> {
			pseudo1button.setVisible(false);
			pseudo2button.setVisible(false);
			pseudo3button.setVisible(false);
			pseudo4button.setVisible(false);
			pseudo5button.setVisible(false);
		});
		pseudo2button.setOnMouseEntered(e -> {
			pseudo1button.setVisible(false);
			pseudo2button.setVisible(false);
			pseudo3button.setVisible(false);
			pseudo4button.setVisible(false);
			pseudo5button.setVisible(false);
		});
		pseudo3button.setOnMouseEntered(e -> {
			pseudo1button.setVisible(false);
			pseudo2button.setVisible(false);
			pseudo3button.setVisible(false);
			pseudo4button.setVisible(false);
			pseudo5button.setVisible(false);
		});
		pseudo4button.setOnMouseEntered(e -> {
			pseudo1button.setVisible(false);
			pseudo2button.setVisible(false);
			pseudo3button.setVisible(false);
			pseudo4button.setVisible(false);
			pseudo5button.setVisible(false);
		});
		pseudo5button.setOnMouseEntered(e -> {
			pseudo1button.setVisible(false);
			pseudo2button.setVisible(false);
			pseudo3button.setVisible(false);
			pseudo4button.setVisible(false);
			pseudo5button.setVisible(false);
		});
		
		Label membersLabel = new Label(" Rate  your Trainer(s)");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 80.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 102.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//submit
		AnchorPane.setRightAnchor(submit, 27.0);
		AnchorPane.setBottomAnchor(submit, 22.0);
		//trainersTableX
		AnchorPane.setTopAnchor(trainersTableX, 100.0 + 50.0);
		AnchorPane.setLeftAnchor(trainersTableX, 65.0);
		AnchorPane.setBottomAnchor(trainersTableX, 70.0);
		//idLabel
		AnchorPane.setTopAnchor(idLabel, 100.0 + 45.0);
		AnchorPane.setLeftAnchor(idLabel, 460.0);
		//idX
		AnchorPane.setTopAnchor(idTX, 100.0 + 44.0);
		AnchorPane.setLeftAnchor(idTX, 500.0);
		//nameLabel
		AnchorPane.setTopAnchor(nameLabel, 100.0 + 45.0 + 40.0);
		AnchorPane.setLeftAnchor(nameLabel, 460.0);
		//nameTX
		AnchorPane.setTopAnchor(nameTX, 100.0 + 44.0 + 40.0);
		AnchorPane.setLeftAnchor(nameTX, 520.0);
		//genderLabel
		AnchorPane.setTopAnchor(genderLabel, 100.0 + 45.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(genderLabel, 460.0);
		//genderTX
		AnchorPane.setTopAnchor(genderTX, 100.0 + 44.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(genderTX, 500.0);
		//ratingGivenLabel
		AnchorPane.setTopAnchor(ratingGivenLabel, 100.0 + 55.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(ratingGivenLabel, 460.0);
		//star5button
		AnchorPane.setBottomAnchor(star5button, 122.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(star5button, 560.0);
		//star4button
		AnchorPane.setBottomAnchor(star4button, 122.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(star4button, 560.0);
		//star3button
		AnchorPane.setBottomAnchor(star3button, 122.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(star3button, 560.0);
		//star2button
		AnchorPane.setBottomAnchor(star2button, 122.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(star2button, 560.0);
		//star1button
		AnchorPane.setBottomAnchor(star1button, 122.0 + 40.0 * 3);
		AnchorPane.setLeftAnchor(star1button, 560.0);
		//giveRatingLabel
		AnchorPane.setTopAnchor(giveRatingLabel, 100.0 + 60.0 + 40.0 * 4.2 + 20.0);
		AnchorPane.setLeftAnchor(giveRatingLabel, 440.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 110.0 + 60.0 + 40.0 * 4.2 + 20.0);
		AnchorPane.setLeftAnchor(underline2, 440.0);
		AnchorPane.setRightAnchor(underline2, 65.0);
		//myRatingLabel
		AnchorPane.setTopAnchor(myRatingLabel, 110.0 + 55.0 + 40.0 * 6 + 15.0);
		AnchorPane.setLeftAnchor(myRatingLabel, 460.0);
		//rate0button
		AnchorPane.setBottomAnchor(rate0button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(rate0button, 550.0);
		//rate1button
		AnchorPane.setBottomAnchor(rate1button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(rate1button, 550.0);
		//rate2button
		AnchorPane.setBottomAnchor(rate2button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(rate2button, 550.0);
		//rate3button
		AnchorPane.setBottomAnchor(rate3button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(rate3button, 550.0);
		//rate4button
		AnchorPane.setBottomAnchor(rate4button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(rate4button, 550.0);
		//rate5button
		AnchorPane.setBottomAnchor(rate5button, 70.0 + 25.0);
		AnchorPane.setLeftAnchor(rate5button, 550.0);
		//inv1
		AnchorPane.setBottomAnchor(inv1, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(inv1, 554.0);
		//inv2
		AnchorPane.setBottomAnchor(inv2, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(inv2, 578.0);
		//inv3
		AnchorPane.setBottomAnchor(inv3, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(inv3, 603.0);
		//inv4
		AnchorPane.setBottomAnchor(inv4, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(inv4, 628.0);
		//inv5
		AnchorPane.setBottomAnchor(inv5, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(inv5, 653.0);
		//ratingTX
		AnchorPane.setTopAnchor(ratingTX, 100.0 + 80.0 + 40.0 * 3);
		AnchorPane.setRightAnchor(ratingTX, 70.0);
		//pseudo1button
		AnchorPane.setBottomAnchor(pseudo1button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(pseudo1button, 550.0);
		//pseudo2button
		AnchorPane.setBottomAnchor(pseudo2button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(pseudo2button, 550.0);
		//pseudo3button
		AnchorPane.setBottomAnchor(pseudo3button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(pseudo3button, 550.0);
		//pseudo4button
		AnchorPane.setBottomAnchor(pseudo4button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(pseudo4button, 550.0);
		//pseudo5button
		AnchorPane.setBottomAnchor(pseudo5button, 70.0 + 23.0);
		AnchorPane.setLeftAnchor(pseudo5button, 550.0);
		
		updateBackButton(0);
		menuBar.setStyle("-fx-background-color: '" + theme01 + "';");
		root.setStyle("-fx-background-color: '" + theme00 + "';");
		root.getChildren().addAll(menuBar , back, underline , membersLabel , submit , trainersTableX ,
				idLabel , nameLabel , genderLabel , ratingGivenLabel , idTX , nameTX , genderTX ,
				giveRatingLabel , underline2 , myRatingLabel , star5button , star4button ,
				star3button , star2button , star1button , rate5button , rate4button ,
				rate3button , rate2button , rate1button , rate0button , inv1 , inv2 , inv3 ,
				inv4 , inv5 , ratingTX , pseudo5button , pseudo4button , pseudo3button , pseudo2button ,
				pseudo1button);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label nameLabel = new Label("Name:");
	Label totalAmountLabel = new Label("Total Amount:       Rs.");
	Label pendingAmountLabel = new Label("Pending Amount:       Rs.");
	Label memberFeeName = new Label("");
	Label memberFeeTotalAmount = new Label("");
	Label memberFeePendingAmount = new Label("");
	
	public void loadMemberFeeData(String selectedMemberID) {
		nameLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		totalAmountLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		pendingAmountLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		
		memberFeeName.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 16.5px;");
		memberFeeTotalAmount.setStyle("-fx-text-fill: '#336600';" + "-fx-font-size: 16.5px;");
		memberFeePendingAmount.setStyle("-fx-text-fill: '#ff1a1a';" + "-fx-font-size: 16.5px;");
		
		memberFeeName.setText("");
		memberFeeTotalAmount.setText("");
		
		//load transactions history table
		try {
			PreparedStatement history =  connection.prepareStatement("SELECT `Date_Of_Payment` , `Amount_Payed` , `Receipt_Number` , `Transaction_ID` FROM `studentdatabase`.`gym_fee` WHERE `ID` = '" + selectedMemberID + "' ORDER BY `Date_Of_Payment` DESC;");
			ResultSet data = history.executeQuery();
			transactionHistoryTable.getItems().clear();
			while(data.next()) {
				String dop = data.getString(1);
				String ap = data.getString(2);
				String rn = data.getString(3);
				String tid = data.getString(4);
				
				transactionHistoryTable.getItems().add(new TransactionHistoryTable(dop , ap , rn , tid));
			}
		}catch(Exception e) {}
		
		//load MBR Name, Total_Amount from member table
		try {
			PreparedStatement na =  connection.prepareStatement("SELECT `Name` , `Amount` FROM `studentdatabase`.`gym_members` WHERE `ID` = '" + selectedMemberID + "';");
			ResultSet dna = na.executeQuery();
			dna.next();
			memberFeeName.setText(dna.getString(1).toString());
			memberFeeTotalAmount.setText(dna.getString(2).toString() + " only");
		}catch(Exception e) {}
		
		try {
			PreparedStatement pending =  connection.prepareStatement("Select `Amount_Payed` FROM `studentdatabase`.`gym_fee` WHERE `ID` = '" + selectedMemberID + "';");
			ResultSet p = pending.executeQuery();
			int total = 0;
			while(p.next()) {
				total += Integer.parseInt(p.getString(1));
			}
			int remaining = Integer.parseInt(memberFeeTotalAmount.getText().substring(0 , memberFeeTotalAmount.getText().indexOf(" "))) - total;
			memberFeePendingAmount.setText(Integer.toString(remaining));
			
			//update
			try {
				PreparedStatement u =  connection.prepareStatement("UPDATE `studentdatabase`.`gym_fee` SET `Pending_Amount` = '" + memberFeePendingAmount.getText() + "' WHERE (`ID` = '" + selectedMemberID + "');");
				u.execute();
			}catch(Exception e) {}
		}catch(Exception e) {
			memberFeePendingAmount.setText(memberFeeTotalAmount.getText().substring(0 , memberFeeTotalAmount.getText().indexOf(" ")));
		}
	}
	
	public void savePayment(Stage s , String memberID , String money , String receipt , String transaction , boolean isOnlinePayment) {
		String regex = "[0-9]*";
		String data = money;
		boolean isCorrectMoney = data.matches(regex);
		
		boolean flag = false;
		if(isCorrectMoney == false || money.equals("") == true || receipt.equals("") == true) {
			flag = false;
		}else {
			if(isOnlinePayment == true && transaction.equals("") == true) {
				flag = false;
			}else if(isOnlinePayment == true && transaction.equals("") == false) {
				flag = true;
			}else if(isOnlinePayment == false) {
				flag = true;
			}
		}
		if(flag == true && Integer.parseInt(money) > 0) {
			//save to DB and update pending amount of all tuples having ID = selectedMemberID
			try {
				String savedPending = memberFeePendingAmount.getText();
				int value = Integer.parseInt(savedPending) - Integer.parseInt(money);
				if(value >= 0) {
					String calculatedPendingAmount = Integer.toString(value);
					
					String formatedDate = "";
					String today = getCurrentDate().replaceAll("/", "-");
					StringTokenizer part = new StringTokenizer(today , "-");
					String day = part.nextToken();
					String month = part.nextToken();
					String year = part.nextToken();
					formatedDate = year + "-" + month + "-" + day;
					
					//retrieve membership of member
					String retrievedMembership = "";
					try{
						PreparedStatement membership = connection.prepareStatement("SELECT `Membership_Type` FROM `studentdatabase`.`gym_members` WHERE `ID` = '" + memberID + "';");
						ResultSet mr = membership.executeQuery();
						while(mr.next()) {
							retrievedMembership = mr.getString(1).trim();
						}
					}catch(Exception e) {}
					
					PreparedStatement insert = connection.prepareStatement("INSERT INTO `studentdatabase`.`gym_fee` (`ID`, `Name`, `Total_Amount`, `Membership_Type`, `Receipt_Number`, `Transaction_ID`, `Date_Of_Payment`, `Pending_Amount`, `Amount_Payed`) VALUES ('" + memberID + "', '" + memberFeeName.getText() + "', '" + memberFeeTotalAmount.getText().substring(0, memberFeeTotalAmount.getText().indexOf(" ")) + "', '" + retrievedMembership + "', 'RECEIPT" + receipt.trim() + "', '" + transaction.trim() + "', '" + formatedDate + "', '" + calculatedPendingAmount + "', '" + money.trim() + "');");
					insert.execute();
					
					//update Pending Amount of all tuples having ID = selectedMemberID
					PreparedStatement update = connection.prepareStatement("UPDATE `studentdatabase`.`gym_fee` SET `Pending_Amount` = '" + calculatedPendingAmount + "' WHERE (`ID` = '" + memberID + "');");
					update.execute();
					
					Alert done = new Alert(AlertType.INFORMATION);
					done.setTitle("Successful");
					done.setHeaderText("Payment Saved");
					done.setContentText("Payment made by the member has been successfully stored. "
							+ "Press OK to continue.\n\n\n");
					Optional<ButtonType> op = done.showAndWait();
					if(op.get() == ButtonType.OK) {
						feeDetailsOfMemberScreen(s , memberID , true);
					}
				}else {
					Alert ndone = new Alert(AlertType.WARNING);
					ndone.setTitle("Message");
					ndone.setHeaderText("Check Amount");
					ndone.setContentText("You are paying more money than your Membership Amount."
							+ " Check Amount and try again.\n\n\n");
					ndone.show();
				}
			}catch(Exception e) {
				Alert ndone = new Alert(AlertType.ERROR);
				ndone.setTitle("Failed");
				ndone.setHeaderText("Payment Unsuccessful");
				ndone.setContentText("Invalid data might have been entered. "
						+ "Change Receipt Number or Amount and try again.\n\n\n");
				ndone.show();
			}
			
			
		}else {
			Alert invalid = new Alert(AlertType.WARNING);
			invalid.setTitle("Error");
			invalid.setHeaderText("Failed");
			invalid.setContentText("Data missing or invalid amount entered. Do not use '.' in "
					+ "Amount. Check and try again.\n\n\n");
			invalid.show();
		}
	}
	
	public void makePaymentScreen(Stage s , String selectedMemberID) {
		Button done = new Button("Done");
		Button cancel = new Button("Cancel");
		
		Button background = new Button();
		background.setStyle("-fx-border-color: '595959';" + "-fx-background-color: '" + this.currentBackground + "';");
		
		Label membersLabel = new Label(" New Transaction of " + selectedMemberID);
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		Label amountLabel = new Label("Amount:      Rs.");
		Label receiptNumberLabel = new Label("Receipt Number:");
		Label transactionIDLabel = new Label("Transaction ID:");
		String spaces = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "   ";
		Label underline2 = new Label(spaces);
		underline2.setStyle("-fx-text-fill: '595959';");
		underline2.setUnderline(true);
		CheckBox isOnlinePayment = new CheckBox("I am doing online transaction");
		Label dateOfPaymentLabel = new Label("Date of Payment:");
		TextField money = new TextField();
		TextField receipt = new TextField();
		TextField transaction = new TextField();
		Label receiptTag = new Label("RECEIPT");
		receiptTag.setStyle("-fx-font-size: 15px;");
		receiptTag.setUnderline(true);
		
		dateOfPaymentLabel.setText("Date of Payment: \t" + getCurrentDate() + " (today)");
		money.setText(memberFeePendingAmount.getText());
		
		amountLabel.setStyle("-fx-font-size: 14px;");
		receiptNumberLabel.setStyle("-fx-font-size: 14px;");
		transactionIDLabel.setStyle("-fx-font-size: 14px;");
		dateOfPaymentLabel.setStyle("-fx-font-size: 14px;"+"-fx-text-fill: 'black';");
		isOnlinePayment.setStyle("-fx-font-size: 13px;");
		
		done.setPrefSize(100.0, 30.0);
		cancel.setPrefSize(100.0, 30.0);
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14.5px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '" + currentDarkColor + "';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14.5px;";
		
		done.setStyle(goN);
		done.setOnMouseMoved(e-> done.setStyle(goH));
		done.setOnMouseReleased(e-> done.setStyle(goN));
		done.setOnMousePressed(e-> done.setStyle(goP));
		done.setOnMouseExited(e-> done.setStyle(goN));
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14.5px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14.5px;";
		
		cancel.setStyle(delN);
		cancel.setOnMouseMoved(e-> cancel.setStyle(delH));
		cancel.setOnMouseReleased(e-> cancel.setStyle(delN));
		cancel.setOnMousePressed(e-> cancel.setStyle(delP));
		cancel.setOnMouseExited(e-> cancel.setStyle(delN));
		
		//default
		transaction.setDisable(true);
		transaction.setText("");
		underline2.setStyle("-fx-text-fill: '#999999';");
		transactionIDLabel.setStyle("-fx-text-fill: '#999999';" + "-fx-font-size: 14px;");
		
		isOnlinePayment.setOnAction(e -> {
			if(isOnlinePayment.isSelected() == true) {
				transaction.setDisable(false);
				transaction.setText("");
				underline2.setStyle("-fx-text-fill: '#595959';");
				transactionIDLabel.setStyle("-fx-text-fill: 'black';" + "-fx-font-size: 14px;");
			}else {
				transaction.setDisable(true);
				transaction.setText("");
				underline2.setStyle("-fx-text-fill: '#999999';");
				transactionIDLabel.setStyle("-fx-text-fill: '#999999';" + "-fx-font-size: 14px;");
			}
		});
		
		//controls
		back.setOnAction(e -> feeDetailsOfMemberScreen(s , selectedMemberID , true));
		cancel.setOnAction(e -> viewMemberDetailsScreen(s , selectedMemberID));
		done.setOnAction(e -> {
			savePayment(s , selectedMemberID , money.getText() , receipt.getText() , transaction.getText() , isOnlinePayment.isSelected());
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//nameLabel
		AnchorPane.setTopAnchor(nameLabel, 130.0);
		AnchorPane.setRightAnchor(nameLabel, 600.0);
		//totalAmountLabel
		AnchorPane.setTopAnchor(totalAmountLabel, 170.0);
		AnchorPane.setRightAnchor(totalAmountLabel, 555.0);
		//pendingAmountLabel
		AnchorPane.setTopAnchor(pendingAmountLabel, 205.0);
		AnchorPane.setRightAnchor(pendingAmountLabel, 555.0);
		//memberFeeName
		AnchorPane.setTopAnchor(memberFeeName, 127.0);
		AnchorPane.setLeftAnchor(memberFeeName, 340.0);
		//memberFeeTotalAmount
		AnchorPane.setTopAnchor(memberFeeTotalAmount, 167.0);
		AnchorPane.setLeftAnchor(memberFeeTotalAmount, 360.0);
		//memberFeePendingAmount
		AnchorPane.setTopAnchor(memberFeePendingAmount, 202.0);
		AnchorPane.setLeftAnchor(memberFeePendingAmount, 360.0);
		//cancel
		AnchorPane.setRightAnchor(cancel, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(cancel, 22.0);
		//done
		AnchorPane.setRightAnchor(done, 27.0);
		AnchorPane.setBottomAnchor(done, 22.0);
		//background
		AnchorPane.setTopAnchor(background, 260.0);
		AnchorPane.setLeftAnchor(background, 140.0);
		AnchorPane.setRightAnchor(background, 140.0);
		AnchorPane.setBottomAnchor(background, 85.0);
		//amountLabel
		AnchorPane.setTopAnchor(amountLabel, 275.0);
		AnchorPane.setRightAnchor(amountLabel, 555.0);
		//receiptNumberLabel
		AnchorPane.setTopAnchor(receiptNumberLabel, 315.0);
		AnchorPane.setRightAnchor(receiptNumberLabel, 555.0);
		//isOnlinePayment
		AnchorPane.setTopAnchor(isOnlinePayment, 360.0);
		AnchorPane.setRightAnchor(isOnlinePayment, 525.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 370.0);
		AnchorPane.setLeftAnchor(underline2, 170.0);
		AnchorPane.setRightAnchor(underline2, 160.0);
		//transactionIDLabel
		AnchorPane.setTopAnchor(transactionIDLabel, 405.0);
		AnchorPane.setRightAnchor(transactionIDLabel, 555.0);
		//dateOfPaymentLabel
		AnchorPane.setTopAnchor(dateOfPaymentLabel, 460.0);
		AnchorPane.setLeftAnchor(dateOfPaymentLabel, 150.0);
		//money
		AnchorPane.setTopAnchor(money, 272.0);
		AnchorPane.setLeftAnchor(money, 360.0);
		AnchorPane.setRightAnchor(money, 350.0);
		//receiptTag
		AnchorPane.setTopAnchor(receiptTag, 314.0);
		AnchorPane.setLeftAnchor(receiptTag, 360.0);
		//receipt
		AnchorPane.setTopAnchor(receipt, 312.0);
		AnchorPane.setLeftAnchor(receipt, 420.0);
		AnchorPane.setRightAnchor(receipt, 300.0);
		//transaction
		AnchorPane.setTopAnchor(transaction, 402.0);
		AnchorPane.setLeftAnchor(transaction, 360.0);
		AnchorPane.setRightAnchor(transaction, 300.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , background , underline , membersLabel ,
				pendingAmountLabel , underline2 , money , receipt , transaction ,
				totalAmountLabel , nameLabel , memberFeeName , memberFeeTotalAmount ,
				memberFeePendingAmount , cancel , done , amountLabel , transactionIDLabel ,
				receiptNumberLabel , isOnlinePayment , dateOfPaymentLabel , receiptTag);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
		
	}
	
	public void clearFeeEntry(String selectedMemberID , String receipt) {
		Alert isSure = new Alert(AlertType.CONFIRMATION);
		isSure.setTitle("Confirmation");
		isSure.setHeaderText("Are you sure?");
		isSure.setContentText("Transaction entry will be deleted. Press OK to delete else "
				+ "press Cancel.\n\n\n");
		Optional<ButtonType> op = isSure.showAndWait();
		if(op.get() == ButtonType.OK) {
			try {
				//retrieve amount paid
				PreparedStatement ap = connection.prepareStatement("Select * FROM `studentdatabase`.`gym_fee` WHERE (`Receipt_Number` = '" + receipt + "');");
				ResultSet am = ap.executeQuery();
				am.next();
				int prv = Integer.parseInt(am.getString(9).toString());
				
				PreparedStatement delete = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_fee` WHERE (`Receipt_Number` = '" + receipt + "');");
				delete.execute();
				Alert done = new Alert(AlertType.INFORMATION);
				done.setTitle("Successful");
				done.setHeaderText("Entry Removed");
				done.setContentText("Transaction entry has been removed successfully. "
						+ "Press OK to continue\n\n\n");
				done.show();
				
				//update pending amount (how: add amount that has been deleted to previous
				//pending amount) and save changes
				
				String newPendingAmount = "";
				int value = 0;
				try {
					int pending = Integer.parseInt(memberFeePendingAmount.getText());
					newPendingAmount = Integer.toString(pending + prv);
					PreparedStatement up = connection.prepareStatement("UPDATE `studentdatabase`.`gym_fee` SET `Pending_Amount` = '" + newPendingAmount + "' WHERE (`ID` = '" + selectedMemberID + "');");
					up.execute();
					
					memberFeePendingAmount.setText(newPendingAmount);
				}catch(Exception d) {}
			}catch(Exception e) {
				Alert ndone = new Alert(AlertType.ERROR);
				ndone.setTitle("Failed");
				ndone.setHeaderText("An error occured");
				ndone.setContentText("Transaction entry was not removed.\n\n\n");
				ndone.show();
			}
		}
	}
	
	String addN = "-fx-text-fill: white;" + "-fx-background-color: '#00ff00';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14px;";
	String addH = "-fx-text-fill: white;" + "-fx-background-color: '#00b300';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";
	String addP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#008000';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";

	String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14px;";
	String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";
	String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";
	
	String goN = "-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 14px;";
	String goH = "-fx-text-fill: white;" + "-fx-background-color: '#0066cc';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";
	String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#004d99';" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;";
	
	public void feeDetailsOfMemberScreen(Stage s , String selectedMemberID , boolean sentFrom) {
		/*  sentFrom: true -> viewMemberDetailsScreen , makePaymentScreen
		 *  sentFrom: false -> feeScreen
		 */
		
		loadMemberFeeData(selectedMemberID);
		
		Button add = new Button("Make Payment");
		Button clear = new Button("Delete");
		Button ok = new Button("OK");
		
		add.setStyle(addN);
		add.setOnMouseMoved(e-> add.setStyle(addH));
		add.setOnMouseReleased(e-> add.setStyle(addN));
		add.setOnMousePressed(e-> add.setStyle(addP));
		add.setOnMouseExited(e-> add.setStyle(addN));
		
		clear.setStyle(delN);
		clear.setOnMouseMoved(e-> clear.setStyle(delH));
		clear.setOnMouseReleased(e-> clear.setStyle(delN));
		clear.setOnMousePressed(e-> clear.setStyle(delP));
		clear.setOnMouseExited(e-> clear.setStyle(delN));
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
	
		
		ok.setStyle(goN);
		ok.setOnMouseMoved(e-> ok.setStyle(goH));
		ok.setOnMouseReleased(e-> ok.setStyle(goN));
		ok.setOnMousePressed(e-> ok.setStyle(goP));
		ok.setOnMouseExited(e-> ok.setStyle(goN));
			
		add.setPrefSize(150.0, 30.0);
		clear.setPrefSize(100.0, 30.0);
		ok.setPrefSize(100.0, 30.0);
		
		Label membersLabel = new Label(" Fee Payment Details of " + selectedMemberID);
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		Label transactionHistoryLabel = new Label("Transaction History");
		String underlineTH2 = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + " ";
		Label underline2 = new Label(underlineTH2);
		underline2.setUnderline(true);
		
		transactionHistoryLabel.setStyle("-fx-font-size: 18px;");
		
		//controls
		if(sentFrom == true) {
			back.setOnAction(e -> viewMemberDetailsScreen(s , selectedMemberID));
		}else {
			back.setOnAction(e -> feeScreen(s));
		}
		ok.setOnAction(e -> membersScreen(s));
		add.setOnAction(e -> makePaymentScreen(s , selectedMemberID));
		clear.setOnAction(e -> {
			if(transactionHistoryTable.getSelectionModel().getSelectedItem() != null) {
				TransactionHistoryTable tht = (TransactionHistoryTable) transactionHistoryTable.getSelectionModel().getSelectedItem();
				String selectedReceiptNumber = tht.getReceiptNumber();
				clearFeeEntry(selectedMemberID , selectedReceiptNumber);
				loadMemberFeeData(selectedMemberID);
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//nameLabel
		AnchorPane.setTopAnchor(nameLabel, 130.0);
		AnchorPane.setRightAnchor(nameLabel, 600.0);
		//totalAmountLabel
		AnchorPane.setTopAnchor(totalAmountLabel, 170.0);
		AnchorPane.setRightAnchor(totalAmountLabel, 555.0);
		//pendingAmountLabel
		AnchorPane.setTopAnchor(pendingAmountLabel, 205.0);
		AnchorPane.setRightAnchor(pendingAmountLabel, 555.0);
		//memberFeeName
		AnchorPane.setTopAnchor(memberFeeName, 127.0);
		AnchorPane.setLeftAnchor(memberFeeName, 340.0);
		//memberFeeTotalAmount
		AnchorPane.setTopAnchor(memberFeeTotalAmount, 167.0);
		AnchorPane.setLeftAnchor(memberFeeTotalAmount, 360.0);
		//memberFeePendingAmount
		AnchorPane.setTopAnchor(memberFeePendingAmount, 202.0);
		AnchorPane.setLeftAnchor(memberFeePendingAmount, 360.0);
		//transactionHistoryLabel
		AnchorPane.setTopAnchor(transactionHistoryLabel, 250.0);
		AnchorPane.setLeftAnchor(transactionHistoryLabel, 90.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 264.0);
		AnchorPane.setLeftAnchor(underline2, 80.0);
		AnchorPane.setRightAnchor(underline2, 70.0);
		//transactionHistoryTable
		AnchorPane.setTopAnchor(transactionHistoryTable, 295.0);
		AnchorPane.setLeftAnchor(transactionHistoryTable, 108.0);
		AnchorPane.setRightAnchor(transactionHistoryTable, 108.0);
		AnchorPane.setBottomAnchor(transactionHistoryTable, 90.0);
		//add
		AnchorPane.setRightAnchor(add, 27.0  + 100.0 + 17.0 + 120.0);
		AnchorPane.setBottomAnchor(add, 22.0);
		//clear
		AnchorPane.setRightAnchor(clear, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(clear, 22.0);
		//ok
		AnchorPane.setRightAnchor(ok, 27.0);
		AnchorPane.setBottomAnchor(ok, 22.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, underline , membersLabel , pendingAmountLabel ,
				totalAmountLabel , nameLabel , memberFeeName , memberFeeTotalAmount ,
				memberFeePendingAmount , transactionHistoryLabel , underline2 ,
				transactionHistoryTable , add , clear , ok);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void viewMemberDetailsScreen(Stage s , String selectedMemberID) {
		loadMemberDataFromDB(selectedMemberID);
		
		Button updateInfo = new Button("Update Info");
		Button feeDetails = new Button("Fee Details");
		Button rateTrainer = new Button("Give Rating");
		
		updateInfo.setPrefSize(140.0, 32.0);
		feeDetails.setPrefSize(140.0, 32.0);
		rateTrainer.setPrefSize(140.0, 32.0);
		
		updateInfo.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		feeDetails.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		updateInfo.setOnMouseExited(e -> updateInfo.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		feeDetails.setOnMouseExited(e -> feeDetails.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		updateInfo.setOnMouseReleased(e -> updateInfo.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		feeDetails.setOnMouseReleased(e -> feeDetails.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		updateInfo.setOnMouseEntered(e -> updateInfo.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		feeDetails.setOnMouseEntered(e -> feeDetails.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		updateInfo.setOnMousePressed(e -> updateInfo.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		feeDetails.setOnMousePressed(e -> feeDetails.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		rateTrainer.setStyle("-fx-background-color: '#ff8000';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#ff8000';" + "-fx-font-weight: normal;");
		rateTrainer.setOnMouseExited(e -> rateTrainer.setStyle("-fx-background-color: '#ff8000';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#ff8000';" + "-fx-font-weight: normal;"));
		rateTrainer.setOnMouseReleased(e -> rateTrainer.setStyle("-fx-background-color: '#ff8000';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#ff8000';" + "-fx-font-weight: normal;"));
		rateTrainer.setOnMouseEntered(e -> rateTrainer.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '#ff8000';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '#ff8000';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		rateTrainer.setOnMousePressed(e -> rateTrainer.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '#ff8000';" + "-fx-font-size: 13px;" + "-fx-border-color: '#ff8000';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		Label membersLabel = new Label(" Details");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		
		//controls
		back.setOnAction(e -> membersScreen(s));
		updateInfo.setOnAction(e -> updateMemberDetailsScreen(s , selectedMemberID));
		rateTrainer.setOnAction(e -> giveTrainerRatingScreen(s , selectedMemberID , viewMemberMembership.getText()));
		feeDetails.setOnAction(e -> feeDetailsOfMemberScreen(s , selectedMemberID , true));
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0 - 20.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//rateTrainer
		AnchorPane.setRightAnchor(rateTrainer, 27.0  + 100.0 + 17.0 + 40.0 + 140.0 + 18.0 );
		AnchorPane.setBottomAnchor(rateTrainer, 22.0);
		//feeDetails
		AnchorPane.setRightAnchor(feeDetails, 27.0  + 100.0 + 17.0 + 40.0);
		AnchorPane.setBottomAnchor(feeDetails, 22.0);
		//updateInfo
		AnchorPane.setRightAnchor(updateInfo, 27.0);
		AnchorPane.setBottomAnchor(updateInfo, 22.0);
		//viewMemberIDLabel
		AnchorPane.setTopAnchor(viewMemberIDLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberIDLabel, 60.0);
		//viewMemberNameLabel
		AnchorPane.setTopAnchor(viewMemberNameLabel, 160.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberNameLabel, 60.0);
		//viewMemberGenderLabel
		AnchorPane.setTopAnchor(viewMemberGenderLabel, 190.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberGenderLabel, 60.0);
		//viewMemberDOBLabel
		AnchorPane.setTopAnchor(viewMemberDOBLabel, 220.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOBLabel, 60.0);
		//viewMemberAgeLabel
		AnchorPane.setTopAnchor(viewMemberAgeLabel, 250.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAgeLabel, 60.0);
		//viewMemberWeightLabel
		AnchorPane.setTopAnchor(viewMemberWeightLabel, 280.0 + 25.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberWeightLabel, 60.0);
		//viewMemberOccupationLabel
		AnchorPane.setTopAnchor(viewMemberOccupationLabel, 310.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberOccupationLabel, 60.0);
		//viewMemberAddressLabel
		AnchorPane.setTopAnchor(viewMemberAddressLabel, 340.0 + 35.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAddressLabel, 60.0);
		//viewMemberCityLabel
		AnchorPane.setTopAnchor(viewMemberCityLabel, 370.0 + 40.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCityLabel, 60.0);
		//viewMemberCountryLabel
		AnchorPane.setTopAnchor(viewMemberCountryLabel, 400.0 + 45.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCountryLabel, 60.0);
		//viewMemberPinLabel
		AnchorPane.setTopAnchor(viewMemberPinLabel, 430.0 + 50.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPinLabel, 60.0);
		//viewMemberPhNoLabel
		AnchorPane.setTopAnchor(viewMemberPhNoLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPhNoLabel, 460.0);
		//viewMemberEmailLabel
		AnchorPane.setTopAnchor(viewMemberEmailLabel, 190.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberEmailLabel, 460.0);
		//viewMemberMembershipLabel
		AnchorPane.setTopAnchor(viewMemberMembershipLabel, 220.0 + 21.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberMembershipLabel, 460.0);
		//viewMemberAmountLabel
		AnchorPane.setTopAnchor(viewMemberAmountLabel, 250.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAmountLabel, 460.0);
		//viewMemberNoteLabel
		AnchorPane.setTopAnchor(viewMemberNoteLabel, 280.0 + 35.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberNoteLabel, 460.0);
		//viewMemberDOJLabel
		AnchorPane.setTopAnchor(viewMemberDOJLabel, 370.0 + 56.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOJLabel, 460.0);
		
		//viewMemberID
		AnchorPane.setTopAnchor(viewMemberID, 129.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberID, 90.0);
		//viewMemberName
		AnchorPane.setTopAnchor(viewMemberName, 159.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberName, 120.0);
		//viewMemberGender
		AnchorPane.setTopAnchor(viewMemberGender, 189.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberGender, 125.0);
		//viewMemberDOB
		AnchorPane.setTopAnchor(viewMemberDOB, 219.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOB, 110.0);
		//viewMemberAge
		AnchorPane.setTopAnchor(viewMemberAge, 249.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAge, 110.0);
		//viewMemberOccupation
		AnchorPane.setTopAnchor(viewMemberOccupation, 309.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberOccupation, 160.0);
		//viewMemberAddress
		AnchorPane.setTopAnchor(viewMemberAddress, 339.0 + 30.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAddress, 130.0);
		AnchorPane.setRightAnchor(viewMemberAddress, 480.0);
		AnchorPane.setBottomAnchor(viewMemberAddress, 120.0 + 20.0);
		//viewMemberCity
		AnchorPane.setTopAnchor(viewMemberCity, 369.0 + 40.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCity, 110.0);
		//viewMemberCountry
		AnchorPane.setTopAnchor(viewMemberCountry, 399.0 + 45.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberCountry, 130.0);
		//viewMemberPin
		AnchorPane.setTopAnchor(viewMemberPin, 429.0 + 50.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPin, 140.0);
		//viewMemberPhNo1
		AnchorPane.setTopAnchor(viewMemberPhNo1, 129.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPhNo1, 600.0);
		//viewMemberPhNo2
		AnchorPane.setTopAnchor(viewMemberPhNo2, 159.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberPhNo2, 600.0);
		//viewMemberEmail
		AnchorPane.setTopAnchor(viewMemberEmail, 189.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberEmail, 520.0);
		//viewMemberMembership
		AnchorPane.setTopAnchor(viewMemberMembership, 219.0 + 21.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberMembership, 610.0);
		//viewMemberAmount
		AnchorPane.setTopAnchor(viewMemberAmount, 249.0 + 28.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberAmount, 550.0);
		//viewMemberNote
		AnchorPane.setTopAnchor(viewMemberNote, 280.0 + 32.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberNote, 510.0);
		AnchorPane.setRightAnchor(viewMemberNote, 55.0);
		AnchorPane.setBottomAnchor(viewMemberNote, 120.0 + 20.0);
		//viewMemberDOJ
		AnchorPane.setTopAnchor(viewMemberDOJ, 371.0 + 56.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberDOJ, 580.0);
		//viewMemberWeight
		AnchorPane.setTopAnchor(viewMemberWeight, 279.0 + 24.0 - 20.0);
		AnchorPane.setLeftAnchor(viewMemberWeight, 130.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, underline , membersLabel , updateInfo , feeDetails ,
				viewMemberIDLabel , viewMemberNameLabel , viewMemberGenderLabel , viewMemberDOBLabel ,
				viewMemberPhNoLabel , viewMemberEmailLabel , viewMemberAddressLabel , viewMemberCityLabel ,
				viewMemberPinLabel , viewMemberCountryLabel , viewMemberOccupationLabel ,
				viewMemberMembershipLabel , viewMemberAgeLabel , viewMemberDOJLabel , viewMemberAmountLabel ,
				viewMemberNoteLabel , viewMemberID , viewMemberName , viewMemberGender , viewMemberDOB ,
				viewMemberAge , viewMemberOccupation , viewMemberAddress , viewMemberCity ,
				viewMemberCountry , viewMemberPin , viewMemberPhNo1 , viewMemberPhNo2 , viewMemberEmail ,
				viewMemberMembership , viewMemberAmount , viewMemberNote , viewMemberDOJ , viewMemberWeight ,
				viewMemberWeightLabel , rateTrainer);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void skipToUpdateMemberDetailsScreen(Stage s , String selectedMemberID) {
		loadMemberDataFromDB(selectedMemberID);
		updateMemberDetailsScreen(s , selectedMemberID);
	}
	
	public boolean deleteSelectedMember(String selectedMemberID) {
		try {
			PreparedStatement ps1 = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_members` WHERE (`ID` = '" + selectedMemberID + "');");
			ps1.execute();
			try {
				PreparedStatement ps2 = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_members_connectivity` WHERE (`ID` = '" + selectedMemberID + "');");
				ps2.execute();
				return true;
			}catch(Exception d) {
				return false;
			}
		}catch(Exception e) {
			return false;
		}
	}
	
	public void filterTableM0(String selection , String input , String of , String sex) {
		/* if of == "member" : filterMemberTable
		 * if of == "trainer" : filterTrainerTable
		 */
		
		if(sex.endsWith("'All'") == true) {
			sex = "All";
		}else if(sex.endsWith("'Male'") == true) {
			sex = "Male";
		}else if(sex.endsWith("'Female'") == true) {
			sex = "Female";
		}else {
			sex = "Non-Binary";
		}
		
		String modifiedSelection = "";
		if(selection.equalsIgnoreCase("membership") == true) {
			if(of.equalsIgnoreCase("member") == true) {
				modifiedSelection = "Membership_Type";
			}else {
				modifiedSelection = "Membership_Alloted";
			}
		}else if(selection.equalsIgnoreCase("city") == true) {
			modifiedSelection = "CITY";
		}else {
			modifiedSelection = "PinCode";
		}
		
		String preparedCommand = "";
		
		try {
				filterMemberTable.getItems().clear();
				
				PreparedStatement command;
				if(of.equalsIgnoreCase("member") == true) {
					if(sex.equalsIgnoreCase("All") == true) {
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_members` , `studentdatabase`.`gym_members_connectivity` WHERE `gym_members`.`ID` = `gym_members_connectivity`.`ID` AND `" + modifiedSelection + "` = '" + input + "';";
					}else {
						//include WHERE clause for `Gender`
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_members` , `studentdatabase`.`gym_members_connectivity` WHERE `gym_members`.`ID` = `gym_members_connectivity`.`ID` AND `" + modifiedSelection + "` = '" + input + "' AND `Gender` = '" + sex + "';";
					}
				}else {
					if(sex.equalsIgnoreCase("All") == true) {
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_trainers` , `studentdatabase`.`gym_trainers_connectivity` WHERE `gym_trainers`.`ID` = `gym_trainers_connectivity`.`ID` AND `" + modifiedSelection + "` = '" + input + "';";
					}else {
						//include WHERE clause for `Gender`
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_trainers` , `studentdatabase`.`gym_trainers_connectivity` WHERE `gym_trainers`.`ID` = `gym_trainers_connectivity`.`ID` AND `" + modifiedSelection + "` = '" + input + "' AND `Gender` = '" + sex + "';";
					}
				}
				
				
				command = connection.prepareStatement(preparedCommand);
				ResultSet rs = command.executeQuery();
			    
			    while(rs.next()) {
			    	if(of.equalsIgnoreCase("member")) {
			    		String memberID = rs.getString(1);
					    String memberName = rs.getString(2);
					    String phoneNumber = rs.getString(7);
					    String amount = rs.getString(9);
					    	
					    filterMemberTable.getItems().add(new FilterMemberClass(memberID , memberName , phoneNumber , amount));
			    	}else {
			    		String memberID = rs.getString(1);
					    String memberName = rs.getString(2);
					    String phoneNumber = rs.getString(7);
					    String amount = rs.getString(4);
					    	
					    filterMemberTable.getItems().add(new FilterMemberClass(memberID , memberName , phoneNumber , amount));
			    	}
			    }
			}catch(Exception e) {}
	}
	
	public void filterTableM1(String selection , String aboveBelowBetween , String value , String betweenLower , String betweenUpper , String of , String sex) {
		/* if of == "member" : filterMemberTable
		 * if of == "trainer" : filterTrainerTable
		 */
		
		if(sex.endsWith("'All'") == true) {
			sex = "All";
		}else if(sex.endsWith("'Male'") == true) {
			sex = "Male";
		}else if(sex.endsWith("'Female'") == true) {
			sex = "Female";
		}else {
			sex = "Non-Binary";
		}
		
		String modifiedSelection = "";
		if(selection.equalsIgnoreCase("weight") == true) {
			modifiedSelection = "Weight";
		}else if(selection.equalsIgnoreCase("age") == true) {
			modifiedSelection = "Age";
		}else if(selection.equalsIgnoreCase("amount") == true) {
			modifiedSelection = "Amount";
		}else if(selection.equalsIgnoreCase("salary") == true) {
			modifiedSelection = "Salary";
		}else if(selection.equalsIgnoreCase("rating") == true) {
			modifiedSelection = "RATING";
		}
		
		if(aboveBelowBetween.equalsIgnoreCase("above Value") == true) {
			aboveBelowBetween = " > '" + value + "' ";
		}else if(aboveBelowBetween.equalsIgnoreCase("below Value") == true) {
			aboveBelowBetween = " < '" + value + "' ";
		}else if(aboveBelowBetween.equalsIgnoreCase("equal to Value") == true) {
			aboveBelowBetween = " = '" + value + "' ";
		}else {
			aboveBelowBetween = " BETWEEN '" + betweenLower + "' AND '" + betweenUpper + "' ";
		}
		
		
		String preparedCommand = "";
		
		try {
				filterMemberTable.getItems().clear();
				
				PreparedStatement command;
				
				if(of.equalsIgnoreCase("member") == true) {
					if(sex.equalsIgnoreCase("All") == true) {
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_members` WHERE `" + modifiedSelection + "` " + aboveBelowBetween + ";";
					}else {
						//include WHERE clause for `Gender`
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_members` WHERE `" + modifiedSelection + "` " + aboveBelowBetween + " AND `Gender` = '" + sex + "';";
					}
				}else {
					if(sex.equalsIgnoreCase("All") == true) {
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_trainers` WHERE `" + modifiedSelection + "` " + aboveBelowBetween + " AND `" + modifiedSelection + "` != 'NULL';";
					}else {
						//include WHERE clause for `Gender`
						preparedCommand = "SELECT * FROM `studentdatabase`.`gym_trainers` WHERE `" + modifiedSelection + "` " + aboveBelowBetween + " AND `" + modifiedSelection + "` != 'NULL' AND `Gender` = '" + sex + "';";
					}
				}
				
				command = connection.prepareStatement(preparedCommand);
				ResultSet rs = command.executeQuery();
			    
			    while(rs.next()){  
			    	if(of.equalsIgnoreCase("member")) {
			    		String memberID = rs.getString(1);
					    String memberName = rs.getString(2);
					    String phoneNumber = rs.getString(7);
					    String amount = rs.getString(9);
					    	
					    filterMemberTable.getItems().add(new FilterMemberClass(memberID , memberName , phoneNumber , amount));
			    	}else {
			    		String memberID = rs.getString(1);
					    String memberName = rs.getString(2);
					    String phoneNumber = rs.getString(7);
					    String amount = rs.getString(4);
					    	
					    filterMemberTable.getItems().add(new FilterMemberClass(memberID , memberName , phoneNumber , amount));
			    	}
			    }
				
			}catch(Exception e) {}
	}
	
	RadioButton filterAll = new RadioButton("All");
	RadioButton filterMale = new RadioButton("Male");
	RadioButton filterFemale = new RadioButton("Female");
	RadioButton filterNonBinary = new RadioButton("Non-Binary");
	ToggleGroup filterSex = new ToggleGroup();
	TextField inputA = new TextField();
	TextField inputX = new TextField();
	TextField inputY = new TextField();
	ObservableList<String> options0 = FXCollections.observableArrayList("membership" , "city" , "PIN code");
	ComboBox<String> comboBox0 = new ComboBox<String>(options0);
	TextField input0 = new TextField();
	Button run0 = new Button("Filter");
	ObservableList<String> options1 = FXCollections.observableArrayList("weight" , "age" , "amount");
	ObservableList<String> optionsT1 = FXCollections.observableArrayList("age" , "salary" , "rating");
	ComboBox<String> comboBox1;
	ObservableList<String> inputsAvailable = FXCollections.observableArrayList("above Value" , "below Value" , "equal to Value" , "between ___ and ___");
	ComboBox<String> input1 = new ComboBox<String>(inputsAvailable);
	Button run1 = new Button("Filter");
	
	public void filterScreen(Stage s , boolean isMember) {
		/* if isMember == true: filter member table
		 * if isMember == false: filter trainer table
		 * */
		
		if(isMember == true) {
			filterColumn4.setText("Amount");
		}else {
			filterColumn4.setText("Salary");
		}
		
		inputA.setPromptText("Number");
		inputX.setPromptText("Number");
		inputY.setPromptText("Number");
		input0.setPromptText("Input");
		
		filterMemberTable.getItems().clear();
		
		if(isMember == true) {
			comboBox1 = new ComboBox<String>(options1);
		}else {
			comboBox1 = new ComboBox<String>(optionsT1);
		}
		
		filterAll.setToggleGroup(filterSex);
		filterMale.setToggleGroup(filterSex);
		filterFemale.setToggleGroup(filterSex);
		filterNonBinary.setToggleGroup(filterSex);
		
		filterAll.setSelected(true);
		
		filterAll.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
		filterMale.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
		filterFemale.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
		filterNonBinary.setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
		
		inputA.setPrefWidth(80.0);
		inputX.setPrefWidth(80.0);
		inputY.setPrefWidth(80.0);
		
		inputA.setText("");
		inputX.setText("");
		inputY.setText("");
		input0.setText("");
		
		comboBox0.getSelectionModel().selectFirst();
		comboBox0.setPrefWidth(150.0);
		
		run0.setPrefWidth(80.0);
		
		comboBox1.getSelectionModel().selectFirst();
		comboBox1.setPrefWidth(150.0);
		
		input1.getSelectionModel().selectFirst();
		input1.setPrefWidth(150.0);
		
		run1.setPrefWidth(80.0);
		
		Label membersLabel = new Label(" Filter");
		if(isMember == true) {
			membersLabel.setText(" Filter Members");
		}else {
			membersLabel.setText(" Filter Trainers");
		}
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		Label filterSexLabel = new Label("Select Gender:");
		filterSexLabel.setStyle("-fx-font-size: 14px;");
		Label underline1 = new Label(underlineMembers);
		underline1.setUnderline(true);
		underline1.setOpacity(0.6);
		Label aLabel = new Label("Value:");
		Label betweenLabel = new Label("Between                               AND");
		aLabel.setStyle("-fx-font-size: 14px;");
		betweenLabel.setStyle("-fx-font-size: 14px;");
		Label display0 = new Label("Display delails of all those whose\t\t\t\t\t\tis");
		display0.setStyle("-fx-font-size: 15px;");
		Label display1 = new Label("Display delails of all those whose\t\t\t\t\t\tis");
		display1.setStyle("-fx-font-size: 15px;");
		Label underline2 = new Label(underlineMembers);
		underline2.setUnderline(true);
		underline2.setOpacity(0.6);
		
		Button highlight0 = new Button();
		Button highlight1 = new Button();
		
		highlight0.setStyle("-fx-background-color: '#b3d9ff';");
		highlight1.setStyle("-fx-background-color: '#b3d9ff';");
		
		highlight0.setPrefHeight(33.0);
		highlight1.setPrefHeight(33.0);
		
		highlight0.setVisible(false);
		highlight1.setVisible(false);
		
		run0.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 11px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		run1.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 11px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		
		//controls
		if(isMember == true) {
			back.setOnAction(e -> membersScreen(s));
		}else {
			back.setOnAction(e -> trainersScreen(s));
		}
		run0.setOnAction(e -> {
			highlight0.setVisible(true);
			highlight1.setVisible(false);
			if(isMember == true) {
				if(input0.getText().trim().equals("") == false) {
					filterTableM0(comboBox0.getSelectionModel().getSelectedItem().toString() , input0.getText().trim() , "member" , filterSex.getSelectedToggle().toString());
				}else {
					Alert empty = new Alert(AlertType.WARNING);
					empty.setTitle("Empty Field");
					empty.setHeaderText("Please Enter Value");
					empty.setContentText("Input field is found empty. Enter value and try "
							+ "again.\n\n\n");
					empty.show();
				}
			}else {
				if(input0.getText().trim().equals("") == false) {
					filterTableM0(comboBox0.getSelectionModel().getSelectedItem().toString() , input0.getText().trim() , "trainer" , filterSex.getSelectedToggle().toString());
				}else {
					Alert empty = new Alert(AlertType.WARNING);
					empty.setTitle("Empty Field");
					empty.setHeaderText("Please Enter Value");
					empty.setContentText("Input field is found empty. Enter value and try "
							+ "again.\n\n\n");
					empty.show();
				}
			}
		});
		run1.setOnAction(e -> {
			highlight0.setVisible(false);
			highlight1.setVisible(true);
			if(isMember == true) {
				if(input1.getSelectionModel().getSelectedItem().equalsIgnoreCase("between ___ and ___") == true) {
					if(inputX.getText().trim().equals("") == false && inputY.getText().trim().equals("") == false) {
						filterTableM1(comboBox1.getSelectionModel().getSelectedItem().toString() , input1.getSelectionModel().getSelectedItem().toString() , "null" , inputX.getText().trim() , inputY.getText().trim() , "member" , filterSex.getSelectedToggle().toString());
					}else {
						Alert empty = new Alert(AlertType.WARNING);
						empty.setTitle("Empty Field");
						empty.setHeaderText("Please Enter Value");
						empty.setContentText("Input field is found empty. Enter value and try "
								+ "again.\n\n\n");
						empty.show();
					}
				}else {
					if(inputA.getText().trim().equals("") == false) {
						filterTableM1(comboBox1.getSelectionModel().getSelectedItem().toString() , input1.getSelectionModel().getSelectedItem().toString() , inputA.getText().trim() , "null" , "null" , "member" , filterSex.getSelectedToggle().toString());
					}else {
						Alert empty = new Alert(AlertType.WARNING);
						empty.setTitle("Empty Field");
						empty.setHeaderText("Please Enter Value");
						empty.setContentText("Input field is found empty. Enter value and try "
								+ "again.\n\n\n");
						empty.show();
					}
				}
			}else {
				if(input1.getSelectionModel().getSelectedItem().equalsIgnoreCase("between ___ and ___") == true) {
					if(inputX.getText().trim().equals("") == false && inputY.getText().trim().equals("") == false) {
						filterTableM1(comboBox1.getSelectionModel().getSelectedItem().toString() , input1.getSelectionModel().getSelectedItem().toString() , "null" , inputX.getText().trim() , inputY.getText().trim() , "trainer" , filterSex.getSelectedToggle().toString());
					}else {
						Alert empty = new Alert(AlertType.WARNING);
						empty.setTitle("Empty Field");
						empty.setHeaderText("Please Enter Value");
						empty.setContentText("Input field is found empty. Enter value and try "
								+ "again.\n\n\n");
						empty.show();
					}
				}else {
					if(inputA.getText().trim().equals("") == false) {
						filterTableM1(comboBox1.getSelectionModel().getSelectedItem().toString() , input1.getSelectionModel().getSelectedItem().toString() , inputA.getText().trim() , "null" , "null" , "trainer" , filterSex.getSelectedToggle().toString());
					}else {
						Alert empty = new Alert(AlertType.WARNING);
						empty.setTitle("Empty Field");
						empty.setHeaderText("Please Enter Value");
						empty.setContentText("Input field is found empty. Enter value and try "
								+ "again.\n\n\n");
						empty.show();
					}
				}
			}
		});
		comboBox0.setOnAction(e -> {
			highlight0.setVisible(true);
			highlight1.setVisible(false);
		});
		input0.setOnMouseClicked(e -> {
			highlight0.setVisible(true);
			highlight1.setVisible(false);
		});
		comboBox1.setOnAction(e -> {
			highlight0.setVisible(false);
			highlight1.setVisible(true);
		});
		input1.setOnAction(e -> {
			highlight0.setVisible(false);
			highlight1.setVisible(true);
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 47.0);
		AnchorPane.setLeftAnchor(membersLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 73.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//underline1
		AnchorPane.setBottomAnchor(underline1, 50.0);
		AnchorPane.setRightAnchor(underline1, 65.0);
		AnchorPane.setLeftAnchor(underline1, 65.0);
		//filterSexLabel
		AnchorPane.setBottomAnchor(filterSexLabel, 20.0);
		AnchorPane.setLeftAnchor(filterSexLabel, 90.0);
		//filterAll
		AnchorPane.setBottomAnchor(filterAll, 20.0);
		AnchorPane.setLeftAnchor(filterAll, 220.0);
		//filterMale
		AnchorPane.setBottomAnchor(filterMale, 20.0);
		AnchorPane.setLeftAnchor(filterMale, 350.0);
		//filterFemale
		AnchorPane.setBottomAnchor(filterFemale, 20.0);
		AnchorPane.setLeftAnchor(filterFemale, 480.0);
		//filterNonBinary
		AnchorPane.setBottomAnchor(filterNonBinary, 20.0);
		AnchorPane.setLeftAnchor(filterNonBinary, 610.0);
		//aLabel
		AnchorPane.setTopAnchor(aLabel, 170.0 - 60.0);
		AnchorPane.setLeftAnchor(aLabel, 190.0);
		//betweenLabel
		AnchorPane.setTopAnchor(betweenLabel, 170.0 - 60.0);
		AnchorPane.setLeftAnchor(betweenLabel, 400.0);
		//inputA
		AnchorPane.setTopAnchor(inputA, 168.0 - 60.0);
		AnchorPane.setLeftAnchor(inputA, 245.0);
		//inputX
		AnchorPane.setTopAnchor(inputX, 168.0 - 60.0);
		AnchorPane.setLeftAnchor(inputX, 470.0);
		//inputY
		AnchorPane.setTopAnchor(inputY, 168.0 - 60.0);
		AnchorPane.setLeftAnchor(inputY, 620.0);
		//display0
		AnchorPane.setTopAnchor(display0, 230.0 - 70.0);
		AnchorPane.setLeftAnchor(display0, 70.0 + 20.0);
		//comboBox0
		AnchorPane.setTopAnchor(comboBox0, 228.0 - 70.0);
		AnchorPane.setLeftAnchor(comboBox0, 300.0 + 20.0);
		//input0
		AnchorPane.setTopAnchor(input0, 228.0 - 70.0);
		AnchorPane.setLeftAnchor(input0, 490.0 + 20.0);
		//run0
		AnchorPane.setTopAnchor(run0, 228.0 - 70.0);
		AnchorPane.setLeftAnchor(run0, 680.0 + 20.0);
		//display1
		AnchorPane.setTopAnchor(display1, 280.0 - 80.0);
		AnchorPane.setLeftAnchor(display1, 70.0 + 20.0);
		//comboBox1
		AnchorPane.setTopAnchor(comboBox1, 280.0 - 80.0);
		AnchorPane.setLeftAnchor(comboBox1, 300.0 + 20.0);
		//input1
		AnchorPane.setTopAnchor(input1, 280.0 - 80.0);
		AnchorPane.setLeftAnchor(input1, 490.0 + 20.0);
		//run1
		AnchorPane.setTopAnchor(run1, 280.0 - 80.0);
		AnchorPane.setLeftAnchor(run1, 680.0 + 20.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 310.0 - 80.0);
		AnchorPane.setRightAnchor(underline2, 65.0);
		AnchorPane.setLeftAnchor(underline2, 65.0);
		//filterMemberTable
		AnchorPane.setTopAnchor(filterMemberTable, 310 - 70.0 + 20.0);
		AnchorPane.setRightAnchor(filterMemberTable, 100.0);
		AnchorPane.setLeftAnchor(filterMemberTable, 100.0);
		AnchorPane.setBottomAnchor(filterMemberTable, 70.0);
		//highlight0
		AnchorPane.setTopAnchor(highlight0, 230.0 - 76.0);
		AnchorPane.setRightAnchor(highlight0, 80.0);
		AnchorPane.setLeftAnchor(highlight0, 80.0);
		//highlight1
		AnchorPane.setTopAnchor(highlight1, 280.0 - 84.0);
		AnchorPane.setRightAnchor(highlight1, 80.0);
		AnchorPane.setLeftAnchor(highlight1, 80.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , underline , highlight0 , highlight1 ,
				membersLabel , underline1 , underline2 , filterAll , aLabel , betweenLabel ,
				inputA , inputX , input1 , run1 , inputY , display0 , comboBox0 , input0 ,
				run0 , display1 , comboBox1 , filterMale , filterFemale , filterNonBinary ,
				filterSexLabel , filterMemberTable);
		
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	boolean deletedXYZ = false;
	
	public void membersScreen(Stage s) {
		prepareMembersTable();
		
		loadAllPotentialSearchElements();
		
		Button update= new Button("Update");
		Button filter= new Button("Filter");
		Button addMember= new Button("+");
		Button deleteMember = new Button("-");
		Button goMember = new Button(">");
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		back.setOnAction(e -> homeScreen(s));
		addMember.setOnAction(e -> addNewMember(s , false));
		goMember.setOnAction(e -> {
			if(membersTable.getSelectionModel().getSelectedItem() != null) {
				MembersListClass selectedMember = (MembersListClass) membersTable.getSelectionModel().getSelectedItem();
				String selectedMemberID = selectedMember.getMemberID();
				viewMemberDetailsScreen(s , selectedMemberID);
			}
		});
		deleteMember.setOnAction(e -> {
			if(membersTable.getSelectionModel().getSelectedItem() != null) {
				MembersListClass selectedMember = (MembersListClass) membersTable.getSelectionModel().getSelectedItem();
				String selectedMemberID = selectedMember.getMemberID();
				Alert ask = new Alert(AlertType.CONFIRMATION);
				ask.setTitle("Confirmation");
				ask.setHeaderText("Are you sure?");
				ask.setContentText(selectedMember.getMemberName() + " will be removed from "
						+ "the Gym. Once action done cannot be undone. Press OK to delete "
						+ "else press Cancel.\n\n\n");
				Optional<ButtonType> what = ask.showAndWait();
				if(what.get() == ButtonType.OK) {
					deletedXYZ = deleteSelectedMember(selectedMemberID);
					if(deletedXYZ == true) {
						prepareMembersTable();
						Alert donexz = new Alert(AlertType.INFORMATION);
						donexz.setTitle("Successful");
						donexz.setHeaderText("Member Removed");
						donexz.setContentText("Member has been removed. Press OK to continue.\n\n\n");
						donexz.show();
					}
				}
			}
		});
		update.setOnAction(e -> {
			if(membersTable.getSelectionModel().getSelectedItem() != null) {
				MembersListClass selectedMember = (MembersListClass) membersTable.getSelectionModel().getSelectedItem();
				String selectedMemberID = selectedMember.getMemberID();
				skipToUpdateMemberDetailsScreen(s , selectedMemberID);
			}
		});
		filter.setOnAction(e -> filterScreen(s , true));
		
		Label membersLabel = new Label(" Members List");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);

		update.setPrefSize(120.0, 32.0);
		filter.setPrefSize(120.0, 32.0);
		addMember.setPrefSize(100.0, 32.0);
		deleteMember.setPrefSize(100.0, 32.0);
		goMember.setPrefSize(100.0, 32.0);
		
		String addN = "-fx-text-fill: white;" + "-fx-background-color: '#00ff00';" +
		        	"-fx-font-weight: bold;" +
		        	"-fx-font-family: 'Arial';" +
		        	"-fx-font-size: 15px;";
		String addH = "-fx-text-fill: white;" + "-fx-background-color: '#00b300';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
		String addP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#008000';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
	
		addMember.setStyle(addN);
		addMember.setOnMouseMoved(e-> addMember.setStyle(addH));		//hover
		addMember.setOnMouseReleased(e-> addMember.setStyle(addN));	//normal
		addMember.setOnMousePressed(e-> addMember.setStyle(addP));	//pressed
		addMember.setOnMouseExited(e-> addMember.setStyle(addN));		//normal
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
	    			"-fx-font-weight: bold;" +
	    			"-fx-font-family: 'Arial';" +
	    			"-fx-font-size: 15px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		deleteMember.setStyle(delN);
		deleteMember.setOnMouseMoved(e-> deleteMember.setStyle(delH));		//hover
		deleteMember.setOnMouseReleased(e-> deleteMember.setStyle(delN));		//normal
		deleteMember.setOnMousePressed(e-> deleteMember.setStyle(delP));		//pressed
		deleteMember.setOnMouseExited(e-> deleteMember.setStyle(delN));		//normal
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 15px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '#0066cc';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#004d99';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		goMember.setStyle(goN);
		goMember.setOnMouseMoved(e-> goMember.setStyle(goH));			//hover
		goMember.setOnMouseReleased(e-> goMember.setStyle(goN));		//normal
		goMember.setOnMousePressed(e-> goMember.setStyle(goP));		//pressed
		goMember.setOnMouseExited(e-> goMember.setStyle(goN));		//normal
		
		update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		update.setOnMouseExited(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		filter.setOnMouseExited(e -> filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseReleased(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		filter.setOnMouseReleased(e -> filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseEntered(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		filter.setOnMouseEntered(e -> filter.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		update.setOnMousePressed(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		filter.setOnMousePressed(e -> filter.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		search.setPrefWidth(250.0);
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 93.0);
		AnchorPane.setLeftAnchor(membersLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//membersTable
		AnchorPane.setTopAnchor(membersTable, 165.0);
		AnchorPane.setLeftAnchor(membersTable, 90.0);
		AnchorPane.setRightAnchor(membersTable, 90.0);
		AnchorPane.setBottomAnchor(membersTable, 90.0);
		//update
		AnchorPane.setLeftAnchor(update, 27.0);
		AnchorPane.setBottomAnchor(update, 22.0);
		//filter
		AnchorPane.setLeftAnchor(filter, 27.0  + 120.0 + 17.0);
		AnchorPane.setBottomAnchor(filter, 22.0);
		//addMember
		AnchorPane.setRightAnchor(addMember, 127.0 + 17.0 + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(addMember, 22.0);
		//deleteMember
		AnchorPane.setRightAnchor(deleteMember, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(deleteMember, 22.0);
		//goMember
		AnchorPane.setRightAnchor(goMember, 27.0);
		AnchorPane.setBottomAnchor(goMember, 22.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, search , settings , underline ,
							membersLabel , membersTable , addMember , deleteMember , goMember , filter ,
							update);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void resetAddNewMemberWizard() {
		newID.setPromptText("Unique ID");
		newName.setPromptText("Name");
		prefixID.setStyle("-fx-font-size: 15px;");
		prefixID.setUnderline(true);
		selectedSex.getSelectionModel().selectFirst();
		weightLabel.setStyle("-fx-font-size: 15px;");
		newWeight.setPromptText("Enter Weight in Kg(s).");
		kgLabel.setStyle("-fx-font-size: 15px;");
		newDOB.setPromptText("Select Date of Birth");
		ageLabel.setStyle("-fx-font-size: 15px;");
		newAge.setStyle("-fx-font-size: 17px;");
		newPhoneNumber.setPromptText("Contact Number");
		newAltPhoneNumber.setPromptText("Alternate Contact Number");
		newEmail.setPromptText("Email");
		newOccupation.setPromptText("Occupation");
		newAddress.setPromptText("Address");
		newNationality.setPromptText("Country");
		newPin.setPromptText("PIN Code");
		newCity.setPromptText("City");
		invalidWizard.setVisible(false);
		invalidWizard.setStyle("-fx-font-family: 'Marlett';" + "-fx-font-size: 12px;" + "-fx-text-fill: red;");
		newID.setStyle(ref.getStyle());
		newName.setStyle(ref.getStyle());
		newDOB.setStyle(ref.getStyle());
		newPhoneNumber.setStyle(ref.getStyle());
		
		newID.setText("");
		newName.setText("");
		selectedSex.getSelectionModel().selectFirst();
		newWeight.setText("");
		newDOB.getEditor().clear();
		newAge.setText("");
		newPhoneNumber.setText("");
		newAltPhoneNumber.setText("");
		newEmail.setText("");
		newOccupation.setText("");
		newAddress.setText("");
		newNationality.setText("");
		newPin.setText("");
		newCity.setText("");
		newAge.setText("Null");
		
		aboutContext = "Null";
		aboutDescription.setText(aboutContext);
		savedDuration.setText("Null");
		savedGymKit.setText("Not Included");
		savedAmount.setText("Rs. 0.00");
		availableMemberships.getSelectionModel().clearSelection();
		
		userNotes.setText("");
	}
	
	public void calculateCurrentAge(String inputDOB , String ageOf) {
		String calculatedAge = "Null";
		Date date = new Date();

	    String thisYear = date.toString().substring(date.toString().lastIndexOf(" ") + 1 , date.toString().length());
	    String thisMonth = date.toString().substring(date.toString().indexOf(" ") + 1 , date.toString().indexOf(" ", date.toString().indexOf(" ") + 1)).toUpperCase();
	    int intThisMonth = 0;
	    switch(thisMonth) {
	    case "JAN":
	    	intThisMonth = 1;
	    break;
	    case "FEB":
	    	intThisMonth = 2;
	    break;
	    case "MAR":
	    	intThisMonth = 3;
	    break;
	    case "APR":
	    	intThisMonth = 4;
	    break;
	    case "MAY":
	    	intThisMonth = 5;
	    break;
	    case "JUN":
	    	intThisMonth = 6;
	    break;
	    case "JUL":
	    	intThisMonth = 7;
	    break;
	    case "AUG":
	    	intThisMonth = 8;
	    break;
	    case "SEP":
	    	intThisMonth = 9;
	    break;
	    case "OCT":
	    	intThisMonth = 10;
	    break;
	    case "NOV":
	    	intThisMonth = 11;
	    break;
	    case "DEC":
	    	intThisMonth = 12;
	    break;
	    default:
	    	intThisMonth = 0;
	    }
	    String inputYear = inputDOB.substring(0 , inputDOB.indexOf("-"));
	    String inputMonth = inputDOB.substring(inputDOB.indexOf("-") + 1 , inputDOB.indexOf("-", inputDOB.indexOf("-") + 1));
	    
	    //calculating
	    
	    int yearGap = Integer.parseInt(thisYear) - Integer.parseInt(inputYear);
	    if(intThisMonth >= Integer.parseInt(inputMonth)) {
	    	//nothing
	    }else {
	    	yearGap--;
	    }
	    calculatedAge = Integer.toString(yearGap);
	    
	    if(ageOf.equalsIgnoreCase("member") == true)
	    	newAge.setText(calculatedAge);
	    else if(ageOf.equalsIgnoreCase("trainer") == true)
	    	newTrainerAge.setText(calculatedAge);
	    else if(ageOf.equalsIgnoreCase("updateMember") == true)
	    	viewMemberModifyAge.setText(calculatedAge);
	    else if(ageOf.equalsIgnoreCase("updateTrainer") == true)
	    	viewTrainerModifyAge.setText(calculatedAge);
	}
	
	boolean isCorrect = true;
	
	public void addNewMember(Stage s , boolean fromAddNewMember2) {
		
		if(fromAddNewMember2 == false) {
			resetAddNewMemberWizard();
		}else {						//called from addNewMember2(Stage s)
			newID.setStyle(ref.getStyle());
			newName.setStyle(ref.getStyle());
			newDOB.setStyle(ref.getStyle());
			newPhoneNumber.setStyle(ref.getStyle());
		}
			
		Label heading = new Label(" Add New Gym Member Wizard (Step 1 of 3)");
		heading.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 27px;");
		String space = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "   ";
		Label headingUnderline = new Label(space);
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.65);
		Button next = new Button("Next");
		Button reset = new Button("Reset");
		next.setPrefSize(100.0, 33.0);
		reset.setPrefSize(100.0, 33.0);
		next.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		reset.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#008000';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		back.setOnAction(e -> membersScreen(s));
		newDOB.setOnAction(e -> calculateCurrentAge(newDOB.getValue().toString() , "member"));
		reset.setOnAction(e -> resetAddNewMemberWizard());
		
		CheckDuplicateEmails ch = new CheckDuplicateEmails();
		next.setOnAction(e -> {
			isCorrect = true;
			if(newAge.getText().equalsIgnoreCase("Null") == false) {
				if(Integer.parseInt(newAge.getText().trim()) <= 0) {
					isCorrect = false;
				}
			}
			if(newEmail.getText().trim().equals("") == false) {
				if(newEmail.getText().trim().contains("@") == false) {
					isCorrect = false;
				}
			}
			if(newAltPhoneNumber.getText().trim().equals("") == false) {
				if(checkPhoneNumber(newAltPhoneNumber.getText().trim()) == false) {
					isCorrect = false;
				}
			}
			if(newPhoneNumber.getText().trim().equals("") == true) {
				isCorrect = false;
			}else {
				if(checkPhoneNumber(newPhoneNumber.getText().trim()) == false) {
					isCorrect = false;
				}
			}
			if(newPin.getText().trim().equals("") == false) {
				if(checkPin(newPin.getText().trim()) == false) {
					isCorrect = false;
				}
			}
			
			if(isCorrect == true) {
				if((newEmail.getText().trim().equalsIgnoreCase("") == false && ch.isNewEmail(connection, newEmail.getText().trim()) == false) || newID.getText().equals("") == true || newName.getText().equals("") == true || newDOB.getEditor().getText().equals("") == true || newPhoneNumber.getText().equals("") == true) {
					//invalid
					
					if(newID.getText().equals("") == true) {
						newID.setStyle("-fx-border-color: 'red';");
					}else {
						newID.setStyle(ref.getStyle());
					}
					if(newName.getText().equals("") == true) {
						newName.setStyle("-fx-border-color: 'red';");
					}else {
						newName.setStyle(ref.getStyle());
					}
					if(newDOB.getEditor().getText().equals("") == true) {
						newDOB.setStyle("-fx-border-color: 'red';");
					}else {
						newDOB.setStyle(ref.getStyle());
					}
					if(newPhoneNumber.getText().equals("") == true) {
						newPhoneNumber.setStyle("-fx-border-color: 'red';");
					}else {
						newPhoneNumber.setStyle(ref.getStyle());
					}
					
					if(newEmail.getText().trim().equalsIgnoreCase("") == false && ch.isNewEmail(connection, newEmail.getText().trim()) == false) {
						Alert duplicateEmail = new Alert(AlertType.WARNING);
						duplicateEmail.setTitle("Warning");
						duplicateEmail.setHeaderText("Change Email");
						duplicateEmail.setContentText("Entered email is already in use. Change "
								+ "your email and try again.\n\n\n");
						duplicateEmail.show();
					}
					
					invalidWizard.setVisible(true);
				}else {
					invalidWizard.setVisible(false);
					addNewMember2(s);
				}
			}else {
				//invalid
				Alert al = new Alert(AlertType.WARNING);
				al.setTitle("Warning");
				al.setHeaderText("Invalid Data Entered");
				al.setContentText("Some of the entered data might be invalid. "
						+ "Make sure that phone number is of ten digits and email entered "
						+ "is valid.\nMake sure that age is positive number and "
						+ "PIN Code consists of only six digits.\nCheck and try again.\n\n\n");
				al.show();
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//heading
		AnchorPane.setTopAnchor(heading, 78.0);
		AnchorPane.setLeftAnchor(heading, 40.0);
		//headingUnderline
		AnchorPane.setTopAnchor(headingUnderline, 87.0);
		AnchorPane.setLeftAnchor(headingUnderline, 40.0);
		AnchorPane.setRightAnchor(headingUnderline, 40.0);
		//next
		AnchorPane.setRightAnchor(next, 27.0);
		AnchorPane.setBottomAnchor(next, 22.0);
		//invalidWizard
		AnchorPane.setRightAnchor(invalidWizard, 29.0);
		AnchorPane.setBottomAnchor(invalidWizard, 62.0);
		//reset
		AnchorPane.setRightAnchor(reset, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(reset, 22.0);
		//prefixID
		AnchorPane.setTopAnchor(prefixID, 146.0);
		AnchorPane.setLeftAnchor(prefixID, 170.0);
		//newID
		AnchorPane.setTopAnchor(newID, 145.0);
		AnchorPane.setLeftAnchor(newID, 206.0);
		AnchorPane.setRightAnchor(newID, 550.0);
		//newName
		AnchorPane.setTopAnchor(newName, 145.0);
		AnchorPane.setLeftAnchor(newName, 360.0);
		AnchorPane.setRightAnchor(newName, 170.0);
		//selectedSex
		AnchorPane.setTopAnchor(selectedSex, 185.0);
		AnchorPane.setLeftAnchor(selectedSex, 190.0);	//20.0
		AnchorPane.setRightAnchor(selectedSex, 580.0);
		//weightLabel
		AnchorPane.setTopAnchor(weightLabel, 190.0);
		AnchorPane.setLeftAnchor(weightLabel, 470.0);
		//kgLabel
		AnchorPane.setTopAnchor(kgLabel, 190.0);
		AnchorPane.setRightAnchor(kgLabel, 187.0);
		//newWeight
		AnchorPane.setTopAnchor(newWeight, 185.0);
		AnchorPane.setLeftAnchor(newWeight, 540.0);
		AnchorPane.setRightAnchor(newWeight, 215.0);
		//newDOB
		AnchorPane.setTopAnchor(newDOB, 220.0 + 10.0);
		AnchorPane.setLeftAnchor(newDOB, 190.0);
		AnchorPane.setRightAnchor(newDOB, 450.0);
		//ageLabel
		AnchorPane.setTopAnchor(ageLabel, 222.0 + 10.0);
		AnchorPane.setRightAnchor(ageLabel, 310.0);
		//newAge
		AnchorPane.setTopAnchor(newAge, 222.0 + 6.0);
		AnchorPane.setRightAnchor(newAge, 270.0);
		//newPhoneNumber
		AnchorPane.setTopAnchor(newPhoneNumber, 255.0 + 20.0);
		AnchorPane.setLeftAnchor(newPhoneNumber, 190.0);
		AnchorPane.setRightAnchor(newPhoneNumber, 455.0);
		//newAltPhoneNumber
		AnchorPane.setTopAnchor(newAltPhoneNumber, 255.0 + 20.0);
		AnchorPane.setLeftAnchor(newAltPhoneNumber, 455.0);
		AnchorPane.setRightAnchor(newAltPhoneNumber, 190.0);
		//newEmail
		AnchorPane.setTopAnchor(newEmail, 255.0 + 20.0 + 40.0);
		AnchorPane.setLeftAnchor(newEmail, 190.0);
		AnchorPane.setRightAnchor(newEmail, 340.0);
		//newOccupation
		AnchorPane.setTopAnchor(newOccupation, 255.0 + 20.0 + 40.0);
		AnchorPane.setLeftAnchor(newOccupation, 570.0);
		AnchorPane.setRightAnchor(newOccupation, 190.0);
		//newAddress
		AnchorPane.setTopAnchor(newAddress, 255.0 + 20.0 + 80.0);
		AnchorPane.setLeftAnchor(newAddress, 170.0);
		AnchorPane.setRightAnchor(newAddress, 170.0);
		//newNationality
		AnchorPane.setTopAnchor(newNationality, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newNationality, 430.0);
		AnchorPane.setRightAnchor(newNationality, 320.0);
		//newPin
		AnchorPane.setTopAnchor(newPin, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newPin, 590.0);
		AnchorPane.setRightAnchor(newPin, 170.0);
		//newCity
		AnchorPane.setTopAnchor(newCity, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newCity, 170.0);
		AnchorPane.setRightAnchor(newCity, 480.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, settings , headingUnderline , heading , reset , next ,
				newID , newName , selectedSex , weightLabel , kgLabel , newWeight , newDOB , ageLabel ,
				newAge , newPhoneNumber , newAltPhoneNumber , newEmail , newOccupation , newAddress ,
				newCity , newNationality , newPin , invalidWizard , prefixID);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	public void storeDataToLabels(String selectedMembership) {
		try {
			PreparedStatement loadDuration = connection.prepareStatement("select * from `studentdatabase`.`gym_memberships` where `Name` = '" + selectedMembership + "';");  
		    ResultSet d = loadDuration.executeQuery();  
		    d.next();
		    savedDuration.setText(d.getString(2) + "");
		    savedAmount.setText("Rs. " + d.getString(3));
		    savedGymKit.setText(d.getString(4) + "");
		    aboutDescription.setText(d.getString(5) + "");
		    
		    
		}catch(Exception e) {}
	}
	public void addNewMember2(Stage s) {
		//load available Memberships Names
		savedMemberships.clear();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from `studentdatabase`.`gym_memberships`;");  
		    ResultSet rs2 = stmt.executeQuery();  
		    while(rs2.next()){  
		    	savedMemberships.add(rs2.getString(1).toString());
		    }
		}catch(Exception e) {}
		
		Label heading = new Label(" Add New Gym Member Wizard (Step 2 of 3)");
		heading.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 27px;");
		String space = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "   ";
		Label headingUnderline = new Label(space);
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.65);
		Label selectMembershipLabel = new Label("Choose a Membership:");
		selectMembershipLabel.setStyle("-fx-font-size: 15px;" + "-fx-font-family: 'MS Reference Sans Serif';");
		Label durationLabel = new Label("Duration:");
		durationLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		Label aboutLabel = new Label("About:");
		aboutLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		
		aboutDescription.setText(aboutContext);
		aboutDescription.setStyle("-fx-border-color: '#666666';" + "-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		aboutDescription.setEditable(false);
		aboutDescription.setWrapText(true);
		Label gymKitLabel = new Label("Gym Kit:");
		gymKitLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		Label amountLabel = new Label("Amount:");
		amountLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 13px;");
		
		savedDuration.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 15px;");
		savedGymKit.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 15px;");
		savedAmount.setStyle("-fx-font-family: 'Microsoft JhengHei UI';" + "-fx-font-size: 15px;");
		
		Button next = new Button("Next");
		Button cancel = new Button("Cancel");
		next.setPrefSize(100.0, 33.0);
		cancel.setPrefSize(100.0, 33.0);
		next.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		cancel.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		back.setOnAction(e -> addNewMember(s , true));
		cancel.setOnAction(e -> homeScreen(s));
		availableMemberships.setOnAction(e -> {
			String selectedMembership = availableMemberships.getSelectionModel().getSelectedItem();
			//store row Cell Data to Labels
			storeDataToLabels(selectedMembership);
			
		});
		next.setOnAction(e -> {
			String selectedMembership = availableMemberships.getSelectionModel().getSelectedItem();
			if(selectedMembership != null) {
				addNewMember3(s);
			}else {
				Alert noMembershipSelected = new Alert(AlertType.WARNING);
				noMembershipSelected.setTitle("Invalid");
				noMembershipSelected.setHeaderText("No Membership Selected");
				noMembershipSelected.setContentText("Please select a Membership to continue.\n\n");
				noMembershipSelected.show();
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//heading
		AnchorPane.setTopAnchor(heading, 78.0);
		AnchorPane.setLeftAnchor(heading, 40.0);
		//headingUnderline
		AnchorPane.setTopAnchor(headingUnderline, 87.0);
		AnchorPane.setLeftAnchor(headingUnderline, 40.0);
		AnchorPane.setRightAnchor(headingUnderline, 40.0);
		//next
		AnchorPane.setRightAnchor(next, 27.0);
		AnchorPane.setBottomAnchor(next, 22.0);
		//cancel
		AnchorPane.setRightAnchor(cancel, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(cancel, 22.0);
		//availableMemberships
		AnchorPane.setTopAnchor(availableMemberships, 138.0);
		AnchorPane.setLeftAnchor(availableMemberships, 380.0);
		AnchorPane.setRightAnchor(availableMemberships, 170.0);
		//selectMembershipLabel
		AnchorPane.setTopAnchor(selectMembershipLabel, 140.0);
		AnchorPane.setLeftAnchor(selectMembershipLabel, 170.0);
		//durationLabel
		AnchorPane.setTopAnchor(durationLabel, 210.0);
		AnchorPane.setRightAnchor(durationLabel, 630.0);
		//aboutLabel
		AnchorPane.setTopAnchor(aboutLabel, 250.0);
		AnchorPane.setRightAnchor(aboutLabel, 630.0);
		//aboutDescription
		AnchorPane.setTopAnchor(aboutDescription, 245.0);
		AnchorPane.setLeftAnchor(aboutDescription, 296.0);
		AnchorPane.setRightAnchor(aboutDescription, 100.0);
		AnchorPane.setBottomAnchor(aboutDescription, 200.0);
		//gymKitLabel
		AnchorPane.setTopAnchor(gymKitLabel, 390.0);
		AnchorPane.setRightAnchor(gymKitLabel, 630.0);
		//amountLabel
		AnchorPane.setTopAnchor(amountLabel, 440.0);
		AnchorPane.setRightAnchor(amountLabel, 630.0);
		//savedDuration
		AnchorPane.setTopAnchor(savedDuration, 208.0);
		AnchorPane.setLeftAnchor(savedDuration, 296.0);
		//savedGymKit
		AnchorPane.setTopAnchor(savedGymKit, 388.0);
		AnchorPane.setLeftAnchor(savedGymKit, 296.0);
		//savedAmount
		AnchorPane.setTopAnchor(savedAmount, 438.0);
		AnchorPane.setLeftAnchor(savedAmount, 296.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, settings , headingUnderline , heading , next , cancel ,
				availableMemberships , selectMembershipLabel , durationLabel , aboutLabel ,
				aboutDescription , gymKitLabel , amountLabel ,savedDuration , savedGymKit, savedAmount);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return(dateFormat.format(date).toString());	//Example: 16/11/2019
	}
	
	public void deletePhoneMemberConnectivityEntry(String phone) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_members_connectivity` WHERE (`PhoneNumberOne` = '" + phone + "');");
			ps.execute();
		}catch(Exception e) {}
	}
	
	public void makeNewMemberEntry(Stage s , String newID , String newName , String selectedSex , String newWeight , String newDOB , String newAge , String newPhoneNumber , String newAltPhoneNumber , String newEmail , String newOccupation , String newAddress , String newCity , String newNationality , String newPin , String availableMemberships , String savedAmount , String enteredNote , String getCurrentDate) {
		//removing "Rs ." from " Rs. <Amount>"
		savedAmount = savedAmount.substring(savedAmount.indexOf(" ") + 1 , savedAmount.length());
		//converting to DBMS Date format
		StringTokenizer st = new StringTokenizer(getCurrentDate , "/");
		String day = st.nextToken();
		String month = st.nextToken();
		String year = st.nextToken();
		getCurrentDate = "";
		getCurrentDate = year + "-" + month + "-" + day;
		
		try {
			String insertCode0 = "INSERT INTO `studentdatabase`.`gym_members_connectivity` (`PhoneNumberOne`, `PhoneNumberTwo`, `Email`, `Address`, `CITY`, `COUNTRY`, `PinCode` , `ID`) VALUES ('" + newPhoneNumber + "', '" + newAltPhoneNumber + "', '" + newEmail + "', '" + newAddress + "', '" + newCity + "', '" + newNationality + "', '" + newPin + "', '" + "MBR" + newID + "');";
			PreparedStatement ins0 = connection.prepareStatement(insertCode0);
			ins0.execute();
			
			String insertCode = "INSERT INTO `studentdatabase`.`gym_members` (`ID`, `Name`, `Gender`, `Weight`, `DOB`, `Age`, `PhoneNumOne`, `Membership_Type`, `Amount`, `Note`, `Date_Of_Joining`, `Occupation`) VALUES ('" + "MBR" + newID + "', '" + newName +"', '" + selectedSex +"', '" + newWeight + "', '" + newDOB + "', '" + newAge + "', '" + newPhoneNumber + "', '" + availableMemberships + "', '" + savedAmount + "', '" + enteredNote + "', '" + getCurrentDate + "', '" + newOccupation + "');";
			PreparedStatement ins = connection.prepareStatement(insertCode);
			ins.execute();
			
			Alert done = new Alert(AlertType.INFORMATION);
			done.setTitle("Successful");
			done.setHeaderText("New Member Enrolled");
			done.setContentText("New member " + newName + " has been successfully enrolled to the Gym."
					+ " Press OK to continue.\n\n\n");
			Optional<ButtonType> bt = done.showAndWait();
			if(bt.get() == ButtonType.OK) {
				membersScreen(s);
			}
		}catch(Exception e) {
			deletePhoneMemberConnectivityEntry(newPhoneNumber);
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Error Occured");
			err.setHeaderText("Enrollment Failed");
			err.setContentText("New Member enrollment was not possible. Please check your phone "
					+ "number, ID. Enter valid data. Try again by changing Phone number or ID.\n\n\n");
			err.show();
		}
		
	}
	
	public void addNewMember3(Stage s) {
		Button addNewMember = new Button("Add");
		Button cancel = new Button("Cancel");
		addNewMember.setPrefSize(100.0, 33.0);
		cancel.setPrefSize(100.0, 33.0);
		addNewMember.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		cancel.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		
		Label heading = new Label(" Add New Gym Member Wizard (Step 3 of 3)");
		heading.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 27px;");
		String space = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "   ";
		Label headingUnderline = new Label(space);
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.65);
		userNotes.setWrapText(true);
		userNotes.setStyle("-fx-border-color: '#0073e6';" + "-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 16px;");
		userNotes.setPromptText("Write your Specifications here...");
		String noteText = "Hey, if you want to mention anything then write it down below. Your trainer"
						+ " will be able to see it and assist you accordingly. If\nyou don't have anything"
						+ " to tell then press Add to save.";
		Label note = new Label(noteText);
		note.setStyle("-fx-font-size: 14px;");
		String dojText = "Your session will start from " + getCurrentDate() + " (today).";
		Label doj = new Label(dojText);
		doj.setStyle("-fx-font-size: 14px;");
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		back.setOnAction(e -> addNewMember2(s));
		cancel.setOnAction(e -> homeScreen(s));
		addNewMember.setOnAction(e -> {
			String enteredNote = userNotes.getText().trim();

			//3. Collect all INFORMATION entered and send it to a function with the info as parameters.
			//(from function store it to DB) Make CONFIRMATION ALERT BOX in the new function.
			makeNewMemberEntry(s , newID.getText().trim() , newName.getText().trim() , selectedSex.getSelectionModel().getSelectedItem() , newWeight.getText().trim() , newDOB.getValue().toString() , newAge.getText().trim() , newPhoneNumber.getText().trim() , newAltPhoneNumber.getText().trim() , newEmail.getText().trim() , newOccupation.getText().trim() , newAddress.getText().trim() , newCity.getText().trim() , newNationality.getText().trim() , newPin.getText().trim() , availableMemberships.getSelectionModel().getSelectedItem() , savedAmount.getText() , enteredNote , getCurrentDate().toString());
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//heading
		AnchorPane.setTopAnchor(heading, 78.0);
		AnchorPane.setLeftAnchor(heading, 40.0);
		//headingUnderline
		AnchorPane.setTopAnchor(headingUnderline, 87.0);
		AnchorPane.setLeftAnchor(headingUnderline, 40.0);
		AnchorPane.setRightAnchor(headingUnderline, 40.0);
		//addNewMember
		AnchorPane.setRightAnchor(addNewMember, 27.0);
		AnchorPane.setBottomAnchor(addNewMember, 22.0);
		//cancel
		AnchorPane.setRightAnchor(cancel, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(cancel, 22.0);
		//note
		AnchorPane.setTopAnchor(note, 145.0);
		AnchorPane.setLeftAnchor(note, 67.0);
		//userNotes
		AnchorPane.setTopAnchor(userNotes, 210.0);
		AnchorPane.setLeftAnchor(userNotes, 100.0);
		AnchorPane.setRightAnchor(userNotes, 100.0);
		AnchorPane.setBottomAnchor(userNotes, 90.0);
		//doj
		AnchorPane.setLeftAnchor(doj, 67.0);
		AnchorPane.setBottomAnchor(doj, 50.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, settings , heading , headingUnderline , addNewMember ,
								cancel , note , userNotes , doj);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label prefixTrainer = new Label("TNR");
	
	public void resetAddNewTrainerWizard() {
		prefixTrainer.setStyle("-fx-font-size: 15px;");
		prefixTrainer.setUnderline(true);
		newTrainerID.setPromptText("Unique ID");
		newTrainerName.setPromptText("Name");
		selectedTrainerSex.getSelectionModel().selectFirst();
		salaryLabel.setStyle("-fx-font-size: 15px;");
		newTrainerSalary.setPromptText("Salary in Rupee(s)");
		newTrainerDOB.setPromptText("Select Date of Birth");
		ageTrainerLabel.setStyle("-fx-font-size: 15px;");
		newTrainerAge.setStyle("-fx-font-size: 17px;");
		newTrainerPhoneNumber.setPromptText("Contact Number");
		newTrainerAltPhoneNumber.setPromptText("Alternate Contact Number");
		newTrainerEmail.setPromptText("Email");
		newTrainerOccupation.setPromptText("Occupation");
		newTrainerAddress.setPromptText("Address");
		newTrainerNationality.setPromptText("Country");
		newTrainerPin.setPromptText("PIN Code");
		newTrainerCity.setPromptText("City");
		invalidTrainerWizard.setVisible(false);
		invalidTrainerWizard.setStyle("-fx-font-family: 'Marlett';" + "-fx-font-size: 12px;" + "-fx-text-fill: red;");
		newTrainerID.setStyle(ref.getStyle());
		newTrainerName.setStyle(ref.getStyle());
		newTrainerDOB.setStyle(ref.getStyle());
		newTrainerPhoneNumber.setStyle(ref.getStyle());
		monthLabel.setStyle("-fx-font-size: 15px;");
		membershipLabel.setStyle("-fx-font-size: 15px;");
		dojLabel.setStyle("-fx-font-size: 13px;");
		
		dojLabel.setText("Date of Joining:\t" + getCurrentDate() + " (today)");
		//loading membershipTrainer
		membershipTrainer.clear();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from `studentdatabase`.`gym_memberships`;");  
		    ResultSet rs2 = stmt.executeQuery();  
		    while(rs2.next()){  
		    	membershipTrainer.add(rs2.getString(1).toString());
		    }
		}catch(Exception e) {}
		
		newTrainerID.setText("");
		newTrainerName.setText("");
		selectedTrainerSex.getSelectionModel().selectFirst();
		newTrainerDesignation.getSelectionModel().selectFirst();
		newTrainerSalary.setText("");
		try {
			newTrainerDOB.getEditor().clear();
		}catch(Exception e) {}
		newTrainerAge.setText("");
		newTrainerPhoneNumber.setText("");
		newTrainerAltPhoneNumber.setText("");
		newTrainerEmail.setText("");
		newTrainerAddress.setText("");
		newTrainerNationality.setText("");
		newTrainerPin.setText("");
		newTrainerCity.setText("");
		newTrainerAge.setText("Null");
		
	}
	
	public void addNewTrainer(Stage s) {
		resetAddNewTrainerWizard();
		
		Label heading = new Label(" Add New Trainer Wizard");
		heading.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 27px;");
		String space = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "   ";
		Label headingUnderline = new Label(space);
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.65);
		
		Button addNewTrainer = new Button("Add");
		Button resetTrainer = new Button("Reset");
		addNewTrainer.setPrefSize(100.0, 33.0);
		resetTrainer.setPrefSize(100.0, 33.0);
		addNewTrainer.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		resetTrainer.setStyle("-fx-text-fill: white;" + "-fx-background-color: '#008000';" + "-fx-font-weight: bold;" + "-fx-font-family: 'Arial';" + "-fx-font-size: 15px;");
		
		EntryToTrainerTable ettt = new EntryToTrainerTable();
		
		//controls
		back.setOnAction(e -> trainersScreen(s));
		settings.setOnAction(e -> settingsScreen(s));
		newTrainerDOB.setOnAction(e -> calculateCurrentAge(newTrainerDOB.getValue().toString() , "trainer"));
		resetTrainer.setOnAction(e -> resetAddNewTrainerWizard());
		
		CheckDuplicateEmails cde = new CheckDuplicateEmails();
		
		addNewTrainer.setOnAction(e -> {
			if((newTrainerEmail.getText().trim().equalsIgnoreCase("") == false && cde.isNewEmail(connection, newTrainerEmail.getText().trim()) == false) || membershipToTrainer.getSelectionModel().getSelectedItem() == null || newTrainerID.getText().equals("") == true || newTrainerName.getText().equals("") == true || newTrainerDOB.getEditor().getText().equals("") == true || newTrainerPhoneNumber.getText().equals("") == true) {
				//invalid
				
				if(newTrainerID.getText().equals("") == true) {
					newTrainerID.setStyle("-fx-border-color: 'red';");
				}else {
					newTrainerID.setStyle(ref.getStyle());
				}
				if(newTrainerName.getText().equals("") == true) {
					newTrainerName.setStyle("-fx-border-color: 'red';");
				}else {
					newTrainerName.setStyle(ref.getStyle());
				}
				if(newTrainerDOB.getEditor().getText().equals("") == true) {
					newTrainerDOB.setStyle("-fx-border-color: 'red';");
				}else {
					newTrainerDOB.setStyle(ref.getStyle());
				}
				if(newTrainerPhoneNumber.getText().equals("") == true) {
					newTrainerPhoneNumber.setStyle("-fx-border-color: 'red';");
				}else {
					newTrainerPhoneNumber.setStyle(ref.getStyle());
				}
				if(membershipToTrainer.getSelectionModel().getSelectedItem() == null) {
					membershipToTrainer.setStyle("-fx-border-color: 'red';");
				}else {
					membershipToTrainer.setStyle(ref.getStyle());
				}
				
				if(newTrainerEmail.getText().trim().equalsIgnoreCase("") == false && cde.isNewEmail(connection, newTrainerEmail.getText().trim()) == false) {
					Alert duplicateEmail = new Alert(AlertType.WARNING);
					duplicateEmail.setTitle("Warning");
					duplicateEmail.setHeaderText("Change Email");
					duplicateEmail.setContentText("Entered email is already in use. Change "
							+ "your email and try again.\n\n\n");
					duplicateEmail.show();
				}
				
				invalidTrainerWizard.setVisible(true);
			}else {
				this.isCorrect = true;
				
				if(newTrainerPhoneNumber.getText().trim().equals("") == false) {
					if(checkPhoneNumber(newTrainerPhoneNumber.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(newTrainerAltPhoneNumber.getText().trim().equals("") == false) {
					if(checkPhoneNumber(newTrainerAltPhoneNumber.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(newTrainerEmail.getText().trim().equals("") == false) {
					if(newTrainerEmail.getText().trim().contains("@") == false) {
						this.isCorrect = false;
					}
				}
				if(newTrainerPin.getText().trim().equals("") == false) {
					if(checkPin(newTrainerPin.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(newTrainerAge.getText().trim().equalsIgnoreCase("Null") == false) {
					if(Integer.parseInt(newTrainerAge.getText().trim()) <= 0) {
						this.isCorrect = false;
					}
				}
				
				if(this.isCorrect == true) {
					invalidTrainerWizard.setVisible(false);
					newTrainerID.setStyle(ref.getStyle());
					newTrainerName.setStyle(ref.getStyle());
					newTrainerDOB.setStyle(ref.getStyle());
					newTrainerPhoneNumber.setStyle(ref.getStyle());
					membershipToTrainer.setStyle(ref.getStyle());
					boolean isDone = ettt.makeNewTrainerEntry(connection , newTrainerID.getText() , newTrainerName.getText() , selectedTrainerSex.getSelectionModel().getSelectedItem() , newTrainerSalary.getText() , newTrainerDOB.getValue().toString() , newTrainerAge.getText() , newTrainerPhoneNumber.getText() , newTrainerAltPhoneNumber.getText() , newTrainerEmail.getText() , newTrainerDesignation.getSelectionModel().getSelectedItem() , newTrainerAddress.getText() ,newTrainerCity.getText() , newTrainerNationality.getText() , newTrainerPin.getText() , membershipToTrainer.getSelectionModel().getSelectedItem() , getCurrentDate());
					if(isDone == true) {
						Alert yes = new Alert(AlertType.INFORMATION);
						yes.setTitle("Successful");
						yes.setHeaderText("Trainer Added");
						yes.setContentText("Trainer " + newTrainerName.getText() + " is added successfully to "
								+ "the gym.\n\n\n");
						Optional<ButtonType> op = yes.showAndWait();
						if(op.get() == ButtonType.OK) {
							trainersScreen(s);
						}
					}else {
						Alert no = new Alert(AlertType.ERROR);
						no.setTitle("Failed");
						no.setHeaderText("Trainer was not Added");
						no.setContentText("An error occured. Please try by giving valid data. Or by "
								+ "changing the Trainer ID.\n\n\n");
						no.show();
					}
				}else {
					Alert al = new Alert(AlertType.WARNING);
					al.setTitle("Warning");
					al.setHeaderText("Invalid Data Entered");
					al.setContentText("Some of the entered data might be invalid. "
							+ "Make sure that phone number is of ten digits and email entered "
							+ "is valid.\nMake sure that age is positive number and "
							+ "PIN Code consists of only six digits.\nCheck and try again.\n\n\n");
					al.show();
				}
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//heading
		AnchorPane.setTopAnchor(heading, 78.0);
		AnchorPane.setLeftAnchor(heading, 40.0);
		//headingUnderline
		AnchorPane.setTopAnchor(headingUnderline, 87.0);
		AnchorPane.setLeftAnchor(headingUnderline, 40.0);
		AnchorPane.setRightAnchor(headingUnderline, 40.0);
		//addNewTrainer
		AnchorPane.setRightAnchor(addNewTrainer, 27.0);
		AnchorPane.setBottomAnchor(addNewTrainer, 22.0);
		//invalidTrainerLabel
		AnchorPane.setRightAnchor(invalidTrainerWizard, 29.0);
		AnchorPane.setBottomAnchor(invalidTrainerWizard, 62.0);
		//resetTrainer
		AnchorPane.setRightAnchor(resetTrainer, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(resetTrainer, 22.0);
		//prefixTrainer
		AnchorPane.setTopAnchor(prefixTrainer, 146.0);
		AnchorPane.setLeftAnchor(prefixTrainer, 170.0);
		//newTrainerID
		AnchorPane.setTopAnchor(newTrainerID, 145.0);
		AnchorPane.setLeftAnchor(newTrainerID, 206.0);
		AnchorPane.setRightAnchor(newTrainerID, 550.0);
		//newTrainerName
		AnchorPane.setTopAnchor(newTrainerName, 145.0);
		AnchorPane.setLeftAnchor(newTrainerName, 360.0);
		AnchorPane.setRightAnchor(newTrainerName, 170.0);
		//selectedTrainerSex
		AnchorPane.setTopAnchor(selectedTrainerSex, 185.0);
		AnchorPane.setLeftAnchor(selectedTrainerSex, 190.0);
		AnchorPane.setRightAnchor(selectedTrainerSex, 580.0);
		//salaryLabel
		AnchorPane.setTopAnchor(salaryLabel, 190.0);
		AnchorPane.setLeftAnchor(salaryLabel, 465.0);
		//monthLabel
		AnchorPane.setTopAnchor(monthLabel, 190.0);
		AnchorPane.setRightAnchor(monthLabel, 187.0);
		//newTrainerSalary
		AnchorPane.setTopAnchor(newTrainerSalary, 185.0);
		AnchorPane.setLeftAnchor(newTrainerSalary, 520.0);
		AnchorPane.setRightAnchor(newTrainerSalary, 250.0);
		//newTrainerDOB
		AnchorPane.setTopAnchor(newTrainerDOB, 220.0 + 10.0);
		AnchorPane.setLeftAnchor(newTrainerDOB, 190.0);
		AnchorPane.setRightAnchor(newTrainerDOB, 450.0);
		//ageTrainerLabel
		AnchorPane.setTopAnchor(ageTrainerLabel, 222.0 + 10.0);
		AnchorPane.setRightAnchor(ageTrainerLabel, 310.0);
		//newTrainerAge
		AnchorPane.setTopAnchor(newTrainerAge, 222.0 + 6.0);
		AnchorPane.setRightAnchor(newTrainerAge, 270.0);
		//newTrainerPhoneNumber
		AnchorPane.setTopAnchor(newTrainerPhoneNumber, 255.0 + 20.0);
		AnchorPane.setLeftAnchor(newTrainerPhoneNumber, 190.0);
		AnchorPane.setRightAnchor(newTrainerPhoneNumber, 455.0);
		//newTrainerAltPhoneNumber
		AnchorPane.setTopAnchor(newTrainerAltPhoneNumber, 255.0 + 20.0);
		AnchorPane.setLeftAnchor(newTrainerAltPhoneNumber, 455.0);
		AnchorPane.setRightAnchor(newTrainerAltPhoneNumber, 190.0);
		//newTrainerEmail
		AnchorPane.setTopAnchor(newTrainerEmail, 255.0 + 20.0 + 40.0);
		AnchorPane.setLeftAnchor(newTrainerEmail, 190.0);
		AnchorPane.setRightAnchor(newTrainerEmail, 340.0);
		//newTrainerDesignation
		AnchorPane.setTopAnchor(newTrainerDesignation, 255.0 + 20.0 + 40.0);
		AnchorPane.setLeftAnchor(newTrainerDesignation, 570.0);
		AnchorPane.setRightAnchor(newTrainerDesignation, 190.0);
		//newTrainerAddress
		AnchorPane.setTopAnchor(newTrainerAddress, 255.0 + 20.0 + 80.0);
		AnchorPane.setLeftAnchor(newTrainerAddress, 170.0);
		AnchorPane.setRightAnchor(newTrainerAddress, 170.0);
		//newTrainerNationality
		AnchorPane.setTopAnchor(newTrainerNationality, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newTrainerNationality, 430.0);
		AnchorPane.setRightAnchor(newTrainerNationality, 320.0);
		//newTrainerPin
		AnchorPane.setTopAnchor(newTrainerPin, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newTrainerPin, 590.0);
		AnchorPane.setRightAnchor(newTrainerPin, 170.0);
		//newTrainerCity
		AnchorPane.setTopAnchor(newTrainerCity, 255.0 + 20.0 + 120.0);
		AnchorPane.setLeftAnchor(newTrainerCity, 170.0);
		AnchorPane.setRightAnchor(newTrainerCity, 480.0);
		//membershipLabel
		AnchorPane.setTopAnchor(membershipLabel, 255.0 + 20.0 + 120.0 + 42.0);
		AnchorPane.setLeftAnchor(membershipLabel, 170.0);
		//membershipToTrainer
		AnchorPane.setTopAnchor(membershipToTrainer, 255.0 + 20.0 + 120.0 + 41.0);
		AnchorPane.setLeftAnchor(membershipToTrainer, 340.0);
		AnchorPane.setRightAnchor(membershipToTrainer, 360.0);
		//dojLabel
		AnchorPane.setTopAnchor(dojLabel, 255.0 + 20.0 + 120.0 + 95.0);
		AnchorPane.setLeftAnchor(dojLabel, 40.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, settings , heading , headingUnderline , addNewTrainer ,
				invalidTrainerWizard , resetTrainer , newTrainerID , newTrainerName , selectedTrainerSex ,
				monthLabel , salaryLabel , newTrainerSalary , newTrainerDOB , ageTrainerLabel , newTrainerAge ,
				newTrainerPhoneNumber , newTrainerAltPhoneNumber , prefixTrainer ,
				newTrainerEmail , newTrainerDesignation , newTrainerAddress , newTrainerNationality ,
				newTrainerPin , newTrainerCity , membershipLabel , dojLabel , membershipToTrainer);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void prepareTrainersTable() {
		loadAllPotentialSearchElements();
		
		try {
			try {
				//check whether GYM_TRAINERS_CONNECTIVITY TABLE is present or not
				PreparedStatement test0 = connection.prepareStatement("select * from `studentdatabase`.`GYM_TRAINERS_CONNECTIVITY`;");  
			    ResultSet run = test0.executeQuery();
			}catch(Exception e) {
				//create GYM_TRAINERS_CONNECTIVITY TABLE
				String code1 = "CREATE TABLE `studentdatabase`.GYM_TRAINERS_CONNECTIVITY (PhoneNumberOne VARCHAR(13),"
						+ "PhoneNumberTwo VARCHAR(13) , Email VARCHAR(50) , Address VARCHAR(200) , CITY VARCHAR(100) ,"
						+ "COUNTRY VARCHAR(100) , PinCode VARCHAR(20) , ID VARCHAR(10) PRIMARY KEY);";
				PreparedStatement first = connection.prepareStatement(code1);
				first.execute();
			}
			try {
				//check whether GYM_TRAINERS TABLE is present or not
				PreparedStatement test1 = connection.prepareStatement("select * from `studentdatabase`.`GYM_TRAINERS`;");  
			    ResultSet run1 = test1.executeQuery();
			}catch(Exception e) {
				String code2 = "CREATE TABLE `studentdatabase`.GYM_TRAINERS (ID VARCHAR(10) PRIMARY KEY , Name VARCHAR(30) ,"
						+ "Gender VARCHAR(15) , Salary INT , DOB VARCHAR(12) , Age INT , PhoneNumOne VARCHAR(13) ,"
						+ "Membership_Alloted VARCHAR(20) , Trainer_Type VARCHAR(30) , Date_Of_Joining VARCHAR(12) , RATING VARCHAR(5)"
						+ ", FOREIGN KEY (ID) REFERENCES `studentdatabase`.`GYM_TRAINERS_CONNECTIVITY` (ID)"
						+ ", FOREIGN KEY (Membership_Alloted) REFERENCES `studentdatabase`.`GYM_MEMBERSHIPS` (Name));";
				PreparedStatement second = connection.prepareStatement(code2);
				second.execute();
			}
			String className = "";
			try {
				trainersTable.getItems().clear();
				PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_TRAINERS`;");
			    ResultSet rs = command.executeQuery();
			    while(rs.next()){  
			    	String trainerID = rs.getString(1);
			    	String trainerName = rs.getString(2);
			    	
			    	trainersTable.getItems().add(new TrainersListClass(trainerID , trainerName));
			    }
			}catch(Exception e) {}
		}catch(Exception e) {}
	}
	
	//modifyTrainer
	Label viewTrainerModifyID = new Label("");
	TextField viewTrainerModifyName = new TextField("");
	ComboBox<String> viewTrainerModifyGender = new ComboBox<String>(sex);
	TextField viewTrainerModifyDOB = new TextField("");
	Label viewTrainerModifyAge = new Label("");
	TextArea viewTrainerModifyAddress = new TextArea("");
	TextField viewTrainerModifyCity = new TextField("");
	TextField viewTrainerModifyCountry = new TextField("");
	TextField viewTrainerModifyPin = new TextField("");
	TextField viewTrainerModifyPhNo1 = new TextField("");
	TextField viewTrainerModifyPhNo2 = new TextField("");
	TextField viewTrainerModifyEmail = new TextField("");
	ComboBox<String> viewTrainerModifyMembership = new ComboBox<String>(savedMemberships);
	TextField viewTrainerModifySalary = new TextField("");
	TextField viewTrainerModifyDOJ = new TextField("");
	ObservableList<String> availableTypes = FXCollections.observableArrayList("Jr. Trainer" , "Sr. Trainer");
	ComboBox<String> viewTrainerModifyType = new ComboBox<String>(availableTypes);
	
	//trainer view/details
	Label viewTrainerIDLabel = new Label("ID:");
	Label viewTrainerNameLabel = new Label("Name:");
	Label viewTrainerGenderLabel = new Label("Gender:");
	Label viewTrainerDOBLabel = new Label("DOB:");
	Label viewTrainerPhNoLabel = new Label("Contact Number(s):");
	Label viewTrainerEmailLabel = new Label("Email:");
	Label viewTrainerAddressLabel = new Label("Address:");
	Label viewTrainerCityLabel = new Label("City:");
	Label viewTrainerPinLabel = new Label("PIN Code:");
	Label viewTrainerCountryLabel = new Label("Country:");
	Label viewTrainerTypeLabel = new Label("Designation:");
	Label viewTrainerMembershipLabel = new Label("Membership alloted:");
	Label viewTrainerAgeLabel = new Label("Age:");
	Label viewTrainerDOJLabel = new Label("Date of Joining:");
	Label viewTrainerSalaryLabel = new Label("Salary:   Rs.                               / month");
	Label viewTrainerRatingLabel = new Label("Rating:");
	
	Label viewTrainerID = new Label("");
	Label viewTrainerName = new Label("");
	Label viewTrainerGender = new Label("");
	Label viewTrainerDOB = new Label("");
	Label viewTrainerAge = new Label("");
	Label viewTrainerSalary = new Label("");
	TextArea viewTrainerAddress = new TextArea("");
	Label viewTrainerCity = new Label("");
	Label viewTrainerCountry = new Label("");
	Label viewTrainerPin = new Label("");
	Label viewTrainerPhNo1 = new Label("");
	Label viewTrainerPhNo2 = new Label("");
	Label viewTrainerEmail = new Label("");
	Label viewTrainerMembership = new Label("");
	Label viewTrainerDOJ = new Label("");
	Label viewTrainerType = new Label("");
	
	public void setTrainerDetailsLabelStyle() {
		viewTrainerIDLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerNameLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerGenderLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerDOBLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerPhNoLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerEmailLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerAddressLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerCityLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerPinLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerCountryLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerMembershipLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerAgeLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerDOJLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerSalaryLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerRatingLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		viewTrainerTypeLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		
		viewTrainerID.setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");
		viewTrainerName.setStyle("-fx-font-size: 15px;");
		viewTrainerGender.setStyle("-fx-font-size: 15px;");
		viewTrainerDOB.setStyle("-fx-font-size: 15px;");
		viewTrainerAge.setStyle("-fx-font-size: 15px;");
		viewTrainerCity.setStyle("-fx-font-size: 15px;");
		viewTrainerCountry.setStyle("-fx-font-size: 15px;");
		viewTrainerPin.setStyle("-fx-font-size: 15px;");
		viewTrainerPhNo1.setStyle("-fx-font-size: 15px;");
		viewTrainerPhNo2.setStyle("-fx-font-size: 15px;");
		viewTrainerEmail.setStyle("-fx-font-size: 15px;");
		viewTrainerMembership.setStyle("-fx-font-size: 15px;");
		viewTrainerSalary.setStyle("-fx-font-size: 15px;");
		viewTrainerDOJ.setStyle("-fx-font-size: 15px;");
		viewTrainerType.setStyle("-fx-font-size: 15px;");
		
		viewTrainerAddress.setWrapText(true);
		viewTrainerAddress.setEditable(false);
		
		viewTrainerID.setText("");
		viewTrainerName.setText("");
		viewTrainerGender.setText("");
		viewTrainerDOB.setText("");
		viewTrainerAge.setText("");
		viewTrainerType.setText("");
		viewTrainerAddress.setText("");
		viewTrainerCity.setText("");
		viewTrainerCountry.setText("");
		viewTrainerPin.setText("");
		viewTrainerPhNo1.setText("");
		viewTrainerPhNo2.setText("");
		viewTrainerEmail.setText("");
		viewTrainerMembership.setText("");
		viewTrainerSalary.setText("");
		viewTrainerDOJ.setText("");
		
		viewTrainerModifyID.setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");
		viewTrainerModifyAge.setStyle("-fx-font-size: 15px;");
		
		viewTrainerModifySalary.setText("");
		
		dateFormatLabel0.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		dateFormatLabel1.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		
		viewTrainerModifyAddress.setWrapText(true);
		
		//loading savedMemberships
		savedMemberships.clear();
		try {
			PreparedStatement stmt = connection.prepareStatement("select * from `studentdatabase`.`gym_memberships`;");  
		    ResultSet rs2 = stmt.executeQuery();  
		    while(rs2.next()){  
		    	savedMemberships.add(rs2.getString(1).toString());
		    }
		}catch(Exception e) {}
		
	}
	
	//rated5button
	Button rated5button = new Button();
	FileInputStream inputRated5Image;
	Image rated5Image;
	//rated4button
	Button rated4button = new Button();
	FileInputStream inputRated4Image;
	Image rated4Image;
	//rated3button
	Button rated3button = new Button();
	FileInputStream inputRated3Image;
	Image rated3Image;
	//rated2button
	Button rated2button = new Button();
	FileInputStream inputRated2Image;
	Image rated2Image;
	//rated1button
	Button rated1button = new Button();
	FileInputStream inputRated1Image;
	Image rated1Image;
	
	public void loadTrainerRatedStarsFromViewTrainersScreen() {
		
		try {
			inputRated5Image = new FileInputStream("resources/logos/stars/onlyFive.jpg");
			rated5Image = new Image(inputRated5Image);
			rated5button.setGraphic(new ImageView(rated5Image));
			rated5button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRated4Image = new FileInputStream("resources/logos/stars/onlyFour.jpg");
			rated4Image = new Image(inputRated4Image);
			rated4button.setGraphic(new ImageView(rated4Image));
			rated4button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRated3Image = new FileInputStream("resources/logos/stars/onlyThree.jpg");
			rated3Image = new Image(inputRated3Image);
			rated3button.setGraphic(new ImageView(rated3Image));
			rated3button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRated2Image = new FileInputStream("resources/logos/stars/onlyTwo.jpg");
			rated2Image = new Image(inputRated2Image);
			rated2button.setGraphic(new ImageView(rated2Image));
			rated2button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		try {
			inputRated1Image = new FileInputStream("resources/logos/stars/onlyOne.jpg");
			rated1Image = new Image(inputRated1Image);
			rated1button.setGraphic(new ImageView(rated1Image));
			rated1button.setStyle("-fx-background-color: '#ccffe6';");
		}catch(Exception e) {}
		
		rated1button.setVisible(false);
		rated2button.setVisible(false);
		rated3button.setVisible(false);
		rated4button.setVisible(false);
		rated5button.setVisible(false);
	}
	
	public void updateTrainerInformationScreen(Stage s , String trainerID) {
		//load data from DB
		EntryToTrainerTable loadData = new EntryToTrainerTable();
		try {
			String[] data = loadData.loadTrainerDataFromDB(connection , trainerID);
			viewTrainerModifyID.setText(data[0]);
			viewTrainerModifyName.setText(data[1]);
			viewTrainerModifyGender.getSelectionModel().select(data[2]);
			viewTrainerModifySalary.setText(data[3]);
			viewTrainerModifyDOB.setText(data[4]);
			viewTrainerModifyAge.setText(data[5]);
			viewTrainerModifyPhNo1.setText(data[6]);
			viewTrainerModifyMembership.getSelectionModel().select(data[7]);
			viewTrainerModifyType.getSelectionModel().select(data[8]);
			viewTrainerModifyDOJ.setText(data[9]);
			//rating : data[10]
			viewTrainerModifyPhNo2.setText(data[11]);
			viewTrainerModifyEmail.setText(data[12]);
			viewTrainerModifyAddress.setText(data[13]);
			viewTrainerModifyCity.setText(data[14]);
			viewTrainerModifyCountry.setText(data[15]);
			viewTrainerModifyPin.setText(data[16]);
			
		}catch(Exception e) {}
		
		Button save = new Button("Save Changes");
		Button cancel = new Button("Cancel");
		
		save.setPrefSize(140.0, 32.0);
		cancel.setPrefSize(140.0, 32.0);
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
    			"-fx-font-weight: bold;" +
    			"-fx-font-family: 'Arial';" +
    			"-fx-font-size: 13px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		
		cancel.setStyle(delN);
		cancel.setOnMouseMoved(e-> cancel.setStyle(delH));		//hover
		cancel.setOnMouseReleased(e-> cancel.setStyle(delN));		//normal
		cancel.setOnMousePressed(e-> cancel.setStyle(delP));		//pressed
		cancel.setOnMouseExited(e-> cancel.setStyle(delN));		//normal
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '" + currentDarkColor + "';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 13.5px;";
		
		save.setStyle(goN);
		save.setOnMouseMoved(e-> save.setStyle(goH));			//hover
		save.setOnMouseReleased(e-> save.setStyle(goN));		//normal
		save.setOnMousePressed(e-> save.setStyle(goP));		//pressed
		save.setOnMouseExited(e-> save.setStyle(goN));		//normal
		
		Label membersLabel = new Label(" Modify Trainer Detail(s)");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		
		EntryToTrainerTable updateData = new EntryToTrainerTable();
		
		//controls
		back.setOnAction(e -> viewTrainerDetailsScreen(s , trainerID));
		cancel.setOnAction(e -> trainersScreen(s));
		viewTrainerModifyDOB.setOnKeyReleased(e -> {
			if(checkDBMSDateFormat(viewTrainerModifyDOB.getText()) == true) {
				calculateCurrentAge(viewTrainerModifyDOB.getText() , "updateTrainer");
			}
		});
		
		CheckDuplicateEmails ce = new CheckDuplicateEmails();
		
		save.setOnAction(e -> {
			this.isCorrect = true;
			
			if(checkDBMSDateFormat(viewTrainerModifyDOB.getText()) == false || checkDBMSDateFormat(viewTrainerModifyDOJ.getText()) == false || checkAmountDigits(viewTrainerModifySalary.getText().trim()) == false || viewTrainerModifyName.getText().trim().equals("") == true || viewTrainerModifyPhNo1.getText().trim().equals("") == true) {
				Alert warn = new Alert(AlertType.WARNING);
				warn.setTitle("Invalid Data");
				warn.setHeaderText("Cannot Update Info");
				warn.setContentText("Some of the data entered is invalid. Please check Name, Phone number 1,"
						+ " DOB, Date of Joining and Trainer Salary. Try again after entering valid data.\n\n\n");
				warn.show();
			}else {				//save to DB
				this.isCorrect = true;
				
				if(viewTrainerModifyPin.getText().trim().equals("") == false) {
					if(checkPin(viewTrainerModifyPin.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(viewTrainerModifyEmail.getText().trim().equals("") == false) {
					if(viewTrainerModifyEmail.getText().trim().contains("@") == false) {
						this.isCorrect = false;
					}
				}
				if(viewTrainerModifyPhNo1.getText().trim().equals("") == false) {
					if(checkPhoneNumber(viewTrainerModifyPhNo1.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(viewTrainerModifyPhNo2.getText().trim().equals("") == false) {
					if(checkPhoneNumber(viewTrainerModifyPhNo2.getText().trim()) == false) {
						this.isCorrect = false;
					}
				}
				if(viewTrainerModifyAge.getText().trim().equals("") == false) {
					if(Integer.parseInt(viewTrainerModifyAge.getText().trim()) <= 0) {
						this.isCorrect = false;
					}
				}
				
				if(this.isCorrect == true) {
					if(viewTrainerModifyEmail.getText().trim().equals("") == true) {
						
						Boolean isUpdated = updateData.updateTrainerData(connection , viewTrainerModifyID.getText().trim() , viewTrainerModifyName.getText().trim() , viewTrainerModifyGender.getSelectionModel().getSelectedItem() , viewTrainerModifyDOB.getText().trim() , viewTrainerModifyAge.getText().trim() , viewTrainerModifyType.getSelectionModel().getSelectedItem() , viewTrainerModifyAddress.getText().trim() , viewTrainerModifyCity.getText().trim() , viewTrainerModifyCountry.getText().trim() , viewTrainerModifyPin.getText().trim() , viewTrainerModifyPhNo1.getText().trim() ,viewTrainerModifyPhNo2.getText().trim() , viewTrainerModifyEmail.getText().trim() , viewTrainerModifyMembership.getSelectionModel().getSelectedItem() , viewTrainerModifySalary.getText().trim() , viewTrainerModifyDOJ.getText().trim());
						if(isUpdated == true) {
							Alert updated = new Alert(AlertType.INFORMATION);
							updated.setTitle("Successful");
							updated.setHeaderText("Info Updated");
							updated.setContentText("Trainer data successfully updated. "
									+ "Press OK to continue.\n\n\n");
							Optional<ButtonType> oop = updated.showAndWait();
							if(oop.get() == ButtonType.OK) {
								trainersScreen(s);
							}
						}else {
							Alert warn = new Alert(AlertType.WARNING);
							warn.setTitle("Invalid Data");
							warn.setHeaderText("Cannot Update Info");
							warn.setContentText("Some of the data entered is invalid. Please check Name, Phone number 1,"
									+ " DOB, Date of Joining and Trainer Salary. Try again after entering valid data.\n\n\n");
							warn.show();
						}
					}else {
						if(ce.checkIfEmailPresentInMembersTable(connection, viewTrainerModifyEmail.getText().trim()) == true && ce.checkIfDuplicateEmail(connection, "trainer", viewTrainerModifyID.getText().trim() , viewTrainerModifyEmail.getText().trim()) == true) {

							Boolean isUpdated = updateData.updateTrainerData(connection , viewTrainerModifyID.getText().trim() , viewTrainerModifyName.getText().trim() , viewTrainerModifyGender.getSelectionModel().getSelectedItem() , viewTrainerModifyDOB.getText().trim() , viewTrainerModifyAge.getText().trim() , viewTrainerModifyType.getSelectionModel().getSelectedItem() , viewTrainerModifyAddress.getText().trim() , viewTrainerModifyCity.getText().trim() , viewTrainerModifyCountry.getText().trim() , viewTrainerModifyPin.getText().trim() , viewTrainerModifyPhNo1.getText().trim() ,viewTrainerModifyPhNo2.getText().trim() , viewTrainerModifyEmail.getText().trim() , viewTrainerModifyMembership.getSelectionModel().getSelectedItem() , viewTrainerModifySalary.getText().trim() , viewTrainerModifyDOJ.getText().trim());
							if(isUpdated == true) {
								Alert updated = new Alert(AlertType.INFORMATION);
								updated.setTitle("Successful");
								updated.setHeaderText("Info Updated");
								updated.setContentText("Trainer data successfully updated. "
										+ "Press OK to continue.\n\n\n");
								Optional<ButtonType> oop = updated.showAndWait();
								if(oop.get() == ButtonType.OK) {
									trainersScreen(s);
								}
							}else {
								Alert warn = new Alert(AlertType.WARNING);
								warn.setTitle("Invalid Data");
								warn.setHeaderText("Cannot Update Info");
								warn.setContentText("Some of the data entered is invalid. Please check Name, Phone number 1,"
										+ " DOB, Date of Joining and Trainer Salary. Try again after entering valid data.\n\n\n");
								warn.show();
							}
							
						}else {
							Alert duplicateEmail = new Alert(AlertType.WARNING);
							duplicateEmail.setTitle("Warning");
							duplicateEmail.setHeaderText("Change Email");
							duplicateEmail.setContentText("Entered email is already in use. Change "
									+ "your email and try again.\n\n\n");
							duplicateEmail.show();
						}
					}
				}else {
					Alert al = new Alert(AlertType.WARNING);
					al.setTitle("Warning");
					al.setHeaderText("Invalid Data Entered");
					al.setContentText("Some of the entered data might be invalid. "
							+ "Make sure that phone number is of ten digits and email entered "
							+ "is valid.\nMake sure that age is positive number and "
							+ "PIN Code consists of only six digits.\nCheck and try again.\n\n\n");
					al.show();
				}
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0 - 20.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//save
		AnchorPane.setRightAnchor(save, 27.0);
		AnchorPane.setBottomAnchor(save, 22.0);
		//cancel
		AnchorPane.setRightAnchor(cancel, 27.0  + 100.0 + 17.0 + 40.0);
		AnchorPane.setBottomAnchor(cancel, 22.0);
		//viewTrainerIDLabel
		AnchorPane.setTopAnchor(viewTrainerIDLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerIDLabel, 60.0);
		//viewTrainerNameLabel
		AnchorPane.setTopAnchor(viewTrainerNameLabel, 160.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerNameLabel, 60.0);
		//viewTrainerGenderLabel
		AnchorPane.setTopAnchor(viewTrainerGenderLabel, 190.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerGenderLabel, 60.0);
		//viewTrainerDOBLabel
		AnchorPane.setTopAnchor(viewTrainerDOBLabel, 220.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerDOBLabel, 60.0);
		//viewTrainerAgeLabel
		AnchorPane.setTopAnchor(viewTrainerAgeLabel, 250.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerAgeLabel, 60.0);
		//viewTrainerTypeLabel
		AnchorPane.setTopAnchor(viewTrainerTypeLabel, 280.0 + 25.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerTypeLabel, 60.0);
		//viewTrainerAddressLabel
		AnchorPane.setTopAnchor(viewTrainerAddressLabel, 340.0 + 35.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerAddressLabel, 60.0);
		//viewTrainerCityLabel
		AnchorPane.setTopAnchor(viewTrainerCityLabel, 370.0 + 40.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerCityLabel, 60.0);
		//viewTrainerCountryLabel
		AnchorPane.setTopAnchor(viewTrainerCountryLabel, 400.0 + 45.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerCountryLabel, 60.0);
		//viewTrainerPinLabel
		AnchorPane.setTopAnchor(viewTrainerPinLabel, 430.0 + 50.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerPinLabel, 60.0);
		//viewTrainerPhNoLabel
		AnchorPane.setTopAnchor(viewTrainerPhNoLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerPhNoLabel, 460.0);
		//viewTrainerEmailLabel
		AnchorPane.setTopAnchor(viewTrainerEmailLabel, 190.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerEmailLabel, 460.0);
		//viewTrainerMembershipLabel
		AnchorPane.setTopAnchor(viewTrainerMembershipLabel, 220.0 + 21.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerMembershipLabel, 460.0);
		//viewTrainerSalaryLabel
		AnchorPane.setTopAnchor(viewTrainerSalaryLabel, 250.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerSalaryLabel, 460.0);
		//viewTrainerRatingLabel
		AnchorPane.setTopAnchor(viewTrainerRatingLabel, 280.0 + 35.0 - 20.0 + 20.0);
		AnchorPane.setLeftAnchor(viewTrainerRatingLabel, 460.0);
		//viewTrainerDOJLabel
		AnchorPane.setTopAnchor(viewTrainerDOJLabel, 370.0 + 56.0 + 14.0 - 20.0 - 60.0);
		AnchorPane.setLeftAnchor(viewTrainerDOJLabel, 460.0);
		//viewTrainerModifyID
		AnchorPane.setTopAnchor(viewTrainerModifyID, 129.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyID, 90.0);
		//viewTrainerModifyName
		AnchorPane.setTopAnchor(viewTrainerModifyName, 159.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyName, 120.0);
		AnchorPane.setRightAnchor(viewTrainerModifyName, 480.0);
		//viewTrainerModifyGender
		AnchorPane.setTopAnchor(viewTrainerModifyGender, 189.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyGender, 125.0);
		AnchorPane.setRightAnchor(viewTrainerModifyGender, 640.0);
		//viewTrainerModifyDOB
		AnchorPane.setTopAnchor(viewTrainerModifyDOB, 219.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyDOB, 110.0);
		//dateFormatLabel0
		AnchorPane.setTopAnchor(dateFormatLabel0, 219.0 + 17.0 - 20.0);
		AnchorPane.setLeftAnchor(dateFormatLabel0, 265.0);
		//viewTrainerModifyAge
		AnchorPane.setTopAnchor(viewTrainerModifyAge, 249.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyAge, 110.0);
		//viewTrainerModifyAddress
		AnchorPane.setTopAnchor(viewTrainerModifyAddress, 340.0 + 35.0 - 25.0 - 30.0 - 2.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyAddress, 130.0);
		AnchorPane.setRightAnchor(viewTrainerModifyAddress, 480.0);
		AnchorPane.setBottomAnchor(viewTrainerModifyAddress, 120.0 + 55.0);
		//viewTrainerModifyCity
		AnchorPane.setTopAnchor(viewTrainerModifyCity, 370.0 + 40.0 + 14.0 - 20.0 - 30.0 - 2.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyCity, 110.0);
		AnchorPane.setRightAnchor(viewTrainerModifyCity, 480.0);
		//viewTrainerModifyCountry
		AnchorPane.setTopAnchor(viewTrainerModifyCountry, 400.0 + 45.0 + 14.0 - 20.0 - 30.0 - 2.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyCountry, 130.0);
		AnchorPane.setRightAnchor(viewTrainerModifyCountry, 480.0);
		//viewTrainerModifyPin
		AnchorPane.setTopAnchor(viewTrainerModifyPin, 430.0 + 50.0 + 14.0 - 20.0 - 30.0 - 2.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyPin, 140.0);
		//viewTrainerModifyPhNo1
		AnchorPane.setTopAnchor(viewTrainerModifyPhNo1, 129.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyPhNo1, 600.0);
		//viewTrainerModifyPhNo2
		AnchorPane.setTopAnchor(viewTrainerModifyPhNo2, 159.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyPhNo2, 600.0);
		//viewTrainerModifyEmail
		AnchorPane.setTopAnchor(viewTrainerModifyEmail, 189.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyEmail, 520.0);
		AnchorPane.setRightAnchor(viewTrainerModifyEmail, 55.0);
		//viewTrainerModifyMembership
		AnchorPane.setTopAnchor(viewTrainerModifyMembership, 219.0 + 21.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyMembership, 610.0);
		AnchorPane.setRightAnchor(viewTrainerModifyMembership, 100.0);
		//viewTrainerModifySalary
		AnchorPane.setTopAnchor(viewTrainerModifySalary, 247.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifySalary, 550.0);
		AnchorPane.setRightAnchor(viewTrainerModifySalary, 260.0);
		//viewTrainerModifyDOJ
		AnchorPane.setTopAnchor(viewTrainerModifyDOJ, 370.0 + 56.0 + 14.0 - 20.0 - 60.0 - 3.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyDOJ, 580.0);
		//dateFormatLabel1
		AnchorPane.setTopAnchor(dateFormatLabel1, 370.0 + 56.0 + 14.0 - 20.0 - 60.0);
		AnchorPane.setLeftAnchor(dateFormatLabel1, 736.0);
		//viewTrainerModifyType
		AnchorPane.setTopAnchor(viewTrainerModifyType, 279.0 + 24.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerModifyType, 160.0);
		AnchorPane.setRightAnchor(viewTrainerModifyType, 610.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , cancel , viewTrainerIDLabel ,
				save , membersLabel , underline , viewTrainerNameLabel , viewTrainerGenderLabel ,
				viewTrainerDOBLabel , viewTrainerAgeLabel , viewTrainerTypeLabel , 
				viewTrainerAddressLabel , viewTrainerCityLabel , viewTrainerCountryLabel , 
				viewTrainerPinLabel , viewTrainerPhNoLabel , viewTrainerEmailLabel ,
				viewTrainerMembershipLabel , viewTrainerSalaryLabel , 
				viewTrainerDOJLabel , viewTrainerModifyID , viewTrainerModifyName , viewTrainerModifyGender ,
				viewTrainerModifyDOB , viewTrainerModifyAge , viewTrainerModifyType , viewTrainerModifyAddress , 
				viewTrainerModifyCity , viewTrainerModifyCountry , viewTrainerModifyPin , viewTrainerModifyPhNo1 ,
				viewTrainerModifyPhNo2 , viewTrainerModifyEmail , viewTrainerModifyMembership , viewTrainerModifySalary ,
				viewTrainerModifyDOJ , dateFormatLabel0 , dateFormatLabel1);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void prepareMembersTableX(String trainerMembership) {
		loadAllPotentialSearchElements();
		
		try {
			try {
				//check whether GYM_MEMBERS_CONNECTIVITY TABLE is present or not
				PreparedStatement test0 = connection.prepareStatement("select * from `studentdatabase`.`GYM_MEMBERS_CONNECTIVITY`;");  
			    ResultSet run = test0.executeQuery();
			}catch(Exception e) {
				//create GYM_MEMBERS_CONNECTIVITY TABLE
				String code1 = "CREATE TABLE `studentdatabase`.GYM_MEMBERS_CONNECTIVITY (PhoneNumberOne VARCHAR(13),"
						+ "PhoneNumberTwo VARCHAR(13) , Email VARCHAR(50) , Address VARCHAR(200) , CITY VARCHAR(100) ,"
						+ "COUNTRY VARCHAR(100) , PinCode VARCHAR(20) , ID VARCHAR(10) PRIMARY KEY);";
				PreparedStatement first = connection.prepareStatement(code1);
				first.execute();
			}
			try {
				//check whether GYM_MEMBERS TABLE is present or not
				PreparedStatement test1 = connection.prepareStatement("select * from `studentdatabase`.`GYM_MEMBERS`;");  
			    ResultSet run1 = test1.executeQuery();
			}catch(Exception e) {
				String code2 = "CREATE TABLE `studentdatabase`.GYM_MEMBERS (ID VARCHAR(10) PRIMARY KEY , Name VARCHAR(30) ,"
						+ "Gender VARCHAR(15) , Weight INT , DOB VARCHAR(12) , Age INT , PhoneNumOne VARCHAR(13) ,"
						+ "Membership_Type VARCHAR(20) , Amount INT , Note VARCHAR(300) , Date_Of_Joining VARCHAR(12) , Occupation VARCHAR(50)"
						+ ", FOREIGN KEY (ID) REFERENCES `studentdatabase`.`GYM_MEMBERS_CONNECTIVITY` (ID)"
						+ ", FOREIGN KEY (Membership_Type) REFERENCES `studentdatabase`.`GYM_MEMBERSHIPS` (Name));";
				PreparedStatement second = connection.prepareStatement(code2);
				second.execute();
			}
			//load 
			String className = "";
			try {
				membersTableX.getItems().clear();
				PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_MEMBERS` WHERE `Membership_Type` = '" + trainerMembership + "';");
			    ResultSet rs = command.executeQuery();
			    while(rs.next()){  
			    	String memberID = rs.getString(1);
			    	String memberName = rs.getString(2);
			    	
			    	membersTableX.getItems().add(new MembersListClass(memberID , memberName , ""));
			    }
			}catch(Exception d) {}
		}catch(Exception e) {}
	}
	
	Label idX = new Label("");
	Label nameX = new Label("");
	Label genderX = new Label("");
	Label ageX = new Label("");
	TextArea noteX = new TextArea("Empty");
	
	public void loadViewData(String mID) {
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_members` WHERE `ID` = '" + mID + "';");
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			idX.setText(rs.getString(1));
			nameX.setText(rs.getString(2));
			genderX.setText(rs.getString(3));
			ageX.setText(rs.getString(6));
			noteX.setText(rs.getString(10));
			if(noteX.getText().equals("") == true) {
				noteX.setText("Empty.");
			}
			if(noteX.getText().equals("Empty.") == true) {
				noteX.setStyle("-fx-font-size: 14px;" + "-fx-border-color: '595959';" + "-fx-text-fill: '595959';");
			}else {
				noteX.setStyle("-fx-font-size: 16px;" + "-fx-border-color: '595959';" + "-fx-text-fill: black;");
			}
			
		}catch(Exception e) {}
	}
	
	public void viewParticularMembershipMembers(Stage s , String trainerMembership , String trainerID) {
		//called by: view your members button, in viewTrainerDetailsScreen(Stage s , String trainerID)
		
		prepareMembersTableX(trainerMembership);
		
		idX.setText("");
		nameX.setText("");
		genderX.setText("");
		ageX.setText("");
		noteX.setText("Empty.");
		
		Label membersLabel = new Label(" Members in " + trainerMembership);
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		Label idLabel = new Label("ID:");
		Label nameLabel = new Label("Name:");
		Label genderLabel = new Label("Gender:");
		Label ageLabel = new Label("Age:");
		Label noteLabel = new Label("  Note");
		Label underline2 = new Label("\t\t\t\t\t\t\t\t\t\t\t\t\t      ");
		
		idLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		nameLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		idLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		genderLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		ageLabel.setStyle("-fx-font-size: 15px;" + "-fx-text-fill: '595959';");
		noteLabel.setStyle("-fx-font-size: 18px;" + "-fx-text-fill: '595959';");
		underline2.setStyle("-fx-font-size: 13px;" + "-fx-text-fill: '595959';");
		underline2.setUnderline(true);
		
		idX.setStyle("-fx-font-size: 16.5px;" + "-fx-font-weight: bold;");
		nameX.setStyle("-fx-font-size: 16.5px;");
		genderX.setStyle("-fx-font-size: 16.5px;");
		ageX.setStyle("-fx-font-size: 16.5px;");
		noteX.setWrapText(true);
		noteX.setStyle("-fx-font-size: 14px;" + "-fx-border-color: '595959';" + "-fx-text-fill: '595959';");
		noteX.setEditable(false);
		
		Button ok = new Button("OK");
		
		ok.setPrefSize(100.0, 32.0);
		
		ok.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		ok.setOnMouseExited(e -> ok.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		ok.setOnMouseReleased(e -> ok.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		ok.setOnMouseEntered(e -> ok.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		ok.setOnMousePressed(e -> ok.setStyle("-fx-background-color: '" + currentBackground +"';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		//controls
		back.setOnAction(e -> viewTrainerDetailsScreen(s , trainerID));
		ok.setOnAction(e -> trainersScreen(s));
		membersTableX.setOnMousePressed(e -> {
			if(membersTableX.getSelectionModel().getSelectedItem() != null) {
				MembersListClass mlc = (MembersListClass) membersTableX.getSelectionModel().getSelectedItem();
				String mID = mlc.getMemberID();
				loadViewData(mID);
			}
		});
		membersTableX.setOnKeyReleased(e -> {
			if(membersTableX.getSelectionModel().getSelectedItem() != null) {
				MembersListClass mlc = (MembersListClass) membersTableX.getSelectionModel().getSelectedItem();
				String mID = mlc.getMemberID();
				loadViewData(mID);
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0 + 10);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0 + 10.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//ok
		AnchorPane.setRightAnchor(ok, 27.0);
		AnchorPane.setBottomAnchor(ok, 22.0);
		//membersTableX
		AnchorPane.setTopAnchor(membersTableX, 100.0 + 50.0);
		AnchorPane.setLeftAnchor(membersTableX, 65.0);
		AnchorPane.setBottomAnchor(membersTableX, 70.0);
		//idLabel
		AnchorPane.setTopAnchor(idLabel, 100.0 + 45.0);
		AnchorPane.setLeftAnchor(idLabel, 460.0);
		//idX
		AnchorPane.setTopAnchor(idX, 100.0 + 44.0);
		AnchorPane.setLeftAnchor(idX, 500.0);
		//nameLabel
		AnchorPane.setTopAnchor(nameLabel, 100.0 + 45.0 + 40.0);
		AnchorPane.setLeftAnchor(nameLabel, 460.0);
		//nameX
		AnchorPane.setTopAnchor(nameX, 100.0 + 44.0 + 40.0);
		AnchorPane.setLeftAnchor(nameX, 520.0);
		//genderLabel
		AnchorPane.setTopAnchor(genderLabel, 100.0 + 45.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(genderLabel, 460.0);
		//genderX
		AnchorPane.setTopAnchor(genderX, 100.0 + 44.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(genderX, 530.0);
		//ageLabel
		AnchorPane.setTopAnchor(ageLabel, 100.0 + 45.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(ageLabel, 460.0 + 220.0);
		//ageX
		AnchorPane.setTopAnchor(ageX, 100.0 + 44.0 + 40.0 * 2);
		AnchorPane.setLeftAnchor(ageX, 460.0 + 210.0 + 60.0);
		//noteLabel
		AnchorPane.setTopAnchor(noteLabel, 100.0 + 60.0 + 40.0 * 3 - 10.0);
		AnchorPane.setLeftAnchor(noteLabel, 440.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 110.0 + 60.0 + 40.0 * 3 - 10.0);
		AnchorPane.setLeftAnchor(underline2, 440.0);
		AnchorPane.setRightAnchor(underline2, 65.0);
		//noteX
		AnchorPane.setTopAnchor(noteX, 122.0 + 80.0 + 40.0 * 3 - 10.0);
		AnchorPane.setLeftAnchor(noteX, 450.0);
		AnchorPane.setRightAnchor(noteX, 75.0);
		AnchorPane.setBottomAnchor(noteX, 80.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , ok , membersLabel , underline , idLabel , nameLabel ,
				genderLabel , ageLabel , noteLabel , membersTableX , idX , nameX , genderX , ageX , noteX ,
				underline2);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
		
		
	}
	
	public void displayTrainerRatingStars(String ratedStars) {
		if(ratedStars.equalsIgnoreCase("NULL") == false) {
			if(Double.parseDouble(ratedStars) >= 0.1 && Double.parseDouble(ratedStars) < 2.0) {
				rated1button.setVisible(true);
				rated2button.setVisible(false);
				rated3button.setVisible(false);
				rated4button.setVisible(false);
				rated5button.setVisible(false);
			}else if(Double.parseDouble(ratedStars) >= 2.0 && Double.parseDouble(ratedStars) < 3.0) {
				rated1button.setVisible(false);
				rated2button.setVisible(true);
				rated3button.setVisible(false);
				rated4button.setVisible(false);
				rated5button.setVisible(false);
			}else if(Double.parseDouble(ratedStars) >= 3.0 && Double.parseDouble(ratedStars) < 4.0) {
				rated1button.setVisible(false);
				rated2button.setVisible(false);
				rated3button.setVisible(true);
				rated4button.setVisible(false);
				rated5button.setVisible(false);
			}else if(Double.parseDouble(ratedStars) >= 4.0 && Double.parseDouble(ratedStars) < 5.0) {
				rated1button.setVisible(false);
				rated2button.setVisible(false);
				rated3button.setVisible(false);
				rated4button.setVisible(true);
				rated5button.setVisible(false);
			}else if(Double.parseDouble(ratedStars) >= 5.0) {
				rated1button.setVisible(false);
				rated2button.setVisible(false);
				rated3button.setVisible(false);
				rated4button.setVisible(false);
				rated5button.setVisible(true);
			}
		}
	}
	
	public void viewTrainerDetailsScreen(Stage s , String trainerID) {
		loadTrainerRatedStarsFromViewTrainersScreen();
		
		rated1button.setVisible(false);
		rated2button.setVisible(false);
		rated3button.setVisible(false);
		rated4button.setVisible(false);
		rated5button.setVisible(false);
		
		setTrainerDetailsLabelStyle();
		
		EntryToTrainerTable loadData = new EntryToTrainerTable();
		try {
			String[] data = loadData.loadTrainerDataFromDB(connection , trainerID);
			viewTrainerID.setText(data[0]);
			viewTrainerName.setText(data[1]);
			viewTrainerGender.setText(data[2]);
			viewTrainerSalary.setText(data[3]);
			viewTrainerDOB.setText(data[4]);
			viewTrainerAge.setText(data[5]);
			viewTrainerPhNo1.setText(data[6]);
			viewTrainerMembership.setText(data[7]);
			viewTrainerType.setText(data[8]);
			viewTrainerDOJ.setText(data[9]);
			String ratedStars = data[10];
			viewTrainerPhNo2.setText(data[11]);
			viewTrainerEmail.setText(data[12]);
			viewTrainerAddress.setText(data[13]);
			viewTrainerCity.setText(data[14]);
			viewTrainerCountry.setText(data[15]);
			viewTrainerPin.setText(data[16]);
			
			displayTrainerRatingStars(ratedStars);
			
		}catch(Exception e) {}
		
		Button viewYourMembers = new Button("View Your Members");
		Button updateInfo = new Button("Update Info");
		
		viewYourMembers.setPrefSize(160.0, 32.0);
		updateInfo.setPrefSize(140.0, 32.0);
		
		viewYourMembers.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;");
		updateInfo.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;");
		viewYourMembers.setOnMouseExited(e -> viewYourMembers.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		updateInfo.setOnMouseExited(e -> updateInfo.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		viewYourMembers.setOnMouseReleased(e -> viewYourMembers.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		updateInfo.setOnMouseReleased(e -> updateInfo.setStyle("-fx-background-color: '#0073e6';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: normal;"));
		viewYourMembers.setOnMouseEntered(e -> viewYourMembers.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		updateInfo.setOnMouseEntered(e -> updateInfo.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		viewYourMembers.setOnMousePressed(e -> viewYourMembers.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 13px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		updateInfo.setOnMousePressed(e -> updateInfo.setStyle("-fx-background-color: '#ccffe6';" + "-fx-text-fill: '#0073e6';" + "-fx-font-size: 13px;" + "-fx-border-color: '#0073e6';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		Label membersLabel = new Label(" Details");
		membersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 26px;");
		String underlineMembers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline = new Label(underlineMembers);
		underline.setUnderline(true);
		
		//controls
		back.setOnAction(e -> {
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			updateBackButton(currentTheme);
			trainersScreen(s);
		});
		updateInfo.setOnAction(e -> {
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			updateBackButton(currentTheme);
			updateTrainerInformationScreen(s , trainerID);
		});
		viewYourMembers.setOnAction(e -> {
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			updateBackButton(currentTheme);
			viewParticularMembershipMembers(s , viewTrainerMembership.getText() , trainerID);
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membersLabel
		AnchorPane.setTopAnchor(membersLabel, 70.0 - 20.0);
		AnchorPane.setLeftAnchor(membersLabel, 60.0);
		//underline
		AnchorPane.setTopAnchor(underline, 92.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 55.0);
		AnchorPane.setLeftAnchor(underline, 55.0);
		//updateInfo
		AnchorPane.setRightAnchor(updateInfo, 27.0);
		AnchorPane.setBottomAnchor(updateInfo, 22.0);
		//viewYourMembers
		AnchorPane.setRightAnchor(viewYourMembers, 27.0  + 100.0 + 17.0 + 40.0);
		AnchorPane.setBottomAnchor(viewYourMembers, 22.0);
		//viewTrainerIDLabel
		AnchorPane.setTopAnchor(viewTrainerIDLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerIDLabel, 60.0);
		//viewTrainerNameLabel
		AnchorPane.setTopAnchor(viewTrainerNameLabel, 160.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerNameLabel, 60.0);
		//viewTrainerGenderLabel
		AnchorPane.setTopAnchor(viewTrainerGenderLabel, 190.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerGenderLabel, 60.0);
		//viewTrainerDOBLabel
		AnchorPane.setTopAnchor(viewTrainerDOBLabel, 220.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerDOBLabel, 60.0);
		//viewTrainerAgeLabel
		AnchorPane.setTopAnchor(viewTrainerAgeLabel, 250.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerAgeLabel, 60.0);
		//viewTrainerTypeLabel
		AnchorPane.setTopAnchor(viewTrainerTypeLabel, 280.0 + 25.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerTypeLabel, 60.0);
		//viewTrainerAddressLabel
		AnchorPane.setTopAnchor(viewTrainerAddressLabel, 340.0 + 35.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerAddressLabel, 60.0);
		//viewTrainerCityLabel
		AnchorPane.setTopAnchor(viewTrainerCityLabel, 370.0 + 40.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerCityLabel, 60.0);
		//viewTrainerCountryLabel
		AnchorPane.setTopAnchor(viewTrainerCountryLabel, 400.0 + 45.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerCountryLabel, 60.0);
		//viewTrainerPinLabel
		AnchorPane.setTopAnchor(viewTrainerPinLabel, 430.0 + 50.0 + 14.0 - 20.0 - 30.0);
		AnchorPane.setLeftAnchor(viewTrainerPinLabel, 60.0);
		//viewTrainerPhNoLabel
		AnchorPane.setTopAnchor(viewTrainerPhNoLabel, 130.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerPhNoLabel, 460.0);
		//viewTrainerEmailLabel
		AnchorPane.setTopAnchor(viewTrainerEmailLabel, 190.0 + 14.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerEmailLabel, 460.0);
		//viewTrainerMembershipLabel
		AnchorPane.setTopAnchor(viewTrainerMembershipLabel, 220.0 + 21.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerMembershipLabel, 460.0);
		//viewTrainerSalaryLabel
		AnchorPane.setTopAnchor(viewTrainerSalaryLabel, 250.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerSalaryLabel, 460.0);
		//viewTrainerRatingLabel
		AnchorPane.setTopAnchor(viewTrainerRatingLabel, 280.0 + 35.0 - 20.0 + 20.0);
		AnchorPane.setLeftAnchor(viewTrainerRatingLabel, 460.0);
		//rated5button
		AnchorPane.setTopAnchor(rated5button, 280.0 + 35.0 - 23.0 + 15.0);
		AnchorPane.setLeftAnchor(rated5button, 515.0);
		//rated4button
		AnchorPane.setTopAnchor(rated4button, 280.0 + 35.0 - 23.0 + 15.0);
		AnchorPane.setLeftAnchor(rated4button, 515.0);
		//rated3button
		AnchorPane.setTopAnchor(rated3button, 280.0 + 35.0 - 23.0 + 15.0);
		AnchorPane.setLeftAnchor(rated3button, 515.0);
		//rated2button
		AnchorPane.setTopAnchor(rated2button, 280.0 + 35.0 - 23.0 + 15.0);
		AnchorPane.setLeftAnchor(rated2button, 515.0);
		//rated1button
		AnchorPane.setTopAnchor(rated1button, 280.0 + 35.0 - 23.0 + 15.0);
		AnchorPane.setLeftAnchor(rated1button, 515.0);
		//viewTrainerDOJLabel
		AnchorPane.setTopAnchor(viewTrainerDOJLabel, 410.0 + 56.0 + 14.0 - 20.0 - 60.0);
		AnchorPane.setLeftAnchor(viewTrainerDOJLabel, 460.0);
		
		//viewTrainerID
		AnchorPane.setTopAnchor(viewTrainerID, 129.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerID, 90.0);
		//viewTrainerName
		AnchorPane.setTopAnchor(viewTrainerName, 159.0 + 5.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerName, 120.0);
		//viewTrainerGender
		AnchorPane.setTopAnchor(viewTrainerGender, 189.0 + 10.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerGender, 125.0);
		//viewTrainerDOB
		AnchorPane.setTopAnchor(viewTrainerDOB, 219.0 + 15.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerDOB, 110.0);
		//viewTrainerAge
		AnchorPane.setTopAnchor(viewTrainerAge, 249.0 + 20.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerAge, 110.0);
		//viewTrainerAddress
		AnchorPane.setTopAnchor(viewTrainerAddress, 320.0);
		AnchorPane.setLeftAnchor(viewTrainerAddress, 130.0);
		AnchorPane.setRightAnchor(viewTrainerAddress, 480.0);
		AnchorPane.setBottomAnchor(viewTrainerAddress, 120.0 + 50.0);
		//viewTrainerCity
		AnchorPane.setTopAnchor(viewTrainerCity, 370.0 + 40.0 + 14.0 - 20.0 - 30.0 - 1.0);
		AnchorPane.setLeftAnchor(viewTrainerCity, 110.0);
		//viewTrainerCountry
		AnchorPane.setTopAnchor(viewTrainerCountry, 400.0 + 45.0 + 14.0 - 20.0 - 30.0 - 1.0);
		AnchorPane.setLeftAnchor(viewTrainerCountry, 130.0);
		//viewTrainerPin
		AnchorPane.setTopAnchor(viewTrainerPin, 430.0 + 50.0 + 14.0 - 20.0 - 30.0 - 1.0);
		AnchorPane.setLeftAnchor(viewTrainerPin, 140.0);
		//viewTrainerPhNo1
		AnchorPane.setTopAnchor(viewTrainerPhNo1, 129.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerPhNo1, 600.0);
		//viewTrainerPhNo2
		AnchorPane.setTopAnchor(viewTrainerPhNo2, 159.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerPhNo2, 600.0);
		//viewTrainerEmail
		AnchorPane.setTopAnchor(viewTrainerEmail, 189.0 + 14.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerEmail, 520.0);
		//viewTrainerMembership
		AnchorPane.setTopAnchor(viewTrainerMembership, 219.0 + 21.0 - 1.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerMembership, 610.0);
		//viewTrainerSalary
		AnchorPane.setTopAnchor(viewTrainerSalary, 249.0 + 28.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerSalary, 550.0);
		//viewTrainerDOJ
		AnchorPane.setTopAnchor(viewTrainerDOJ, 410.0 + 56.0 + 14.0 - 20.0 - 60.0 - 1.0);
		AnchorPane.setLeftAnchor(viewTrainerDOJ, 580.0);
		//viewTrainerType
		AnchorPane.setTopAnchor(viewTrainerType, 279.0 + 24.0 - 20.0);
		AnchorPane.setLeftAnchor(viewTrainerType, 160.0);
		
		menuBar.setStyle("-fx-background-color: '" + theme01 + "';");
		root.setStyle("-fx-background-color: '" + theme00 + "';");
		updateBackButton(0);
		root.getChildren().addAll(menuBar , back , viewYourMembers , viewTrainerIDLabel ,
				updateInfo , membersLabel , underline , viewTrainerNameLabel , viewTrainerGenderLabel ,
				viewTrainerDOBLabel , viewTrainerAgeLabel , viewTrainerTypeLabel , 
				viewTrainerAddressLabel , viewTrainerCityLabel , viewTrainerCountryLabel , 
				viewTrainerPinLabel , viewTrainerPhNoLabel , viewTrainerEmailLabel ,
				viewTrainerMembershipLabel , viewTrainerSalaryLabel , viewTrainerRatingLabel ,
				viewTrainerDOJLabel , viewTrainerID , viewTrainerName , viewTrainerGender ,
				viewTrainerDOB , viewTrainerAge , viewTrainerType , viewTrainerAddress , 
				viewTrainerCity , viewTrainerCountry , viewTrainerPin , viewTrainerPhNo1 ,
				viewTrainerPhNo2 , viewTrainerEmail , viewTrainerMembership , viewTrainerSalary ,
				viewTrainerDOJ , rated1button , rated2button , rated3button , rated4button ,
				rated5button);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label exName = new Label();
	Label exSex = new Label();
	Label exDesignation = new Label();
	Label exCity = new Label();
	Label exPIN= new Label();
	Label exContact1 = new Label();
	Label exContact2 = new Label();
	Label exEmail = new Label();
	Label exSalary = new Label();
	Label exDOJ = new Label();
	Label exDOL = new Label();
	
	public void prepareExTrainersTable() {
		exName.setText("");
		exSex.setText("");
		exDesignation.setText("");
		exCity.setText("");
		exPIN.setText("");
		exContact1.setText("");
		exContact2.setText("");
		exEmail.setText("");
		exSalary.setText("");
		exDOJ.setText("");
		exDOL.setText("");
		
		exTrainersTable.getItems().clear();
		try {
			PreparedStatement ret = connection.prepareStatement("SELECT * FROM `studentdatabase`.`ex_trainers`;");
			ResultSet result = ret.executeQuery();
			while(result.next()) {
				String name = result.getString(1);
				String address = result.getString(2);
				
				exTrainersTable.getItems().add(new ExTrainerListClass(name , address));
			}
		}catch(Exception e) {}
	}
	
	public void loadCurrentExTrainerData(String selectedName , String selectedAddress) {
		try {
			PreparedStatement curr = connection.prepareStatement("SELECT * FROM `studentdatabase`.`ex_trainers` WHERE `Name` = '" + selectedName + "' AND `Address` = '" + selectedAddress + "';");
			ResultSet rc = curr.executeQuery();
			rc.next();
			
			String name = rc.getString(1);
			String address = rc.getString(2);
			String gender = rc.getString(3);
			String designation = rc.getString(4);
			String city = rc.getString(5);
			String pinCode = rc.getString(6);
			String phNo1 = rc.getString(7);
			String phNo2 = rc.getString(8);
			String email = rc.getString(9);
			String salary = rc.getString(10);
			String doj = rc.getString(11);
			String dol = rc.getString(12);
			
			exName.setText(name);
			exSex.setText(gender);
			exDesignation.setText(designation);
			exCity.setText(city);
			exPIN.setText(pinCode);
			exContact1.setText(phNo1);
			exContact2.setText(phNo2);
			exEmail.setText(email);
			exSalary.setText(salary);
			exDOJ.setText(doj);
			exDOL.setText(dol);
			
		}catch(Exception e) {}
	}
	
	public void exTrainersScreen(Stage s) {
		prepareExTrainersTable();
		
		Label nameLabel = new Label("Name:");
		Label sexLabel = new Label("Sex:");
		Label designationLabel = new Label("Designation:");
		Label cityLabel = new Label("City:");
		Label pinLabel = new Label("PIN Code:");
		Label contactLabel = new Label("Contact Number(s):");
		Label emailLabel = new Label("Email:");
		Label salaryLabel = new Label("Salary:");
		Label dojLabel = new Label("Date of Joining:");
		Label dolLabel = new Label("Left on");
		
		nameLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		sexLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		designationLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		cityLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		pinLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		contactLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		emailLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		salaryLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		dojLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		dolLabel.setStyle("-fx-font-size: 14px;" + "-fx-text-fill: '595959';");
		
		exName.setStyle("-fx-font-size: 15.5px;" + "-fx-font-weight: bold;");
		exSex.setStyle("-fx-font-size: 15.5px;");
		exDesignation.setStyle("-fx-font-size: 15.5px;");
		exCity.setStyle("-fx-font-size: 15.5px;");
		exPIN.setStyle("-fx-font-size: 15.5px;");
		exContact1.setStyle("-fx-font-size: 15.5px;");
		exContact2.setStyle("-fx-font-size: 15.5px;");
		exEmail.setStyle("-fx-font-size: 15.5px;");
		exSalary.setStyle("-fx-font-size: 15.5px;");
		exDOJ.setStyle("-fx-font-size: 15.5px;");
		exDOL.setStyle("-fx-font-size: 15.5px;");
		
		Label trainersLabel = new Label(" Gym Ex - Trainers List");
		trainersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineTrainers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineTrainers);
		underline.setUnderline(true);
		
		Button delete = new Button("Remove");
		Button deleteAll = new Button("Remove All");
		
		delete.setPrefSize(120.0, 32.0);
		deleteAll.setPrefSize(140.0, 32.0);
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
    			"-fx-font-family: 'Arial';" +
    			"-fx-font-size: 15px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		delete.setStyle(delN);
		delete.setOnMouseMoved(e-> delete.setStyle(delH));
		delete.setOnMouseReleased(e-> delete.setStyle(delN));
		delete.setOnMousePressed(e-> delete.setStyle(delP));
		delete.setOnMouseExited(e-> delete.setStyle(delN));
		
		deleteAll.setStyle(delN);
		deleteAll.setOnMouseMoved(e-> deleteAll.setStyle(delH));
		deleteAll.setOnMouseReleased(e-> deleteAll.setStyle(delN));
		deleteAll.setOnMousePressed(e-> deleteAll.setStyle(delP));
		deleteAll.setOnMouseExited(e-> deleteAll.setStyle(delN));
		
		//controls
		back.setOnAction(e -> trainersScreen(s));
		exTrainersTable.setOnMouseClicked(e -> {
			if(exTrainersTable.getSelectionModel().getSelectedItem() != null) {
				ExTrainerListClass selected = (ExTrainerListClass) exTrainersTable.getSelectionModel().getSelectedItem();
				String selectedName = selected.getName();
				String selectedAddress = selected.getAddress();
				loadCurrentExTrainerData(selectedName , selectedAddress);
			}
		});
		exTrainersTable.setOnKeyReleased(e -> {
			if(exTrainersTable.getSelectionModel().getSelectedItem() != null) {
				ExTrainerListClass selected = (ExTrainerListClass) exTrainersTable.getSelectionModel().getSelectedItem();
				String selectedName = selected.getName();
				String selectedAddress = selected.getAddress();
				loadCurrentExTrainerData(selectedName , selectedAddress);
			}
		});
		ExTrainerSQL del = new ExTrainerSQL();
		delete.setOnAction(e ->{
			if(exTrainersTable.getSelectionModel().getSelectedItem() != null) {
				ExTrainerListClass selected = (ExTrainerListClass) exTrainersTable.getSelectionModel().getSelectedItem();
				String selectedName = selected.getName();
				String selectedAddress = selected.getAddress();
				
				boolean isDeleted = del.deleteParticualEntry(connection, selectedName, selectedAddress);
				if(isDeleted == true) {
					prepareExTrainersTable();
					exName.setText("");
					exSex.setText("");
					exDesignation.setText("");
					exCity.setText("");
					exPIN.setText("");
					exContact1.setText("");
					exContact2.setText("");
					exEmail.setText("");
					exSalary.setText("");
					exDOJ.setText("");
					exDOL.setText("");
					Alert done = new Alert(AlertType.INFORMATION);
					done.setTitle("Successful");
					done.setHeaderText("Trainer Removed");
					done.setContentText("Trainer has been successfully removed from "
							+ "Ex-Trainers List. Press OK to continue.\n\n\n");
					done.show();
				}else {
					Alert ndone = new Alert(AlertType.ERROR);
					ndone.setTitle("Failed");
					ndone.setHeaderText("An Error Occured");
					ndone.setContentText("Something went wrong. Trainer was not "
							+ "removed from Ex-Trainers List.\n\n\n");
					ndone.show();
				}
			}
		});
		ExTrainerSQL delAll = new ExTrainerSQL();
		deleteAll.setOnAction(e -> {
			Alert ask = new Alert(AlertType.CONFIRMATION);
			ask.setTitle("Confirmation");
			ask.setHeaderText("Are you Sure?");
			ask.setContentText("All the ex-trainer entries will be permanently deleted. "
					+ "Action once done cannot be undone. Press OK to continue else "
					+ "press Cancel.\n\n\n");
			Optional<ButtonType> isSure = ask.showAndWait();
			if(isSure.get() == ButtonType.OK) {
				boolean isDeleted = delAll.dropTable(connection);
				if(isDeleted == true) {
					prepareExTrainersTable();
					
					Alert done = new Alert(AlertType.INFORMATION);
					done.setTitle("Successful");
					done.setHeaderText("Ex-Trainer(s) Removed");
					done.setContentText("All the Ex-Trainer has been successfully removed. "
							+ "Press OK to continue.\n\n\n");
					done.show();
				}else {
					Alert ndone = new Alert(AlertType.ERROR);
					ndone.setTitle("Failed");
					ndone.setHeaderText("An Error Occured");
					ndone.setContentText("Something went wrong. Ex-Trainers List was "
							+ "not deleted.\n\n\n");
					ndone.show();
				}
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//trainersLabel
		AnchorPane.setTopAnchor(trainersLabel, 63.0);
		AnchorPane.setLeftAnchor(trainersLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 88.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//delete
		AnchorPane.setRightAnchor(delete, 27.0  + 140.0 + 17.0);
		AnchorPane.setBottomAnchor(delete, 22.0);
		//deleteAll
		AnchorPane.setRightAnchor(deleteAll, 27.0);
		AnchorPane.setBottomAnchor(deleteAll, 22.0);
		//nameLabel
		AnchorPane.setTopAnchor(nameLabel, 150.0 - 20.0);
		AnchorPane.setLeftAnchor(nameLabel, 450.0);
		//sexLabel
		AnchorPane.setTopAnchor(sexLabel, 190.0 - 20.0);
		AnchorPane.setLeftAnchor(sexLabel, 450.0);
		//designationLabel
		AnchorPane.setTopAnchor(designationLabel, 190.0 - 20.0);
		AnchorPane.setLeftAnchor(designationLabel, 630.0);
		//cityLabel
		AnchorPane.setTopAnchor(cityLabel, 230.0 - 20.0);
		AnchorPane.setLeftAnchor(cityLabel, 450.0);
		//pinLabel
		AnchorPane.setTopAnchor(pinLabel, 230.0 - 20.0);
		AnchorPane.setLeftAnchor(pinLabel, 650.0);
		//contactLabel
		AnchorPane.setTopAnchor(contactLabel, 270.0 - 20.0);
		AnchorPane.setLeftAnchor(contactLabel, 450.0);
		//emailLabel
		AnchorPane.setTopAnchor(emailLabel, 345.0 - 20.0);
		AnchorPane.setLeftAnchor(emailLabel, 450.0);
		//salaryLabel
		AnchorPane.setTopAnchor(salaryLabel, 385.0 - 20.0);
		AnchorPane.setLeftAnchor(salaryLabel, 450.0);
		//dojLabel
		AnchorPane.setTopAnchor(dojLabel, 425.0 - 20.0);
		AnchorPane.setLeftAnchor(dojLabel, 450.0);
		//dolLabel
		AnchorPane.setTopAnchor(dolLabel, 465.0 - 20.0);
		AnchorPane.setLeftAnchor(dolLabel, 450.0);
		//exName
		AnchorPane.setTopAnchor(exName, 150.0 - 21.0);
		AnchorPane.setLeftAnchor(exName, 510.0);
		//exSex
		AnchorPane.setTopAnchor(exSex, 190.0 - 21.0);
		AnchorPane.setLeftAnchor(exSex, 490.0);
		//exDesignation
		AnchorPane.setTopAnchor(exDesignation, 190.0 - 21.0);
		AnchorPane.setLeftAnchor(exDesignation, 720.0);
		//exCity
		AnchorPane.setTopAnchor(exCity, 230.0 - 21.0);
		AnchorPane.setLeftAnchor(exCity, 490.0);
		//exPIN
		AnchorPane.setTopAnchor(exPIN, 230.0 - 21.0);
		AnchorPane.setLeftAnchor(exPIN, 720.0);
		//exContact1
		AnchorPane.setTopAnchor(exContact1, 270.0 - 21.0);
		AnchorPane.setLeftAnchor(exContact1, 600.0);
		//exContact2
		AnchorPane.setTopAnchor(exContact2, 310.0 - 21.0);
		AnchorPane.setLeftAnchor(exContact2, 600.0);
		//exEmail
		AnchorPane.setTopAnchor(exEmail, 345.0 - 21.0);
		AnchorPane.setLeftAnchor(exEmail, 510.0);
		//exSalary
		AnchorPane.setTopAnchor(exSalary, 385.0 - 21.0);
		AnchorPane.setLeftAnchor(exSalary, 510.0);
		//exDOJ
		AnchorPane.setTopAnchor(exDOJ, 425.0 - 21.0);
		AnchorPane.setLeftAnchor(exDOJ, 580.0);
		//exDOL
		AnchorPane.setTopAnchor(exDOL, 465.0 - 21.0);
		AnchorPane.setLeftAnchor(exDOL, 510.0);
		//exTrainersTable
		AnchorPane.setTopAnchor(exTrainersTable, 125.0);
		AnchorPane.setLeftAnchor(exTrainersTable, 80.0);
		AnchorPane.setRightAnchor(exTrainersTable, 490.0);
		AnchorPane.setBottomAnchor(exTrainersTable, 50.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , underline , trainersLabel , delete ,
			nameLabel , sexLabel , designationLabel , cityLabel , pinLabel , contactLabel ,
			emailLabel , salaryLabel , dojLabel , dolLabel , exContact1 , exContact2 ,
			deleteAll , exName , exSex , exDesignation , exCity , exPIN , exEmail , exSalary ,
			exDOJ , exDOL , exTrainersTable);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void trainersScreen(Stage s) {
		prepareTrainersTable();
		
		loadAllPotentialSearchElements();
		
		ExTrainerSQL ext = new ExTrainerSQL();
		ext.createExTrainersTable(connection);
		
		Button update= new Button("Update");
		Button filter= new Button("Filter");
		Button addTrainer= new Button("+");
		Button deleteTrainer = new Button("-");
		Button goTrainer = new Button(">");
		Button extrainers = new Button("Ex-Trainers");
		
		EntryToTrainerTable delTrainer = new EntryToTrainerTable();
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		back.setOnAction(e -> homeScreen(s));
		addTrainer.setOnAction(e -> addNewTrainer(s));
		goTrainer.setOnAction(e -> {
			if(trainersTable.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass selectedRow = (TrainersListClass) trainersTable.getSelectionModel().getSelectedItem();
				String tnrID = selectedRow.getTrainerID();
				viewTrainerDetailsScreen(s , tnrID);
			}
		});
		update.setOnAction(e -> {
			if(trainersTable.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass selectedRow = (TrainersListClass) trainersTable.getSelectionModel().getSelectedItem();
				String tnrID = selectedRow.getTrainerID();
				updateTrainerInformationScreen(s , tnrID);
			}
		});
		deleteTrainer.setOnAction(e -> {
			if(trainersTable.getSelectionModel().getSelectedItem() != null) {
				TrainersListClass selectedRow = (TrainersListClass) trainersTable.getSelectionModel().getSelectedItem();
				String tnrID = selectedRow.getTrainerID();
				Alert sure = new Alert(AlertType.CONFIRMATION);
				sure.setTitle("Confirmation");
				sure.setHeaderText("Are you sure?");
				sure.setContentText("Trainer " + selectedRow.getTrainerName() + " will be removed from "
						+ "Gym. Once action done cannot be undone. Press OK to delete else press Cancel.\n\n\n");
				Optional<ButtonType> isSure = sure.showAndWait();
				if(isSure.get() == ButtonType.OK) {
					boolean isTrainerDeleted = delTrainer.deleteTrainer(connection, tnrID);
					if(isTrainerDeleted == true) {
						prepareTrainersTable();
						Alert yes = new Alert(AlertType.INFORMATION);
						yes.setTitle("Successful");
						yes.setHeaderText("Trainer Removed");
						yes.setContentText("Selected trainer has been successfully removed from gym. "
								+ "Press OK to continue.\n\n\n");
						yes.show();
					}else {
						Alert no = new Alert(AlertType.ERROR);
						no.setTitle("Failed");
						no.setHeaderText("Trainer Not Removed");
						no.setContentText("While removing the selected trainer some error occured. "
								+ "Please try again.\n\n\n");
						no.show();
					}
				}
			}
		});
		extrainers.setOnAction(e -> exTrainersScreen(s));
		filter.setOnAction(e -> filterScreen(s , false));
		
		Label trainersLabel = new Label(" Trainers List");
		trainersLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineTrainers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineTrainers);
		underline.setUnderline(true);
		
		update.setPrefSize(120.0, 32.0);
		filter.setPrefSize(120.0, 32.0);
		extrainers.setPrefSize(120.0, 32.0);
		addTrainer.setPrefSize(100.0, 32.0);
		deleteTrainer.setPrefSize(100.0, 32.0);
		goTrainer.setPrefSize(100.0, 32.0);
		
		String addN = "-fx-text-fill: white;" + "-fx-background-color: '#00ff00';" +
		        	"-fx-font-weight: bold;" +
		        	"-fx-font-family: 'Arial';" +
		        	"-fx-font-size: 15px;";
		String addH = "-fx-text-fill: white;" + "-fx-background-color: '#00b300';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
		String addP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#008000';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
	
		addTrainer.setStyle(addN);
		addTrainer.setOnMouseMoved(e-> addTrainer.setStyle(addH));		//hover
		addTrainer.setOnMouseReleased(e-> addTrainer.setStyle(addN));	//normal
		addTrainer.setOnMousePressed(e-> addTrainer.setStyle(addP));	//pressed
		addTrainer.setOnMouseExited(e-> addTrainer.setStyle(addN));		//normal
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
	    			"-fx-font-weight: bold;" +
	    			"-fx-font-family: 'Arial';" +
	    			"-fx-font-size: 15px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		deleteTrainer.setStyle(delN);
		deleteTrainer.setOnMouseMoved(e-> deleteTrainer.setStyle(delH));		//hover
		deleteTrainer.setOnMouseReleased(e-> deleteTrainer.setStyle(delN));		//normal
		deleteTrainer.setOnMousePressed(e-> deleteTrainer.setStyle(delP));		//pressed
		deleteTrainer.setOnMouseExited(e-> deleteTrainer.setStyle(delN));		//normal
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 15px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '#0066cc';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#004d99';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		goTrainer.setStyle(goN);
		goTrainer.setOnMouseMoved(e-> goTrainer.setStyle(goH));			//hover
		goTrainer.setOnMouseReleased(e-> goTrainer.setStyle(goN));		//normal
		goTrainer.setOnMousePressed(e-> goTrainer.setStyle(goP));		//pressed
		goTrainer.setOnMouseExited(e-> goTrainer.setStyle(goN));		//normal
		
		update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		update.setOnMouseExited(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		filter.setOnMouseExited(e -> filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseReleased(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		filter.setOnMouseReleased(e -> filter.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseEntered(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		filter.setOnMouseEntered(e -> filter.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		update.setOnMousePressed(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		filter.setOnMousePressed(e -> filter.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		extrainers.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: 'black';" + "-fx-font-weight: normal;");
		extrainers.setOnMouseExited(e -> extrainers.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: 'black';" + "-fx-font-weight: normal;"));
		extrainers.setOnMouseReleased(e -> extrainers.setStyle("-fx-background-color: 'black';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: 'black';" + "-fx-font-weight: normal;"));
		extrainers.setOnMouseEntered(e -> extrainers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: 'black';" + "-fx-font-size: 12.9px;" + "-fx-border-color: 'black';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		extrainers.setOnMousePressed(e -> extrainers.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: 'black';" + "-fx-font-size: 13px;" + "-fx-border-color: 'black';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		search.setPrefWidth(250.0);
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//trainersLabel
		AnchorPane.setTopAnchor(trainersLabel, 93.0);
		AnchorPane.setLeftAnchor(trainersLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//trainersTable
		AnchorPane.setTopAnchor(trainersTable, 165.0);
		AnchorPane.setLeftAnchor(trainersTable, 90.0);
		AnchorPane.setRightAnchor(trainersTable, 90.0);
		AnchorPane.setBottomAnchor(trainersTable, 90.0);
		//update
		AnchorPane.setLeftAnchor(update, 27.0);
		AnchorPane.setBottomAnchor(update, 22.0);
		//filter
		AnchorPane.setLeftAnchor(filter, 27.0  + 120.0 + 17.0);
		AnchorPane.setBottomAnchor(filter, 22.0);
		//addTrainer
		AnchorPane.setRightAnchor(addTrainer, 127.0 + 17.0 + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(addTrainer, 22.0);
		//deleteTrainer
		AnchorPane.setRightAnchor(deleteTrainer, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(deleteTrainer, 22.0);
		//goTrainer
		AnchorPane.setRightAnchor(goTrainer, 27.0);
		AnchorPane.setBottomAnchor(goTrainer, 22.0);
		//extrainers
		AnchorPane.setLeftAnchor(extrainers, 27.0  + 120.0 + 17.0 + 120.0 + 17.0);
		AnchorPane.setBottomAnchor(extrainers, 22.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, search , settings , underline ,
							trainersLabel , trainersTable , addTrainer , deleteTrainer , goTrainer ,
							filter , update , extrainers);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void prepareEquipmentsTable() {
		loadAllPotentialSearchElements();
		
		equipmentsColumn5.setVisible(false);
		dateOfSelectedEquipment.setText("");
		dateOfSelectedEquipment.setStyle("-fx-font-size: 16px;");
		
		String equipName = "";
		String equipNumber = "";
		String equipCost = "";
		String equipAvailability = "";
		String equipDOI = "";
		
		try {
			equipmentsTable.getItems().clear();
			PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_EQUIPMENTS`;");
		    ResultSet rs = command.executeQuery();
		    
		    while(rs.next()) {
		    	equipName = rs.getString(1).toString();
		    	equipNumber = rs.getString(2).toString();
		    	equipCost = rs.getString(3).toString();
		    	equipAvailability = rs.getString(4).toString();
		    	equipDOI = rs.getString(5).toString();
		    	
		    	equipmentsTable.getItems().add(new EquipmentsListClass(equipName , equipNumber , equipCost , equipAvailability , equipDOI));
		    }
		}catch(Exception e) {
			//create GYM_EQUIPMENTS
			try {
				PreparedStatement createCommand = connection.prepareStatement("CREATE TABLE `studentdatabase`.GYM_EQUIPMENTS(Name VARCHAR(100) PRIMARY KEY, Number INT , Cost INT , Availability VARCHAR(15) , DOI DATE);");
			    createCommand.execute();
			}catch(Exception f) {}
			
		}
	}
	
	public void deleteEquipmentFromDB(String selectedEquipmentName) {
		try {
			PreparedStatement command = connection.prepareStatement("DELETE FROM `studentdatabase`.`GYM_EQUIPMENTS` WHERE `GYM_EQUIPMENTS`.Name = '" + selectedEquipmentName + "';");
			command.execute();
			prepareEquipmentsTable();
		}catch(Exception e) {}
	}
	
	Label invalidEquipmentWizard = new Label("Enter Valid Data");
	TextField newEquipmentName = new TextField();
	Label unitsLabel = new Label("Units:");
	TextField newEquipmentUnits = new TextField();
	Label costLabel = new Label("Cost:      Rs.");
	TextField newEquipmentCost = new TextField();
	Label dateOfInstallationLabel = new Label("Date of Installation:");
	Label dateOfInstallation = new Label("Null");
	
	public void resetAddNewEquipmentScreen() {
		invalidEquipmentWizard.setVisible(false);
		invalidEquipmentWizard.setStyle("-fx-font-family: 'Marlett';" + "-fx-font-size: 12px;" + "-fx-text-fill: red;");
		newEquipmentName.setPromptText("Name of the Equipment");
		newEquipmentUnits.setPromptText("Unit(s)");
		unitsLabel.setStyle("-fx-font-size: 15px;");
		costLabel.setStyle("-fx-font-size: 15px;");
		newEquipmentCost.setPromptText("In Rupee(s)");
		dateOfInstallationLabel.setStyle("-fx-font-size: 15px;");
		dateOfInstallation.setStyle("-fx-font-size: 17px;");
		
		newEquipmentName.setStyle(ref.getStyle());
		newEquipmentUnits.setStyle(ref.getStyle());
		newEquipmentCost.setStyle(ref.getStyle());
		
		newEquipmentName.setText("");
		newEquipmentUnits.setText("");
		newEquipmentCost.setText("");
		
		dateOfInstallation.setText(getCurrentDate());
	}
	
	public void addNewEquipmentEntry(Stage s , String stringName , String stringUnits , String stringCost , String stringInstallation) {
		try {
			String statement = "INSERT INTO `studentdatabase`.`gym_equipments`"
				+ "(`Name`, `Number`, `Cost`, `Availability` , `DOI`) VALUES ('" + stringName + "', '"
				+ stringUnits + "', '" + stringCost + "', '" + "AVAILABLE' , CURDATE());";
			PreparedStatement ps = connection.prepareStatement(statement);
			ps.execute();
			
			Alert success = new Alert(AlertType.INFORMATION);
			success.setTitle("Installation Successful");
			success.setHeaderText("New Equipment has been installed");
			success.setContentText(stringName + " has been successfully installed. Press OK to continue.\n\n");
			Optional op = success.showAndWait();
			if(op.get() == ButtonType.OK) {
				equipmentsScreen(s);
			}
		}catch(Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Error Occured");
			error.setHeaderText("Installation Failed");
			error.setContentText("Installation of New Equipment Failed. Change Equipment Name and try again.\n\n");
			error.show();
		}
	}
	
	public void addNewEquipmentScreen(Stage  s) {
		resetAddNewEquipmentScreen();
		
		Button addEquipment= new Button("Install");
		Button resetEquipment = new Button("Reset");
		
		Label equipmentsLabel = new Label(" Install New Equipment");
		equipmentsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineTrainers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineTrainers);
		underline.setUnderline(true);
		
		//controls
		back.setOnAction(e -> equipmentsScreen(s));
		settings.setOnAction(e -> settingsScreen(s));
		resetEquipment.setOnAction(e -> resetAddNewEquipmentScreen());
		addEquipment.setOnAction(e -> {
			if(newEquipmentName.getText().equals("") == true || newEquipmentUnits.getText().equals("") == true || newEquipmentCost.getText().equals("") == true) {
				if(newEquipmentName.getText().equals("") == true) {
					newEquipmentName.setStyle("-fx-border-color: 'red';");
				}else {
					newEquipmentName.setStyle(ref.getStyle());
				}
				if(newEquipmentUnits.getText().equals("") == true) {
					newEquipmentUnits.setStyle("-fx-border-color: 'red';");
				}else {
					newEquipmentUnits.setStyle(ref.getStyle());
				}
				if(newEquipmentCost.getText().equals("") == true) {
					newEquipmentCost.setStyle("-fx-border-color: 'red';");
				}else {
					newEquipmentCost.setStyle(ref.getStyle());
				}
				
			}else {
				invalidEquipmentWizard.setVisible(false);
				
				final int COUNT_FLAG = 2;
				int count_flag = 0;
				
				String enteredUnits = newEquipmentUnits.getText();
				int error1 = 0 , error2 = 0;
				for(int i = 0 ; i < enteredUnits.length() ; i++) {
					char ch = enteredUnits.charAt(i);
					if(ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
						error1 = 0;
						newEquipmentUnits.setStyle(ref.getStyle());
						continue;
					}else {			//incorrect input
						error1 = 1;
						invalidEquipmentWizard.setVisible(true);
						newEquipmentUnits.setStyle("-fx-border-color: 'red';");
						break;
					}
				}
				if(error1 == 0)
					count_flag++;
				
				String enteredCost = newEquipmentCost.getText();
				for(int i = 0 ; i < enteredCost.length() ; i++) {
					char ch = enteredCost.charAt(i);
					if(ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
						error2 = 0;
						newEquipmentCost.setStyle(ref.getStyle());
						continue;
					}else {			//incorrect input
						error2 = 1;
						invalidEquipmentWizard.setVisible(true);
						newEquipmentCost.setStyle("-fx-border-color: 'red';");
						break;
					}
				}
				if(error2 == 0)
					count_flag++;
				
				if(count_flag == COUNT_FLAG) {
					newEquipmentName.setStyle(ref.getStyle());
					newEquipmentUnits.setStyle(ref.getStyle());
					newEquipmentCost.setStyle(ref.getStyle());
					
					Alert confirmEquipment = new Alert(AlertType.CONFIRMATION);
					confirmEquipment.setTitle("Install New Equipment");
					confirmEquipment.setHeaderText("New Equipment Installation");
					confirmEquipment.setContentText("New Equipment of Name: " + newEquipmentName.getText()
											+ " will be installed. To proceed press OK.\n\n");
					Optional<ButtonType> userConfirmation = confirmEquipment.showAndWait();
					if(userConfirmation.get() == ButtonType.OK) {
						addNewEquipmentEntry(s , newEquipmentName.getText() , newEquipmentUnits.getText() , newEquipmentCost.getText() , dateOfInstallation.getText());
					}
				}
			}
		});
		
		addEquipment.setPrefSize(100.0, 32.0);
		resetEquipment.setPrefSize(100.0, 32.0);
		
		addEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		resetEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		addEquipment.setOnMouseExited(e -> addEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		resetEquipment.setOnMouseExited(e -> resetEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		addEquipment.setOnMouseReleased(e -> addEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		resetEquipment.setOnMouseReleased(e -> resetEquipment.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		addEquipment.setOnMouseEntered(e -> addEquipment.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		resetEquipment.setOnMouseEntered(e -> resetEquipment.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		addEquipment.setOnMousePressed(e -> addEquipment.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		resetEquipment.setOnMousePressed(e -> resetEquipment.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//equipmentsLabel
		AnchorPane.setTopAnchor(equipmentsLabel, 93.0);
		AnchorPane.setLeftAnchor(equipmentsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//addEquipment
		AnchorPane.setRightAnchor(addEquipment, 27.0);
		AnchorPane.setBottomAnchor(addEquipment, 22.0);
		//resetEquipment
		AnchorPane.setRightAnchor(resetEquipment, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(resetEquipment, 22.0);
		//invalidEquipmentLabel
		AnchorPane.setRightAnchor(invalidEquipmentWizard, 29.0);
		AnchorPane.setBottomAnchor(invalidEquipmentWizard, 62.0);
		//newEquipmentName
		AnchorPane.setTopAnchor(newEquipmentName, 175.0 + 30.0);
		AnchorPane.setLeftAnchor(newEquipmentName, 200.0);
		AnchorPane.setRightAnchor(newEquipmentName, 200.0);
		//unitsLabel
		AnchorPane.setTopAnchor(unitsLabel, 228.0 + 30.0);
		AnchorPane.setLeftAnchor(unitsLabel, 210.0);
		//newEquipmentUnits
		AnchorPane.setTopAnchor(newEquipmentUnits, 225.0 + 30.0);
		AnchorPane.setLeftAnchor(newEquipmentUnits, 270.0);
		AnchorPane.setRightAnchor(newEquipmentUnits, 500.0);
		//costLabel
		AnchorPane.setTopAnchor(costLabel, 228.0 + 30.0);
		AnchorPane.setLeftAnchor(costLabel, 470.0);
		//newEquipmentCost
		AnchorPane.setTopAnchor(newEquipmentCost, 225.0 + 30.0);
		AnchorPane.setLeftAnchor(newEquipmentCost, 560.0);
		AnchorPane.setRightAnchor(newEquipmentCost, 210.0);
		//dateOfInstallationLabel
		AnchorPane.setTopAnchor(dateOfInstallationLabel, 305.0 + 30.0);
		AnchorPane.setLeftAnchor(dateOfInstallationLabel, 200.0);
		//dateOfInstallation
		AnchorPane.setTopAnchor(dateOfInstallation, 302.0 + 30.0);
		AnchorPane.setRightAnchor(dateOfInstallation, 450.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, settings , underline , invalidEquipmentWizard ,
				equipmentsLabel , addEquipment , resetEquipment , newEquipmentName , unitsLabel ,
				newEquipmentUnits , costLabel , newEquipmentCost , dateOfInstallationLabel ,
				dateOfInstallation);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	String equipmentNameValue = "";
	
	public void updateEquipmentNumber(Stage s , String newUnit) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `studentdatabase`.`GYM_EQUIPMENTS` SET Number = '" + newUnit + "' WHERE Name = '" + equipmentNameValue + "';");
			ps.execute();
			Alert done = new Alert(AlertType.INFORMATION);
			done.setTitle("Message");
			done.setHeaderText("Modification Successful");
			done.setContentText("Changes have been made Successfully. Press OK to continue.\n\n");
			Optional<ButtonType> input= done.showAndWait();
			if(input.get() == ButtonType.OK) {
				equipmentsScreen(s);
			}
		}catch(Exception e) {
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Error Occured");
			err.setHeaderText("Modification Failed");
			err.setContentText("Please check your input values and try again.\n\n");
			err.show();
		}
	}
	
	public void updateEquipmentCost(Stage s , String newCost) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `studentdatabase`.`GYM_EQUIPMENTS` SET Cost = '" + newCost + "' WHERE Name = '" + equipmentNameValue + "';");
			ps.execute();
			Alert done = new Alert(AlertType.INFORMATION);
			done.setTitle("Message");
			done.setHeaderText("Modification Successful");
			done.setContentText("Changes have been made Successfully. Press OK to continue.\n\n");
			Optional<ButtonType> input= done.showAndWait();
			if(input.get() == ButtonType.OK) {
				equipmentsScreen(s);
			}
		}catch(Exception e) {
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Error Occured");
			err.setHeaderText("Modification Failed");
			err.setContentText("Please check your input values and try again.\n\n");
			err.show();
		}
	}
	
	public void updateEquipmentAvailability(Stage s , String newAvail) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `studentdatabase`.`GYM_EQUIPMENTS` SET Availability = '" + newAvail + "' WHERE Name = '" + equipmentNameValue + "';");
			ps.execute();
			Alert done = new Alert(AlertType.INFORMATION);
			done.setTitle("Message");
			done.setHeaderText("Modification Successful");
			done.setContentText("Changes have been made Successfully. Press OK to continue.\n\n");
			Optional<ButtonType> input= done.showAndWait();
			if(input.get() == ButtonType.OK) {
				equipmentsScreen(s);
			}
		}catch(Exception e) {
			Alert err = new Alert(AlertType.ERROR);
			err.setTitle("Error Occured");
			err.setHeaderText("Modification Failed");
			err.setContentText("Please check your input values and try again.\n\n");
			err.show();
		}
	}
	
	TextField changedUnit = new TextField();
	TextField changedCost = new TextField();
	ObservableList<String> options = FXCollections.observableArrayList("Available" , "Unavailable");
	ComboBox<String> changedAvail = new ComboBox<String>(options);
	
	public void modifyEquipmentsScreen(Stage s) {
		changedAvail.getSelectionModel().selectFirst();
		
		ToggleGroup tg = new ToggleGroup(); 
		RadioButton r1 = new RadioButton(" Change Number of Unit(s)"); 
        RadioButton r2 = new RadioButton(" Change Cost of the Equipment"); 
        RadioButton r3 = new RadioButton(" Change Availability Option"); 
        r1.setStyle("-fx-font-size: 13px;");
        r2.setStyle("-fx-font-size: 13px;");
        r3.setStyle("-fx-font-size: 13px;");
        r1.setToggleGroup(tg); 
        r2.setToggleGroup(tg); 
        r3.setToggleGroup(tg); 
        r1.setSelected(false);
		r2.setSelected(false);
		r3.setSelected(false);
		
        Button saveChanges = new Button("Save Changes");
        saveChanges.setPrefSize(120.0, 32.0);
        
        saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
        saveChanges.setOnMouseExited(e -> saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
        saveChanges.setOnMouseReleased(e -> saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
        saveChanges.setOnMouseEntered(e -> saveChanges.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
        saveChanges.setOnMousePressed(e -> saveChanges.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
        Label modifyLabel = new Label(" Modify Changes");
		modifyLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineModify = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineModify);
		underline.setUnderline(true);
		Label equip = new Label("Equipment:");
		Label name = new Label("");
		name.setText(sentName);
		equip.setStyle("-fx-font-size: 16px;");
		name.setStyle("-fx-font-size: 17px;");
		equipmentNameValue = name.getText().toString();
		String unr = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "     ";
		Label underlineNameR1 = new Label(unr);
		underlineNameR1.setUnderline(true);
		underlineNameR1.setOpacity(0.3);
		Label underlineNameR2 = new Label(unr);
		underlineNameR2.setUnderline(true);
		underlineNameR2.setOpacity(0.3);
		Label underlineNameR3 = new Label(unr);
		underlineNameR3.setUnderline(true);
		underlineNameR3.setOpacity(0.3);
		Label unitsLabel = new Label("Unit(s):");
		Label costLabel = new Label("Cost:");
		Label rsLabel = new Label("Rs.");
		rsLabel.setStyle("-fx-font-size: 15px;");
		Label availLabel = new Label("Availability:");
		unitsLabel.setStyle("-fx-font-size: 15px;");
		costLabel.setStyle("-fx-font-size: 15px;");
		availLabel.setStyle("-fx-font-size: 15px;");
		Label doiLabel = new Label("Date of Installation:");
		doiLabel.setStyle("-fx-font-size: 16px");
		Label doi = new Label("");
		doi.setStyle("-fx-font-size: 17px");
		
		changedUnit.setDisable(true);
		changedCost.setDisable(true);
		changedAvail.setDisable(true);
		unitsLabel.setOpacity(0.6);
		costLabel.setOpacity(0.6);
		rsLabel.setOpacity(0.6);
		availLabel.setOpacity(0.6);
		
		try {
			EquipmentsListClass selectedRow = (EquipmentsListClass) equipmentsTable.getSelectionModel().getSelectedItem();
			changedUnit.setText(selectedRow.getEquipmentNum());
			changedCost.setText(selectedRow.getEquipmentCost());
			doi.setText(selectedRow.getEquipmentDOI());
			String avail = selectedRow.getEquipmentAvailability();
			if(avail.equalsIgnoreCase("AVAILABLE") == true) {
				changedAvail.setValue("Available");
			}else {
				changedAvail.setValue("Unavailable");
			}
		}catch(Exception e){}
		
		//controls
		back.setOnAction(e -> equipmentsScreen(s));
		r1.setOnAction(e -> {
			underlineNameR2.setOpacity(0.3);
			underlineNameR3.setOpacity(0.3);
			changedCost.setDisable(true);
			changedAvail.setDisable(true);
			costLabel.setOpacity(0.6);
			rsLabel.setOpacity(0.6);
			availLabel.setOpacity(0.6);
			
			underlineNameR1.setOpacity(0.55);
			changedUnit.setDisable(false);
			unitsLabel.setOpacity(1.0);
		});
		r2.setOnAction(e -> {
			underlineNameR1.setOpacity(0.3);
			underlineNameR3.setOpacity(0.3);
			changedUnit.setDisable(true);
			changedAvail.setDisable(true);
			unitsLabel.setOpacity(0.6);
			availLabel.setOpacity(0.6);
			
			underlineNameR2.setOpacity(0.55);
			changedCost.setDisable(false);
			costLabel.setOpacity(1.0);
			rsLabel.setOpacity(1.0);
		});
		r3.setOnAction(e -> {
			underlineNameR1.setOpacity(0.3);
			underlineNameR2.setOpacity(0.3);
			changedUnit.setDisable(true);
			changedCost.setDisable(true);
			unitsLabel.setOpacity(0.6);
			costLabel.setOpacity(0.6);
			rsLabel.setOpacity(0.6);
			
			underlineNameR3.setOpacity(0.55);
			changedAvail.setDisable(false);
			availLabel.setOpacity(1.0);
		});
		saveChanges.setOnAction(e -> {
			if(r1.isSelected() == true) {
				updateEquipmentNumber(s , changedUnit.getText().toString());
			}else if(r2.isSelected() == true) {
				updateEquipmentCost(s , changedCost.getText().toString());
			}else if(r3.isSelected() == true) {
				updateEquipmentAvailability(s , changedAvail.getSelectionModel().getSelectedItem().toUpperCase());
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//modifyLabel
		AnchorPane.setTopAnchor(modifyLabel, 93.0 - 20.0);
		AnchorPane.setLeftAnchor(modifyLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//equip
		AnchorPane.setTopAnchor(equip, 135.0);
		AnchorPane.setRightAnchor(equip, 720.0);
		//name
		AnchorPane.setTopAnchor(name, 134.0);
		AnchorPane.setLeftAnchor(name, 200.0);
		//r1
		AnchorPane.setTopAnchor(r1, 190.0);
		AnchorPane.setLeftAnchor(r1, 240.0);
		//r2
		AnchorPane.setTopAnchor(r2, 280.0);
		AnchorPane.setLeftAnchor(r2, 240.0);
		//r3
		AnchorPane.setTopAnchor(r3, 370.0);
		AnchorPane.setLeftAnchor(r3, 240.0);
		//underlineNameR1
		AnchorPane.setTopAnchor(underlineNameR1, 200.0);
		AnchorPane.setLeftAnchor(underlineNameR1, 230.0);
		AnchorPane.setRightAnchor(underlineNameR1, 230.0);
		//underlineNameR2
		AnchorPane.setTopAnchor(underlineNameR2, 290.0);
		AnchorPane.setLeftAnchor(underlineNameR2, 230.0);
		AnchorPane.setRightAnchor(underlineNameR2, 230.0);
		//underlineNameR3
		AnchorPane.setTopAnchor(underlineNameR3, 380.0);
		AnchorPane.setLeftAnchor(underlineNameR3, 230.0);
		AnchorPane.setRightAnchor(underlineNameR3, 230.0);
		//unitsLabel
		AnchorPane.setTopAnchor(unitsLabel, 228.0);
		AnchorPane.setRightAnchor(unitsLabel, 530.0);
		//costLabel
		AnchorPane.setTopAnchor(costLabel, 318.0);
		AnchorPane.setRightAnchor(costLabel, 530.0);
		//rsLabel
		AnchorPane.setTopAnchor(rsLabel, 318.0);
		AnchorPane.setLeftAnchor(rsLabel, 410.0);
		//availLabel
		AnchorPane.setTopAnchor(availLabel, 408.0);
		AnchorPane.setRightAnchor(availLabel, 530.0);
		//changedUnit
		AnchorPane.setTopAnchor(changedUnit, 226.0);
		AnchorPane.setLeftAnchor(changedUnit, 400.0);
		AnchorPane.setRightAnchor(changedUnit, 300.0);
		//changedCost
		AnchorPane.setTopAnchor(changedCost, 316.0);
		AnchorPane.setLeftAnchor(changedCost, 440.0);
		AnchorPane.setRightAnchor(changedCost, 300.0);
		//changedAvail
		AnchorPane.setTopAnchor(changedAvail, 406.0);
		AnchorPane.setLeftAnchor(changedAvail, 400.0);
		AnchorPane.setRightAnchor(changedAvail, 300.0);
		//doiLabel
		AnchorPane.setTopAnchor(doiLabel, 475.0);
		AnchorPane.setRightAnchor(doiLabel, 661.0);
		//doi
		AnchorPane.setTopAnchor(doi, 474.0);
		AnchorPane.setLeftAnchor(doi, 260.0);
		//saveChanges
		AnchorPane.setRightAnchor(saveChanges, 35.0);
		AnchorPane.setBottomAnchor(saveChanges, 25.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, underline , modifyLabel , doiLabel, doi , rsLabel ,
							saveChanges , name , equip , underlineNameR1, underlineNameR2 ,
							underlineNameR3 , r1 , r2 , r3 , unitsLabel , costLabel , availLabel ,
							changedUnit , changedCost , changedAvail);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	Label dateOfSelectedEquipment = new Label("");
	String sentName = "";
	public void getEquipmentDOI() {
		equipmentsColumn5.setVisible(false);
		
		EquipmentsListClass selectedRow = (EquipmentsListClass) equipmentsTable.getSelectionModel().getSelectedItem();
		String selectedEquip = selectedRow.getEquipmentDOI();
		dateOfSelectedEquipment.setText(selectedEquip);
		sentName = selectedRow.getEquipmentName();
	}
	
	public void equipmentsScreen(Stage s) {
		prepareEquipmentsTable();
		
		loadAllPotentialSearchElements();
		
		Button update= new Button("Modify");
		Button addEquipment= new Button("+");
		Button deleteEquipment = new Button("-");

		Label equipmentsLabel = new Label(" Equipments List");
		equipmentsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineTrainers = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineTrainers);
		underline.setUnderline(true);
		Label dateOfInstLabel = new Label("Date of Installation:");
		dateOfInstLabel.setStyle("-fx-font-size: 15px;");
		
		update.setPrefSize(120.0, 32.0);
		addEquipment.setPrefSize(100.0, 32.0);
		deleteEquipment.setPrefSize(100.0, 32.0);
		
		//controls
		back.setOnAction(e -> homeScreen(s));
		settings.setOnAction(e -> settingsScreen(s));
		deleteEquipment.setOnAction(e -> {
			if(equipmentsTable.getSelectionModel().getSelectedItem() != null) {
				EquipmentsListClass selectedEquipment = (EquipmentsListClass) equipmentsTable.getSelectionModel().getSelectedItem();
				String selectedEquipmentName = selectedEquipment.getEquipmentName();
				Alert confirmDelete = new Alert(AlertType.CONFIRMATION);
				confirmDelete.setTitle("Delete Equipment");
				confirmDelete.setHeaderText("Are you Sure?");
				confirmDelete.setContentText(selectedEquipmentName + " will be deleted. Once action done cannot"
											+ " be undone. Press OK to delete.\n\n");
				
				Optional<ButtonType> userAnswer = confirmDelete.showAndWait();
				if(userAnswer.get() == ButtonType.OK) {
					deleteEquipmentFromDB(selectedEquipmentName);
				}
			}
		});
		addEquipment.setOnAction( e -> addNewEquipmentScreen(s));
		equipmentsTable.setOnMouseClicked(e -> {
			if(equipmentsTable.getSelectionModel().getSelectedItem() != null) {
				getEquipmentDOI();
			}
		});
		equipmentsTable.setOnKeyReleased(e -> {
			if(equipmentsTable.getSelectionModel().getSelectedItem() != null) {
				getEquipmentDOI();
			}
		});
		update.setOnAction(e -> {
			EquipmentsListClass selectedRow = (EquipmentsListClass) equipmentsTable.getSelectionModel().getSelectedItem();
			if(selectedRow != null) {
				modifyEquipmentsScreen(s);
			}
		});
		
		String addN = "-fx-text-fill: white;" + "-fx-background-color: '#00ff00';" +
		        	"-fx-font-weight: bold;" +
		        	"-fx-font-family: 'Arial';" +
		        	"-fx-font-size: 15px;";
		String addH = "-fx-text-fill: white;" + "-fx-background-color: '#00b300';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
		String addP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#008000';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";

		addEquipment.setStyle(addN);
		addEquipment.setOnMouseMoved(e-> addEquipment.setStyle(addH));		//hover
		addEquipment.setOnMouseReleased(e-> addEquipment.setStyle(addN));	//normal
		addEquipment.setOnMousePressed(e-> addEquipment.setStyle(addP));	//pressed
		addEquipment.setOnMouseExited(e-> addEquipment.setStyle(addN));		//normal
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
	    			"-fx-font-weight: bold;" +
	    			"-fx-font-family: 'Arial';" +
	    			"-fx-font-size: 15px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
			
		deleteEquipment.setStyle(delN);
		deleteEquipment.setOnMouseMoved(e-> deleteEquipment.setStyle(delH));		//hover
		deleteEquipment.setOnMouseReleased(e-> deleteEquipment.setStyle(delN));		//normal
		deleteEquipment.setOnMousePressed(e-> deleteEquipment.setStyle(delP));		//pressed
		deleteEquipment.setOnMouseExited(e-> deleteEquipment.setStyle(delN));		//normal
		
		update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		update.setOnMouseExited(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseReleased(e -> update.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		update.setOnMouseEntered(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		update.setOnMousePressed(e -> update.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		search.setPrefWidth(250.0);
	
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//equipmentsLabel
		AnchorPane.setTopAnchor(equipmentsLabel, 93.0);
		AnchorPane.setLeftAnchor(equipmentsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//equipmentsTable
		AnchorPane.setTopAnchor(equipmentsTable, 160.0);
		AnchorPane.setLeftAnchor(equipmentsTable, 80.0);
		AnchorPane.setRightAnchor(equipmentsTable, 80.0);
		AnchorPane.setBottomAnchor(equipmentsTable, 105.0);
		//dateOfInstLabel
		AnchorPane.setLeftAnchor(dateOfInstLabel, 70.0);
		AnchorPane.setBottomAnchor(dateOfInstLabel, 75.0);
		//dateOfSelectedEquipment
		AnchorPane.setLeftAnchor(dateOfSelectedEquipment, 215.0);
		AnchorPane.setBottomAnchor(dateOfSelectedEquipment, 73.0);
		//update
		AnchorPane.setLeftAnchor(update, 27.0);
		AnchorPane.setBottomAnchor(update, 22.0);
		//addEquipment
		AnchorPane.setRightAnchor(addEquipment, 27.0);
		AnchorPane.setBottomAnchor(addEquipment, 22.0);
		//deleteEquipment
		AnchorPane.setRightAnchor(deleteEquipment, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(deleteEquipment, 22.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, search , settings , underline ,
				equipmentsLabel , equipmentsTable , addEquipment , deleteEquipment , update ,
				dateOfInstLabel , dateOfSelectedEquipment);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void prepareMembershipsTable() {
		loadAllPotentialSearchElements();
		
		String membershipName = "";
		String membershipDuration = "";
		String membershipAmount = "";
		String equipAvailability = "";
		String equipDOI = "";
		
		try {
			membershipsTable.getItems().clear();
			PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_MEMBERSHIPS`;");
		    ResultSet rs = command.executeQuery();
		    
		    while(rs.next()) {
		    	membershipName = rs.getString(1).toString();
		    	membershipDuration = rs.getString(2).toString();
		    	membershipAmount = rs.getString(3).toString();
		    	
		    	membershipsTable.getItems().add(new MembershipsListClass(membershipName , membershipDuration , membershipAmount));
		    }
		}catch(Exception e) {
			//create GYM_MEMBERSHIPS
			try {
				PreparedStatement createCommand = connection.prepareStatement("CREATE TABLE `studentdatabase`.GYM_MEMBERSHIPS(Name VARCHAR(20) PRIMARY KEY, Duration VARCHAR(30) , Amount INT NOT NULL, GymKit VARCHAR(16) NOT NULL, About VARCHAR(300) NOT NULL);");
			    createCommand.execute();
			}catch(Exception f) {}
			
		}
	}
	
	TextField newMembershipName = new TextField();
	TextField newMembershipAmount = new TextField();
	TextField newMembershipDuration = new TextField();
	TextArea newMembershipAbout = new TextArea();
	Label amountLabel = new Label("Amount:");
	Label durationLabel = new Label("Duration:");
	Label gymKitLabel = new Label("Gym Kit:");
	Label invalidMembershipWizard = new Label("Enter correct information");
	
	public void resetAddNewembershipScreen() {
		invalidMembershipWizard.setVisible(false);
		invalidMembershipWizard.setStyle("-fx-font-family: 'Marlett';" + "-fx-font-size: 12px;" + "-fx-text-fill: red;");
		
		newMembershipAbout.setWrapText(true);
		newGymKit.getSelectionModel().selectLast();	//Not Included
		
		newMembershipName.setPromptText("Membership Name");
		newMembershipAmount.setPromptText("Enter in Rs.");
		newMembershipDuration.setPromptText("Enter in months");
		newMembershipAbout.setPromptText("About the membership in 300 words");
		
		amountLabel.setStyle("-fx-font-size: 15px;");
		durationLabel.setStyle("-fx-font-size: 15px;");
		gymKitLabel.setStyle("-fx-font-size: 15px;");
		
		newMembershipName.setStyle(ref.getStyle());
		newMembershipAmount.setStyle(ref.getStyle());
		newMembershipDuration.setStyle(ref.getStyle());
		newMembershipAbout.setStyle(ref.getStyle());
		
		newMembershipName.setText("");
		newMembershipAmount.setText("");
		newMembershipDuration.setText("");
		newMembershipAbout.setText("");
	}
	
	public void addNewMembershipEntry(Stage s) {
		String name = newMembershipName.getText();
		String amount = newMembershipAmount.getText();
		String duration = newMembershipDuration.getText();
		String about = newMembershipAbout.getText();
		String gymKit = newGymKit.getSelectionModel().getSelectedItem();
		
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `studentdatabase`.`gym_memberships` (`Name`, `Duration`, `Amount`, `GymKit`, `About`) VALUES ('" + name + "' , '" + duration + "' , '" + amount + "' , '" + gymKit + "' , '" + about + "');");
			ps.execute();
			Alert entryAdded = new Alert(AlertType.INFORMATION);
			entryAdded.setTitle("Successful");
			entryAdded.setHeaderText("New Membership Successfully Added");
			entryAdded.setContentText("New Membership has been successfully added to the Gym database.\n\n\n");
			Optional o = entryAdded.showAndWait();
			if(o.get() == ButtonType.OK) {
				membershipsScreen(s);
			}
		}catch(Exception e) {
			invalidMembershipWizard.setVisible(true);
			Alert entryNotAdded = new Alert(AlertType.ERROR);
			entryNotAdded.setTitle("Failed");
			entryNotAdded.setHeaderText("New Membership Not Added");
			entryNotAdded.setContentText("New Membership was not added. Please enter valid data or "
					+ "change the Membership name and try again.\n\n\n");
			entryNotAdded.show();
		}
		
	}
	
	public void addNewMembershipScreen(Stage s) {
		resetAddNewembershipScreen();
		
		Label newMembershipLabel = new Label(" Add New Membership");
		newMembershipLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		
		Button addMembership = new Button("Add");
		Button resetMembership = new Button("Reset");
		
		addMembership.setPrefSize(100.0, 32.0);
		resetMembership.setPrefSize(100.0, 32.0);
		
		addMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		resetMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		addMembership.setOnMouseExited(e -> addMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		resetMembership.setOnMouseExited(e -> resetMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		addMembership.setOnMouseReleased(e -> addMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		resetMembership.setOnMouseReleased(e -> resetMembership.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		addMembership.setOnMouseEntered(e -> addMembership.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		resetMembership.setOnMouseEntered(e -> resetMembership.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		addMembership.setOnMousePressed(e -> addMembership.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		resetMembership.setOnMousePressed(e -> resetMembership.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		//controls
		back.setOnAction(e -> membershipsScreen(s));
		resetMembership.setOnAction(e -> resetAddNewembershipScreen());
		addMembership.setOnAction(e -> {
			if(newMembershipName.getText().equals("") || newMembershipAmount.getText().equals("") || newMembershipDuration.getText().equals("") || newMembershipAbout.getText().equals("")) {
				if(newMembershipName.getText().equals("") == true) {
					newMembershipName.setStyle("-fx-border-color: red;");
					invalidMembershipWizard.setVisible(true);
				} else {
					newMembershipName.setStyle(ref.getStyle());
				}
				if(newMembershipAmount.getText().equals("") == true) {
					newMembershipAmount.setStyle("-fx-border-color: red;");
					invalidMembershipWizard.setVisible(true);
				} else {
					newMembershipAmount.setStyle(ref.getStyle());
				}
				if(newMembershipDuration.getText().equals("") == true) {
					newMembershipDuration.setStyle("-fx-border-color: red;");
					invalidMembershipWizard.setVisible(true);
				} else {
						newMembershipDuration.setStyle(ref.getStyle());
					}
				if(newMembershipAbout.getText().equals("") == true) {
					newMembershipAbout.setStyle("-fx-border-color: red;");
					invalidMembershipWizard.setVisible(true);
				} else {
					newMembershipAbout.setStyle(ref.getStyle());
				}
			}else {
				invalidMembershipWizard.setVisible(false);
				newMembershipName.setStyle(ref.getStyle());
				newMembershipAmount.setStyle(ref.getStyle());
				newMembershipDuration.setStyle(ref.getStyle());
				newMembershipAbout.setStyle(ref.getStyle());
				Alert yesNo = new Alert(AlertType.CONFIRMATION);
				yesNo.setTitle("Confirmation");
				yesNo.setHeaderText("Add New Membership");
				yesNo.setContentText("To add New Membership press OK else press Cancel.\n\n\n");
				Optional<ButtonType> op = yesNo.showAndWait();
				if(op.get() == ButtonType.OK) {
					addNewMembershipEntry(s);
				}
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//newMembershipLabel
		AnchorPane.setTopAnchor(newMembershipLabel, 93.0);
		AnchorPane.setLeftAnchor(newMembershipLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//addMembership
		AnchorPane.setRightAnchor(addMembership, 27.0);
		AnchorPane.setBottomAnchor(addMembership, 22.0);
		//resetMembership
		AnchorPane.setRightAnchor(resetMembership, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(resetMembership, 22.0);
		//invalidMembershipWizard
		AnchorPane.setRightAnchor(invalidMembershipWizard, 29.0);
		AnchorPane.setBottomAnchor(invalidMembershipWizard, 62.0);
		//newMembershipName
		AnchorPane.setTopAnchor(newMembershipName, 200.0);
		AnchorPane.setLeftAnchor(newMembershipName, 190.0);
		AnchorPane.setRightAnchor(newMembershipName, 190.0);
		//amountLabel
		AnchorPane.setTopAnchor(amountLabel, 240.0);
		AnchorPane.setLeftAnchor(amountLabel, 190.0);
		//newMembershipAmount
		AnchorPane.setTopAnchor(newMembershipAmount, 239.0);
		AnchorPane.setLeftAnchor(newMembershipAmount, 260.0);
		AnchorPane.setRightAnchor(newMembershipAmount, 490.0);
		//durationLabel
		AnchorPane.setTopAnchor(durationLabel, 240.0);
		AnchorPane.setLeftAnchor(durationLabel, 440.0);
		//newMembershipDuration
		AnchorPane.setTopAnchor(newMembershipDuration, 239.0);
		AnchorPane.setLeftAnchor(newMembershipDuration, 520.0);
		AnchorPane.setRightAnchor(newMembershipDuration, 190.0);
		//newMembershipAbout
		AnchorPane.setTopAnchor(newMembershipAbout, 280.0);
		AnchorPane.setLeftAnchor(newMembershipAbout, 190.0);
		AnchorPane.setRightAnchor(newMembershipAbout, 190.0);
		AnchorPane.setBottomAnchor(newMembershipAbout, 160.0);
		//gymKitLabel
		AnchorPane.setTopAnchor(gymKitLabel, 400.0);
		AnchorPane.setLeftAnchor(gymKitLabel, 190.0);
		//newGymKit
		AnchorPane.setTopAnchor(newGymKit, 399.0);
		AnchorPane.setLeftAnchor(newGymKit, 270.0);
		AnchorPane.setRightAnchor(newGymKit, 470.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, underline , newMembershipLabel , addMembership ,
				resetMembership , newMembershipName , newMembershipAmount , newMembershipDuration ,
				newMembershipAbout , amountLabel , durationLabel , gymKitLabel , invalidMembershipWizard ,
				newGymKit);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void deleteMembershipEntry(Stage s) {
		if(membershipsTable.getSelectionModel().getSelectedItem() != null) {
			MembershipsListClass selectedRow = (MembershipsListClass) membershipsTable.getSelectionModel().getSelectedItem();
			String msName = selectedRow.getMembershipName();
			Alert yn = new Alert(AlertType.CONFIRMATION);
			yn.setTitle("Delete Confirmation");
			yn.setHeaderText("Delete Selected Membership");
			yn.setContentText("Selected Membership will be deleted. Once action done cannot be undone. "
					+ "Press OK to delete else press Cancel.\n\n\n");
			Optional<ButtonType> opx = yn.showAndWait();
			if(opx.get() == ButtonType.OK) {
				try {
					PreparedStatement ps = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_memberships` WHERE (`Name` = '" + msName + "');");
					ps.execute();
					prepareMembershipsTable();	//refreshing table
					Alert entryDeleted = new Alert(AlertType.INFORMATION);
					entryDeleted.setTitle("Delete Successful");
					entryDeleted.setHeaderText("Selected Membership Deleted");
					entryDeleted.setContentText("Selected Membership has been successfully removed "
							+ "from Gym Records. Press OK to continue.\n\n\n");
					Optional con = entryDeleted.showAndWait();
					if(con.get() == ButtonType.OK) {
						membershipsScreen(s);
					}
				}catch(Exception e) {
					Alert err = new Alert(AlertType.ERROR);
					err.setTitle("Cannot be Deleted");
					err.setHeaderText("Membership Not Deleted");
					err.setContentText("Please note that gym members and trainers are still using this "
							+ "membership, so deletion of this membership is not possible. Please modify "
							+ "the member(s) and trainers(s) who are enrolled with this membership "
							+ "before deleting this membership.\n\n\n");
					err.show();
				}
			}
		}
		
	}
	
	Label durationViewEditMembershipLabel = new Label("Duration:");
	Label amountViewEditMembershipLabel = new Label("Amount:        Rs.");
	Label gymKitViewEditMembershipLabel = new Label("Gym Kit:");
	TextField savedMembershipDuration = new TextField();
	TextField savedMembershipAmount = new TextField();
	TextArea savedMembershipAbout = new TextArea();
	ComboBox<String> savedMembershipGymKit = new ComboBox<String>(kitOptionsAvailable);
	
	public void loadDataViewEditMembershipScreen(String selectedMembershipName) {
		savedMembershipAbout.setWrapText(true);
		savedMembershipDuration.setText("");
		savedMembershipAmount.setText("");
		savedMembershipAbout.setText("");
		
		savedMembershipDuration.setPromptText("Enter in months");
		savedMembershipAmount.setPromptText("Enter in Rs.");
		savedMembershipAbout.setPromptText("About Membership");
		savedMembershipGymKit.getSelectionModel().selectLast();
		
		durationViewEditMembershipLabel.setStyle("-fx-font-size: 15px;");
		amountViewEditMembershipLabel.setStyle("-fx-font-size: 15px;");
		gymKitViewEditMembershipLabel.setStyle("-fx-font-size: 15px;");
		
		try {
			PreparedStatement loadRow = connection.prepareStatement("select * from `studentdatabase`.`GYM_MEMBERSHIPS` WHERE Name = '" + selectedMembershipName + "';");  
		    ResultSet rs = loadRow.executeQuery();
		    rs.next();
		    savedMembershipDuration.setText(rs.getString(2) + "");
		    savedMembershipAmount.setText(rs.getInt(3) + "");
		    if(rs.getString(4).toString().equalsIgnoreCase("Included") == true) {		//gymKit
		    	savedMembershipGymKit.getSelectionModel().selectFirst();
		    }else {
		    	savedMembershipGymKit.getSelectionModel().selectLast();
		    }
		    savedMembershipAbout.setText("" + rs.getString(5).toString());
		}catch(Exception e) {}
	}
	
	public void modifyMembershipChanges(Stage s , String selectedMembershipName) {
		try {
			//update duration
			PreparedStatement updateDuration = connection.prepareStatement("UPDATE `studentdatabase`.`gym_memberships` SET `Duration` = '" + savedMembershipDuration.getText().trim() + "' WHERE (`Name` = '" + selectedMembershipName + "');");
			updateDuration.execute();
			//update amount
			PreparedStatement updateAmount = connection.prepareStatement("UPDATE `studentdatabase`.`gym_memberships` SET `Amount` = '" + savedMembershipAmount.getText().trim() + "' WHERE (`Name` = '" + selectedMembershipName + "');");
			updateAmount.execute();
			//update about
			PreparedStatement updateAbout = connection.prepareStatement("UPDATE `studentdatabase`.`gym_memberships` SET `About` = '" + savedMembershipAbout.getText().trim() + "' WHERE (`Name` = '" + selectedMembershipName + "');");
			updateAbout.execute();
			//update gymKit
			PreparedStatement updateGymKit = connection.prepareStatement("UPDATE `studentdatabase`.`gym_memberships` SET `GymKit` = '" + savedMembershipGymKit.getSelectionModel().getSelectedItem().toString() + "' WHERE (`Name` = '" + selectedMembershipName + "');");
			updateGymKit.execute();
			Alert updatedMembershipSuccessfully = new Alert(AlertType.INFORMATION);
			updatedMembershipSuccessfully.setTitle("Modification Successful");
			updatedMembershipSuccessfully.setHeaderText("Membership Updated");
			updatedMembershipSuccessfully.setContentText("Selected Membership has been successfully modified "
					+ "with new data. Press OK to continue.\n\n\n");
			Optional con = updatedMembershipSuccessfully.showAndWait();
			if(con.get() == ButtonType.OK) {
				membershipsScreen(s);
			}
		}
		catch(Exception e) {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Failed");
			error.setHeaderText("Membership Modification Failed");
			error.setContentText("Membership was not updated with new data. Please enter valid data "
					+ "and try again.\n\n\n");
			error.show();
		}
	}
	
	public void viewEditMembershipScreen(Stage s , String selectedMembershipName) {
		loadDataViewEditMembershipScreen(selectedMembershipName);
		
		Button saveChanges = new Button("Save Changes");
		
		Label membershipNameLabel = new Label();
		membershipNameLabel.setText(" " + selectedMembershipName);
		membershipNameLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		
		saveChanges.setPrefSize(130.0, 32.0);
		
		saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;");
		saveChanges.setOnMouseExited(e -> saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		saveChanges.setOnMouseReleased(e -> saveChanges.setStyle("-fx-background-color: '" + currentDarkColor + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 12.7px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: normal;"));
		saveChanges.setOnMouseEntered(e -> saveChanges.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 12.9px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.5px;"));
		saveChanges.setOnMousePressed(e -> saveChanges.setStyle("-fx-background-color: '" + currentBackground + "';" + "-fx-text-fill: '" + currentDarkColor + "';" + "-fx-font-size: 13px;" + "-fx-border-color: '" + currentDarkColor + "';" + "-fx-font-weight: bold;" + "-fx-border-width: 1.7px;"));
		
		//controls
		back.setOnAction(e -> membershipsScreen(s));
		saveChanges.setOnAction(e -> modifyMembershipChanges(s , selectedMembershipName));
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membershipNameLabel
		AnchorPane.setTopAnchor(membershipNameLabel, 93.0);
		AnchorPane.setLeftAnchor(membershipNameLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//saveChanges
		AnchorPane.setRightAnchor(saveChanges, 27.0);
		AnchorPane.setBottomAnchor(saveChanges, 22.0);
		//durationViewEditMembershipLabel
		AnchorPane.setTopAnchor(durationViewEditMembershipLabel, 180.0);
		AnchorPane.setLeftAnchor(durationViewEditMembershipLabel, 180.0 - 20.0);
		//savedMembershipDuration
		AnchorPane.setTopAnchor(savedMembershipDuration, 179.0);
		AnchorPane.setLeftAnchor(savedMembershipDuration, 290.0 - 30.0);
		AnchorPane.setRightAnchor(savedMembershipDuration, 450.0 + 30.0);
		//amountViewEditMembershipLabel
		AnchorPane.setTopAnchor(amountViewEditMembershipLabel, 180.0);
		AnchorPane.setLeftAnchor(amountViewEditMembershipLabel, 470.0);
		//savedMembershipAmount
		AnchorPane.setTopAnchor(savedMembershipAmount, 179.0);
		AnchorPane.setLeftAnchor(savedMembershipAmount, 590.0);
		AnchorPane.setRightAnchor(savedMembershipAmount, 180.0 - 20.0);
		//savedMembershipAbout
		AnchorPane.setTopAnchor(savedMembershipAbout, 234.0);
		AnchorPane.setLeftAnchor(savedMembershipAbout, 180.0);
		AnchorPane.setRightAnchor(savedMembershipAbout, 180.0);
		AnchorPane.setBottomAnchor(savedMembershipAbout, 150.0);
		//gymKitViewEditMembershipLabel
		AnchorPane.setTopAnchor(gymKitViewEditMembershipLabel, 415.0);
		AnchorPane.setLeftAnchor(gymKitViewEditMembershipLabel, 180.0 - 20.0);
		//savedMembershipGymKit
		AnchorPane.setTopAnchor(savedMembershipGymKit, 414.0);
		AnchorPane.setLeftAnchor(savedMembershipGymKit, 250.0);
		AnchorPane.setRightAnchor(savedMembershipGymKit, 500.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back , underline , membershipNameLabel , saveChanges ,
				durationViewEditMembershipLabel , amountViewEditMembershipLabel ,
				gymKitViewEditMembershipLabel , savedMembershipDuration , savedMembershipAmount ,
				savedMembershipAbout , savedMembershipGymKit);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void membershipsScreen(Stage s) {
		prepareMembershipsTable();
		
		loadAllPotentialSearchElements();
		
		Button addMembership = new Button("+");
		Button deleteMembership = new Button("-");
		Button goMembership = new Button(">");
		
		Label membershipsLabel = new Label(" Available Membership(s) List");
		membershipsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		
		//controls
		back.setOnAction(e -> homeScreen(s));
		settings.setOnAction(e -> settingsScreen(s));
		addMembership.setOnAction(e -> addNewMembershipScreen(s));
		deleteMembership.setOnAction(e -> deleteMembershipEntry(s));
		goMembership.setOnAction(e -> {
			if(membershipsTable.getSelectionModel().getSelectedItem() != null) {
				MembershipsListClass selectedRow = (MembershipsListClass) membershipsTable.getSelectionModel().getSelectedItem();
				String msName = selectedRow.getMembershipName();
				viewEditMembershipScreen(s , msName);
			}
		});
		
		search.setPrefWidth(250.0);
		
		addMembership.setPrefSize(100.0, 32.0);
		deleteMembership.setPrefSize(100.0, 32.0);
		goMembership.setPrefSize(100.0, 32.0);
		
		String addN = "-fx-text-fill: white;" + "-fx-background-color: '#00ff00';" +
		        	"-fx-font-weight: bold;" +
		        	"-fx-font-family: 'Arial';" +
		        	"-fx-font-size: 15px;";
		String addH = "-fx-text-fill: white;" + "-fx-background-color: '#00b300';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
		String addP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#008000';" +
	       	    	"-fx-font-weight: bold;" +
	       	    	"-fx-font-family: 'Arial';" +
	       	    	"-fx-font-size: 16px;";
	
		addMembership.setStyle(addN);
		addMembership.setOnMouseMoved(e-> addMembership.setStyle(addH));		//hover
		addMembership.setOnMouseReleased(e-> addMembership.setStyle(addN));	//normal
		addMembership.setOnMousePressed(e-> addMembership.setStyle(addP));	//pressed
		addMembership.setOnMouseExited(e-> addMembership.setStyle(addN));		//normal
		
		String delN = "-fx-text-fill: white;" + "-fx-background-color: '#ff1a1a';" +
	    			"-fx-font-weight: bold;" +
	    			"-fx-font-family: 'Arial';" +
	    			"-fx-font-size: 15px;";
		String delH = "-fx-text-fill: white;" + "-fx-background-color: '#e60000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String delP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#cc0000';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		deleteMembership.setStyle(delN);
		deleteMembership.setOnMouseMoved(e-> deleteMembership.setStyle(delH));		//hover
		deleteMembership.setOnMouseReleased(e-> deleteMembership.setStyle(delN));		//normal
		deleteMembership.setOnMousePressed(e-> deleteMembership.setStyle(delP));		//pressed
		deleteMembership.setOnMouseExited(e-> deleteMembership.setStyle(delN));		//normal
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 15px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '#0066cc';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#004d99';" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Arial';" +
					"-fx-font-size: 16px;";
		
		goMembership.setStyle(goN);
		goMembership.setOnMouseMoved(e-> goMembership.setStyle(goH));			//hover
		goMembership.setOnMouseReleased(e-> goMembership.setStyle(goN));		//normal
		goMembership.setOnMousePressed(e-> goMembership.setStyle(goP));		//pressed
		goMembership.setOnMouseExited(e-> goMembership.setStyle(goN));		//normal
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//membershipsLabel
		AnchorPane.setTopAnchor(membershipsLabel, 93.0);
		AnchorPane.setLeftAnchor(membershipsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//addMembership
		AnchorPane.setRightAnchor(addMembership, 127.0 + 17.0 + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(addMembership, 22.0);
		//deleteMembership
		AnchorPane.setRightAnchor(deleteMembership, 27.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(deleteMembership, 22.0);
		//goMembership
		AnchorPane.setRightAnchor(goMembership, 27.0);
		AnchorPane.setBottomAnchor(goMembership, 22.0);
		//membershipsTable
		AnchorPane.setTopAnchor(membershipsTable, 160.0);
		AnchorPane.setLeftAnchor(membershipsTable, 80.0);
		AnchorPane.setRightAnchor(membershipsTable, 80.0);
		AnchorPane.setBottomAnchor(membershipsTable, 105.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, search  , settings , underline ,
				membershipsLabel , addMembership , deleteMembership , goMembership , membershipsTable);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void alterFeeTableColumns(boolean isShowAdv) {
		if(isShowAdv == true) {
			feeTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
			feeColumn1.setCellValueFactory(new PropertyValueFactory<>("ID"));
			feeColumn1.setMinWidth(80.0);
			feeColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
			feeColumn2.setMinWidth(140.0);
			feeColumn3.setCellValueFactory(new PropertyValueFactory<>("membership"));
			feeColumn3.setMinWidth(110.0);
			feeColumn4.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
			feeColumn4.setMinWidth(115.0);
			feeColumn5.setCellValueFactory(new PropertyValueFactory<>("pendingAmount"));
			feeColumn5.setMinWidth(115.0);
			feeColumn6.setCellValueFactory(new PropertyValueFactory<>("receiptNumber"));
			feeColumn6.setMinWidth(130.0);
			feeColumn7.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
			feeColumn7.setMinWidth(130.0);
			feeColumn8.setCellValueFactory(new PropertyValueFactory<>("dateOfPayment"));
			feeColumn8.setMinWidth(130.0);
			feeColumn9.setCellValueFactory(new PropertyValueFactory<>("amountPayed"));
			feeColumn9.setMinWidth(115.0);
			
			feeTable.getColumns().clear();
			feeTable.getColumns().addAll(feeColumn1 , feeColumn2 , feeColumn3 , feeColumn4 , feeColumn5 , feeColumn6 , feeColumn9 , feeColumn7 , feeColumn8);
			
		}else {
			feeTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
			feeColumn1.setCellValueFactory(new PropertyValueFactory<>("ID"));
			feeColumn1.setMinWidth(105.0);
			feeColumn1.setMaxWidth(105.0);
			feeColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
			feeColumn2.setMinWidth(190.0);
			feeColumn2.setMaxWidth(190.0);
			feeColumn3.setCellValueFactory(new PropertyValueFactory<>("membership"));
			feeColumn3.setMinWidth(180.0);
			feeColumn3.setMaxWidth(180.0);
			feeColumn4.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
			feeColumn4.setMinWidth(155.0);
			feeColumn4.setMaxWidth(155.0);
			feeColumn5.setCellValueFactory(new PropertyValueFactory<>("pendingAmount"));
			feeColumn5.setMinWidth(155.0);
			feeColumn5.setMaxWidth(155.0);
			
			feeTable.getColumns().clear();
			feeTable.getColumns().addAll(feeColumn1 , feeColumn2 , feeColumn3 , feeColumn4 , feeColumn5);
		}
	}
	
	public void prepareFeeTableAllID() {
		loadAllPotentialSearchElements();
		
		FeeTable ft = new FeeTable();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.gym_fee;");
			ps.executeQuery();
		}catch(Exception e) {
			//create table
			ft.createFeeTable(connection);
		}
		//load
		String className = "";
		try {
			feeTable.getItems().clear();
			PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_FEE`;");
		    ResultSet rs = command.executeQuery();
		    while(rs.next()){  
		    	String id = rs.getString(1);
		    	String name = rs.getString(2);
		    	String membership = rs.getString(4);
		    	String totalAmount = rs.getString(3);
		    	String pendingAmount = rs.getString(8);
		    	String receiptNumber = rs.getString(5);
		    	String transactionID = rs.getString(6);
		    	String dateOfPayment = rs.getString(7);
		    	String amountPayed = rs.getString(9);
		    	
		    	feeTable.getItems().add(new FeeListClass(id , name , membership , totalAmount , pendingAmount , receiptNumber , transactionID , dateOfPayment , amountPayed));
		    }
		}catch(Exception e) {}
	}
	
	public void refreshPrepareFeeTable() {
		loadAllPotentialSearchElements();
		
		FeeTable ft = new FeeTable();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_fee`;");
			ps.executeQuery();
		}catch(Exception e) {
			//create table
			ft.createFeeTable(connection);
		}
		//load
		String className = "";
		try {
			feeTable.getItems().clear();
			PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`GYM_FEE` GROUP BY `ID`;");
		    ResultSet rs = command.executeQuery();
		    while(rs.next()){  
		    	String id = rs.getString(1);
		    	String name = rs.getString(2);
		    	String totalAmount = rs.getString(3);
		    	String membership = rs.getString(4);
		    	String receiptNumber = rs.getString(5);
		    	String transactionID = rs.getString(6);
		    	String dateOfPayment = rs.getString(7);
		    	String pendingAmount = rs.getString(8);
		    	String amountPayed = rs.getString(9);
		    	
		    	feeTable.getItems().add(new FeeListClass(id , name , membership , totalAmount , pendingAmount , receiptNumber , transactionID , dateOfPayment , amountPayed));
		    }
		}catch(Exception e) {}
	}
	
	public void prepareFeeTable() {
		loadAllPotentialSearchElements();
		
		alterFeeTableColumns(false);
		showAdvFeeTable.setText("Show Adv.");
		showAdvFeeTable.setSelected(false);
		showAllTransactions.setSelected(false);
		
		FeeTable ft = new FeeTable();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_fee`;");
			ps.executeQuery();
		}catch(Exception e) {
			//create table
			ft.createFeeTable(connection);
		}
		//load
		String className = "";
		try {
			feeTable.getItems().clear();
			PreparedStatement command = connection.prepareStatement("SELECT * FROM `studentdatabase`.`gym_fee` GROUP BY `ID`;");
		    ResultSet rs = command.executeQuery();
		    while(rs.next()){  
		    	String id = rs.getString(1);
		    	String name = rs.getString(2);
		    	String totalAmount = rs.getString(3);
		    	String membership = rs.getString(4);
		    	String receiptNumber = rs.getString(5);
		    	String transactionID = rs.getString(6);
		    	String dateOfPayment = rs.getString(7);
		    	String pendingAmount = rs.getString(8);
		    	String amountPayed = rs.getString(9);
		    	
		    	feeTable.getItems().add(new FeeListClass(id , name , membership , totalAmount , pendingAmount , receiptNumber , transactionID , dateOfPayment , amountPayed));
		    }
		}catch(Exception e) {}
	}
	
	ToggleButton showAdvFeeTable = new ToggleButton("Show Adv.");
	CheckBox showAllTransactions = new CheckBox("Display Complete History");
	
	public void clearFeeMemberCompleteEntry(String memberID) {
		Alert isSure = new Alert(AlertType.CONFIRMATION);
		isSure.setTitle("Confirmation");
		isSure.setHeaderText("Are you sure?");
		isSure.setContentText("All transaction of " + memberID + " will be deleted. Press OK to delete else "
				+ "press Cancel.\n\n\n");
		Optional<ButtonType> op = isSure.showAndWait();
		if(op.get() == ButtonType.OK) {
			try {
				PreparedStatement p = connection.prepareStatement("DELETE FROM `studentdatabase`.`gym_fee` WHERE `ID` = '" + memberID + "';");
				p.execute();
				Alert d = new Alert(AlertType.INFORMATION);
				d.setTitle("Successful");
				d.setHeaderText("All Transaction(s) Removed");
				d.setContentText("All transactions of " + memberID + " has been deleted. "
						+ "Press OK to continue.\n\n\n");
				d.show();
			}catch(Exception e) {
				Alert d = new Alert(AlertType.ERROR);
				d.setTitle("Failed");
				d.setHeaderText("Transaction(s) Not Removed");
				d.setContentText("An error occured. Transactions of " + memberID +
						" was not deleted.\n\n\n");
				d.show();
			}
		}
	}
	
	public void viaAlertMemberIDDialog(Stage s) {		//load memberNames from Database
		Dictionary options = new Hashtable();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT `ID` , `Name` FROM `studentdatabase`.`gym_members`;");
			ResultSet res = ps.executeQuery();
			String id = "";
			String name = "";
			while(res.next()) {
				id = res.getString(1);
				name = res.getString(2);
				options.put(id, name);
			}
		}catch(Exception e) {}
		
		ChoiceDialog<String> d = new ChoiceDialog<String>("Select");
		
		d.setTitle("Selection");
		d.setHeaderText("Choose a Member");
		
		Enumeration j = options.keys();
		for(Enumeration i = options.elements() ; i.hasMoreElements() ;) {
			d.getItems().add(i.nextElement() + "\t( " + j.nextElement() + " )");
		}
		
		Optional<String> selected = d.showAndWait();
		if(selected.isPresent() == true && d.getSelectedItem().toString().equalsIgnoreCase("Select") == false) {
			String getData = d.getSelectedItem().toString().substring(d.getSelectedItem().toString().indexOf("(") + 1, d.getSelectedItem().toString().lastIndexOf(")") - 1).trim();
			feeDetailsOfMemberScreen(s , getData , false);
		}
	}
	
	public void feeScreen(Stage s) {
		prepareFeeTable();
		
		loadAllPotentialSearchElements();
		
		Button addMembership = new Button("New Transaction");
		Button deleteMembership = new Button("Remove");
		Button goMembership = new Button("View Details");
		
		Label membershipsLabel = new Label(" Fee Details");
		membershipsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "            ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		
		showAdvFeeTable.setPrefSize(120.0, 20.0);
		addMembership.setPrefSize(150.0, 32.0);
		deleteMembership.setPrefSize(130.0, 32.0);
		goMembership.setPrefSize(130.0, 32.0);
		
		String goN = "-fx-text-fill: white;" + "-fx-background-color: '#1a8cff';" +
				"-fx-font-weight: normal;" +
				"-fx-font-family: 'Arial';" +
				"-fx-font-size: 13px;";
		String goH = "-fx-text-fill: white;" + "-fx-background-color: '#0066cc';" +
				"-fx-font-weight: normal;" +
				"-fx-font-family: 'Arial';" +
				"-fx-font-size: 14px;";
		String goP = "-fx-text-fill: '#e6e6e6';" + "-fx-background-color: '#004d99';" +
				"-fx-font-weight: bold;" +
				"-fx-font-family: 'Arial';" +
				"-fx-font-size: 14px;";
	
		addMembership.setStyle(goN);
		addMembership.setOnMouseMoved(e-> addMembership.setStyle(goH));
		addMembership.setOnMouseReleased(e-> addMembership.setStyle(goN));
		addMembership.setOnMousePressed(e-> addMembership.setStyle(goP));
		addMembership.setOnMouseExited(e-> addMembership.setStyle(goN));
		
		deleteMembership.setStyle(goN);
		deleteMembership.setOnMouseMoved(e-> deleteMembership.setStyle(goH));		//hover
		deleteMembership.setOnMouseReleased(e-> deleteMembership.setStyle(goN));		//normal
		deleteMembership.setOnMousePressed(e-> deleteMembership.setStyle(goP));		//pressed
		deleteMembership.setOnMouseExited(e-> deleteMembership.setStyle(goN));		//normal
		
		goMembership.setStyle(goN);
		goMembership.setOnMouseMoved(e-> goMembership.setStyle(goH));			//hover
		goMembership.setOnMouseReleased(e-> goMembership.setStyle(goN));		//normal
		goMembership.setOnMousePressed(e-> goMembership.setStyle(goP));		//pressed
		goMembership.setOnMouseExited(e-> goMembership.setStyle(goN));		//normal
		
		//controls
		back.setOnAction(e -> homeScreen(s));
		settings.setOnAction(e -> settingsScreen(s));
		showAdvFeeTable.setOnAction(e -> {
			if(showAdvFeeTable.isSelected() == true) {
				alterFeeTableColumns(true);
				showAdvFeeTable.setText("Hide Adv.");
			}else {
				alterFeeTableColumns(false);
				showAdvFeeTable.setText("Show Adv.");
			}
		});
		showAllTransactions.setOnAction(e -> {
			if(showAllTransactions.isSelected() == true) {
				prepareFeeTableAllID();
			}else {
				prepareFeeTable();
			}
		});
		deleteMembership.setOnAction(e -> {
			if(feeTable.getSelectionModel().getSelectedItem() != null) {
				FeeListClass entry = (FeeListClass) feeTable.getSelectionModel().getSelectedItem();
				String midX = entry.getID();
				String receiptX = entry.getReceiptNumber();
				if(showAllTransactions.isSelected() == true) {
					clearFeeEntry(midX , receiptX);
				}else {
					clearFeeMemberCompleteEntry(midX);
				}
				if(showAllTransactions.isSelected() == true) {
					prepareFeeTableAllID();
				}else {
					refreshPrepareFeeTable();
				}
			}
		});
		goMembership.setOnAction(e -> {
			if(feeTable.getSelectionModel().getSelectedItem() != null) {
				FeeListClass entry = (FeeListClass) feeTable.getSelectionModel().getSelectedItem();
				String midX = entry.getID();
				feeDetailsOfMemberScreen(s , midX , false);
			}
		});
		addMembership.setOnAction(e ->{
			viaAlertMemberIDDialog(s);
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//membershipsLabel
		AnchorPane.setTopAnchor(membershipsLabel, 93.0 - 20.0);
		AnchorPane.setLeftAnchor(membershipsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 118.0 - 20.0);
		AnchorPane.setRightAnchor(underline, 35.0);
		AnchorPane.setLeftAnchor(underline, 35.0);
		//addMembership
		AnchorPane.setRightAnchor(addMembership, 127.0 + 17.0 + 100.0 + 17.0 + 60.0);
		AnchorPane.setBottomAnchor(addMembership, 22.0);
		//deleteMembership
		AnchorPane.setRightAnchor(deleteMembership, 27.0  + 100.0 + 17.0 + 30.0);
		AnchorPane.setBottomAnchor(deleteMembership, 22.0);
		//goMembership
		AnchorPane.setRightAnchor(goMembership, 27.0);
		AnchorPane.setBottomAnchor(goMembership, 22.0);
		//feeTable
		AnchorPane.setTopAnchor(feeTable, 160.0 - 20.0);
		AnchorPane.setLeftAnchor(feeTable, 50.0);
		AnchorPane.setRightAnchor(feeTable, 50.0);
		AnchorPane.setBottomAnchor(feeTable, 120.0);
		//showAdvFeeTable
		AnchorPane.setRightAnchor(showAdvFeeTable, 50.0);
		AnchorPane.setBottomAnchor(showAdvFeeTable, 85.0);
		//showAllTransactions
		AnchorPane.setLeftAnchor(showAllTransactions, 55.0);
		AnchorPane.setBottomAnchor(showAllTransactions, 95.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , back, search , settings , underline ,
				membershipsLabel , addMembership , deleteMembership , goMembership , feeTable ,
				showAdvFeeTable , showAllTransactions);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void viaAlertMemberIDDialogToRating(Stage s) {	//load memberNames from Database
		Dictionary options = new Hashtable();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT `ID` , `Name` FROM `studentdatabase`.`gym_trainers`;");
			ResultSet res = ps.executeQuery();
			String id = "";
			String name = "";
			while(res.next()) {
				id = res.getString(1);
				name = res.getString(2);
				options.put(id, name);
			}
		}catch(Exception e) {}
		
		ChoiceDialog<String> d = new ChoiceDialog<String>("Select");
		
		d.setTitle("See Rating");
		d.setHeaderText("Who are you?");
		
		Enumeration j = options.keys();
		for(Enumeration i = options.elements() ; i.hasMoreElements() ;) {
			d.getItems().add(i.nextElement() + " ( " + j.nextElement() + " )");
		}
		
		Optional<String> selected = d.showAndWait();
		if(selected.isPresent() == true && d.getSelectedItem().toString().equalsIgnoreCase("Select") == false) {
			String getData = d.getSelectedItem().toString().substring(d.getSelectedItem().toString().indexOf("(") + 1, d.getSelectedItem().toString().lastIndexOf(")") - 1).trim();
			
			viewTrainerDetailsScreen(s , getData);
		}
	}
	
	List<String> searchSet = new ArrayList<String>();
	AutoCompletionBinding primary;
	boolean firstTime = true;
	public void loadAllPotentialSearchElements() {
		if(firstTime == true) {
			firstTime = false;
		}else {
			primary.dispose();
		}
		
		searchSet.clear();
		
		//member Name
		try {
			PreparedStatement mn = connection.prepareStatement("SELECT `Name` , `ID` FROM `studentdatabase`.`gym_members`;");
			ResultSet names = mn.executeQuery();
			while(names.next()) {
				searchSet.add(names.getString(1) + " (" + names.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//member PhoneNum1
		try {
			PreparedStatement mph1 = connection.prepareStatement("SELECT `PhoneNumberOne`, `ID` FROM `studentdatabase`.`gym_members_connectivity`;");
			ResultSet ph1 = mph1.executeQuery();
			while(ph1.next()) {
				if(ph1.getString(1).equals("") == false)
					searchSet.add(ph1.getString(1) + " (" + ph1.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//member PhoneNum2
		try {
			PreparedStatement mph2 = connection.prepareStatement("SELECT `PhoneNumberTwo`, `ID` FROM `studentdatabase`.`gym_members_connectivity`;");
			ResultSet ph2 = mph2.executeQuery();
			while(ph2.next()) {
				if(ph2.getString(1).equals("") == false)
					searchSet.add(ph2.getString(1) + " (" + ph2.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//member Email
		try {
			PreparedStatement me = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_members_connectivity`;");
			ResultSet emails = me.executeQuery();
			while(emails.next()) {
				searchSet.add(emails.getString(1));
			}
		}catch(Exception e) {}
		
		//trainer PhoneNum1
		try {
			PreparedStatement tph1 = connection.prepareStatement("SELECT `PhoneNumberOne`, `ID` FROM `studentdatabase`.`gym_trainers_connectivity`;");
			ResultSet ph1 = tph1.executeQuery();
			while(ph1.next()) {
				if(ph1.getString(1).equals("") == false)
					searchSet.add(ph1.getString(1) + " (" + ph1.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//trainer PhoneNum2
		try {
			PreparedStatement tph2 = connection.prepareStatement("SELECT `PhoneNumberTwo`, `ID` FROM `studentdatabase`.`gym_trainers_connectivity`;");
			ResultSet ph2 = tph2.executeQuery();
			while(ph2.next()) {
				if(ph2.getString(1).equals("") == false)
					searchSet.add(ph2.getString(1) + " (" + ph2.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//trainer Name
		try {
			PreparedStatement tn = connection.prepareStatement("SELECT `Name` , `ID` FROM `studentdatabase`.`gym_trainers`;");
			ResultSet names = tn.executeQuery();
			while(names.next()) {
				searchSet.add(names.getString(1) + " (" + names.getString(2) + ")");
			}
		}catch(Exception e) {}
		
		//trainer Email
		try {
			PreparedStatement te = connection.prepareStatement("SELECT `Email` FROM `studentdatabase`.`gym_trainers_connectivity`;");
			ResultSet emails = te.executeQuery();
			while(emails.next()) {
				searchSet.add(emails.getString(1));
			}
		}catch(Exception e) {}
		
		//membership Name
		try {
			PreparedStatement mn = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_memberships`;");
			ResultSet names = mn.executeQuery();
			while(names.next()) {
				searchSet.add(names.getString(1));
			}
		}catch(Exception e) {}
		
		//equipments Name
		try {
			PreparedStatement mn = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_equipments`;");
			ResultSet names = mn.executeQuery();
			while(names.next()) {
				searchSet.add(names.getString(1));
			}
		}catch(Exception e) {}
		
		//Receipt_Number
		try {
			PreparedStatement frn = connection.prepareStatement("SELECT `Receipt_Number` FROM `studentdatabase`.`gym_fee`;");
			ResultSet numbers = frn.executeQuery();
			while(numbers.next()) {
				searchSet.add(numbers.getString(1));
			}
		}catch(Exception e) {}
		
		primary = TextFields.bindAutoCompletion(search, searchSet);
	}
	
	public void searchRequested(Stage s , String searchText) {
		boolean isFound = false;
		
		if(searchText.contains("(") && searchText.contains(")")) {
			//possibility of member/trainer id , name, phone
			
			try {
				String id = searchText.substring(searchText.indexOf("(") + 1 , searchText.indexOf(")"));
				if(id.startsWith("MBR") == true) {
					//it is Member
					try {
						PreparedStatement s0 = connection.prepareStatement("SELECT `ID` FROM `studentdatabase`.`gym_members` WHERE `ID` = '" + id + "';");
						ResultSet r0 = s0.executeQuery();
						while(r0.next()) {
							if(r0.getString(1).equalsIgnoreCase(id) == true) {
								isFound = true;
								viewMemberDetailsScreen(s , id);
							}
						}
					}catch(Exception e) {}
				}else {
					//it is trainer
					try {
						PreparedStatement s1 = connection.prepareStatement("SELECT `ID` FROM `studentdatabase`.`gym_trainers` WHERE `ID` = '" + id + "';");
						ResultSet r1 = s1.executeQuery();
						while(r1.next()) {
							if(r1.getString(1).equalsIgnoreCase(id) == true) {
								isFound = true;
								viewTrainerDetailsScreen(s , id);
							}
						}
					}catch(Exception e) {}
				}
			}catch(Exception e) {}
		}else {
			//possibility of equipment/membership/receipt/email
			if(searchText.contains("@") == true) {
				//it is EMAIL
				
				String email = searchText.trim();
				
				//first search in gym_members
				try {
					PreparedStatement s2 = connection.prepareStatement("SELECT `Email`,`ID` FROM `studentdatabase`.`gym_members_connectivity` WHERE `Email` = '" + email + "';");
					ResultSet r2 = s2.executeQuery();
					while(r2.next()) {
						if(r2.getString(1).equalsIgnoreCase(email) == true) {
							isFound = true;
							viewMemberDetailsScreen(s , r2.getString(2).trim());
						}
					}
				}catch(Exception e) {}
				
				//now search in gym_trainers
				try {
					PreparedStatement s3 = connection.prepareStatement("SELECT `Email`,`ID` FROM `studentdatabase`.`gym_trainers_connectivity` WHERE `Email` = '" + email + "';");
					ResultSet r3 = s3.executeQuery();
					while(r3.next()) {
						if(r3.getString(1).equalsIgnoreCase(email) == true) {
							isFound = true;
							viewTrainerDetailsScreen(s , r3.getString(2).trim());
						}
					}
				}catch(Exception e) {}
				
				
			}else if(searchText.startsWith("RECEIPT")){
				//it is RECEIPT, if not then membership/equipment
				
				try {
					String receiptNumber = searchText.trim();
					PreparedStatement s4 = connection.prepareStatement("SELECT `ID`,`Receipt_Number` FROM `studentdatabase`.`gym_fee` WHERE `Receipt_Number` = '" + receiptNumber + "';");
					ResultSet r4 = s4.executeQuery();
					while(r4.next()) {
						if(r4.getString(2).equalsIgnoreCase(receiptNumber) == true) {
							isFound = true;
							feeDetailsOfMemberScreen(s , r4.getString(1).trim() , true);
						}
					}
				}catch(Exception e) {}
				
				//first search for equipment
				try {
					String equipment = searchText.trim();
					PreparedStatement s5 = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_equipments` WHERE `Name` = '" + equipment + "';");
					ResultSet r5 = s5.executeQuery();
					while(r5.next()) {
						if(r5.getString(1).equalsIgnoreCase(equipment) == true) {
							isFound = true;
							equipmentsScreen(s);
						}
					}
				}catch(Exception e) {}
				
				//now search for membership
				try {
					String membership = searchText.trim();
					PreparedStatement s6 = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_memberships` WHERE `Name` = '" + membership + "';");
					ResultSet r6 = s6.executeQuery();
					while(r6.next()) {
						if(r6.getString(1).equalsIgnoreCase(membership) == true) {
							isFound = true;
							viewEditMembershipScreen(s , membership);
						}
					}
				}catch(Exception e) {}
				
			}else {
				//it is EQUIPMENT/MEMBERSHIP
				
				//first search for equipment
				try {
					String equipment = searchText.trim();
					PreparedStatement s5 = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_equipments` WHERE `Name` = '" + equipment + "';");
					ResultSet r5 = s5.executeQuery();
					while(r5.next()) {
						if(r5.getString(1).equalsIgnoreCase(equipment) == true) {
							isFound = true;
							equipmentsScreen(s);
						}
					}
				}catch(Exception e) {}
				
				//now search for membership
				try {
					String membership = searchText.trim();
					PreparedStatement s6 = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`gym_memberships` WHERE `Name` = '" + membership + "';");
					ResultSet r6 = s6.executeQuery();
					while(r6.next()) {
						if(r6.getString(1).equalsIgnoreCase(membership) == true) {
							isFound = true;
							viewEditMembershipScreen(s , membership);
						}
					}
				}catch(Exception e) {}
			}
		}
		if(isFound == false) {
			Alert notFound = new Alert(AlertType.ERROR);
			notFound.setTitle("Oops");
			notFound.setHeaderText("No Search Result");
			notFound.setContentText("Entered data didn't match any element.\n\n\n");
			notFound.show();
		}
	}
	
	TextArea note = new TextArea();
	Button clearNote = new Button("Clear");
	Button saveNote = new Button("Save");
	Button writeNote = new Button("Write");
	
	public void homeScreen(Stage s) {
		loadSettings();
		
		loadAllPotentialSearchElements();
		
		note.setEditable(false);
		note.setWrapText(true);
		
		//load saved note
		try {
			PreparedStatement getNote = connection.prepareStatement("SELECT `Note` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + loginUserID.getText().trim() + "';");
			ResultSet gotNote = getNote.executeQuery();
			gotNote.next();
			String loadedNote = gotNote.getString(1).toString();
			note.setText(loadedNote);
		}catch(Exception e) {
			note.setText("You don't have any saved notes.");
		}
		if(note.getText().equalsIgnoreCase("You don't have any saved notes.") == true) {
			try {
				PreparedStatement v1 = connection.prepareStatement("SELECT `Note` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + currentAdminEmail + "';");
				ResultSet v2 = v1.executeQuery();
				v2.next();
				String loadedNote = v2.getString(1).toString();
				note.setText(loadedNote);
			}catch(Exception e) {
				note.setText("You don't have any saved notes.");
			}
		}
		
		Button rectangle = new Button();
		rectangle.setStyle("-fx-border-color: black;" + "-fx-background-color: '" + this.currentBackground + "';");
		rectangle.setPrefSize(260.0, height - 70.0);
		rectangle.setOpacity(0.45);
		
		Button members = new Button("Members");
		Button trainers = new Button("Trainers");
		Button fee = new Button("Fee Management");
		Button equipments = new Button("Equipments");
		Button gymDetails = new Button("Gym Details");
		Button memberships = new Button("Memberships");
		
		members.setPrefSize(240.0, 40.0);
		trainers.setPrefSize(240.0, 40.0);
		fee.setPrefSize(240.0, 40.0);
		equipments.setPrefSize(240.0, 40.0);
		gymDetails.setPrefSize(240.0, 40.0);
		memberships.setPrefSize(240.0, 40.0);
		
		members.setStyle("-fx-background-color: '#b3b3ff';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		members.setOnMouseExited(e -> members.setStyle("-fx-background-color: '#b3b3ff';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		members.setOnMouseExited(e -> members.setStyle("-fx-background-color: '#b3b3ff';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		members.setOnMouseEntered(e -> members.setStyle("-fx-background-color: '#9999ff';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		members.setOnMousePressed(e -> members.setStyle("-fx-background-color: '#6666ff';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));
		
		trainers.setStyle("-fx-background-color: '#ffff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		trainers.setOnMouseExited(e -> trainers.setStyle("-fx-background-color: '#ffff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		trainers.setOnMouseExited(e -> trainers.setStyle("-fx-background-color: '#ffff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		trainers.setOnMouseEntered(e -> trainers.setStyle("-fx-background-color: '#ffff4d';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		trainers.setOnMousePressed(e -> trainers.setStyle("-fx-background-color: '#ffff1a';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));
		
		fee.setStyle("-fx-background-color: '#bfff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		fee.setOnMouseExited(e -> fee.setStyle("-fx-background-color: '#bfff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		fee.setOnMouseExited(e -> fee.setStyle("-fx-background-color: '#bfff80';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		fee.setOnMouseEntered(e -> fee.setStyle("-fx-background-color: '#a6ff4d';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		fee.setOnMousePressed(e -> fee.setStyle("-fx-background-color: '#8cff1a';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));
		
		equipments.setStyle("-fx-background-color: '#ffcccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		equipments.setOnMouseExited(e -> equipments.setStyle("-fx-background-color: '#ffcccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		equipments.setOnMouseExited(e -> equipments.setStyle("-fx-background-color: '#ffcccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		equipments.setOnMouseEntered(e -> equipments.setStyle("-fx-background-color: '#ffb3b3';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		equipments.setOnMousePressed(e -> equipments.setStyle("-fx-background-color: '#ff8080';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));

		gymDetails.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		gymDetails.setOnMouseExited(e -> gymDetails.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		gymDetails.setOnMouseExited(e -> gymDetails.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		gymDetails.setOnMouseEntered(e -> gymDetails.setStyle("-fx-background-color: '#94b8b8';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		gymDetails.setOnMousePressed(e -> gymDetails.setStyle("-fx-background-color: '#75a3a3';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));
		
		memberships.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;");
		memberships.setOnMouseExited(e -> memberships.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		memberships.setOnMouseExited(e -> memberships.setStyle("-fx-background-color: '#b3cccc';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13px;"));
		memberships.setOnMouseEntered(e -> memberships.setStyle("-fx-background-color: '#94b8b8';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 13.5px;"));
		memberships.setOnMousePressed(e -> memberships.setStyle("-fx-background-color: '#75a3a3';" + "-fx-font-family: 'Microsoft JhengHei';" + "-fx-font-size: 14px;"));
		
		search.setPrefWidth(250.0);
		
		Label welcome = new Label("Welcome");
		welcome.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 25px;");
		String space = "\t\t\t\t\t\t\t\t\t\t";
		Label headingUnderline = new Label(space);
		headingUnderline.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline.setUnderline(true);
		headingUnderline.setOpacity(0.5);
		Label noteLabel = new Label("Your saved notes");
		noteLabel.setStyle("-fx-font-size: 17px;");
		noteLabel.setOpacity(0.8);
		Label todayDate = new Label();
		todayDate.setText("Today is " + getCurrentDate());
		todayDate.setOpacity(0.7);
		todayDate.setStyle("-fx-font-size: 14px;");
		note.setStyle("-fx-font-size: 15px;");
		
		clearNote.setPrefSize(80.0, 25.0);
		saveNote.setPrefSize(80.0, 25.0);
		writeNote.setPrefSize(80.0, 25.0);
		
		try {
			PreparedStatement ppv = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + loginUserID.getText().trim() + "';");
			ResultSet nnxv = ppv.executeQuery();
			nnxv.next();
			String loadedName = "Welcome, ".concat(nnxv.getString(1).toString());
			welcome.setText(loadedName);
			
		}catch(Exception e) {
			welcome.setText("Welcome");
		}
		
		if(welcome.getText().equalsIgnoreCase("Welcome") == true) {
			try {
				PreparedStatement ppv = connection.prepareStatement("SELECT `Name` FROM `studentdatabase`.`Admins` WHERE `Email` = '" + currentAdminEmail + "';");
				ResultSet nnxv = ppv.executeQuery();
				nnxv.next();
				String loadedName = "Welcome, ".concat(nnxv.getString(1).toString());
				welcome.setText(loadedName);
			}catch(Exception e) {
				welcome.setText("Welcome");
			}
		}
		
		Button add = new Button();
		Button remove = new Button();
		Button rating = new Button();
		
		String[] addList = {"Member" , "Trainer" , "Membership" , "Equipment"};
		ChoiceDialog<String> addOptions = new ChoiceDialog<String>(addList[0] , addList);
		addOptions.setTitle("Add");
		addOptions.setHeaderText("Select and continue");
		
		ChoiceDialog<String> removeOptions = new ChoiceDialog<String>(addList[0] , addList);
		removeOptions.setTitle("Remove");
		removeOptions.setHeaderText("Select and continue");
		
		ImageView searchInside = new ImageView(searchImage);
		search.setLeft(searchInside);
		
		//controls
		settings.setOnAction(e -> settingsScreen(s));
		gymDetails.setOnAction(e -> gymDetailsScreen(s));
		members.setOnAction(e -> membersScreen(s));
		trainers.setOnAction(e -> trainersScreen(s));
		equipments.setOnAction(e -> equipmentsScreen(s));
		memberships.setOnAction(e -> membershipsScreen(s));
		fee.setOnAction(e -> feeScreen(s));
		add.setOnAction(e -> {
			Optional<String> selected = addOptions.showAndWait();
			if(selected.isPresent() == true) {
				String getData = addOptions.getSelectedItem().toString();
				if(getData.equalsIgnoreCase("Member") == true) {
					addNewMember(s , false);
				}else if(getData.equalsIgnoreCase("Trainer") == true) {
					addNewTrainer(s);
				}else if(getData.equalsIgnoreCase("Membership") == true) {
					addNewMembershipScreen(s);
				}else if(getData.equalsIgnoreCase("Equipment") == true) {
					addNewEquipmentScreen(s);
				}
			}
		});
		remove.setOnAction(e -> {
			Optional<String> selected = removeOptions.showAndWait();
			if(selected.isPresent() == true) {
				String getData = removeOptions.getSelectedItem().toString();
				if(getData.equalsIgnoreCase("Member") == true) {
					membersScreen(s);
				}else if(getData.equalsIgnoreCase("Trainer") == true) {
					trainersScreen(s);
				}else if(getData.equalsIgnoreCase("Membership") == true) {
					membershipsScreen(s);
				}else if(getData.equalsIgnoreCase("Equipment") == true) {
					equipmentsScreen(s);
				}
			}
		});
		rating.setOnAction(e -> {
			viaAlertMemberIDDialogToRating(s);
		});
		writeNote.setOnAction(e -> note.setEditable(true));
		clearNote.setOnAction(e -> {
			AdminSQL clsNote = new AdminSQL();
			boolean isCleared = clsNote.clearNote(connection, currentAdminEmail);
			if(isCleared == true) {
				note.setText("");
				note.setEditable(true);
			}
		});
		saveNote.setOnAction(e -> {
			AdminSQL saveNote = new AdminSQL();
			boolean isSaved = saveNote.saveNote(connection, currentAdminEmail, note.getText().trim());
			if(isSaved == true) {
				Alert sw = new Alert(AlertType.INFORMATION);
				sw.setTitle("Message");
				sw.setHeaderText("Notes Saved");
				sw.setContentText("Your notes have been successfully saved. Press OK to continue.\n\n\n");
				sw.show();
			}else {
				Alert nsw = new Alert(AlertType.ERROR);
				nsw.setTitle("Error");
				nsw.setHeaderText("Something went wrong");
				nsw.setContentText("Failed to save notes. Please enter alphabets and digits only and try again.\n\n\n");
				nsw.show();
			}
			
			note.setEditable(false);
		});
		searchInside.setOnMouseClicked(e -> {
			if(search.getText().trim().equals("") == false) {
				searchRequested(s , search.getText().trim());
			}
		});
		
		FileInputStream inputRatingImage;
		Image ratingImage;
		try {
			inputRatingImage = new FileInputStream("resources/logos/rating.png");
			ratingImage = new Image(inputRatingImage);
			rating.setGraphic(new ImageView(ratingImage));
			rating.setStyle("-fx-background-color: '" + this.currentBackground + "';");
		}catch(Exception e) {}
		
		try {
			inputRatingImage = new FileInputStream("resources/logos/add.png");
			ratingImage = new Image(inputRatingImage);
			add.setGraphic(new ImageView(ratingImage));
			add.setStyle("-fx-background-color: '" + this.currentBackground + "';");
		}catch(Exception e) {}
		
		try {
			inputRatingImage = new FileInputStream("resources/logos/remove.png");
			ratingImage = new Image(inputRatingImage);
			remove.setGraphic(new ImageView(ratingImage));
			remove.setStyle("-fx-background-color: '" + this.currentBackground + "';");
		}catch(Exception e) {}
		
		AnchorPane root = new AnchorPane();
		//settings
		AnchorPane.setTopAnchor(settings, 35.0);
		AnchorPane.setRightAnchor(settings, 15.0);
		//rectangle
		AnchorPane.setTopAnchor(rectangle, 45.0);
		AnchorPane.setLeftAnchor(rectangle, 15.0);
		//members
		AnchorPane.setTopAnchor(members, 45.0 + 10.0);
		AnchorPane.setLeftAnchor(members, 15.0 + 10.0);
		//trainers
		AnchorPane.setTopAnchor(trainers, (45.0 + 6.0) * 2 + 4);
		AnchorPane.setLeftAnchor(trainers, 15.0 + 10.0);
		//fee
		AnchorPane.setTopAnchor(fee, (45.0 + 6.0) * 3 + 4);
		AnchorPane.setLeftAnchor(fee, 15.0 + 10.0);
		//memberships
		AnchorPane.setTopAnchor(memberships, (45.0 + 6.0) * 4 + 4);
		AnchorPane.setLeftAnchor(memberships, 15.0 + 10.0);
		//equipments
		AnchorPane.setTopAnchor(equipments, (45.0 + 6.0) * 5 + 4);
		AnchorPane.setLeftAnchor(equipments, 15.0 + 10.0);
		//gymDetails
		AnchorPane.setTopAnchor(gymDetails, (45.0 + 6.0) * 6 + 4);
		AnchorPane.setLeftAnchor(gymDetails, 15.0 + 10.0);
		//search
		AnchorPane.setTopAnchor(search, 37.0);
		AnchorPane.setRightAnchor(search, 75.0);
		//searchIcon
		AnchorPane.setTopAnchor(searchIcon, 35.0);
		AnchorPane.setRightAnchor(searchIcon, 55.0 + 20.0);
		//welcome
		AnchorPane.setTopAnchor(welcome, 120.0);
		AnchorPane.setLeftAnchor(welcome, 310.0);
		//underline
		AnchorPane.setTopAnchor(headingUnderline, 125.0);
		AnchorPane.setLeftAnchor(headingUnderline, 300.0);
		AnchorPane.setRightAnchor(headingUnderline, 26.0);
		//delete
		AnchorPane.setRightAnchor(remove, 14.0  + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(remove, 17.0);
		//add
		AnchorPane.setRightAnchor(add, 127.0 + 100.0 + 17.0);
		AnchorPane.setBottomAnchor(add, 17.0);
		//rating
		AnchorPane.setRightAnchor(rating, 19.0);
		AnchorPane.setBottomAnchor(rating, 17.0);
		//noteLabel
		AnchorPane.setTopAnchor(noteLabel, 180.0);
		AnchorPane.setLeftAnchor(noteLabel, 310.0);
		//note
		AnchorPane.setTopAnchor(note, 210.0);
		AnchorPane.setLeftAnchor(note, 310.0);
		AnchorPane.setRightAnchor(note, 55.0);
		AnchorPane.setBottomAnchor(note, 150.0);
		//writeNote
		AnchorPane.setRightAnchor(writeNote, 60.0);
		AnchorPane.setBottomAnchor(writeNote, 117.0);
		//saveNote
		AnchorPane.setRightAnchor(saveNote, 60.0 + 80.0 + 10.0);
		AnchorPane.setBottomAnchor(saveNote, 117.0);
		//clearNote
		AnchorPane.setRightAnchor(clearNote, 60.0 + (80.0 + 10.0) * 2.0);
		AnchorPane.setBottomAnchor(clearNote, 117.0);
		//todayDate
		AnchorPane.setLeftAnchor(todayDate, 310.0);
		AnchorPane.setBottomAnchor(todayDate, 70.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(menuBar , settings , rectangle , members , trainers , fee , equipments , 
				memberships , gymDetails , search , welcome , headingUnderline ,
				remove , add , rating , note , clearNote , saveNote , writeNote ,
				noteLabel , todayDate);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	TextField adminName = new TextField();
	TextField adminPhNo1 = new TextField();
	TextField adminPhNo2 = new TextField();
	TextField adminEmail = new TextField();
	TextField adminAddress = new TextField();
	ObservableList<String> availableQuestions = FXCollections.observableArrayList("Which is your favourite place on Earth?" , "Who was your first best-friend?" , "What is the name of your favourite novel?" , "Which dish do you like to eat most?" , "Who is your favourite Film-Star?");
	ComboBox<String> adminQuestion= new ComboBox<String>(availableQuestions);
	TextField adminAnswer = new TextField();
	PasswordField adminPassword = new PasswordField();
	PasswordField adminConfirmPassword = new PasswordField();
	PasswordField ownerPassword = new PasswordField();
	
	public void resetSignUpScreen() {
		adminName.setText("");
		adminPhNo1.setText("");
		adminPhNo2.setText("");
		adminEmail.setText("");
		adminAddress.setText("");
		adminQuestion.getSelectionModel().selectFirst();
		adminAnswer.setText("");
		adminPassword.setText("");
		adminConfirmPassword.setText("");
		ownerPassword.setText("");
		
		adminName.setPromptText("Name");
		adminPhNo1.setPromptText("Contact Number");
		adminPhNo2.setPromptText("Alternate Contact Number");
		adminEmail.setPromptText("Email");
		adminAddress.setPromptText("Address");
		adminQuestion.getSelectionModel().selectFirst();
		adminAnswer.setPromptText("Enter Security Answer");
		adminPassword.setPromptText("Password");
		adminConfirmPassword.setPromptText("Confirm Password");
		ownerPassword.setPromptText("Database Name");
	}
	
	Label forgotNameLabel = new Label("Enter your Name or Email:");
	TextField forgotName = new TextField();
	TextField forgotAnswer = new TextField();
	PasswordField forgotPassword = new PasswordField();
	PasswordField forgotConfirmPassword = new PasswordField();
	Label passwordLabel = new Label("Account found. Please set new Password.");
	Button finalOK = new Button("Confirm");
	
	String underlineString1 = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "            ";
	Label underline1 = new Label(underlineString1);
	
	public void resetForgotPasswordScreen() {
		forgotName.setText("");
		forgotAnswer.setText("");
		forgotPassword.setText("");
		forgotConfirmPassword.setText("");
		adminQuestion.getSelectionModel().selectFirst();
		
		forgotName.setPromptText("Admin Email");
		forgotAnswer.setPromptText("Enter saved Security Answer");
		forgotPassword.setPromptText("Set New Password");
		forgotConfirmPassword.setPromptText("Confirm New Password");
		
		forgotPassword.setVisible(false);
		forgotConfirmPassword.setVisible(false);
		passwordLabel.setVisible(false);
		finalOK.setVisible(false);
		underline1.setVisible(false);
	}
	
	public void forgotPasswordScreen(Stage s) {
		resetForgotPasswordScreen();
		
		Button firstOK = new Button("Submit");
		
		finalOK.setPrefSize(130.0, 32.0);
		
		firstOK.setStyle(goN);
		firstOK.setOnMouseMoved(e-> firstOK.setStyle(goH));
		firstOK.setOnMouseReleased(e-> firstOK.setStyle(goN));
		firstOK.setOnMousePressed(e-> firstOK.setStyle(goP));
		firstOK.setOnMouseExited(e-> firstOK.setStyle(goN));
		
		finalOK.setStyle(goN);
		finalOK.setOnMouseMoved(e-> finalOK.setStyle(goH));
		finalOK.setOnMouseReleased(e-> finalOK.setStyle(goN));
		finalOK.setOnMousePressed(e-> finalOK.setStyle(goP));
		finalOK.setOnMouseExited(e-> finalOK.setStyle(goN));
		
		Label membershipsLabel = new Label(" Forgot Password");
		membershipsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		Label enterLabel = new Label("Enter your Email:");
		enterLabel.setStyle("-fx-font-size: 14px;");
		Label securityQuestionLabel = new Label("Security Question:");
		securityQuestionLabel.setStyle("-fx-font-size: 14px;");
		
		underline1.setUnderline(true);
		underline1.setOpacity(0.6);
		
		passwordLabel.setStyle("-fx-font-size: 14px;");
		
		//controls
		back.setOnAction(e -> loginScreen(s));
		AdminSQL find = new AdminSQL();
		firstOK.setOnAction(e -> {
			boolean isFound = find.findEmail(connection, forgotName.getText().trim(), adminQuestion.getSelectionModel().getSelectedItem(), forgotAnswer.getText().trim());
			if(isFound == true) {
				forgotPassword.setVisible(true);
				forgotConfirmPassword.setVisible(true);
				passwordLabel.setVisible(true);
				finalOK.setVisible(true);
				underline1.setVisible(true);
			}else {
				forgotPassword.setVisible(false);
				forgotConfirmPassword.setVisible(false);
				passwordLabel.setVisible(false);
				finalOK.setVisible(false);
				underline1.setVisible(false);
				Alert password = new Alert(AlertType.WARNING);
				password.setTitle("Message");
				password.setHeaderText("Not Found");
				password.setContentText("Account not found or Security question or answer wrong.\n\n\n");
				password.show();
			}
		});
		AdminSQL ad = new AdminSQL();
		finalOK.setOnAction(e -> {
			if(forgotPassword.getText().equals("") == false && forgotConfirmPassword.getText().equals("") == false) {
				if(forgotPassword.getText().equals(forgotConfirmPassword.getText()) == true) {
					boolean isUpdated = ad.updatePassword(connection, forgotName.getText().trim(), forgotPassword.getText());
					if(isUpdated == true) {
						Alert password = new Alert(AlertType.INFORMATION);
						password.setTitle("Message");
						password.setHeaderText("Password Updated");
						password.setContentText("Password has been updated.\n\n\n");
						password.show();
						loginScreen(s);
					}else {
						Alert password = new Alert(AlertType.WARNING);
						password.setTitle("Error");
						password.setHeaderText("Some Error Occured");
						password.setContentText("Password was not updated.\n\n\n");
						password.show();
					}
				}else {
					Alert password = new Alert(AlertType.WARNING);
					password.setTitle("Error");
					password.setHeaderText("Password Mismatch");
					password.setContentText("Password and Confirm Password did not match. "
							+ "Please enter again and continue.\n\n\n");
					password.show();
				}
			}else {
				Alert password = new Alert(AlertType.WARNING);
				password.setTitle("Error");
				password.setHeaderText("Password Not Set");
				password.setContentText("Password or Confirm Password field found empty. "
						+ "Please set password and try again.\n\n\n");
				password.show();
			}
		});
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 20.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membershipsLabel
		AnchorPane.setTopAnchor(membershipsLabel, 63.0);
		AnchorPane.setLeftAnchor(membershipsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 89.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//finalOK
		AnchorPane.setRightAnchor(finalOK, 27.0);
		AnchorPane.setBottomAnchor(finalOK, 20.0);
		//enterLabel
		AnchorPane.setTopAnchor(enterLabel, 142.0);
		AnchorPane.setLeftAnchor(enterLabel, 130.0);
		//forgotName
		AnchorPane.setTopAnchor(forgotName, 140.0);
		AnchorPane.setLeftAnchor(forgotName, 250.0);
		AnchorPane.setRightAnchor(forgotName, 130.0);
		//securityQuestionLabel
		AnchorPane.setTopAnchor(securityQuestionLabel, 187.0);
		AnchorPane.setLeftAnchor(securityQuestionLabel, 200.0);
		//adminQuestion
		AnchorPane.setTopAnchor(adminQuestion, 185.0);
		AnchorPane.setLeftAnchor(adminQuestion, 330.0);
		AnchorPane.setRightAnchor(adminQuestion, 200.0);
		//forgotAnswer
		AnchorPane.setTopAnchor(forgotAnswer, 230.0);
		AnchorPane.setLeftAnchor(forgotAnswer, 220.0);
		AnchorPane.setRightAnchor(forgotAnswer, 220.0);
		//firstOK
		AnchorPane.setTopAnchor(firstOK, 270.0);
		AnchorPane.setLeftAnchor(firstOK, 410.0);
		AnchorPane.setRightAnchor(firstOK, 410.0);
		//underline1
		AnchorPane.setTopAnchor(underline1, 310.0);
		AnchorPane.setLeftAnchor(underline1, 140.0);
		AnchorPane.setRightAnchor(underline1, 140.0);
		//passwordLabel
		AnchorPane.setTopAnchor(passwordLabel, 345.0);
		AnchorPane.setLeftAnchor(passwordLabel, 190.0);
		//forgotPassword
		AnchorPane.setTopAnchor(forgotPassword, 385.0);
		AnchorPane.setLeftAnchor(forgotPassword, 300.0);
		AnchorPane.setRightAnchor(forgotPassword, 300.0);
		//forgotConfirmPassword
		AnchorPane.setTopAnchor(forgotConfirmPassword, 420.0);
		AnchorPane.setLeftAnchor(forgotConfirmPassword, 300.0);
		AnchorPane.setRightAnchor(forgotConfirmPassword, 300.0);
		//copyright
		AnchorPane.setLeftAnchor(copyright, 10.0);
		AnchorPane.setBottomAnchor(copyright, 8.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(back , underline1 , passwordLabel , firstOK , finalOK , membershipsLabel ,
				underline , enterLabel ,
				securityQuestionLabel , adminQuestion , forgotName , forgotAnswer , forgotPassword ,
				forgotConfirmPassword , copyright);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public boolean checkPin(String input) {
		String regex = "[0-9][0-9][0-9][0-9][0-9][0-9]";
		String data = input;
		return data.matches(regex);
	}
	
	public boolean checkPhoneNumber(String input) {
		String regex = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		String data = input;
		return data.matches(regex);
	}
	
	String currentAdminName = "";
	
	public void addNewAdmin(Stage s) {
		AdminSQL adxx = new AdminSQL();
		adxx.createAdminTable(connection);
		
		if(adminEmail.getText().contains("@") == false || checkPhoneNumber(adminPhNo2.getText()) == false || checkPhoneNumber(adminPhNo1.getText()) == false || ownerPassword.getText().equals("") == true || adminConfirmPassword.getText().equals("") == true ||adminPassword.getText().equals("") == true ||adminAnswer.getText().equals("") == true ||adminAddress.getText().equals("") == true || adminEmail.getText().equals("") == true || adminPhNo2.getText().equals("") == true || adminName.getText().equals("") == true || adminPhNo1.getText().equals("") == true) {
			//invalid
			Alert warning = new Alert(AlertType.WARNING);
			warning.setTitle("Warning");
			warning.setHeaderText("Invalid or Missing data found");
			warning.setContentText("Please enter valid data to continue. Some data entered is "
					+ "invalid or missing. Enter 10 digits phone number. Try Again.\n\n\n");
			warning.show();
		}else {
			//all data entered.

			if(adminPassword.getText().equals(adminConfirmPassword.getText()) == false) {
				//incorrect password
				Alert password = new Alert(AlertType.WARNING);
				password.setTitle("Error");
				password.setHeaderText("Password Mismatch");
				password.setContentText("Password and Confirm Password did not match. "
						+ "Please enter again and continue.\n\n\n");
				password.show();
			}else {
				//password correct
				
				//checking database name
				if(ownerPassword.getText().equals("studentdatabase") == true) {
					AdminSQL a = new AdminSQL();
					boolean isAdded = a.insertToAdminTable(connection, adminName.getText().trim(), adminPhNo1.getText().trim(), adminPhNo2.getText().trim(), adminEmail.getText().trim(), adminAddress.getText().toString(), adminQuestion.getSelectionModel().getSelectedItem().toString(), adminAnswer.getText().trim(), adminPassword.getText(), getCurrentDate());
					if(isAdded == true) {
						this.currentAdminEmail = adminEmail.getText().trim();
						homeScreen(s);
					}else {
						Alert error = new Alert(AlertType.ERROR);
						error.setTitle("An error occured");
						error.setHeaderText("Admin Not Created");
						error.setContentText("Please try by entering different phone number(s) and "
								+ "email.\n\n\n");
						error.show();
					}
				}else {
					Alert password = new Alert(AlertType.WARNING);
					password.setTitle("Error");
					password.setHeaderText("Password Mismatch");
					password.setContentText("Password and Confirm Password did not match. "
							+ "Please enter again and continue.\n\n\n");
					password.show();
				}
			}
		}
		
	}
	
	public void signUpScreen(Stage s) {
		resetSignUpScreen();
		
		Button reset = new Button("Reset");
		Button create = new Button("Create");
		
		Label membershipsLabel = new Label(" New Admin");
		membershipsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 30px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		Label securityQuestionLabel = new Label("Security Question:");
		securityQuestionLabel.setStyle("-fx-font-size: 14px;");
		Label ownerPasswordLabel = new Label("Database Name:");
		ownerPasswordLabel.setStyle("-fx-font-size: 14px;");
		
		String underlineString1 = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "                ";
		Label underline1 = new Label(underlineString1);
		underline1.setUnderline(true);
		underline1.setOpacity(0.6);
		Label underline2 = new Label(underlineString1);
		underline2.setUnderline(true);
		underline2.setOpacity(0.6);
		Label underline3 = new Label(underlineString1);
		underline3.setUnderline(true);
		underline3.setOpacity(0.6);
		
		reset.setPrefSize(130.0, 32.0);
		create.setPrefSize(130.0, 32.0);
		
		reset.setStyle(goN);
		reset.setOnMouseMoved(e-> reset.setStyle(goH));
		reset.setOnMouseReleased(e-> reset.setStyle(goN));
		reset.setOnMousePressed(e-> reset.setStyle(goP));
		reset.setOnMouseExited(e-> reset.setStyle(goN));
		
		create.setStyle(goN);
		create.setOnMouseMoved(e-> create.setStyle(goH));
		create.setOnMouseReleased(e-> create.setStyle(goN));
		create.setOnMousePressed(e-> create.setStyle(goP));
		create.setOnMouseExited(e-> create.setStyle(goN));
		
		//controls
		back.setOnAction(e -> loginScreen(s));
		reset.setOnAction(e -> resetSignUpScreen());
		create.setOnAction(e -> addNewAdmin(s));
		
		AnchorPane root = new AnchorPane();
		//back
		AnchorPane.setTopAnchor(back, 20.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membershipsLabel
		AnchorPane.setTopAnchor(membershipsLabel, 33.0);
		AnchorPane.setLeftAnchor(membershipsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 58.0);
		AnchorPane.setRightAnchor(underline, 65.0);
		AnchorPane.setLeftAnchor(underline, 65.0);
		//reset
		AnchorPane.setRightAnchor(reset, 27.0  + 100.0 + 17.0 + 30.0);
		AnchorPane.setBottomAnchor(reset, 17.0);
		//create
		AnchorPane.setRightAnchor(create, 27.0);
		AnchorPane.setBottomAnchor(create, 17.0);
		//adminName
		AnchorPane.setTopAnchor(adminName, 100.0);
		AnchorPane.setLeftAnchor(adminName, 240.0);
		AnchorPane.setRightAnchor(adminName, 240.0);
		//adminPhNo1
		AnchorPane.setTopAnchor(adminPhNo1, 140.0 - 5.0);
		AnchorPane.setLeftAnchor(adminPhNo1, 240.0);
		AnchorPane.setRightAnchor(adminPhNo1, 460.0);
		//adminPhNo2
		AnchorPane.setTopAnchor(adminPhNo2, 140.0 - 5.0);
		AnchorPane.setLeftAnchor(adminPhNo2, 460.0);
		AnchorPane.setRightAnchor(adminPhNo2, 240.0);
		//adminEmail
		AnchorPane.setTopAnchor(adminEmail, 175.0 - 5.0);
		AnchorPane.setLeftAnchor(adminEmail, 240.0);
		AnchorPane.setRightAnchor(adminEmail, 240.0);
		//adminAddress
		AnchorPane.setTopAnchor(adminAddress, 210.0 - 5.0);
		AnchorPane.setLeftAnchor(adminAddress, 240.0);
		AnchorPane.setRightAnchor(adminAddress, 240.0);
		//underline1
		AnchorPane.setTopAnchor(underline1, 235.0);
		AnchorPane.setLeftAnchor(underline1, 200.0);
		AnchorPane.setRightAnchor(underline1, 200.0);
		//securityQuestionLabel
		AnchorPane.setTopAnchor(securityQuestionLabel, 270.0);
		AnchorPane.setLeftAnchor(securityQuestionLabel, 220.0);
		//adminQuestion
		AnchorPane.setTopAnchor(adminQuestion, 268.0);
		AnchorPane.setLeftAnchor(adminQuestion, 350.0);
		AnchorPane.setRightAnchor(adminQuestion, 220.0);
		//adminAnswer
		AnchorPane.setTopAnchor(adminAnswer, 305.0);
		AnchorPane.setLeftAnchor(adminAnswer, 250.0);
		AnchorPane.setRightAnchor(adminAnswer, 250.0);
		//underline2
		AnchorPane.setTopAnchor(underline2, 335.0);
		AnchorPane.setLeftAnchor(underline2, 200.0);
		AnchorPane.setRightAnchor(underline2, 200.0);
		//adminPassword
		AnchorPane.setTopAnchor(adminPassword, 367.0);
		AnchorPane.setLeftAnchor(adminPassword, 300.0);
		AnchorPane.setRightAnchor(adminPassword, 300.0);
		//adminConfirmPassword
		AnchorPane.setTopAnchor(adminConfirmPassword, 402.0);
		AnchorPane.setLeftAnchor(adminConfirmPassword, 300.0);
		AnchorPane.setRightAnchor(adminConfirmPassword, 300.0);
		//underline3
		AnchorPane.setTopAnchor(underline3, 425.0);
		AnchorPane.setLeftAnchor(underline3, 200.0);
		AnchorPane.setRightAnchor(underline3, 200.0);
		//ownerPasswordLabel
		AnchorPane.setTopAnchor(ownerPasswordLabel, 457.0);
		AnchorPane.setLeftAnchor(ownerPasswordLabel, 280.0);
		//ownerPassword
		AnchorPane.setTopAnchor(ownerPassword, 455.0);
		AnchorPane.setLeftAnchor(ownerPassword, 420.0);
		AnchorPane.setRightAnchor(ownerPassword, 280.0);
		//copyright
		AnchorPane.setLeftAnchor(copyright, 10.0);
		AnchorPane.setBottomAnchor(copyright, 8.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(underline1 , underline2 , underline3 , securityQuestionLabel ,
				back , reset , create , membershipsLabel , underline , adminName , adminPhNo1 ,
				adminPhNo2 , adminEmail , adminAddress , adminQuestion , adminAnswer , adminPassword ,
				adminConfirmPassword , ownerPassword , ownerPasswordLabel , copyright);
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	TextField loginUserID = new TextField();
	PasswordField loginUserPassword = (PasswordField) TextFields.createClearablePasswordField();
	
	String currentAdminEmail = "";
	
	public void checkPassword(Stage s) {
		AdminSQL login = new AdminSQL();
		login.createAdminTable(connection);
		
		boolean isPasswordCorrect = login.attemptToLogin(connection, loginUserID.getText().toString().trim(), loginUserPassword.getText().toString());
		if(isPasswordCorrect == true) {
			homeScreen(s);
			this.currentAdminEmail = loginUserID.getText().trim();
		}else {
			Alert error = new Alert(AlertType.ERROR);
			error.setTitle("Login Failed");
			error.setHeaderText("Incorrect Email, Name or Password");
			error.setContentText("Entered email, name or password is incorrect. Please try again by "
					+ "entering correct details.\n\n\n");
			error.show();
		}
	}
	
	boolean loginScreenFirstTime = true;
	
	public void loginScreen(Stage s) {
		loginUserID.setText("");
		loginUserPassword.setText("");
		
		Button enter = new Button("Go");
		Button forgotPassword = new Button("Forgot Password?");
		Button signUp = new Button("Sign Up");
		
		forgotPassword.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'orange';" + "-fx-font-size: 15px;");
		signUp.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'blue';" + "-fx-font-size: 15px;");
		
		forgotPassword.setOnMouseExited(e -> forgotPassword.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'orange';" + "-fx-font-size: 15px;"));
		signUp.setOnMouseExited(e -> signUp.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'blue';" + "-fx-font-size: 15px;"));
		
		forgotPassword.setOnMouseReleased(e -> forgotPassword.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'orange';" + "-fx-font-size: 15px;"));
		signUp.setOnMouseReleased(e -> signUp.setStyle("-fx-font-weight: normal;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'blue';" + "-fx-font-size: 15px;"));
		
		forgotPassword.setOnMouseEntered(e -> forgotPassword.setStyle("-fx-font-weight: bold;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'orange';" + "-fx-font-size: 15px;"));
		signUp.setOnMouseEntered(e -> signUp.setStyle("-fx-font-weight: bold;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'blue';" + "-fx-font-size: 15.5px;"));
		
		forgotPassword.setOnMousePressed(e -> forgotPassword.setStyle("-fx-font-weight: bold;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'orange';" + "-fx-font-size: 15px;"));
		signUp.setOnMousePressed(e -> signUp.setStyle("-fx-font-weight: bold;" + "-fx-background-color: '#ccffe6';" + "-fx-text-fill: 'blue';" + "-fx-font-size: 15.5px;"));
		
		enter.setStyle(goN);
		enter.setOnMouseMoved(e-> enter.setStyle(goH));
		enter.setOnMouseReleased(e-> enter.setStyle(goN));
		enter.setOnMousePressed(e-> enter.setStyle(goP));
		enter.setOnMouseExited(e-> enter.setStyle(goN));
		
		//controls
		enter.setOnAction(e -> checkPassword(s));
		signUp.setOnAction(e -> signUpScreen(s));
		forgotPassword.setOnAction(e -> forgotPasswordScreen(s));
		
		Label gymDBMSLabel = new Label("Gym Database Management System");
		gymDBMSLabel.setStyle("-fx-font-weight: bold;" + "-fx-font-family: 'Microsoft PhagsPa';" + "-fx-font-size: 33px;");
		Label loginLabel = new Label("Login");
		loginLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 20px;");
		String space = "\t\t\t\t\t\t\t\t" + "      ";
		Label headingUnderline0 = new Label(space);
		headingUnderline0.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline0.setUnderline(true);
		headingUnderline0.setOpacity(0.4);
		Label headingUnderline1 = new Label(space);
		headingUnderline1.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 24px;");
		headingUnderline1.setUnderline(true);
		headingUnderline1.setOpacity(0.4);
		
		loginUserID.setPromptText("Admin Email");
		loginUserPassword.setPromptText("Admin Password");
		
		AnchorPane root = new AnchorPane();
		//gymDBMSLabel
		AnchorPane.setTopAnchor(gymDBMSLabel, 80.0);
		AnchorPane.setLeftAnchor(gymDBMSLabel, 165.0);
		//loginLabel
		AnchorPane.setTopAnchor(loginLabel, 190.0 + 10.0);
		AnchorPane.setLeftAnchor(loginLabel, width/2.0 - 32.0);
		//headingUnderline0
		AnchorPane.setTopAnchor(headingUnderline0, 190.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(headingUnderline0, 200.0);
		AnchorPane.setRightAnchor(headingUnderline0, 200.0);
		//headingUnderline1
		AnchorPane.setTopAnchor(headingUnderline1, 360.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(headingUnderline1, 200.0);
		AnchorPane.setRightAnchor(headingUnderline1, 200.0);
		//loginUserID
		AnchorPane.setTopAnchor(loginUserID, 255.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(loginUserID, 300.0);
		AnchorPane.setRightAnchor(loginUserID, 300.0);
		//loginUserPassword
		AnchorPane.setTopAnchor(loginUserPassword, 295.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(loginUserPassword, 300.0);
		AnchorPane.setRightAnchor(loginUserPassword, 300.0);
		//enter
		AnchorPane.setTopAnchor(enter, 335.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(enter, 400.0);
		AnchorPane.setRightAnchor(enter, 400.0);
		//forgotPassword
		AnchorPane.setTopAnchor(forgotPassword, 400.0 + 10.0 + 10.0);
		AnchorPane.setRightAnchor(forgotPassword, width/2.0 + 10.0);
		//signUp
		AnchorPane.setTopAnchor(signUp, 400.0 + 10.0 + 10.0);
		AnchorPane.setLeftAnchor(signUp, width/2.0 + 10.0);
		//copyright
		AnchorPane.setLeftAnchor(copyright, 10.0);
		AnchorPane.setBottomAnchor(copyright, 8.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(enter , loginLabel , headingUnderline0 , headingUnderline1 , loginUserID ,
				loginUserPassword , forgotPassword , signUp , gymDBMSLabel , copyright);
		
		Scene scene;
		if(loginScreenFirstTime == true) {
			scene = new Scene(root , width - 10.0 , height - 10.0);
			loginScreenFirstTime = false;
		}else {
			scene = new Scene(root , width , height);
		}
		
		s.setScene(scene);
		s.show();
	}
	
	int currentTheme = 0;
	int potentialTheme = this.currentTheme;
	
	String currentBackground = "#ccffe6";
	String currentDarkColor = "#0073e6";
	
	String theme00 = "#ccffe6";
	String theme01 = "#0073e6";
	String theme10 = "#ffffb3";		//background yellow
	String theme11 = "#e68a00";		//menuBar orange
	String theme20 = "#cccccc";		//background grey
	String theme21 = "#999966";		//menuBar black
	
	public void updateBackButton(int index) {
		if(index == 0) {
			try {
				inputBackImage = new FileInputStream("resources/logos/back.png");
				backImage = new Image(inputBackImage);
				back.setGraphic(new ImageView(backImage));
				back.setStyle("-fx-background-color: '" + theme00 + "';");
			}catch(Exception e) {}
		}else if(index == 1) {
			try {
				inputBackImage = new FileInputStream("resources/logos/back1.png");
				backImage = new Image(inputBackImage);
				back.setGraphic(new ImageView(backImage));
				back.setStyle("-fx-background-color: '" + theme10 + "';");
			}catch(Exception e) {}
		}else {
			try {
				inputBackImage = new FileInputStream("resources/logos/back2.png");
				backImage = new Image(inputBackImage);
				back.setGraphic(new ImageView(backImage));
				back.setStyle("-fx-background-color: '" + theme20 + "';");
			}catch(Exception e) {}
		}
	}
	
	public void updateSettingsButton(int index) {
		if(index == 0) {
			try {
				inputSettingsImage = new FileInputStream("resources/logos/Settings Logo.png");
				settingsImage = new Image(inputSettingsImage);
				settings.setGraphic(new ImageView(settingsImage));
				settings.setMaxHeight(20.0);
				settings.setMaxWidth(20.0);
				settings.setStyle("-fx-background-color: '" + currentBackground + "';");
			}catch(Exception e) {}
		}else if(index == 1) {
			try {
				inputSettingsImage = new FileInputStream("resources/logos/Settings Logo1.png");
				settingsImage = new Image(inputSettingsImage);
				settings.setGraphic(new ImageView(settingsImage));
				settings.setMaxHeight(20.0);
				settings.setMaxWidth(20.0);
				settings.setStyle("-fx-background-color: '" + currentBackground + "';");
			}catch(Exception e) {}
		}else {
			try {
				inputSettingsImage = new FileInputStream("resources/logos/Settings Logo2.png");
				settingsImage = new Image(inputSettingsImage);
				settings.setGraphic(new ImageView(settingsImage));
				settings.setMaxHeight(20.0);
				settings.setMaxWidth(20.0);
				settings.setStyle("-fx-background-color: '" + currentBackground + "';");
			}catch(Exception e) {}
		}
	}
	
	public void themeScreen(Stage s) {
		potentialTheme = this.currentTheme;
		
		Button change = new Button("Set Theme");
		
		Button t0 = new Button();
		FileInputStream inputT0Image;
		Image t0Image;
		try {
			inputT0Image = new FileInputStream("resources/logos/theme0.png");
			t0Image = new Image(inputT0Image);
			t0.setGraphic(new ImageView(t0Image));
		}catch(Exception e) {}
		
		Button t1 = new Button();
		FileInputStream inputT1Image;
		Image t1Image;
		try {
			inputT1Image = new FileInputStream("resources/logos/theme1.png");
			t1Image = new Image(inputT1Image);
			t1.setGraphic(new ImageView(t1Image));
		}catch(Exception e) {}
		
		Button t2 = new Button();
		FileInputStream inputT2Image;
		Image t2Image;
		try {
			inputT2Image = new FileInputStream("resources/logos/theme2.png");
			t2Image = new Image(inputT2Image);
			t2.setGraphic(new ImageView(t2Image));
		}catch(Exception e) {}
		
		Button s0 = new Button();
		FileInputStream inputS0Image;
		Image s0Image;
		try {
			inputS0Image = new FileInputStream("resources/logos/selected0.png");
			s0Image = new Image(inputS0Image);
			s0.setGraphic(new ImageView(s0Image));
		}catch(Exception e) {}
		
		Button s1 = new Button();
		FileInputStream inputS1Image;
		Image s1Image;
		try {
			inputS1Image = new FileInputStream("resources/logos/selected1.png");
			s1Image = new Image(inputS1Image);
			s1.setGraphic(new ImageView(s1Image));
		}catch(Exception e) {}
		
		Button s2 = new Button();
		FileInputStream inputS2Image;
		Image s2Image;
		try {
			inputS2Image = new FileInputStream("resources/logos/selected2.png");
			s2Image = new Image(inputS2Image);
			s2.setGraphic(new ImageView(s2Image));
		}catch(Exception e) {}
		
		t0.setStyle("-fx-background-color: '#ccffe6';");
		t1.setStyle("-fx-background-color: '#ccffe6';");
		t2.setStyle("-fx-background-color: '#ccffe6';");
		
		s0.setStyle("-fx-background-color: '" + theme00 + "';");
		s1.setStyle("-fx-background-color: '" + theme10 + "';");
		s2.setStyle("-fx-background-color: '" + theme20 + "';");
		
		change.setPrefWidth(130.0);
		
		if(currentTheme == 0) {
			change.setStyle("-fx-background-color: '" + theme01 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			t0.setStyle("-fx-background-color: '" + theme00 + "';");
			t1.setStyle("-fx-background-color: '" + theme00 + "';");
			t2.setStyle("-fx-background-color: '" + theme00 + "';");
			
			s0.setVisible(true);
			s1.setVisible(false);
			s2.setVisible(false);
			
		}else if(currentTheme == 1) {
			change.setStyle("-fx-background-color: '" + theme11 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			t0.setStyle("-fx-background-color: '" + theme10 + "';");
			t1.setStyle("-fx-background-color: '" + theme10 + "';");
			t2.setStyle("-fx-background-color: '" + theme10 + "';");
			
			s0.setVisible(false);
			s1.setVisible(true);
			s2.setVisible(false);
			
		}else {
			change.setStyle("-fx-background-color: '" + theme21 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			t0.setStyle("-fx-background-color: '" + theme20 + "';");
			t1.setStyle("-fx-background-color: '" + theme20 + "';");
			t2.setStyle("-fx-background-color: '" + theme20 + "';");
			
			s0.setVisible(false);
			s1.setVisible(false);
			s2.setVisible(true);
		}
		
		Label membershipsLabel = new Label(" Themes");
		membershipsLabel.setStyle("-fx-font-family: 'Microsoft JhengHei UI Light';" + "-fx-font-size: 40px;");
		String underlineMemberships = "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + "          ";
		Label underline = new Label(underlineMemberships);
		underline.setUnderline(true);
		Label description = new Label("Choose any theme of your choice.");
		description.setStyle("-fx-font-size: 15px;");
		
		AnchorPane root = new AnchorPane();
		
		//controls
		back.setOnAction(e -> {
			updateBackButton(currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			settingsScreen(s);
		});
		t0.setOnAction(e -> {
			potentialTheme = 0;
			root.setStyle("-fx-background-color: '" + theme00 + "';");
			t0.setStyle("-fx-background-color: '" + theme00 + "';");
			t1.setStyle("-fx-background-color: '" + theme00 + "';");
			t2.setStyle("-fx-background-color: '" + theme00 + "';");
			menuBar.setStyle("-fx-background-color: '" + theme01 + "';");
			change.setStyle("-fx-background-color: '" + theme01 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			updateBackButton(0);
			s0.setVisible(true);
			s1.setVisible(false);
			s2.setVisible(false);
		});
		t1.setOnAction(e -> {
			potentialTheme = 1;
			root.setStyle("-fx-background-color: '" + theme10 + "';");
			t0.setStyle("-fx-background-color: '" + theme10 + "';");
			t1.setStyle("-fx-background-color: '" + theme10 + "';");
			t2.setStyle("-fx-background-color: '" + theme10 + "';");
			menuBar.setStyle("-fx-background-color: '" + theme11 + "';");
			change.setStyle("-fx-background-color: '" + theme11 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			updateBackButton(1);
			s0.setVisible(false);
			s1.setVisible(true);
			s2.setVisible(false);
		});
		t2.setOnAction(e -> {
			potentialTheme = 2;
			root.setStyle("-fx-background-color: '" + theme20 + "';");
			t0.setStyle("-fx-background-color: '" + theme20 + "';");
			t1.setStyle("-fx-background-color: '" + theme20 + "';");
			t2.setStyle("-fx-background-color: '" + theme20 + "';");
			menuBar.setStyle("-fx-background-color: '" + theme21 + "';");
			change.setStyle("-fx-background-color: '" + theme21 + "';" + "-fx-text-fill: 'white';" + "-fx-font-size: 14.2px;" + "-fx-font-weight: normal;");
			updateBackButton(2);
			s0.setVisible(false);
			s1.setVisible(false);
			s2.setVisible(true);
		});
		change.setOnAction(e -> {
			this.currentTheme = potentialTheme;
			if(this.currentTheme == 0) {
				this.currentBackground = theme00;
				this.currentDarkColor = theme01;
			}else if(this.currentTheme == 1) {
				this.currentBackground = theme10;
				this.currentDarkColor = theme11;
			}else {
				this.currentBackground = theme20;
				this.currentDarkColor = theme21;
			}
			updateSettingsButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			settingsScreen(s);
		});
		
		//back
		AnchorPane.setTopAnchor(back, 38.0);
		AnchorPane.setLeftAnchor(back, 5.0);
		//membershipsLabel
		AnchorPane.setTopAnchor(membershipsLabel, 70.0);
		AnchorPane.setLeftAnchor(membershipsLabel, 70.0);
		//underline
		AnchorPane.setTopAnchor(underline, 119.0 - 14.0);
		AnchorPane.setLeftAnchor(underline, 70.0);
		//description
		AnchorPane.setTopAnchor(description, 119.0 + 20.0);
		AnchorPane.setLeftAnchor(description, 90.0);
		//change
		AnchorPane.setRightAnchor(change, 40.0);
		AnchorPane.setBottomAnchor(change, 21.0);
		//t0
		AnchorPane.setTopAnchor(t0, 270.0 - 70.0);
		AnchorPane.setLeftAnchor(t0, width/2.0 - 120.0);
		//t1
		AnchorPane.setTopAnchor(t1, 340.0 - 70.0);
		AnchorPane.setLeftAnchor(t1, width/2.0 - 120.0);
		//t1
		AnchorPane.setTopAnchor(t2, 410.0 - 70.0);
		AnchorPane.setLeftAnchor(t2, width/2.0 - 120.0);
		//s0
		AnchorPane.setTopAnchor(s0, 270.0 - 67.5);
		AnchorPane.setLeftAnchor(s0, width/2.0 - 120.0 - 45.0);
		//s1
		AnchorPane.setTopAnchor(s1, 340.0 - 67.5);
		AnchorPane.setLeftAnchor(s1, width/2.0 - 120.0 - 45.0);
		//s1
		AnchorPane.setTopAnchor(s2, 410.0 - 67.5);
		AnchorPane.setLeftAnchor(s2, width/2.0 - 120.0 - 45.0);
		
		root.setStyle("-fx-background-color: '" + currentBackground + "';");
		root.getChildren().addAll(back , menuBar , membershipsLabel , underline ,
				change , s0 , s1 , s2 , t0 , t1 , t2 , description);
		
		Scene scene = new Scene(root , width , height);
		s.setScene(scene);
		s.show();
	}
	
	public void openBrowserAboutApp() {
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    Desktop.getDesktop().browse(new URI("aboutAppWebPage.html"));
			}
		}catch(Exception e) {}
	}
	
	public void openBrowserHowToUse() {
		try {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    Desktop.getDesktop().browse(new URI("howToUseWebPage.html"));
			}
		}catch(Exception e) {}
	}
	
	@Override
	public void start(Stage s) throws Exception{
		//app logo
		try {
			Image img = new Image("file:resources/logos/appLogo.png");
	       	s.getIcons().add(img);
		}catch(Exception e) {}
		
		
		createMenuBar();
		//database
		connectingToDB();
		
		//controls
		exitMenuItem.setOnAction(e -> {
			try {
				connection.close();
			}catch(Exception f){}
			System.exit(0);
		});
		homeMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			homeScreen(s);
		});
		gymDetailsMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			gymDetailsScreen(s);
		});
		
		newMemberMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			addNewMember(s , false);
		});
		newTrainerMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			addNewTrainer(s);
		});
		newEquipmentMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			addNewEquipmentScreen(s);
		});
		newMembershipMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			addNewMembershipScreen(s);
		});
		
		viewMemberMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			membersScreen(s);
		});
		viewTrainerMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			trainersScreen(s);
		});
		viewEquipmentMenuItem.setOnAction(e -> {
			updateSettingsButton(this.currentTheme);
			updateBackButton(this.currentTheme);
			menuBar.setStyle("-fx-background-color: '" + currentDarkColor + "';");
			equipmentsScreen(s);
		});
		
		aboutTheDevMenuItem.setOnAction(e -> {
			Alert dev = new Alert(AlertType.INFORMATION);
			dev.setTitle("About Us");
			dev.setHeaderText("Developers");
			dev.setContentText("This Gym Database Management App has been developed by "
					+ "\nAnand Kumar, Ashish Kumar and Ashutosh Paul who are in 3rd Year, 5th Sem Engineering Students of CS&E "
					+ "Department, BE.\n\n");
			dev.show();
		});
		aboutAppMenuItem.setOnAction(e -> openBrowserAboutApp());
		howToUseMenuItem.setOnAction(e -> openBrowserHowToUse());
		
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root , width - 10 , height - 10);
		s.setTitle("Gym Database Management System");
		s.setResizable(false);
		s.setScene(scene);
		
		loginScreen(s);
	}
	
	public static void main(String[] args) {
		launch(args);

	}
}
