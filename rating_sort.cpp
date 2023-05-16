#include <iostream>
#include <vector>
#include <algorithm>
#include <ctime>
#include <cmath>
using namespace std;

const double POPULARITY_CONSTANT_ADMIN = 8.5;
const double POPULARITY_CONSTANT_NON_ADMIN = 6.5;
const int TIME_CONSTANT_ADMIN = 360;
const int TIME_CONSTANT_NON_ADMIN = 720;
const double GRAVITY_ADMIN = 1.5;
const double GRAVITY_NON_ADMIN = 1.7;

const double WEIGHT_OF_LIKE = 1.5;
const double WEIGHT_OF_COMMENT = 2.0;

struct Post
{
  int id;
  int votes;
  double score;
  time_t timestamp;
  bool isAdmin;

  Post(int i, int c, time_t t, bool a, double score)
  {
    id = i;
    votes = c;
    timestamp = t;
    isAdmin = a;
    score = 0.0;
  }
};

// double calculatePopularityFactor(int like, int comment)
// {
//   return (WEIGHT_OF_LIKE * like + WEIGHT_OF_COMMENT * comment);
// }

double calculateRank(int P, int PC, int TE, int TC, double G)
{
  double score = (P + PC) / (pow((TE + TC), G));
  return score;
}

bool comparePosts(Post p1, Post p2)
{
  return p1.score > p2.score;
}

int main()
{
  // int like, comment;
  // cin >> like >> comment;

  int number_of_posts;
  cin >> number_of_posts;

  vector<Post> posts;

  for (int i = 0; i < number_of_posts; i++)
  {
    int votes;
    time_t timestamp;
    bool isAdmin;

    cin >> votes >> timestamp >> isAdmin;

    Post p(i + 1, votes, timestamp, isAdmin, 0.0);
    posts.push_back(p);
  }

  for (int i = 0; i < number_of_posts; i++)
  {
    int G = posts[i].isAdmin ? GRAVITY_ADMIN : GRAVITY_NON_ADMIN;
    int PC = posts[i].isAdmin ? POPULARITY_CONSTANT_ADMIN : POPULARITY_CONSTANT_NON_ADMIN;
    int TC = posts[i].isAdmin ? TIME_CONSTANT_ADMIN : TIME_CONSTANT_NON_ADMIN;
    posts[i].score = calculateRank(posts[i].votes, PC, posts[i].timestamp, TC, G);
  }

  sort(posts.begin(), posts.end(), comparePosts);

  for (int i = 0; i < number_of_posts; i++)
  {
    cout << "Post " << posts[i].id << " with score " << posts[i].score << " has rank " << (i + 1) << endl;
  }
}