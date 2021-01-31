package sample;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;


public class Controller {

    Tree tree=new Tree();
    int[] demandInt=new int[10];


    @FXML
    private TextField txtW1;

    @FXML
    private TextField txtW2;

    @FXML
    private TextField txtW3;

    @FXML
    private TextField txtW4;

    @FXML
    private TextField txtW5;

    @FXML
    private TextField txtW6;

    @FXML
    private TextField txtW7;

    @FXML
    private TextField txtW8;

    @FXML
    private TextField txtW9;

    @FXML
    private TextField txtW10;

    @FXML
    private TextArea txtArea;

    @FXML
    private TextField txtItemID;


    @FXML
    void initialize() throws IOException {

        String[] line=new String[13];

        BufferedReader rd =new BufferedReader(new FileReader("temple.txt"));

        for(int i =0;i<line.length;i++){
            line[i]=rd.readLine();
        }
        rd.close();


        ArrayList<Item> items=new ArrayList<>();

        for (int i=0; i<line.length;i++){

            String[] itemLine=line[i].split(" ");
            Item item=new Item();
            item.ID= Integer.parseInt(itemLine[0]);
            item.itemSpec.ID= item.ID;
            int leadTime=Integer.parseInt(itemLine[4]);
            int amountOnHand=Integer.parseInt(itemLine[1]);
            int scheduledReceipt=Integer.parseInt(itemLine[2]);
            int arrivalOnWeek=Integer.parseInt(itemLine[3]);
            String lotSizingRule=itemLine[5];
            item.itemSpec.fillConstants(leadTime,amountOnHand,scheduledReceipt,arrivalOnWeek,lotSizingRule);
            items.add(item);

        }


        for (int i=0;i<items.size();i++){
            if (i==8){
                tree.addChild(items.get(4));
                tree.addChild(items.get(i));
            }else if (i==items.size()-1){
                tree.addChild(items.get(i));
                tree.addChild(items.get(5));
            }else{
                tree.addChild(items.get(i));
            }
        }
    }

    @FXML
    void fillAllMRPs() {
        demandInt[0]= Integer.parseInt(txtW1.getText());
        demandInt[1]= Integer.parseInt(txtW2.getText());
        demandInt[2]= Integer.parseInt(txtW3.getText());
        demandInt[3]= Integer.parseInt(txtW4.getText());
        demandInt[4]= Integer.parseInt(txtW5.getText());
        demandInt[5]= Integer.parseInt(txtW6.getText());
        demandInt[6]= Integer.parseInt(txtW7.getText());
        demandInt[7]= Integer.parseInt(txtW8.getText());
        demandInt[8]= Integer.parseInt(txtW9.getText());
        demandInt[9]= Integer.parseInt(txtW10.getText());

        try {
            tree.fillAllItemSpecs(demandInt);
        }catch (ArrayIndexOutOfBoundsException e){
            txtArea.setText("Demands can not be ready in the delivery date! ");
        }
    }

    @FXML
    void print() {
        String datas = tree.printDatas();
        txtArea.setText(datas);


    }

    @FXML
    void fillApplyDefaults() {

        txtW1.setText("0");
        txtW2.setText("0");
        txtW3.setText("0");
        txtW4.setText("60");
        txtW5.setText("100");
        txtW6.setText("0");
        txtW7.setText("50");
        txtW8.setText("0");
        txtW9.setText("30");
        txtW10.setText("0");
        fillAllMRPs();

    }



    @FXML
    void printItemConstant() {
        if (txtItemID.getText()==null){
            txtArea.setText("First you need to fill item ID");
        }else {
            Item item=tree.Find(Integer.parseInt(txtItemID.getText()));
            if (item==null){
                txtArea.setText("There is no such as item!!");
            }else {
                String text = item.itemSpec.printConstant();
                txtArea.setText(text);
            }
        }
    }

    @FXML
    void printItemMRP() {
        if (txtItemID.getText()==null){
            txtArea.setText("First you need to fill item ID");
        }else {
            Item item = tree.Find(Integer.parseInt(txtItemID.getText()));
            if (item==null){
                txtArea.setText("There is no such as item!!");
            }else{
                String text=item.itemSpec.printDatas();
                txtArea.setText(text);
            }
        }
    }
}
