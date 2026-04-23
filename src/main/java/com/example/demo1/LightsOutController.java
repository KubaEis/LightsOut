package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.Random;

public class LightsOutController {
    Random rand = new Random();
    // sem se zapisujou tlacitka
    private int gridSizeRow = 3;
    private int gridSizeCol = 3;
    private Button[][] buttons =  new Button[gridSizeRow][gridSizeCol];
    //sem se zapisujou stavy tlacitek
    private boolean[][] states =new boolean[gridSizeRow][gridSizeCol];

    private int numberOfClicks;
    @FXML
    private GridPane buttonGridPane;
    @FXML
    private Label pocetKliknutiLabel;
    @FXML
    private TextField velikostGridu;


    @FXML
    public void initialize(){
        newGame();
    }

    private void prepni(int row, int col) {
        if (row >= 0 && row < gridSizeRow && col >= 0 && col < gridSizeCol) {
            states[row][col] = !states[row][col];
            nastavBarvu(row, col);
            checkGameState();
        }
    }

    private void checkGameState(){
        boolean win = true;
        for(int row=0; row<gridSizeRow; row++){
            for(int col=0; col<gridSizeCol; col++){
                if (states[row][col]) {
                    win = false;
                    break;
                }
            }
        }
        if (win){
            System.out.println("GAME WON");
        }
    }

    @FXML
    public void nastavBarvu(int row, int col){
        if (states[row][col]){
            buttons[row][col].setStyle("-fx-background-color: green");
        }else{
            buttons[row][col].setStyle("-fx-background-color: gray");
        }
    }

    @FXML
    protected void newGame(){
        buttonGridPane.getChildren().clear();
        buttons = new Button[gridSizeRow][gridSizeCol];
        states = new boolean[gridSizeRow][gridSizeCol];
        for(int row=0; row<gridSizeRow; row++){
            for(int col=0; col<gridSizeCol; col++){
                Button button = new Button();
                button.setPrefSize((double) 300 /gridSizeRow, (double) 300 /gridSizeRow);
                buttons[row][col] = button;
                int rn = rand.nextInt(0,2);
                if (rn == 0){
                    states[row][col] = true;
                }else{
                    states[row][col] = false;
                }
                nastavBarvu(row,col);
                int r = row;
                int c = col;
                button.setOnAction(e -> {
                    // Přepneme tlačítko samotné a jeho sousedy
                    prepni(r, c);       // Střed
                    prepni(r - 1, c);   // Horní
                    prepni(r + 1, c);   // Dolní
                    prepni(r, c - 1);   // Levý
                    prepni(r, c + 1);// Pravý
                    click();
                });
                buttonGridPane.add(button,col,row);
                numberOfClicks = 0;
                pocetKliknutiLabel.setText("Number of clicks: " + numberOfClicks);
            }
        }
    }

    private void click(){
        numberOfClicks++;
        pocetKliknutiLabel.setText("Number of clicks: " + numberOfClicks);
    }

    @FXML
    protected void submitSize(){
        gridSizeRow = Integer.parseInt(velikostGridu.getText());
        gridSizeCol = Integer.parseInt(velikostGridu.getText());
        newGame();
    }

    public void resetHry() {
        // 1. Vymaž vizuální prvky z obrazovky
        buttonGridPane.getChildren().clear();

        // 2. Vymaž (reinitializuj) pole v paměti
        buttons = new Button[3][3];
        states = new boolean[3][3];

        // 3. Znovu zavolej inicializaci, která vytvoří nová tlačítka
        initialize();
    }

}
