package newvns;

import java.util.Random;

/**
 * @Author zhouya
 * @Data 2023/2/28 16:45
 * @Modify by
 */
public class NewStrategy {
    private Instance instance;

    public NewStrategy(Instance instance) {
        this.instance = instance;
    }

    /**
     *
     * @param solution
     * @return clone
     * @throws CloneNotSupportedException
     */
    public Solution swapPointMethod(Solution solution) throws CloneNotSupportedException {
        Random random = new Random();
        Solution clone = (Solution) solution.clone();

        int i1 = 0, i2 = 0;
        while (i1 == i2) {
            i1 = random.nextInt(instance.allProdNum);
            i2 = random.nextInt(instance.allProdNum);
        }

        int j1 = 0, j2 = 0;
        while (j1 == j2) {
            j1 = random.nextInt(instance.orderNum);
            j2 = random.nextInt(instance.orderNum);
        }

        swap(clone.productAssign, i1, i2);
        swap(clone.orderAssign, j1, j2);
        return clone;

    }

    public Solution swapOrderPointMethod(Solution solution) throws CloneNotSupportedException {

        Solution clone = (Solution) solution.clone();
        Solution best=(Solution) solution.clone();
        for (int i = 0; i < solution.orderAssign.length; i++) {
            for (int j = i+1; j < solution.orderAssign.length; j++) {
                swap(clone.orderAssign, i, j);
                instance.getTotalCost1(clone);
                if(clone.getTotalCost()<best.getTotalCost()){
                    best=clone;
                }
                clone=(Solution) solution.clone();
            }
        }
        return best;

    }

    public Solution swapOrderPointMethod1(Solution solution) throws CloneNotSupportedException {
        Random random = new Random();
        Solution clone = (Solution) solution.clone();

        for (int i = 0; i < 5; i++) {
            int j1 = 0, j2 = 0;
            while (j1 == j2) {
                j1 = random.nextInt(instance.orderNum);
                j2 = random.nextInt(instance.orderNum);
            }
            swap(clone.orderAssign, j1, j2);
        }
        return clone;
    }

    /**
     * 左插
     *
     * @param solution
     * @return clone
     * @throws CloneNotSupportedException
     *
     */
    public Solution leftInsertMethod(Solution solution) throws CloneNotSupportedException {
        Random random = new Random();
        Solution clone = (Solution) solution.clone();

        int i1 = 0, i2 = 0;
        while (i1 >= i2) {
            i1 = random.nextInt(instance.allProdNum);
            i2 = random.nextInt(instance.allProdNum);
        }

        int j1 = 0, j2 = 0;
        while (j1 >= j2) {
            j1 = random.nextInt(instance.orderNum);
            j2 = random.nextInt(instance.orderNum);
        }

        leftInsert(clone.productAssign,i1,i2);
        leftInsert(clone.orderAssign,j1,j2);
        return clone;
    }


    /**
     *
     * @param solution
     * @return clone
     * @throws CloneNotSupportedException
     */
    public Solution reverseMethod(Solution solution) throws CloneNotSupportedException {
        Random random = new Random();
        Solution clone = (Solution) solution.clone();

        int i1 = 0, i2 = 0;
        while (i1 >= i2) {
            i1 = random.nextInt(instance.allProdNum);
            i2 = random.nextInt(instance.allProdNum);
        }

        int j1 = 0, j2 = 0;
        while (j1 >= j2) {
            j1 = random.nextInt(instance.orderNum);
            j2 = random.nextInt(instance.orderNum);
        }

        reverse(clone.productAssign, i1, i2);
        reverse(clone.orderAssign, j1, j2);
        return clone;

    }

    public Solution mutation1(Solution solution) throws CloneNotSupportedException {

        Solution newSolution= (Solution) solution.clone();
        Random random=new Random();

        int mPointNum1=random.nextInt(15)+5;
        int mPointNum2=random.nextInt(5)+3;


        for (int i = 0; i < mPointNum1; i++) {
            int mPoint1=random.nextInt(solution.productAssign.length);
            newSolution.productAssign[mPoint1]=random.nextInt(instance.duration-1)+1;
        }

        for (int i = 0; i < mPointNum2; i++) {
            int mPoint2=random.nextInt(solution.orderAssign.length);
            double[] toValue=orderToValue(solution.orderAssign);
            toValue[mPoint2]=random.nextDouble();
            newSolution.orderAssign=rankRule(toValue);
        }

        return newSolution;

    }

    public Solution mutation2(Solution solution) throws CloneNotSupportedException {

        Solution newSolution= (Solution) solution.clone();
        Random random=new Random();
        int mPointNum1=5;
        int mPointNum2=random.nextInt(1)+1;

        for (int i = 0; i < mPointNum1; i++) {
            int mPoint1=random.nextInt(solution.productAssign.length);
            newSolution.productAssign[mPoint1]=random.nextInt(instance.duration-1)+1;
        }
        return newSolution;

    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void leftInsert(int[] arr, int i, int j) {
        int temp = arr[j];
        System.arraycopy(arr, i, arr, i + 1, j - i);
        arr[i] = temp;
    }

    public void reverse(int[] arr, int i, int j) {
        for (int k = i; k < (i + j) / 2; k++) {
            int temp = arr[k];
            arr[k] = arr[i + j - k];
            arr[i + j - k] = temp;
        }
    }

    /**
     * @param orderAssign
     * @return
     */
    public double[] orderToValue(int[] orderAssign){
        Random random=new Random();
        double[] value=new double[orderAssign.length];
        double[] toValue=new double[orderAssign.length];
        for (int i = 0; i < orderAssign.length; i++) {
            value[i]=random.nextDouble();
            one:  for (int j = 0; j < i; j++) {
                if(value[j]==value[i]){
                    i--;
                    break one;
                }
            }
        }

        double temp=0;
        for (int i = 0; i < value.length; i++) {
            for (int j = i+1; j < value.length; j++) {
                if(value[i]>value[j]){
                    temp=value[i];
                    value[i]=value[j];
                    value[j]=temp;
                }
            }
        }

        for (int i = 0; i < orderAssign.length; i++) {
            two:  for (int j = 0; j < value.length; j++) {
                if(orderAssign[i]==j){
                    toValue[i]=value[j];
                    break two;
                }
            }
        }

        return toValue;
    }

    /**
     *
     * @param xs2
     */
    private int[] rankRule(double[] xs2) {
        int[] orderAssign=new int[xs2.length];
        double[] ranked = new double[xs2.length];
        for (int i = 0; i < xs2.length; i++) {
            ranked[i] = xs2[i];
        }
        double temp = 0;
        for (int i = 0; i < ranked.length; i++) {
            for (int j = i + 1; j < ranked.length; j++) {
                if (ranked[i] > ranked[j]) {
                    temp = ranked[i];
                    ranked[i] = ranked[j];
                    ranked[j] = temp;
                }
            }
        }
        for (int i = 0; i < ranked.length; i++) {
            for (int j = 0; j < ranked.length; j++) {
                one: if (xs2[i] == ranked[j]) {
                    orderAssign[j] = i;
                    ranked[j]=-1000;
                    break one;
                }
            }
        }
        return orderAssign;
    }

}
