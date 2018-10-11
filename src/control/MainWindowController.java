package control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import logic.*;
import model.Order;
import logic.ValueCalculator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainWindowController {

    private OrderDAO orderDAO;
    private List<Order> currentRep;
    private Integer quantity = 0;
    private Double overallPrice = 0.0;
    private Double meanPrice = 0.0;

    @FXML
    private TableView tableView;
    @FXML
    private TextField quantityTxt;
    @FXML
    private TextField priceTxt;
    @FXML
    private TextField meanPriceTxt;
    @FXML
    private TextField dirTxt;
    @FXML
    private TextField idTxt;
    @FXML
    private Button exportBtn;
    @FXML
    private TableColumn colClientID;
    @FXML
    private TableColumn colReqID;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colQuantity;
    @FXML
    private TableColumn colPrice;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        colClientID.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colReqID.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        orderDAO = new OrderDAO();
    }
    private final DecimalFormat df2 = new DecimalFormat(".##");
    private List<File> selectedFiles;
    private ObservableList<Order> observableList = FXCollections.observableArrayList();


    public void onClickExport(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window window = source.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV", "*.csv"));
        fileChooser.setInitialDirectory(new File("C:\\Users"));
        File dest = fileChooser.showSaveDialog(window);
        if (dest != null) {
            try {
                Export.exportToCsv(dest, currentRep, quantity, overallPrice, meanPrice);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void onClickFiles(ActionEvent actionEvent) {
        dirTxt.clear();
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users"));
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV files", "*.csv"),
                new FileChooser.ExtensionFilter("XML files", "*.xml"));
        selectedFiles = fc.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            dirTxt.appendText(selectedFiles.get(0).getAbsolutePath());
            dirTxt.setEditable(false);
            List<String> fileDir = new ArrayList<>();
            for (File file : selectedFiles) {
                fileDir.add(file.getAbsolutePath());
            }
            try {
                List<Order> orders;
                ArrayList<WrongFile> wrongFiles=new ArrayList<>();
                if (selectedFiles.get(0).getName().replaceAll(".*\\.", "").equals("xml")){
                    TransferObject tObj= XmlReader.readXML(fileDir);
                    orders=tObj.orders;
                    wrongFiles=tObj.wrongRecords;
                    orderDAO.save(orders);
                }else if (selectedFiles.get(0).getName().replaceAll(".*\\.", "").equals("csv")){
                    TransferObject tObj = CsvReader.readCsv(fileDir);
                    orders=tObj.orders;
                    wrongFiles=tObj.wrongRecords;
                    orderDAO.save(orders);
                }
                if (!wrongFiles.isEmpty()){
                    String mess=new String();
                    for(WrongFile file:wrongFiles){
                        mess+='\n'+file.toString();
                    }
                    ModalError.display("There was data mismatch: "+mess);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ModalError.display("Not valid data");
        }
    }


    public void onClickReport(ActionEvent actionEvent) {
        tableView.getItems().clear();
        quantityTxt.clear();
        meanPriceTxt.clear();
        priceTxt.clear();
        if (idTxt.getText().trim().isEmpty()) {
            currentRep = orderDAO.read();
            for (Order order : currentRep) {
                observableList.add(order);
            }
        } else {
            currentRep = orderDAO.readById(idTxt.getText());
            for (Order order : currentRep) {
                observableList.add(order);
            }
        }
        quantity = ValueCalculator.calculateQuantity(currentRep);
        overallPrice = ValueCalculator.calculateOverallPrice(currentRep);
        meanPrice = ValueCalculator.calculateMeanPrice(currentRep);
        quantityTxt.appendText("Quantity: " + quantity.toString());
        priceTxt.appendText("Price: " + overallPrice.toString());
        meanPriceTxt.appendText("Mean price: " + df2.format(meanPrice));
        tableView.setItems(observableList);
        exportBtn.setDisable(false);
    }
}
