package newvns;

/**
 * @Author zhouya
 * @Data 2023/2/28 16:40
 * @Modify by
 */
public class NewNeighborhood1 {
    private NewStrategy strategy;
    private Instance instance;
    private Solution[] solutions;
    public Solution[] candiSolutions;

    public NewNeighborhood1(NewStrategy strategy, Instance instance, Solution[] solutions) {
        this.strategy = strategy;
        this.instance = instance;
        this.solutions = solutions;
    }

    /**
     *
     * @param solution
     * @return
     * @throws CloneNotSupportedException
     */
    public Solution neighborhoodOne(Solution solution) throws CloneNotSupportedException {
        Solution clone;
        Solution optiSolution = (Solution) solution.clone();
        for (int i = 0; i < 1; ) {
            clone = strategy.swapPointMethod(solution);

            Constraint constraint = new Constraint(instance, clone);
            boolean judge = constraint.judgement();
            if (judge) {
                i++;

                instance.getTotalCost1(clone);
                if (clone.getTotalCost() < optiSolution.getTotalCost()) {
                    optiSolution = clone;
                }
            } else {
                System.out.println("the solution is not available");
            }
        }
        instance.getTotalCost1(optiSolution);
        return optiSolution;

    }

    /**
     *
     * @param solution
     * @return
     * @throws CloneNotSupportedException
     */
    public Solution neighborhoodTwo(Solution solution) throws CloneNotSupportedException {
        Solution clone;
        Solution optiSolution = (Solution) solution.clone();

        for (int i = 0; i < 1; ) {
            clone = strategy.leftInsertMethod(solution);

            Constraint constraint = new Constraint(instance, clone);
            boolean judge = constraint.judgement();
            if (judge) {
                i++;

                instance.getTotalCost1(clone);
                if (clone.getTotalCost() < optiSolution.getTotalCost()) {
                    optiSolution = clone;
                }
            } else {
                System.out.println("the solution is not available");
            }
        }
        return optiSolution;
    }

    /**
     *
     * @param solution
     * @return
     * @throws CloneNotSupportedException
     */
    public Solution neighborhoodThree(Solution solution) throws CloneNotSupportedException {
        Solution clone;
        Solution optiSolution = (Solution) solution.clone();

        for (int i = 0; i < 1; ) {
            clone = strategy.reverseMethod(solution);

            Constraint constraint = new Constraint(instance, clone);
            boolean judge = constraint.judgement();
            if (judge) {
                i++;

                instance.getTotalCost1(clone);
                if (clone.getTotalCost() < optiSolution.getTotalCost()) {
                    optiSolution = clone;
                }
            } else {
                System.out.println("the solution is not available");
            }
        }
        return optiSolution;
    }

    public Solution neighborhoodFour1(Solution solution) throws CloneNotSupportedException {
        Solution clone;
        Solution optiSolution = (Solution) solution.clone();

        int searchNum = 3;

        for (int i = 0; i < searchNum; ) {

            clone = (Solution) strategy.mutation2(solution).clone();

            Constraint constraint = new Constraint(instance, clone);
            boolean judge = constraint.judgement();
            if (judge) {
                instance.getTotalCost1(clone);
                i++;

                if (clone.getTotalCost() < optiSolution.getTotalCost()) {
                    optiSolution = (Solution) clone.clone();
                }
            } else {
                System.out.println("the solution is not available");
            }
        }
        return optiSolution;

    }

    public Solution neighborhoodFour2(Solution solution) throws CloneNotSupportedException {
        Solution clone;
        Solution optiSolution = (Solution) solution.clone();

        for (int i = 0; i < 3; ) {
            clone = strategy.swapOrderPointMethod1(solution);

            Constraint constraint = new Constraint(instance, clone);
            boolean judge = constraint.judgement();
            if (judge) {
                i++;

                instance.getTotalCost1(clone);
                if (clone.getTotalCost() < optiSolution.getTotalCost()) {
                    optiSolution = clone;
                }
            } else {
                System.out.println("the solution is not available");
            }


        }
        return optiSolution;

    }
}
