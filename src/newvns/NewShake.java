package newvns;

import java.util.Random;

/**
 * @Author zhouya
 * @Data 2023/2/28 16:38
 * @Modify by
 */
public class NewShake {
    public Instance instance;
    public Solution[] solutions;

    public NewShake() {
    }
    public NewShake(Instance instance, Solution[] solutions) {
        this.instance = instance;
        this.solutions = solutions;
    }

    /**
     *
     * @param solution
     * @param k
     * @return
     * @throws CloneNotSupportedException
     */
    public Solution shaking(Solution solution, int k) throws CloneNotSupportedException {
        Solution solution1 = (Solution) solution.clone();
        NewStrategy strategy=new NewStrategy(instance);
        Random random = new Random();
        Solution[] mutaSolutions = new Solution[10];

        double p = 0.3;
        for (int i = 0; i < 1; ) {
            double r = random.nextDouble();
            while (k > 0) {
                for (int j = 0; j < 10; j++) {
                    mutaSolutions[j] = (Solution) strategy.mutation1(solution1).clone();
                }

                for (int j = 0; j < 10; j++) {
                    for (int l = j + 1; l < 10; l++) {
                        if (mutaSolutions[j].getTotalCost() > mutaSolutions[l].getTotalCost()) {
                            Solution temp = (Solution) mutaSolutions[j].clone();
                            mutaSolutions[j] = (Solution) mutaSolutions[l].clone();
                            mutaSolutions[l] = (Solution) temp.clone();
                        }
                    }
                }
                if (r < p) {
                    solution1 = (Solution) mutaSolutions[0].clone();
                } else {
                    solution1 = (Solution) mutaSolutions[9].clone();
                }
                k--;
            }
            Constraint constraint = new Constraint(instance, solution1);
            boolean judge = constraint.judgement();
            if (judge) {
                i++;
            } else {
            }
        }
        return solution1;
    }
}
