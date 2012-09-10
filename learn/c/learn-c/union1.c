#include <stdio.h>

union A{
  int i;
  char c;
}*p, u;

int main(){
  p = &u;
  p-> i = 1;

  printf("%d", p->c);
}
