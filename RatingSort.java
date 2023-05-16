import java.util.*;

class Post {
  public int id;
  public int votes;
  public double score;
  public long timestamp;
  public boolean isAdmin;

  public Post(int id, int votes, long timestamp, boolean isAdmin, double score) {
    this.id = id;
    this.votes = votes;
    this.timestamp = timestamp;
    this.isAdmin = isAdmin;
    this.score = score;
  }
}

public class RatingSort {

  private static final double POPULARITY_CONSTANT_ADMIN = 8.5;
  private static final double POPULARITY_CONSTANT_NON_ADMIN = 6.5;
  private static final int TIME_CONSTANT_ADMIN = 360;
  private static final int TIME_CONSTANT_NON_ADMIN = 720;
  private static final double GRAVITY_ADMIN = 1.5;
  private static final double GRAVITY_NON_ADMIN = 1.7;

  // private static final double WEIGHT_OF_LIKE = 1.5;
  // private static final double WEIGHT_OF_COMMENT = 2.0;

  private static double calculateRank(int P, double PC, long TE, double TC, double G) {
    double score = (P + PC) / (Math.pow((TE + TC), G));
    return score;
  }

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      int number_of_posts = scanner.nextInt();

      // int like = scanner.nextInt();
      // int comment = scanner.nextInt();

      List<Post> posts = new ArrayList<>();

      for (int i = 0; i < number_of_posts; i++) {
        int votes = scanner.nextInt();
        long timestamp = scanner.nextLong();
        boolean isAdmin = scanner.nextInt() == 1;

        Post p = new Post(i + 1, votes, timestamp, isAdmin, 0.0);
        posts.add(p);
      }

      for (int i = 0; i < number_of_posts; i++) {

        double PC = posts.get(i).isAdmin ? POPULARITY_CONSTANT_ADMIN : POPULARITY_CONSTANT_NON_ADMIN;
        double TC = posts.get(i).isAdmin ? TIME_CONSTANT_ADMIN : TIME_CONSTANT_NON_ADMIN;
        posts.get(i).score = calculateRank(posts.get(i).votes, PC, posts.get(i).timestamp, TC,
            posts.get(i).isAdmin ? GRAVITY_ADMIN : GRAVITY_NON_ADMIN);
      }

      Collections.sort(posts, (p1, p2) -> Double.compare(p2.score, p1.score));

      for (int i = 0; i < number_of_posts; i++) {
        System.out.println("Post " + posts.get(i).id + " with score " + posts.get(i).score + " has rank " + (i + 1));
      }
    }
  }
  // private static double calculatePopularityFactor(int like, int comment) {
  // return (WEIGHT_OF_LIKE * like + WEIGHT_OF_COMMENT * comment);
  // }
}