package Budget_Distribution_to_Educational;

 public class Individual {
    double[] budgetAllocations; // Budget allocation for each institution
    double[] objectives; // Objective values
    int rank; // Non-domination rank
    double crowdingDistance; // Crowding distance for selection

    public Individual(int numInstitutions) {
        this.budgetAllocations = new double[numInstitutions];
        this.objectives = new double[4]; // 4 objectives
    }
}
