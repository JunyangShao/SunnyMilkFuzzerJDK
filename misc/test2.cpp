#include <iostream>
void X() { std::cout << "X" << std::endl; }
void A() { std::cout << "A" << std::endl; }

int main() {
    void (*B)() = A;
    bool test = (B == A);
    A();
    B();
    std::cout << A << std::endl;
}