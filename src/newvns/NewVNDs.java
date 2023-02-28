package newvns;

import java.util.ArrayList;

/**
 * @Author zhouya
 * @Data 2023/2/28 16:50
 * @Modify by
 */
public class NewVNDs {
    ArrayList<NewNeighborhood> list = new ArrayList<>();

    public NewVNDs(){
    }

    public NewVNDs(ArrayList<NewNeighborhood> list){
        this.list=list;
    }
}
