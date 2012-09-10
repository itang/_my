#include <stdio.h>


int main(){
  int i = 100;
  char c = 'a';
  int * p = &i;
  char * p1 = &c;
  long n = 10000;
  printf("sizeof(int): %d\n", sizeof(int));
  //printf("sizeof int: %d\n", sizeof int); //expected primary-expression before 'int'

  printf("sizeof(i): %d\n", sizeof(i));
  printf("sizeof(c): %d\n", sizeof(c));
  printf("sizeof i: %d\n", sizeof i);
  printf("sizeof c: %d\n", sizeof c);

  printf("sizeof(p): %d\n", sizeof(p));
  printf("sizeof(p1): %d\n", sizeof(p1));

  printf("sizeof(n): %d, sizeof(long): %d\n", sizeof(n), sizeof(long));

  int a[100];
  char b[100];

  printf("int a[100]; sizeof(a): %d, sizeof(a[100]): %d\n", sizeof(a), sizeof(a[100]));
  printf("sizeof(&a): %d\n", sizeof(&a));
  printf("sizeof(&a[0]): %d\n", sizeof(&a[0]));
  printf("int b[100]; sizeof(b): %d, sizeof(b[100]): %d\n", sizeof(b), sizeof(b[100]));
  printf("sizeof(&b): %d\n", sizeof(&b));
  printf("sizeof(&b[0]): %d\n", sizeof(&b[0]));
}