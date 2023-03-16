#include <stdio.h>

int x() { return 3;}
extern int (*x_ptr)();
extern int getX();

int main() {
    x_ptr = x;
    printf("x() = %d\n", getX());
    return 0;
}
