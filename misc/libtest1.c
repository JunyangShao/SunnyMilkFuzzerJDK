int x_default() { return 1; }
int (*x_ptr)() = x_default;

int getX() { return x_ptr(); }