#include <stdio.h>

static int j;

int fun1 (void) {
  static int i = 0;
  i ++;
  return i;
}

void fun2 (void) {
  j = 0;
  j ++;
}

int main(){
  int ret = 0;
  for(int k=0; k < 10; k++){
    ret = fun1();
    fun2();
  }
  printf("%d %d\n", j, ret);
  return 0;
}