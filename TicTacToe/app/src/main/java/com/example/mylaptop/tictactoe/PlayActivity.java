package com.example.mylaptop.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Cell{
    private boolean isTick = false;
    private int cellId;
    private String text = new String();

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public void setTextForCell(String text) {
        this.text = text;
    }

    public void setIsTick(boolean isTick){
        this.isTick = isTick;
    }

    public String getTextOfCell(){
        return text;
    }

    public boolean getIsTick(){
        return isTick;
    }

    public int getCellId(){
        return cellId;
    }
}

class Player{
    private String name;
    private String symbol;

    public String getNameOfPlayer() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setNameOfPlayer(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
}

public class PlayActivity extends Activity {

    int session = 1;

    List<Player> playerList = new ArrayList<>();
    List<Cell> listcells = new ArrayList<>();
    List<List<Integer>> listLineWin = new ArrayList<List<Integer>>();

    boolean isEnd = false;

    public void onCreate(Bundle savedOnInstance){
        super.onCreate(savedOnInstance);
        setContentView(R.layout.play_activity);
        initialize();
        setNameForPlayer();
    }

    public void initialize(){
        for(int i = 2131492976 ; i <= 2131492984 ; i++){
            Cell newCell = new Cell();
            newCell.setCellId(i);
            listcells.add(newCell);
        }

        for(int i = 0 ; i < 8 ; i++) {
            List<Integer> subList = new ArrayList<>();
            if (i == 0) {
                subList.add(0);
                subList.add(1);
                subList.add(2);
            }
            else if (i == 1) {
                subList.add(3);
                subList.add(4);
                subList.add(5);
            }
            else if (i == 2) {
                subList.add(6);
                subList.add(7);
                subList.add(8);
            }
            else if (i == 3) {
                subList.add(0);
                subList.add(3);
                subList.add(6);
            }
            else if (i == 4) {
                subList.add(1);
                subList.add(4);
                subList.add(7);
            }
            else if (i == 5) {
                subList.add(2);
                subList.add(5);
                subList.add(8);
            }
            else if (i == 6) {
                subList.add(0);
                subList.add(4);
                subList.add(8);
            }
            else {
                subList.add(2);
                subList.add(4);
                subList.add(6);
            }
            listLineWin.add(subList);
        }
        Player player1 = new Player();
        player1.setSymbol("X");
        player1.setNameOfPlayer(MainActivity.getPlayer1Name());
        playerList.add(player1);

        Player player2 = new Player();
        player2.setSymbol("O");
        player2.setNameOfPlayer(MainActivity.getPlayer2Name());
        playerList.add(player2);
    }

    public void setNameForPlayer(){
        TextView textView = (TextView)findViewById(R.id.nameOfPlayer1);
        textView.setText(MainActivity.getPlayer1Name());
        textView = (TextView)findViewById(R.id.nameOfPlayer2);
        textView.setText(MainActivity.getPlayer2Name());
    }

    public void cellTick(View view){
        int cellId = view.getId();
        Button cellButton = (Button)findViewById(cellId);
        if(cellId != R.id.playAgainBtn) {
            if (session == 1) {
                for (Cell cell : listcells) {
                    if (!cell.getIsTick() && cell.getCellId() == cellId) {
                        cellButton.setText("X");
                        cell.setIsTick(true);
                        cell.setTextForCell("X");
                        if(MainActivity.getModePlay() == 2){
                            session = 2;
                        }
                        checkWin();
                        if(!isEnd) {
                            if (MainActivity.getModePlay() == 1) {
                                machineMove();
                            }
                        }
                        return;
                    }
                }
            }
            else {
                for (Cell cell : listcells) {
                    if (!cell.getIsTick() && cell.getCellId() == cellId) {
                        cellButton.setText("O");
                        session = 1;
                        cell.setIsTick(true);
                        cell.setTextForCell("O");
                        checkWin();
                        if(!isEnd) {
                            if (MainActivity.getModePlay() == 1) {
                                machineMove();
                            }
                        }
                        return;
                    }
                }
            }
        }
        else{
            session = 1;
            isEnd = false;
            for(Cell cell : listcells){
                cellButton = (Button)findViewById(cell.getCellId());
                cellButton.setTextColor(getResources().getColor(R.color.colorWhite));
                cellButton.setClickable(true);
                cellButton.setText("");

                cell.setIsTick(false);
                cell.setTextForCell("");
            }
        }
    }

    public void checkWin(){
        List<String> listTextOfCell = new ArrayList<>();
        for(List<Integer> subList : listLineWin){
            for(Integer i : subList){
                listTextOfCell.add(listcells.get(i).getTextOfCell());
            }
            if(listTextOfCell.get(0).equals(listTextOfCell.get(1)) && listTextOfCell.get(0).equals(listTextOfCell.get(2)) && !listTextOfCell.get(0).equals("")){
                isEnd = true;
                for(Integer i : subList){
                    Button cellButton = (Button)findViewById(listcells.get(i).getCellId());
                    cellButton.setTextColor(getResources().getColor(R.color.colorYellow));
                }
                for(Cell cell : listcells){
                    Button cellButton = (Button)findViewById(cell.getCellId());
                    cellButton.setClickable(false);
                }
                if(listTextOfCell.get(0).equals(playerList.get(0).getSymbol())){
                    Toast.makeText(getBaseContext(), MainActivity.getPlayer1Name() + " Win", Toast.LENGTH_LONG).show();
                    if(MainActivity.getModePlay() == 2) {
                        setSymbolInView("(X)", "(O)");
                        playerList.get(0).setSymbol("X");
                        playerList.get(1).setSymbol("O");
                    }
                    updatePoint(R.id.pointOfPlayer1);
                    return;
                }
                else{

                    Toast.makeText(getBaseContext(), MainActivity.getPlayer2Name() + " Win", Toast.LENGTH_LONG).show();
                    if(MainActivity.getModePlay() == 2) {
                        setSymbolInView("(O)", "(X)");
                        playerList.get(0).setSymbol("O");
                        playerList.get(1).setSymbol("X");
                    }
                    updatePoint(R.id.pointOfPlayer2);
                    return;
                }
            }
            listTextOfCell.clear();
        }
    }

    public void updatePoint(int id){
        TextView textView = (TextView)findViewById(id);
        StringBuilder stringBuilder = new StringBuilder(textView.getText().toString());
        stringBuilder.delete(0, 2);
        int point = Integer.parseInt(stringBuilder.toString());
        point++;
        textView.setText(": " + String.valueOf(point));
    }

    public void setSymbolInView(String symbolForPlayer1, String symbolForPlayer2){
        TextView textView = (TextView)findViewById(R.id.player1Symbol);
        textView.setText(symbolForPlayer1);
        textView = (TextView)findViewById(R.id.player2Symbol);
        textView.setText(symbolForPlayer2);
    }

    // Machine 's Move

    public void machineMove(){
        // Row 1 For O
        if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(1).getTextOfCell().equals("O") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(2).getTextOfCell().equals("O") && listcells.get(1).getTextOfCell().equals("")){
            setTickForMachine(1, "O");
        }
        else if(listcells.get(1).getTextOfCell().equals("O") && listcells.get(2).getTextOfCell().equals("O") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Row 2 For O
        else if(listcells.get(3).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("O") && listcells.get(5).getTextOfCell().equals("")){
            setTickForMachine(5, "O");
        }
        else if(listcells.get(3).getTextOfCell().equals("O") && listcells.get(5).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("O") && listcells.get(5).getTextOfCell().equals("O") && listcells.get(3).getTextOfCell().equals("")){
            setTickForMachine(3, "O");
        }

        // Row 3 For O
        else if(listcells.get(6).getTextOfCell().equals("O") && listcells.get(7).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }
        else if(listcells.get(6).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(7).getTextOfCell().equals("")){
            setTickForMachine(7, "O");
        }
        else if(listcells.get(7).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }

        // Col 1 For O
        else if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(3).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("O") && listcells.get(3).getTextOfCell().equals("")){
            setTickForMachine(3, "O");
        }
        else if(listcells.get(3).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("O") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Col 2 For O
        else if(listcells.get(1).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("O") && listcells.get(7).getTextOfCell().equals("")){
            setTickForMachine(7, "O");
        }
        else if(listcells.get(1).getTextOfCell().equals("O") && listcells.get(7).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(7).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("O") && listcells.get(1).getTextOfCell().equals("")){
            setTickForMachine(1, "O");
        }

        // Col 3 For O
        else if(listcells.get(2).getTextOfCell().equals("O") && listcells.get(5).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(8, "O");
        }
        else if(listcells.get(2).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(5).getTextOfCell().equals("")){
            setTickForMachine(5, "O");
        }
        else if(listcells.get(5).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }

        // Diagonal 1 For O
        else if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(8, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("O") && listcells.get(8).getTextOfCell().equals("O") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Diagonal 2 For O
        else if(listcells.get(2).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }
        else if(listcells.get(2).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("O") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(5, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("O") && listcells.get(6).getTextOfCell().equals("O") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }

        // Row 1 For X
        if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(1).getTextOfCell().equals("X") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(2).getTextOfCell().equals("X") && listcells.get(1).getTextOfCell().equals("")){
            setTickForMachine(1, "O");
        }
        else if(listcells.get(1).getTextOfCell().equals("X") && listcells.get(2).getTextOfCell().equals("X") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Row 2 For X
        else if(listcells.get(3).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("X") && listcells.get(5).getTextOfCell().equals("")){
            setTickForMachine(5, "O");
        }
        else if(listcells.get(3).getTextOfCell().equals("X") && listcells.get(5).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("X") && listcells.get(5).getTextOfCell().equals("X") && listcells.get(3).getTextOfCell().equals("")){
            setTickForMachine(3, "O");
        }

        // Row 3 For X
        else if(listcells.get(6).getTextOfCell().equals("X") && listcells.get(7).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(8, "O");
        }
        else if(listcells.get(6).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(7).getTextOfCell().equals("")){
            setTickForMachine(7, "O");
        }
        else if(listcells.get(7).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }

        // Col 1 For X
        else if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(3).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("X") && listcells.get(3).getTextOfCell().equals("")){
            setTickForMachine(3, "O");
        }
        else if(listcells.get(3).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("X") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Col 2 For X
        else if(listcells.get(1).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("X") && listcells.get(7).getTextOfCell().equals("")){
            setTickForMachine(7, "O");
        }
        else if(listcells.get(1).getTextOfCell().equals("X") && listcells.get(7).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(7).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("X") && listcells.get(1).getTextOfCell().equals("")){
            setTickForMachine(1, "O");
        }

        // Col 3 For X
        else if(listcells.get(2).getTextOfCell().equals("X") && listcells.get(5).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(8, "O");
        }
        else if(listcells.get(2).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(5).getTextOfCell().equals("")){
            setTickForMachine(5, "O");
        }
        else if(listcells.get(5).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }

        // Diagonal 1 For X
        else if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("")){
            setTickForMachine(8, "O");
        }
        else if(listcells.get(0).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("X") && listcells.get(8).getTextOfCell().equals("X") && listcells.get(0).getTextOfCell().equals("")){
            setTickForMachine(0, "O");
        }

        // Diagonal 2 For X
        else if(listcells.get(2).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("")){
            setTickForMachine(6, "O");
        }
        else if(listcells.get(2).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("X") && listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else if(listcells.get(4).getTextOfCell().equals("X") && listcells.get(6).getTextOfCell().equals("X") && listcells.get(2).getTextOfCell().equals("")){
            setTickForMachine(2, "O");
        }

        else if(listcells.get(4).getTextOfCell().equals("")){
            setTickForMachine(4, "O");
        }
        else{
            List<Integer> listCellIsEmpty = new ArrayList<>();
            for(int i = 0 ; i < listcells.size() ; i++){
                if(listcells.get(i).getTextOfCell().equals("")){
                    listCellIsEmpty.add(i);
                }
            }
            if(listCellIsEmpty.size() != 0) {
                Random random = new Random();
                int index = random.nextInt(listCellIsEmpty.size()) + 0;
                setTickForMachine(listCellIsEmpty.get(index), "O");
            }
        }
        checkWin();
    }

    public void setTickForMachine(int id, String symbol){
        Cell cell = listcells.get(id);
        if(!cell.getIsTick()) {
            cell.setTextForCell(symbol);
            cell.setIsTick(true);

            Button button = (Button) findViewById(cell.getCellId());
            button.setText(symbol);
        }
    }
}
