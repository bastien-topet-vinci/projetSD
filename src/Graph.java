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
  private Queue<String> queue;
  private Map<String, String> villeVisitees;
  private static int nbVilles = 0;
  private static int nbRoutes = 0;

  public Graph(File cities, File roads) {
    this.cities = cities;
    this.roads = roads;
    numVille = new HashMap<Integer, City>();
    villeNum = new HashMap<City, Integer>();
    queue = new LinkedList<>();
    trajetVilleRoute = new HashMap<City, Set<Road>>();
    villeVisitees = new HashMap<>();

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

          Road route =new Road(numVille.get(Integer.parseInt(road[0])), numVille.get(Integer.parseInt(road[1])));
          trajetVilleRoute.get(numVille.get(Integer.parseInt(road[0]))).add(route);
        }
      }
    } catch (Exception e) {
      throw  new RuntimeException("Erreur lors de la lecture du fichier roads.txt");
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String ville1, String ville2) {
    System.out.println(ville1 + " -> " + ville2);
    City start = null;
    City end = null;
    for (City city : numVille.values()) {
      if(city.getNom().equals(ville1)) {
        start = city;
      }
      if(city.getNom().equals(ville2)) {
         end = city;
      }
    }
    if (start == null || end == null) {
      throw new RuntimeException("Ville non trouvée");
    }

    Deque<City>  endroitavisiter = new LinkedList<>();
    Set<City>  endroitvisite = new HashSet<>();
    Map<City, Road>  predecessor = new HashMap<>();
    endroitavisiter.add(start);
    boolean trouve = false;
    while (!endroitavisiter.isEmpty()) {
      City current = endroitavisiter.poll();
      if (current.equals(end)) {
        trouve = true;
        break;
      }
      Set<Road> routes = this.trajetVilleRoute.get(current);
      for (Road route : routes) {
        City destination = route.getNumVille2();
        if (!endroitvisite.contains(destination)) {
          endroitavisiter.add(destination);
          endroitvisite.add(destination);
          predecessor.put(destination, route);
        }
      }
    }
    if (trouve) {
      printPath(predecessor, ville1, ville2);
    } else {
      System.out.println("Pas de chemin trouvé");
    }
  }
    private void printPath(Map<City, Road> predecessor, String ville1, String ville2) {
        City end = null;
        City start = null;
        for (City city : numVille.values()) {
            if (city.getNom().equals(ville1)) {
                start = city;
            }
            if (city.getNom().equals(ville2)) {
                end = city;
            }
        }
        Deque<Road> chemin = new LinkedList<>();
        int total = 0;
        double distance = 0;
        while(end != start) {
            Road route = predecessor.get(end);
            chemin.add(route);
            total++;
            distance += route.getDistance();
            end = route.getNumVille1();
        }
      System.out.println("trajet de " +start.getNom() + " à " + end.getNom() + " : " + total + " routes, " + distance + " km");
        while (!chemin.isEmpty()) {
            Road route = chemin.poll();
            System.out.println(route.getNumVille1().getNom() + " -> " + route.getNumVille2().getNom() + " : " + route.getDistance() + " km");
        }
    }




  public void calculerItineraireMinimisantKm(String ville1, String ville2) {

  }



}
