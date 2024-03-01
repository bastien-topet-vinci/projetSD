import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Graph {
  private File cities;
  private File roads;
  private Map<String, Set<Road>> trajetVilleRoute;
  private static int nbVilles = 0;

  public Graph(File cities, File roads) {
    this.cities = cities;
    this.roads = roads;
    trajetVilleRoute = new HashMap<String, Set<Road>>();
  }

  public void calculerItineraireMinimisantNombreRoutes(String ville1, String ville2) {

  }

  public void calculerItineraireMinimisantKm(String ville1, String ville2) {

  }
}
