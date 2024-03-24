import java.util.Map;

public class Road {
  private City nomVille1;
  private City nomVille2;
  private double distance;

  public Road(City numVille1, City numVille2) {
    this.nomVille1 = numVille1;
    this.nomVille2 = numVille2;
    this.distance = calculateDistance(numVille1, numVille2);

  }

  public City getNumVille1() {
    return nomVille1;
  }

  public double calculateDistance(City city1, City city2) {
    City cityObj1 =city1;
    City cityObj2 = city2;

    return Util.distance(cityObj1.getCoordX(), cityObj1.getCoordY(), cityObj2.getCoordX(), cityObj2.getCoordY());
  }
  public double getDistance() {
    return distance;
  }

  public City getNumVille2() {
    return nomVille2;
  }
}
