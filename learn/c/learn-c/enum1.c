#include <stdio.h>

enum Color{
  GREEN = 1,
  RED,
  BLUE,
  GREEN_BLUE = 10,
  GRAY
} ColorVar;

int main(){
  ColorVar = RED;
  printf("%d\n", ColorVar);

  Color v1 = GRAY;
  printf("%d\n", v1);

  printf("sizeof(v1): %d\n", sizeof(v1));
  return 0;
}