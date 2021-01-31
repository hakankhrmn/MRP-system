package sample;

import java.util.ArrayList;

public class Item {
    ArrayList<Item> children=new ArrayList<>();
    int ID;
    int required=1;
    ItemSpec itemSpec=new ItemSpec();
}
