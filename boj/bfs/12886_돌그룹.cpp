#include <cstdio>
#include <queue>
#include <algorithm>

using namespace std;

typedef pair<int, int> pii;

int A, B, C, D;

int solve() {
	queue<pii> q;
	bool visited[1001][1001];

	q.push({ A, B });
	visited[A][B] = true;

	while (!q.empty()) {
		int a = q.front().first, b = q.front().second;
		q.pop();
		int c = D - a - b;

		if (a == b && b == c) {
			return 1;
		}

		int na[] = { a, a, b }, nb[] = { b, c, c };
		for (int i = 0; i < 3; ++i) {
			a = na[i];
			b = nb[i];
			c = D - a - b;

			if (a < b) {
				b -= a;
				a += a;
			}
			else if (a > b) {
				a -= b;
				b += b;
			}
			else {
				continue;
			}

			A = min(min(a, b), c);
			B = max(max(a, b), c);

			if (visited[A][B]) {
				continue;
			}
			visited[A][B] = true;
			q.push({ A, B });
		}
	}

	return 0;
}

int main() {
	scanf("%d %d %d", &A, &B, &C);
	D = A + B + C;
	printf("%d", D % 3 ? 0 : solve());
	return 0;
}