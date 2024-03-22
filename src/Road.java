public class Road {
  private City nomVille1;
  private City nomVille2;

  public Road(City numVille1, City numVille2) {
    this.nomVille1 = numVille1;
    this.nomVille2 = numVille2;
  }

  public City getNumVille1() {
    return nomVille1;
  }

  public City getNumVille2() {
    return nomVille2;
  }
}
