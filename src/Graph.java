import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {
  private File cities;
  private File roads;
  private HashMap<City, Set<Road>> trajetVilleRoute;
  private HashMap<Integer, City> numVille;
  private HashMap<City, Integer> villeNum;
  private static int nbVilles = 0;



  private static int nbRoutes = 0;

  public Graph(File cities, File roads) {
    this.cities = cities;
    this.roads = roads;
    numVille = new HashMap<Integer, City>();
    villeNum = new HashMap<City, Integer>();

    trajetVilleRoute = new HashMap<City, Set<Road>>();

    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(cities));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] ville = ligne.split(",");
        if (ville.length >= 4) {
          City city = new City(Integer.parseInt(ville[0]), ville[1],  Double.parseDouble(ville[2]),  Double.parseDouble(ville[3]));
            numVille.put(Integer.parseInt(ville[0]), city);
            villeNum.put(city, Integer.parseInt(ville[0]));
          trajetVilleRoute.put(city, new HashSet<Road>());
          nbVilles++;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(roads));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] road = ligne.split(",");
        if (road.length >= 2) {
          Road route =new Road(numVille.get(Integer.parseInt(road[0])) , numVille.get(Integer.parseInt(road[1])-1));
          trajetVilleRoute.get(numVille.get(Integer.parseInt(road[0]))).add(route);
        }
      }
    } catch (Exception e) {
      throw  new RuntimeException("Erreur lors de la lecture du fichier roads.txt");
    }




  }

  public void calculerItineraireMinimisantNombreRoutes(String ville1, String ville2) {
    Map<String, String> predecessor = new HashMap<>();
    Queue<String> queue = new LinkedList<>();
    queue.add(ville1);
    predecessor.put(ville1, null);

    while (!queue.isEmpty()) {
      // Dequeue a city from queue
      String currentCity = queue.poll();

      // If this city is the destination, we have found the shortest path
      if (currentCity.equals(ville2)) {
        printPath(predecessor, ville1, ville2);
        return;
      }

      // Get all adjacent cities of the dequeued city
      // If an adjacent city has not been visited, then mark it visited and enqueue it
      for (Road road : trajetVilleRoute.get(currentCity)) {
        String adjacentCity = road.getNumVille2().getNom();
        if (!predecessor.containsKey(adjacentCity)) {
          queue.add(adjacentCity);
          predecessor.put(adjacentCity, currentCity);
        }
      }
    }
  }

  private void printPath(Map<String, String> predecessor, String start, String end) {
    if (start.equals(end)) {
      System.out.print(start);
    } else {
      printPath(predecessor, start, predecessor.get(end));
      System.out.print(" -> " + end);
    }
  }








  public void calculerItineraireMinimisantKm(String ville1, String ville2) {

  }
}
