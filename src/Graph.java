import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Graph {
  private File villes;
  private File routes;

  private HashMap<City, Set<Road>> trajetVilleRoute;
  private HashMap<Integer, City> numVille;
  private HashMap<City, Integer> villeNum;
  private Queue<String> queue;
  private Map<String, String> villeVisitees;
  private static int nbVilles = 0;
  private static int nbRoutes = 0;

  public Graph(File villes, File routes) {
    this.villes = villes;
    this.routes = routes;
    numVille = new HashMap<Integer, City>();
    villeNum = new HashMap<City, Integer>();
    queue = new LinkedList<>();
    trajetVilleRoute = new HashMap<City, Set<Road>>();
    villeVisitees = new HashMap<>();

    try {
      BufferedReader lecteur = new BufferedReader(new FileReader(villes));
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
      BufferedReader lecteur = new BufferedReader(new FileReader(routes));
      String ligne;
      while ((ligne = lecteur.readLine()) != null) {
        String[] road = ligne.split(",");

        if (road.length >= 2) {
          City city1 = numVille.get(Integer.parseInt(road[0]));
          City city2 = numVille.get(Integer.parseInt(road[1]));
          Road route = new Road(city1, city2);
          Set<Road> r = trajetVilleRoute.get(city1);
          r.add(route);
          trajetVilleRoute.put(city1, r);
          Road route2 = new Road(city2, city1);
          Set<Road> r2 = trajetVilleRoute.get(city2);
          r2.add(route2);
          trajetVilleRoute.put(city2, r2);

        }
      }
    } catch (Exception e) {
      throw  new RuntimeException("Erreur lors de la lecture du fichier roads.txt");
    }
  }

  public void calculerItineraireMinimisantNombreRoutes(String ville1, String ville2) {
    City end;
    City start;
    start = getCity(ville1);
    end = getCity(ville2);

    if (start == null || end == null) {
      throw new RuntimeException("Ville non trouvée");
    }

    Deque<City> endroitAVisiter = new LinkedList<>();
    endroitAVisiter.add(start);
    Map<City, Road>  precedent = new HashMap<>();
    Set<City>  endroitVisite = new HashSet<>();
    boolean trouve = false;
    while (!endroitAVisiter.isEmpty()) {
      City current = endroitAVisiter.poll();
      if (current.equals(end)) {
        trouve = true;
        break;
      }
      Set<Road> routes = this.trajetVilleRoute.get(current);
      for (Road route : routes) {
        City destination = route.getNumVille2();
        if (!endroitVisite.contains(destination)) {
          endroitAVisiter.add(destination);
          endroitVisite.add(destination);
          precedent.put(destination, route);
        }
      }
    }
    if (trouve) {
      printPath(precedent, ville1, ville2);
    } else {
      System.out.println("Pas de chemin trouvé");
    }
  }
  private void printPath(Map<City, Road> precedent, String ville1, String ville2) {
    City end;
    City start;
    start = getCity(ville1);
    end = getCity(ville2);
    Deque<Road> chemin = new LinkedList<>();
    int total = 0;
    double distance = 0;
    City current = end;
    while (current != start) {
      Road route = precedent.get(current);
      chemin.add(route);
      total++;
      distance += route.getDistance();
      current = route.getNumVille1();
    }
    System.out.println("Trajet de " +start.getNom() + " à " + end.getNom() + " : " + total + " routes, " + distance + " km");
    while (!chemin.isEmpty()) {
      Road route = chemin.pollLast();
      System.out.println(route.getNumVille1().getNom() + " -> " + route.getNumVille2().getNom() + " : " + route.getDistance() + " km");
    }
  }

  public void calculerItineraireMinimisantKm(String ville1, String ville2) {
    City debut;
    City fin;
    debut = getCity(ville1);
    fin = getCity(ville2);
    if (debut == null || fin == null) {
      throw new RuntimeException("Ville non trouvée");
    }

    Map<City, Double> distances = new HashMap<>();
    Map<City, Road> precedent = new HashMap<>();
    TreeMap<Double, City> queue = new TreeMap<>();
    Set<City> visited = new HashSet<>();

    for (City city : numVille.values()) {
      distances.put(city, city.equals(debut) ? 0.0 : Double.MAX_VALUE);
      queue.put(distances.get(city), city);
    }

    while (!queue.isEmpty()) {
      City sommetCourant = queue.pollFirstEntry().getValue();
      visited.add(sommetCourant);
      Set<Road> roads = trajetVilleRoute.get(sommetCourant);
      for (Road road : roads) {
        City adjacent = road.getNumVille2();
        if (!visited.contains(adjacent)) {
          double newDistance = distances.get(sommetCourant) + road.getDistance();
          if (newDistance < distances.get(adjacent)) {
            distances.put(adjacent, newDistance);
            precedent.put(adjacent, road);
            queue.put(newDistance, adjacent);
          }
        }
      }
    }
    printShortestPath(precedent, debut, fin);
  }

  private void printShortestPath(Map<City, Road> precedent, City start, City end) {
    Deque<Road> path = new LinkedList<>();
    City current = end;

    while (precedent.containsKey(current)) {
      Road road = precedent.get(current);
      path.addFirst(road);
      current = road.getNumVille1();
    }
    Deque<Road> chemin = new LinkedList<>();
    int total = 0;
    double distance = 0;
    current = end;
    while (current != start) {
      Road route = precedent.get(current);
      chemin.add(route);
      total++;
      distance += route.getDistance();
      current = route.getNumVille1();
    }

    double totalDistance = 0.0;
    System.out.println("Trajet de " +start.getNom() + " à " + end.getNom() + " : " + total + " routes, " + distance + " km");
    while (!path.isEmpty()) {
      Road road = path.poll();
      System.out.println(road.getNumVille1().getNom() + " -> " + road.getNumVille2().getNom() + " : " + road.getDistance() + " km");
      totalDistance += road.getDistance();
    }
    System.out.println("Distance totale : " + totalDistance + " km");
  }
  private City getCity (String ville1){
    for (City city : numVille.values()) {
      if (city.getNom().equals(ville1)) {
        return city;
      }
    }
    return null;
  }



}
