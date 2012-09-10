#include <stdio.h>

int main(){
  int * p = NULL;
  int i = 100;
  p = &i;

  void * p1 = p;

  printf("*p: %d, *p1:%d\n", *p, *(int *)p1);

  return 0;
}