package main;

import connection.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller {
    private ObservableList<Order> usersData = FXCollections.observableArrayList();
    private ObservableList<Order> usersData1 = FXCollections.observableArrayList();
    private ObservableList<Order> usersData2 = FXCollections.observableArrayList();
    private static final String allDB = "SELECT * from request";
    public static String $temp;
    private static String temp;
    private static final String allDB1 = "SELECT * from done";
    private static final String allDB2 = "SELECT * from blacklist";
    private static final String query = "INSERT INTO  done(id,name,number,street,housenumber,podezd,taxi_class,comment) VALUES (?,?,?,?,?,?,?,?)";
    private static final String query2 = "INSERT INTO  bisnes(id,name,number,street,housenumber,podezd,comment) VALUES (?,?,?,?,?,?,?)";
    private static final String queryToBlacklist = "INSERT INTO  blacklist(number,comment) VALUE (?,?)";
    private static final String delete = "DELETE FROM request WHERE ID=(?)";
    private static final String sendToDriver = "UPDATE request SET status='Відправлено до водія' WHERE id=(?)";
    Connection connection;
    //прив'язка розмітки в FXML таблиці до змінних
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableColumn<Order, String> status;
    @FXML
    private TableColumn<Order, Integer> idColumn;
    @FXML
    private TableColumn<Order, String> nameColumn;
    @FXML
    private TableColumn<Order, String> numberColumn;
    @FXML
    private TableColumn<Order, String> streetColumn;
    @FXML
    private TableColumn<Order, String> housenumberColumn;
    @FXML
    private TableColumn<Order, String> podezdColumn;
    @FXML
    private TableColumn<Order, String> taxi_classColumn;
    @FXML
    private TableColumn<Order, String> commentColumn;
    @FXML
    private TableView<Order> tableView1;
    @FXML
    private TableColumn<Order, Integer> idColumn1;
    @FXML
    private TableColumn<Order, String> nameColumn1;
    @FXML
    private TableColumn<Order, String> numberColumn1;
    @FXML
    private TableColumn<Order, String> streetColumn1;
    @FXML
    private TableColumn<Order, String> housenumberColumn1;
    @FXML
    private TableColumn<Order, String> podezdColumn1;
    @FXML
    private TableColumn<Order, String> taxi_classColumn1;
    @FXML
    private TableColumn<Order, String> commentColumn1;
    @FXML
    private TableView<Order> tableView2;
    @FXML
    private TableColumn<Order, String> numberColumn2;
    @FXML
    private TableColumn<Order, String> commentColumn2;
    @FXML
    public void init(){
        inBlackList();
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        streetColumn.setCellValueFactory(new PropertyValueFactory<>("street"));
        housenumberColumn.setCellValueFactory(new PropertyValueFactory<>("housenumber"));
        podezdColumn.setCellValueFactory(new PropertyValueFactory<>("podezd"));
        taxi_classColumn.setCellValueFactory(new PropertyValueFactory<>("taxi_class"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tableView.setItems(usersData); //таблиця

        try {
            Statement statement = connection.getConnection().createStatement(); //створення запиту в БД
            ResultSet resultSet = statement.executeQuery(allDB); //результат
            usersData.clear();

            while (resultSet.next()){
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setName(resultSet.getString(2));
                order.setNumber(resultSet.getString(3));
                order.setStreet(resultSet.getString(4));
                order.setHousenumber(resultSet.getString(5));
                order.setPodezd(resultSet.getString(6));
                order.setTaxi_class(resultSet.getString(7));
                order.setComment(resultSet.getString(8));
                order.setStatus(resultSet.getString(9));
                usersData.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        idColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        numberColumn1.setCellValueFactory(new PropertyValueFactory<>("number"));
        streetColumn1.setCellValueFactory(new PropertyValueFactory<>("street"));
        housenumberColumn1.setCellValueFactory(new PropertyValueFactory<>("housenumber"));
        podezdColumn1.setCellValueFactory(new PropertyValueFactory<>("podezd"));
        taxi_classColumn1.setCellValueFactory(new PropertyValueFactory<>("taxi_class"));
        commentColumn1.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tableView1.setItems(usersData1);

        try {
            Statement statement1 = connection.getConnection().createStatement();
            ResultSet resultSet1 = statement1.executeQuery(allDB1);
            usersData1.clear();

            while (resultSet1.next()){
                Order order = new Order();
                order.setId(resultSet1.getInt(1));
                order.setName(resultSet1.getString(2));
                order.setNumber(resultSet1.getString(3));
                order.setStreet(resultSet1.getString(4));
                order.setHousenumber(resultSet1.getString(5));
                order.setPodezd(resultSet1.getString(6));
                order.setTaxi_class(resultSet1.getString(7));
                order.setComment(resultSet1.getString(8));
                usersData1.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        numberColumn2.setCellValueFactory(new PropertyValueFactory<>("number"));
        commentColumn2.setCellValueFactory(new PropertyValueFactory<>("comment"));

        tableView2.setItems(usersData2);

        try {
            Statement statement1 = connection.getConnection().createStatement();
            ResultSet resultSet1 = statement1.executeQuery(allDB2);
            usersData2.clear();

            while (resultSet1.next()){
                Order order = new Order();
                order.setNumber(resultSet1.getString(1));
                order.setComment(resultSet1.getString(2));
                usersData2.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } //метод для виведення БД у ВСІ таблиці оператора
    public void toDone(){
        if (tableView.getSelectionModel().isEmpty() != true) {
            String id = String.valueOf(tableView.getSelectionModel().getSelectedItem().getId());
            int i = Integer.parseInt(id);
            String name = String.valueOf(tableView.getSelectionModel().getSelectedItem().getName());
            String number = String.valueOf(tableView.getSelectionModel().getSelectedItem().getNumber());
            String street = String.valueOf(tableView.getSelectionModel().getSelectedItem().getStreet());
            String housenumber = String.valueOf(tableView.getSelectionModel().getSelectedItem().getHousenumber());
            String podezd = String.valueOf(tableView.getSelectionModel().getSelectedItem().getPodezd());
            String taxi_class = String.valueOf(tableView.getSelectionModel().getSelectedItem().getTaxi_class());
            String comment = String.valueOf(tableView.getSelectionModel().getSelectedItem().getComment());

            Connection connection = new Connection();
            connection.connection();
            PreparedStatement st;
            PreparedStatement st2;

            try {
                st = connection.getConnection().prepareStatement(query);
                st.setInt(1, i);
                st.setString(2, name);
                st.setString(3, number);
                st.setString(4, street);
                st.setString(5, housenumber);
                st.setString(6, podezd);
                st.setString(7, taxi_class);
                st.setString(8, comment);

                st.executeUpdate();

                st2 = connection.getConnection().prepareStatement(delete);
                st2.setInt(1, i);
                st2.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            noSelect();
        }
        init();

    } //метод для переведення із черги в ВИКОНАНІ
    public void noSelect(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Помилка");
        alert.setHeaderText(null);
        alert.setContentText("Ви не вибрали жодного рядка");
        alert.showAndWait();
    }  //вікно, яке викликається коли не вибрано жодного рядка
    public void toBlacklist(){
        if (tableView.getSelectionModel().isEmpty() != true) {
            String number = String.valueOf(tableView.getSelectionModel().getSelectedItem().getNumber());
            String id = String.valueOf(tableView.getSelectionModel().getSelectedItem().getId());
            int i = Integer.parseInt(id);

            Connection connection = new Connection();
            connection.connection();
            PreparedStatement st;
            PreparedStatement st2;
                String temp=null;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("В чорний список");
                dialog.setHeaderText(null);
                dialog.setContentText("Введіть причину: ");

                Optional<String> result = dialog.showAndWait();
                if (result.isPresent()){
                    temp = result.get();
                }
                if(temp.length()!=0) {
                    try {
                        st = connection.getConnection().prepareStatement(queryToBlacklist);
                        st.setString(1, number);
                        st.setString(2, temp);
                        st.executeUpdate();

                        st2 = connection.getConnection().prepareStatement(delete);
                        st2.setInt(1, i);
                        st2.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Помилка");
                    alert.setHeaderText(null);
                    alert.setContentText("Ви не ввели причину");
                    alert.showAndWait();
                    init();
                }
        }
        else{
            noSelect();
        }
        init();
    }//метод для переведення із черги в Чорний список
    public void chooseDriver(){
        if (tableView.getSelectionModel().isEmpty() != true) {
            List<String> choices = new ArrayList<>();
            try {
                $temp = String.valueOf(tableView.getSelectionModel().getSelectedItem().getTaxi_class());
                Statement statement = connection.getConnection().createStatement();
                String drivers = "SELECT driver_id FROM drivers WHERE status='Вільний' AND taxi_class='"+$temp+"'";
                ResultSet resultSet = statement.executeQuery(drivers);

                while (resultSet.next()) {
                    String driver;
                    driver = resultSet.getString(1);
                    choices.add(driver);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("Виберіть водія", choices);
            dialog.setTitle("Вибір водія");
            dialog.setHeaderText(null);
            dialog.setContentText("Виберіть водія:");
            dialog.showAndWait();

            String id = String.valueOf(tableView.getSelectionModel().getSelectedItem().getId());
            int i = Integer.parseInt(id);
            String name = String.valueOf(tableView.getSelectionModel().getSelectedItem().getName());
            String number = String.valueOf(tableView.getSelectionModel().getSelectedItem().getNumber());
            String street = String.valueOf(tableView.getSelectionModel().getSelectedItem().getStreet());
            String housenumber = String.valueOf(tableView.getSelectionModel().getSelectedItem().getHousenumber());
            String podezd = String.valueOf(tableView.getSelectionModel().getSelectedItem().getPodezd());
            String comment = String.valueOf(tableView.getSelectionModel().getSelectedItem().getComment());

            Connection connection = new Connection();
            connection.connection();
            PreparedStatement st;
            PreparedStatement st2;

            try {
                st = connection.getConnection().prepareStatement(query2);
                st.setInt(1, i);
                st.setString(2, name);
                st.setString(3, number);
                st.setString(4, street);
                st.setString(5, housenumber);
                st.setString(6, podezd);
                st.setString(7, comment);
                st.executeUpdate();

                st2 = connection.getConnection().prepareStatement(sendToDriver);
                st2.setInt(1,i);
                st2.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            init();

        }
        else noSelect();



    }//метод для вибору водія
    public void inBlackList(){
        try {
            String number;
            Statement statement1 = connection.getConnection().createStatement();
            String inBlack = "SELECT request.number FROM request,blacklist where request.number=blacklist.number";

            ResultSet resultSet = statement1.executeQuery(inBlack);
            while (resultSet.next()){
                number= resultSet.getString(1);
                String delete = "DELETE FROM request where number='"+number+"'";
                PreparedStatement st = connection.getConnection().prepareStatement(delete);
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//метод для перевірки черги на номери із чорного списку







}
