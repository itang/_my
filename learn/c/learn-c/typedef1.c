#include <stdio.h>

#define int32 static int
//typedef static int int32;

int main(){

  int32 d = 1;
  printf("%d\n", d);
  printf("hello\n");

  int i = 0;
  printf("%d\n", i++ + i);
  printf("%d\n", ++i+ ++i);
  printf("%d\n",  2/(-2));
  printf("%d\n", 2%(-2));
  //printf("%d\n", ++i+++i+++i);
  //printf("%d\n", ++i++ + i++ + i );

 // for(int i=0, printf("first=%d", i);
 //  /   i < 10, printf("Second=%d", i);
 //     i++, printf("Third=%d", i) ){
  //  printf("Fourth=%d", i);
  //}
  return 0;
}