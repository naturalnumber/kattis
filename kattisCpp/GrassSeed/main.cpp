#include <iostream>

int main() {
    double c, w, l, t = 0.0;
    short n;
    std::cin >> c >> n;

    for (int i = 0; i < n; ++i) {
        std::cin >> w >> l;

        t += (w*l*c);
    }

    //std::cout << t << std::endl;
    printf ("%.7f\n", t);

    return 0;
}