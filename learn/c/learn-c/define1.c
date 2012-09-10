#include <stdio.h>

#define EMPTY

#define X 3
#define Y X*2
#undef  X
#define  X 2
int z = Y; //预处理用到时才展开
int main() {
  char c = EMPTY 'd';
  printf("%c\n", c);
  printf("%d\n", z);
  //printf("%d\n", ++i+++i+++i);
  return 0;

  #ifdef EMPTY
  printf("define empty\n");
  #else
  printf("undefine empty\n");
  #endif
}