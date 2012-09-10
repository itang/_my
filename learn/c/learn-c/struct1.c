#include <stdio.h>

struct User
{
  char name[10];
  int age;
  /* data */
};

int main(int argc, char const *argv[])
{
  User user;
  printf("sizeof user: %d", sizeof(user));
  return 0;
}