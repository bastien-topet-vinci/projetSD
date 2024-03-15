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
  private Map<Integer, Set<Road>> trajetVilleRoute;
  private static int nbVilles = 0;

  public Graph(File cities, File roads) {
    this.cities = cities;
    this.roads = roads;
    trajetVilleRoute = new HashMap<Integer, Set<Road>>();

    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(cities));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] ville = ligne.split(",");
        if (ville.length >= 4) {
          City city = new City(Integer.parseInt(ville[0]), ville[1],  Double.parseDouble(ville[2]),  Double.parseDouble(ville[3]));
          trajetVilleRoute.put(city.getNumero(), new HashSet<Road>());
          nbVilles++;
        }
      }
    } catch (Exception e) {
      throw  new RuntimeException("Erreur lors de la lecture du fichier cities.txt");
    }
    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(roads));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] road = ligne.split(",");
        if (road.length >= 2) {
          Road road1 = new Road(Integer.parseInt(road[0]), Integer.parseInt(road[1]));
          trajetVilleRoute.get(road1.getNumVille1()).add(road1);
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
