#include <stdio.h>

int main(){
  int i = 20;
  unsigned j = 10;
  printf("i + j = %d\n", 20 + 10);

  unsigned k;
  //for(k = 9; k >= 0 ; k--){
  for(k = 9; k > 0 ; k--){
    printf("%u\n", k);
  }
  return 0;
}