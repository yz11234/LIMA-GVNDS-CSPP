package newvns;

import java.util.Arrays;
import java.util.Random;

/**
 * @Author zhouya
 * @Data 2023/2/28 16:35
 * @Modify by
 */
public class NewGVNS {

    /**
     * instance: the current plan
     * strategy: VNS
     * solutions:
     * bestSolution:
     */
    private Instance instance;
    public Record[] records;
    private NewStrategy strategy;
    private Solution[] solutions;
    private Solution bestSolution;
    private Solution worstSolution;
    private int maxK = 1;
    private NewNeighborhood1 neighborhood1;

    /**
     * @param instance the current plan
     */
    public NewGVNS(Instance instance, Record[] records) {
        this.instance = instance;
        this.records = records;
        strategy = new NewStrategy(instance);
        neighborhood1 = new NewNeighborhood1(strategy, instance, solutions);

        for (int i = 0; i < records.length; i++) {
            this.records[i] = new Record();
            this.records[i].allProdNum = instance.allProdNum;
            this.records[i].orderNum = instance.orderNum;
            this.records[i].populSize = Parameter.SOL_SIZE_VNS;
            this.records[i].iterNum = Parameter.ITER_MAX;
            this.records[i].algoName = "GVNS";
            this.records[i].minIterCost = new double[Parameter.ITER_MAX];
            this.records[i].maxIterCost = new double[Parameter.ITER_MAX];
        }
    }

    /**
     * generate initial solutions
     */
    private void initialSolution() throws CloneNotSupportedException {

        solutions = new Solution[Parameter.SOL_SIZE_VNS];
        for (int i = 0; i < solutions.length; i++) {
            solutions[i] = new Solution(instance.allProdNum, instance.orderNum);
        }
        Initialize.random(solutions, instance);

        bestSolution = new Solution(instance.allProdNum, instance.orderNum);
        bestSolution = (Solution) solutions[0].clone();

        worstSolution = new Solution(instance.allProdNum, instance.orderNum);
        worstSolution = (Solution) solutions[0].clone();

    }


    /**
     * the main process of vns
     *
     * @throws CloneNotSupportedException
     */
    public void vns(Solution solution, NewVNDs vnds) throws CloneNotSupportedException {

        instance.getTotalCost1(solution);

        int k = 1;
        NewShake shake = new NewShake(instance, solutions);
        Solution solution2 = shake.shaking(solution, k);
        instance.getTotalCost1(solution2);
        int l = 0;

        while (k <= maxK) {
            while (l < vnds.list.size()) {
                Solution solution3 = variableNeighborhoodDescent(solution2, vnds.list.get(l).getCode());

                if (solution3.getTotalCost() < solution2.getTotalCost()) {
                    solution2 = (Solution) solution3.clone();
                    l = 1;

                } else {
                    l = l + 1;

                }
            }
            if (solution2.getTotalCost() < solution.getTotalCost()) {
                solution = (Solution) solution2.clone();

            } else {
                k = k + 1;
            }
        }
        solution = (Solution) solution2.clone();
        if (solution.getTotalCost() < bestSolution.getTotalCost()) {
            bestSolution = (Solution) solution.clone();
        }
        if (solution.getTotalCost() > worstSolution.getTotalCost()) {
            worstSolution = (Solution) solution.clone();
        }
    }

    /**
     * four neighborhoods
     *
     * @param solution
     * @param l
     * @return
     * @throws CloneNotSupportedException
     */
    public Solution variableNeighborhoodDescent(Solution solution, int l) throws CloneNotSupportedException {
        switch (l) {
            case 1: {
                return neighborhood1.neighborhoodOne(solution);
            }
            case 2: {
                return neighborhood1.neighborhoodTwo(solution);
            }
            case 3: {
                return neighborhood1.neighborhoodThree(solution);
            }
            case 4: {
                Solution solution1 = neighborhood1.neighborhoodFour2(solution);
                return neighborhood1.neighborhoodFour1(solution1);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + 0);
            }

        }
    }

    public void start(NewVNDs vnDs) throws CloneNotSupportedException {
        for (int i = 0; i < Parameter.REPEAT_MAX; i++) {
            System.out.println("----------start-----------");
            initialSolution();
            Random random = new Random();
            int point = random.nextInt(solutions.length);
            Solution solution = (Solution) solutions[i].clone();
            double startTime = System.nanoTime();
            for (int j = 0; j < Parameter.ITER_MAX; j++) {
                vns(solution, vnDs);
                records[i].minIterCost[j] = bestSolution.getTotalCost();
                records[i].maxIterCost[j] = worstSolution.getTotalCost();
                System.out.println(bestSolution.getTotalCost());
            }
            double finishTime = System.nanoTime();
            records[i].iterRunTime = (finishTime - startTime) / 1e9;
            records[i].calCost();
            System.out.println(Arrays.toString(bestSolution.productAssign)+" "+ Arrays.toString(bestSolution.orderAssign));
            System.out.println(bestSolution.getTotalCost());
            System.out.println(records[i].iterRunTime);
        }
    }
}
