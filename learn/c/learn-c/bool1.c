#include <stdio.h>

#define FALSE 0

void func(char *msg, bool b);

int main(){
  bool bTestFlag = FALSE;
  func((char*)&"bTestFlag = FALSE", bTestFlag);

  int *p;
  int i = 1;
  int *p1 = &i;
  int *p2 = NULL;
  printf("%d\n", p);
  func((char*)&"int *p", p);
  func((char*)&"int *p1", p1);
  func((char*)&"int *p2", p2);
  return 0;
}

void func(char *msg, bool b){
  if(b){
    printf("%s, %d,  true\n", msg, b);
  }else{
    printf("%s, %d,  false\n", msg, b);
  }
} 