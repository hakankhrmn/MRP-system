package sample;

public class ItemSpec {
    int ID;
    int leadTime;
    int amountOnHand;
    int scheduledReceipt;
    int arrivalOnWeek;
    int lotSizingRule;

    int[] grossRequirements = new int[10];
    int[] scheduledReciptArr = new int[10];
    int[] amountOnHandArr = new int[10];
    int[] netRequirements = new int[10];
    int[] timePhasedNetRequirements = new int[10];
    int[] plannedOrderReleases = new int[10];
    int[] plannedOrderDelivery = new int[10];


    public void fillItemSpec() {
        int onHand=amountOnHand;

        if (arrivalOnWeek!=0){
            scheduledReciptArr[arrivalOnWeek-1]= scheduledReceipt;
            amountOnHandArr[arrivalOnWeek]+= scheduledReceipt;
        }

        for (int i=0;i<10;i++){

            amountOnHandArr[i]=onHand;

            //fillother

            if (grossRequirements[i]>=(amountOnHandArr[i]+scheduledReciptArr[i])){
                netRequirements[i]=grossRequirements[i]-(amountOnHandArr[i]+scheduledReciptArr[i]);
                onHand=0;
            }else{
                onHand=(amountOnHandArr[i]+scheduledReciptArr[i])-grossRequirements[i];
            }

            if (netRequirements[i]>0){

                timePhasedNetRequirements[i-leadTime]=netRequirements[i];

                //fill plannedOrderReleases
                if (lotSizingRule==1){
                    plannedOrderReleases[i-leadTime]=lotSizingRule*timePhasedNetRequirements[i-leadTime];
                }else {

                    int j=1;
                    while (true){
                        if (lotSizingRule*j>=timePhasedNetRequirements[i-leadTime]){
                            plannedOrderReleases[i-leadTime]=lotSizingRule*j;
                            break;
                        }
                        j++;
                    }
                }

                //fill plannedOrderDelivery
                plannedOrderDelivery[i]=plannedOrderReleases[i-leadTime];
                onHand+=plannedOrderDelivery[i]-netRequirements[i];

            }
        }
    }


    public void fillConstants(int leadTime, int amountOnHand, int scheduledRecipt, int arrivalOnWeek, String lotSizingRule){

        this.leadTime=leadTime;
        this.amountOnHand=amountOnHand;
        this.scheduledReceipt =scheduledRecipt;
        this.arrivalOnWeek=arrivalOnWeek;
        this.lotSizingRule=getLotSizingRule(lotSizingRule);
    }

    public String printConstant(){
        String data="";
        data+="ID: "+ this.ID;
        data+="\nLead Time: " + this.leadTime;
        data+="\nLot Sizing Rule:" + this.lotSizingRule;
        data+="\nAmount on Hand:" + this.amountOnHand;
        data+="\nScheduled Receipt:" + this.scheduledReceipt;
        data+="\nArrival on Week:" + this.arrivalOnWeek;
        return data;
    }

    public String printDatas(){

        String data="";
        data+="ID: "+ this.ID + "\tLead Time: " + this.leadTime + "\tLot Sizing Rule:" + this.lotSizingRule;
        data+="\nperiod\t\t\t\t\t\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10";

        data+="\nGross Requirements:\t\t\t";
        for (int i=0;i<10;i++){
            data+=grossRequirements[i]+"\t";
        }

        data+="\nScheduled Receipts:\t\t\t";
        for (int i=0;i<10;i++){
            data+=scheduledReciptArr[i]+"\t";
        }

        data+="\nOn Hand From Prior Period:\t\t";
        for (int i=0;i<10;i++){
            data+=amountOnHandArr[i]+"\t";
        }

        data+="\nNet Requirements:\t\t\t\t";
        for (int i=0;i<10;i++){
            data+=netRequirements[i]+"\t";
        }

        data+="\nTime Phased Net Requirements:\t";
        for (int i=0;i<10;i++){
            data+=timePhasedNetRequirements[i]+"\t";
        }

        data+="\nPlanned Order Releases:\t\t\t";
        for (int i=0;i<10;i++){
            data+=plannedOrderReleases[i]+"\t";
        }

        data+="\nPlanned Order Delivery:\t\t\t";
        for (int i=0;i<10;i++){
            data+=plannedOrderDelivery[i]+"\t";
        }

        data+="\n***************************************************************************************\n";

        return data;

    }

    public int getLotSizingRule(String rule){


        if (rule.equalsIgnoreCase("L4L")){
            return 1;
        }else if (rule.equalsIgnoreCase("MULTIPLESOF30")){
            return 30;
        }else if (rule.equalsIgnoreCase("MULTIPLESOF40")){
            return 40;
        }else if (rule.equalsIgnoreCase("MULTIPLESOF50")){
            return 50;
        }else if (rule.equalsIgnoreCase("MULTIPLESOF100")){
            return 100;
        }

        return -1;
    }

}
