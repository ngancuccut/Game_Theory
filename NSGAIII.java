package Budget_Distribution_to_Educational;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class NSGAIII {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 100;
    private static final double MUTATION_RATE = 0.1;
    private static final double CROSSOVER_RATE = 0.9;
    private static final int NUM_INSTITUTIONS = 5; // Number of institutions
    private List<Individual> population;
    private Random random;

    public NSGAIII() {
        this.population = new ArrayList<>(POPULATION_SIZE);
        this.random = new Random();
        initializePopulation();
    }

    private void initializePopulation() {
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual ind = new Individual(NUM_INSTITUTIONS);
            for (int j = 0; j < NUM_INSTITUTIONS; j++) {
                ind.budgetAllocations[j] = random.nextDouble() * 1000; // Random budget allocation
            }
            population.add(ind);
        }
    }

    private void evaluateObjectives() {
        for (Individual ind : population) {
            // Objective 1: Minimize funding disparity
            ind.objectives[0] = calculateFundingDisparity(ind);
            // Objective 2: Maximize overall quality
            ind.objectives[1] = calculateOverallQuality(ind);
            // Objective 3: Align funding with community needs
            ind.objectives[2] = calculateCommunityAlignment(ind);
            // Objective 4: Optimize resource utilization
            ind.objectives[3] = calculateResourceUtilization(ind);
        }
    }

    private double calculateFundingDisparity(Individual ind) {
        double mean = 0;
        for (double allocation : ind.budgetAllocations) {
            mean += allocation;
        }
        mean /= NUM_INSTITUTIONS;

        double disparity = 0;
        for (double allocation : ind.budgetAllocations) {
            disparity += Math.pow(allocation - mean, 2);
        }
        return Math.sqrt(disparity);
    }

    private double calculateOverallQuality(Individual ind) {
        // Placeholder: Logic for calculating overall educational quality
        return random.nextDouble() * 100; // Random for demonstration
    }

    private double calculateCommunityAlignment(Individual ind) {
        // Placeholder: Logic for aligning funding with community needs
        return random.nextDouble() * 100; // Random for demonstration
    }

    private double calculateResourceUtilization(Individual ind) {
        // Placeholder: Logic for resource utilization
        return random.nextDouble() * 100; // Random for demonstration
    }

    private void nonDominatedSorting() {
        // Implement non-dominated sorting (simplified)
        // Rank individuals based on their objectives
        Collections.sort(population, Comparator.comparingDouble(a -> a.objectives[0]));
        // Further sorting logic to determine ranks would go here
    }

    private void selection() {
        // Implement tournament selection
        List<Individual> newPopulation = new ArrayList<>(POPULATION_SIZE);
        while (newPopulation.size() < POPULATION_SIZE) {
            Individual parent1 = tournamentSelection();
            Individual parent2 = tournamentSelection();
            newPopulation.add(parent1);
            newPopulation.add(parent2);
        }
        population = newPopulation;
    }

    private Individual tournamentSelection() {
        int tournamentSize = 5;
        Individual best = null;
        for (int i = 0; i < tournamentSize; i++) {
            Individual ind = population.get(random.nextInt(POPULATION_SIZE));
            if (best == null || compareIndividuals(ind, best) < 0) {
                best = ind;
            }
        }
        return best;
    }

    private int compareIndividuals(Individual ind1, Individual ind2) {
        // Compare based on objectives for selection
        return Double.compare(ind1.objectives[0], ind2.objectives[0]); // Compare by first objective
    }

    private void crossover() {
        List<Individual> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i += 2) {
            if (i + 1 < population.size() && random.nextDouble() < CROSSOVER_RATE) {
                Individual parent1 = population.get(i);
                Individual parent2 = population.get(i + 1);
                Individual child1 = new Individual(NUM_INSTITUTIONS);
                Individual child2 = new Individual(NUM_INSTITUTIONS);
                for (int j = 0; j < NUM_INSTITUTIONS; j++) {
                    child1.budgetAllocations[j] = (parent1.budgetAllocations[j] + parent2.budgetAllocations[j]) / 2;
                    child2.budgetAllocations[j] = (parent1.budgetAllocations[j] + parent2.budgetAllocations[j]) / 2;
                }
                newPopulation.add(child1);
                newPopulation.add(child2);
            } else {
                newPopulation.add(population.get(i));
            }
        }
        population = newPopulation;
    }

    private void mutation() {
        for (Individual ind : population) {
            if (random.nextDouble() < MUTATION_RATE) {
                for (int j = 0; j < NUM_INSTITUTIONS; j++) {
                    ind.budgetAllocations[j] += random.nextGaussian(); // Gaussian mutation
                }
            }
        }
    }

    public void run() {
        for (int gen = 0; gen < MAX_GENERATIONS; gen++) {
            evaluateObjectives();
            nonDominatedSorting();
            selection();
            crossover();
            mutation();
        }
        // Output final results
        outputResults();
    }

    private void outputResults() {
        System.out.println("Final Population:");
        for (Individual ind : population) {
            System.out.print("Allocations: ");
            for (double allocation : ind.budgetAllocations) {
                System.out.print(allocation + " ");
            }
            System.out.println("Objectives: " + ind.objectives[0] + ", " + ind.objectives[1] + ", " +
                    ind.objectives[2] + ", " + ind.objectives[3]);
        }
    }
}