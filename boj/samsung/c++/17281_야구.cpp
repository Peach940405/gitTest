#include <cstdio>

using namespace std;

const int MAX_N = 50, MAX_HITTER = 9;

int answer, N, map[MAX_N + 1][MAX_HITTER + 1], tmp[MAX_HITTER + 1], buf[MAX_HITTER * 3];
bool visited[MAX_HITTER + 1];

void play() {
	int hitter = 1, score = 0, out, hitCnt, t;

	for (int i = 1; i <= N; ++i) {
		out = hitCnt = 0;
		for (int h = hitter; out < 3; h = hitter) {
			if (map[i][tmp[h]]) {
				buf[++hitCnt] = map[i][tmp[h]];
			}
			else {
				++out;
			}
			hitter = hitter < 9 ? hitter + 1 : 1;
		}
		t = 0;
		while (hitCnt) {
			t += buf[hitCnt];
			if (t > 3) { // 3루수 이상 지났을 경우 즉, 홈에 들어왔을 경우
				score += hitCnt;
				break;
			}
			--hitCnt;
		}
	}

	answer = answer > score ? answer : score;
}

void solve(int depth) {
	if (depth == MAX_HITTER + 1) {
		play();
		return;
	}

	if (depth == 4) {
		tmp[depth] = 1;
		solve(depth + 1);
	}
	else {
		for (int i = 2; i <= MAX_HITTER; ++i) {
			if (visited[i]) {
				continue;
			}
			visited[i] = true;
			tmp[depth] = i;
			solve(depth + 1);
			visited[i] = false;
		}
	}
}

int main() {
	scanf("%d", &N);

	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= MAX_HITTER; ++j) {
			scanf("%d", &map[i][j]);
		}
	}

	solve(1);
	printf("%d", answer);

	return 0;
}