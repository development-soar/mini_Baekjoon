#include <iostream>
#include <vector>
#include <tuple>
#include <algorithm>
#include <string>

using namespace std;

bool compare(const tuple<string, int, int>& a, const tuple<string, int, int>& b) {
    if (get<2>(a) != get<2>(b)) return get<2>(a) > get<2>(b);
    if (get<1>(a) != get<1>(b)) return get<1>(a) < get<1>(b);
    return get<0>(a) < get<0>(b);
}

int main() {
    int n;
    cin >> n;
    vector<tuple<string, int, int>> books;

    for (int i = 0; i < n; ++i) {
        string title;
        int pages, year;
        cin >> title >> pages >> year;
        books.emplace_back(title, pages, year);
    }

    sort(books.begin(), books.end(), compare);

    for (const auto& book : books) {
        cout << get<0>(book) << " " << get<1>(book) << " " << get<2>(book) << "
";
    }

    return 0;
}