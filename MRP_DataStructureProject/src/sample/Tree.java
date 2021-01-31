package sample;

public class Tree {

    public Item root=null;

    public void addChild (Item item){

        if (root==null){
            root=item;
            return;
        }else if (root.children.size()<6){
            root.children.add(item);
            return;
        }else if (root.children.get(0).children.size()<3){
            root.children.get(0).children.add(item);
            return;
        }else if (root.children.get(0).children.get(2).children.size()<2){
            root.children.get(0).children.get(2).children.add(item);
            return;
        }else if (root.children.get(5).children.size()<3){
            root.children.get(5).children.add(item);
            return;
        }

    }

    public Item Find(int id){

        Item item;
        for (int i=0;i<root.children.size();i++){
            item=root.children.get(i);
            if (item.ID==id){
                return item;
            }
        }

        for (int i=0;i<root.children.get(0).children.size();i++){
            item=root.children.get(0).children.get(i);
            if (item.ID==id){
                return item;
            }
        }
        for (int i=0;i<root.children.get(5).children.size();i++){
            item=root.children.get(5).children.get(i);
            if (item.ID==id){
                return item;
            }
        }
        for (int i=0;i<root.children.get(0).children.get(2).children.size();i++){
            item=root.children.get(0).children.get(2).children.get(i);
            if (item.ID==id){
                return item;
            }
        }

        return null;
    }



    public void fillRecursive(Item item){
        for (int i=0;i<item.children.size();i++) {

            Item current= item.children.get(i);

            if ((item==root)&&((current.ID==14127)||(current.ID==62))){
                current.required=4;
            } else if ((item.ID==13122)&&(current.ID==62)){
                current.required=2;
            } else if ((item.ID==314)&&(current.ID==14127)){
                current.required=6;
            }

            for (int n = 0; n < 10; n++) {
                current.itemSpec.grossRequirements[n] += current.required*item.itemSpec.plannedOrderReleases[n];
            }

            if (((current.ID != 62)&&(current.required!=4))||((current.ID != 14127)&&(current.required!=4))) {
                current.itemSpec.fillItemSpec();
            }

        }
        for (int i=0;i<item.children.size();i++) {
            fillRecursive(item.children.get(i));
        }

    }

    public void fillAllItemSpecs(int[] demand){

        Item current=root;
        for (int i=0;i<10;i++){
            current.itemSpec.grossRequirements[i]+=demand[i];
        }
        current.itemSpec.fillItemSpec();

        fillRecursive(root);

    }

    public String printDatas(){

        return printRecursive(root);
    }

    private String printRecursive(Item item){
        String datas="";
        if (item.ID==root.ID){
            datas+=item.itemSpec.printDatas();
            System.out.println(item.itemSpec.printDatas());
        }
        for (int i=0;i<item.children.size();i++){
            datas+=item.children.get(i).itemSpec.printDatas();
            System.out.println(item.children.get(i).itemSpec.printDatas());
            printRecursive(item.children.get(i));
        }


        return datas;
    }
}
