public class City {
  private int numero;
  private String nom;
  private double coordX;
  private double coordY;

  public City(int numero, String nom, double coordX, double coordY) {
    this.numero = numero;
    this.nom = nom;
    this.coordX = coordX;
    this.coordY = coordY;
  }

  public int getNumero() {
    return numero;
  }

  public String getNom() {
    return nom;
  }

  public double getCoordX() {
    return coordX;
  }

  public double getCoordY() {
    return coordY;
  }
}
