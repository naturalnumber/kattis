#include <iostream>
#include <algorithm>

int main() {
    std::string in;
    std::cin >> in;
    bool h = false;

    for (int i = 0; i < in.length()-1 && !h; ++i) {
        if (in[i] == 's' && in[i+1] == 's') {
            h = true;
        }
    }

    if (h) {
        std::cout << "hiss" << std::endl;
    } else {
        std::cout << "no hiss" << std::endl;
    }
    return 0;
}