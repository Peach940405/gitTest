#include <cstdio>
#include <queue>
using namespace std;

struct Passenger {
	int start, end;
};

const int DIR[][2] = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };
int N, M, fuel, map[20][20];
int taxiR, taxiC;
int answer;
Passenger passenger[401];

bool move(int target) {
	queue<int> q;
	bool visited[20][20] = { false, };
	int curTaxi = taxiR * 100 + taxiC;
	int dist = -1;

	visited[taxiR][taxiC] = true;
	q.push(curTaxi);

	while (!q.empty()) {
		int qSize = q.size();
		if (++dist > fuel)
			return false;
		for (int s = 0; s < qSize; ++s) {
			curTaxi = q.front(); q.pop();
			if (curTaxi == passenger[target].end) {
				taxiR = curTaxi / 100;
				taxiC = curTaxi % 100;
				fuel += dist;
				return true;
			}	
			int r = curTaxi / 100;
			int c = curTaxi % 100;
			for (int d = 0; d < 4; ++d) {
				int nr = r + DIR[d][0];
				int nc = c + DIR[d][1];
				if (nr < 0 || nr >= N || nc < 0 || nc >= N)
					continue;
				if (map[nr][nc] == -1 || visited[nr][nc])
					continue;
				int nextTaxi = nr * 100 + nc;
				q.push(nextTaxi);
				visited[nr][nc] = true;
			}
		}
	}
	return false;
}

int findTarget() {
	queue<int> q;
	bool visited[20][20] = { false, };
	int curTaxi = taxiR * 100 + taxiC;
	int dist = -1;
	int tmp[401] = { 0, };
	int tmpSize = 0;

	q.push(curTaxi);
	visited[taxiR][taxiC] = true;
	
	while (!q.empty()) {
		if (tmpSize > 0)
			break;
		++dist;
		int qSize = q.size();
		for (int s = 0; s < qSize; ++s) {
			curTaxi = q.front(); q.pop();
			int r = curTaxi / 100;
			int c = curTaxi % 100;
			// 승객을 만난 경우
			if (map[r][c] > 0) {
				tmp[tmpSize++] = map[r][c];
			}
			for (int d = 0; d < 4; ++d) {
				int nr = r + DIR[d][0];
				int nc = c + DIR[d][1];
				if (nr < 0 || nr >= N || nc < 0 || nc >= N)
					continue;
				if (map[nr][nc] == -1 || visited[nr][nc])
					continue;
				int next = { nr * 100 + nc};
				q.push(next);
				visited[nr][nc] = true;
			}
		}
	}
	if (dist > fuel)
		return -1;

	int res = -1;
	int tmpVal = 10000;
	for (int i = 0; i < tmpSize; ++i) {
		if (tmpVal <= passenger[tmp[i]].start)
			continue;
		tmpVal = passenger[tmp[i]].start;
		res = tmp[i];
	}
	taxiR = passenger[res].start / 100;
	taxiC = passenger[res].start % 100;
	map[taxiR][taxiC] = 0;
	fuel -= dist;
	return res;
}

void solve() {
	answer = -1;
	for (int i = 0; i < M; ++i) {
		// 1. 우선순위 승객찾기1
		int target = findTarget();
		if (target == -1)
			return;
		// 2. 해당 승객의 목적지로 이동하기
		bool isArrive = move(target);
		if (!isArrive)
			return;
	}
	answer = fuel;
}

int main() {
	scanf("%d %d %d", &N, &M, &fuel);
	for (int r = 0; r < N; ++r) {
		for (int c = 0; c < N; ++c) {
			scanf("%d", &map[r][c]);
			if (map[r][c] == 1)
				map[r][c] = -1;
		}
	}
	scanf("%d %d", &taxiR, &taxiC);
	--taxiR, --taxiC;
	for (int i = 1; i <= M; ++i) {
		int sr, sc, er, ec;
		scanf("%d %d %d %d", &sr, &sc, &er, &ec);
		--sr, --sc, --er, --ec;
		passenger[i].start = sr * 100 + sc;
		passenger[i].end = er * 100 + ec;
		map[sr][sc] = i;
	}
	solve();
	printf("%d", answer);
	return 0;
}