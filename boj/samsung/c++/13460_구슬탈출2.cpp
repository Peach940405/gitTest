#include <cstdio>
#include <queue>
#include <algorithm>

using namespace std;

struct Info {
	int rr, rc, br, bc, cnt;
};
const int DIR[][2] = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
int answer, N, M;
char map[11][11];
Info start;

void solve() {
	bool visited[10][10][10][10] = { false, };
	queue<Info> q;
	visited[start.rr][start.rc][start.br][start.bc] = true;
	q.push(start);
	answer = -1;

	while (!q.empty()) {
		Info cur = q.front();
		q.pop();
		if (cur.cnt > 10)
			break;
		if (map[cur.rr][cur.rc] == 'O' && map[cur.br][cur.bc] != 'O') {
			answer = cur.cnt;
			break;
		}
		
		for (int d = 0; d < 4; ++d) {
			int nextRr = cur.rr;
			int nextRc = cur.rc;
			int nextBr = cur.br;
			int nextBc = cur.bc;

			while (1) {
				if (map[nextRr][nextRc] != '#' && map[nextRr][nextRc] != 'O')
					nextRr += DIR[d][0], nextRc += DIR[d][1];
				else {
					if(map[nextRr][nextRc] == '#')
						nextRr -= DIR[d][0], nextRc -= DIR[d][1];
					break;
				}
			}
			while (1) {
				if (map[nextBr][nextBc] != '#' && map[nextBr][nextBc] != 'O')
					nextBr += DIR[d][0], nextBc += DIR[d][1];
				else {
					if (map[nextBr][nextBc] == '#')
						nextBr -= DIR[d][0], nextBc -= DIR[d][1];
					break;
				}
			}
			if (nextRr == nextBr && nextRc == nextBc) {
				if (map[nextRr][nextRc] != 'O') {
					int redDist = abs(nextRr - cur.rr) + abs(nextRc - cur.rc);
					int blueDist = abs(nextBr - cur.br) + abs(nextBc - cur.bc);
					if(redDist > blueDist)
						nextRr -= DIR[d][0], nextRc -= DIR[d][1];
					else
						nextBr -= DIR[d][0], nextBc -= DIR[d][1];
				}
			}
			if (visited[nextRr][nextRc][nextBr][nextBc])
				continue;
			visited[nextRr][nextRc][nextBr][nextBc] = true;
			Info next;
			next.rr = nextRr;
			next.rc = nextRc;
			next.br = nextBr;
			next.bc = nextBc;
			next.cnt = cur.cnt + 1;
			q.push(next);
		}
	}
}

int main() {
	scanf("%d %d", &N, &M);
	for (int i = 0; i < N; ++i)
		scanf("%s", map[i]);
	for (int i = 0; i < N; ++i) {
		for (int j = 0; j < M; ++j) {
			if (map[i][j] == 'R')
				start.rr = i, start.rc = j;
			if (map[i][j] == 'B')
				start.br = i, start.bc = j;
		}
	}
	solve();
	printf("%d", answer);
	return 0;
}