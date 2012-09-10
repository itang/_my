#include <stdio.h>

int main(){

  int a[5] = {1,2,3,4,5};
  int* ptr1 = (int*)(&a + 1);
  int* ptr2 = (int*)((int)a + 1);

  printf("%x\n", ptr1[0]);
  printf("%x\n", ptr1[1]);
  printf("%x\n", ptr1[-1]);
  printf("%x %x\n", ptr1[-1], *ptr2);


  int * p = a;
  printf("%x\n", (p + 1)[-1]);
  return 0;
}