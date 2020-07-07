#include <cstdio>
#include <cstring>
#include <vector>
#include <queue>

using namespace std;

struct Pos {
	int r, c;
};

struct Dist {
	int r, c, dist;
};

const int DIR[][2] = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
const int INF = 987654321;
int N, M, answer;
char map[50][50];
int order[5];
bool visited[50][50], chk[5];
Pos pos[2];
vector<Pos> v;

int getDist(int sr, int sc, int er, int ec) {
	memset(visited, false, sizeof(visited));
	queue<Dist> q;
	visited[sr][sc] = true;
	q.push({ sr, sc, 0 });

	while (!q.empty()) {
		Dist dis = q.front();
		q.pop();

		if (dis.r == er && dis.c == ec)
			return dis.dist;

		for (int d = 0; d < 4; ++d) {
			int nr = dis.r + DIR[d][0];
			int nc = dis.c + DIR[d][1];
			if (nr < 0 || nr >= M || nc < 0 || nc >= N)
				continue;
			if (map[nr][nc] != '.' || visited[nr][nc])
				continue;
			visited[nr][nc] = true;
			q.push({ nr, nc, dis.dist + 1 });
		}
	}
	return 0;
}

void calc() {
	// 1. s에서 0번까지 구하기
	int dist = getDist(pos[0].r, pos[0].c, v[order[0]].r, v[order[0]].c);
	if (answer <= dist)
		return;
	// 2. 0번부터 x개수만큼 구하기
	for (int i = 0; i < v.size() - 1; ++i) {
		dist += getDist(v[order[i]].r, v[order[i]].c, v[order[i + 1]].r, v[order[i + 1]].c);
		if (answer <= dist)
			return;
	}
	// 3. x마지막에서 e까지 구하기
	dist += getDist(v[order[v.size() - 1]].r, v[order[v.size() - 1]].c, pos[1].r, pos[1].c);
	answer = answer < dist ? answer : dist;
}

void solve(int depth) {
	if (depth == v.size()) {
		calc();
		return;
	}

	for (int i = 0; i < v.size(); ++i) {
		if (chk[i])
			continue;
		chk[i] = true;
		order[depth] = i;
		solve(depth + 1);
		chk[i] = false;
	}
}

void solve() {
	if (v.size() == 0) {
		answer = getDist(pos[0].r, pos[0].c, pos[1].r, pos[1].c);
		return;
	}
	solve(0);
}

int main() {
	char input[52];
	int sr, sc;
	scanf("%d %d", &N, &M);
	for (int i = 0; i < M; ++i) {
		scanf("%s", input);
		for (int j = 0; j < N; ++j) {
			map[i][j] = input[j];
			if (map[i][j] == 'S') {
				map[i][j] = '.';
				pos[0].r = i;
				pos[0].c = j;
			}
			else if (map[i][j] == 'E') {
				map[i][j] = '.';
				pos[1].r = i;
				pos[1].c = j;
			}
			else if (map[i][j] == 'X') {
				map[i][j] = '.';
				v.push_back({ i, j });
			}
		}
	}
	answer = INF;
	solve();
	printf("%d", answer);
	return 0;
}