import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import java.util.HashSet;
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

    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(cities));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] ville = ligne.split(",");
        if (ville.length >= 4) {
          City city = new City(Integer.parseInt(ville[0]), ville[1],  Double.parseDouble(ville[2]),  Double.parseDouble(ville[3]));
          trajetVilleRoute.put(city.getNom(), new HashSet<Road>());
        }
      }
    } catch (Exception e) {
      throw  new RuntimeException("Erreur lors de la lecture du fichier cities.txt");
    }



  }

  public void calculerItineraireMinimisantNombreRoutes(String ville1, String ville2) {

  }

  public void calculerItineraireMinimisantKm(String ville1, String ville2) {

  }
}
