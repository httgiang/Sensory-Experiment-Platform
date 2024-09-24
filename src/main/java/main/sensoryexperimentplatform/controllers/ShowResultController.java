package main.sensoryexperimentplatform.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import main.sensoryexperimentplatform.models.Experiment;
import main.sensoryexperimentplatform.models.SurveyDataLoader;
import main.sensoryexperimentplatform.models.SurveyEntry;
import main.sensoryexperimentplatform.viewmodel.ShowResultVM;

public class ShowResultController {

    @FXML
    private TableView<SurveyEntry> tableView;

    @FXML
    private TableColumn<SurveyEntry, String> uidColumn;
    @FXML
    private TableColumn<SurveyEntry, String> typeColumn;
    @FXML
    private TableColumn<SurveyEntry, String> timeColumn;
    @FXML
    private TableColumn<SurveyEntry, Integer> resultColumn;
    @FXML
    private TableColumn<SurveyEntry, String> questionColumn;
    @FXML
    private TableColumn<SurveyEntry, String> lowAnchorColumn;
    @FXML
    private TableColumn<SurveyEntry, String> highAnchorColumn;
    @FXML
    private TableColumn<SurveyEntry, Integer> lowValueColumn;
    @FXML
    private TableColumn<SurveyEntry, Integer> highValueColumn;

    private String FILE_PATH;
    private ShowResultVM viewModel;
    @FXML
    public void initialize() {
        setupTableColumns();
        bindColumnWidths();
    }
    public void setViewModel(ShowResultVM viewModel) {
        this.viewModel = viewModel;
        //load items to table
        if (this.viewModel != null) {
            try{
                ObservableList<SurveyEntry> data = SurveyDataLoader.loadSurveyData(viewModel.getFilePath());
                if (data !=null){
                    tableView.setItems(data);
                }else{
                    System.err.println("The system cannot find the file specified");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        setColumnHeaderWithWrap( lowAnchorColumn,"Low Anchor or Left Button Text");
        setColumnHeaderWithWrap( highAnchorColumn,"High Anchor or Right Button Text");
        setColumnHeaderWithWrap( lowValueColumn,"Low Value or Left Button Value");
        setColumnHeaderWithWrap( highValueColumn,"High Value or Right Button Value");
        wrapTextInColumn(questionColumn);

    }


    private void setupTableColumns() {
        // Set up the cell value factories for each column
        uidColumn.setCellValueFactory(new PropertyValueFactory<>("uid"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        lowAnchorColumn.setCellValueFactory(new PropertyValueFactory<>("lowAnchor"));
        highAnchorColumn.setCellValueFactory(new PropertyValueFactory<>("highAnchor"));
        lowValueColumn.setCellValueFactory(new PropertyValueFactory<>("lowValue"));
        highValueColumn.setCellValueFactory(new PropertyValueFactory<>("highValue"));
    }

    private void bindColumnWidths() {
        // Bind the widths of the table columns to percentages of the table width
        uidColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05)); // 10% of table width
        typeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05)); // 15% of table width
        timeColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)); // 10% of table width
        resultColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.05)); // 10% of table width
        questionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)); // 25% of table width
        lowAnchorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)); // 10% of table width
        highAnchorColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.15)); // 10% of table width
        lowValueColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.125)); // 5% of table width
        highValueColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.125)); // 5% of table width
    }

    private void setColumnHeaderWithWrap(TableColumn<SurveyEntry, ?> column, String headerText) {
        Label label = new Label(headerText);
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxWidth(170);  // Set a max width to encourage wrapping
        label.setMinWidth(column.getPrefWidth());  // Ensure it fits within the column
        column.setGraphic(label);
    }
    private void wrapTextInColumn(TableColumn<SurveyEntry, String> column) {
        column.setCellFactory(new Callback<>() {
            @Override
            public TableCell<SurveyEntry, String> call(TableColumn<SurveyEntry, String> param) {
                return new TableCell<>() {
                    private final Text text = new Text();


                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            text.setText(item);
                            setGraphic(text);
                            setAlignment(Pos.CENTER);
                            text.setTextAlignment(TextAlignment.CENTER);


                            if (getTableColumn() != null) {
                                text.wrappingWidthProperty().bind(getTableColumn().widthProperty());
                            }
                        }
                    }
                };
            }
        });
    }


}
