
/**
* @Date
* 2020-04-06
*
* @Author
* 최병길
*
* @출처
* https://www.acmicpc.net/problem/1613
*
* @문제
* 백준 1613 역사
*
* 세준이가 알고 있는 일부 사건들의 전후 관계들이 주어질 때, 
* 주어진 사건들의 전후 관계도 알 수 있을까? 이를 해결하는 프로그램을 작성해 보도록 하자.
*
* @입력값
* 첫째 줄에 첫 줄에 사건의 개수 n(400 이하의 자연수)과 알고 있는 사건의 
* 전후 관계의 개수 k(50,000 이하의 자연수)가 주어진다. 다음 k줄에는 전후 관계를 알고 있는 
* 두 사건의 번호가 주어진다. 이는 앞에 있는 번호의 사건이 뒤에 있는 번호의 사건보다 
* 먼저 일어났음을 의미한다. 물론 사건의 전후 관계가 모순인 경우는 없다. 
* 다음에는 사건의 전후 관계를 알고 싶은 사건 쌍의 수 s(50,000 이하의 자연수)이 주어진다. 
* 다음 s줄에는 각각 서로 다른 두 사건의 번호가 주어진다. 
* 사건의 번호는 1보다 크거나 같고, N보다 작거나 같은 자연수이다.
*
* @풀이방법
*
* 1. 플로이드 워셜 알고리즘을 이용한다.
* 
* a -> b로 가는 길이 있다면 a가 먼저 나왔다는 의미(-1)
* b -> a로 가는 길이 있다면 b가 먼저 나왔다는 의미(1)
* 둘다 아니라면 서로 중 누가 먼저 나온지 모른다는 의미(0)
*/

#include <cstdio>

using namespace std;

const int MAX_N = 401, INF = 987654321;

int answer, N, K;
int map[MAX_N][MAX_N];

int main() {
	int u, v;
	scanf("%d %d", &N, &K);

	// init
	for (int i = 0; i <= 400; ++i) {
		for (int j = 0; j <= 400; ++j) {
			if (i != j) {
				map[i][j] = INF;
			}
		}
	}

	for (int i = 0; i < K; ++i) {
		scanf("%d %d", &u, &v);
		map[u][v] = 1;
	}

	for (int k = 1; k <= N; ++k) {
		for (int i = 1; i <= N; ++i) {
			for (int j = 1; j <= N; ++j) {
				if (map[i][j] > map[i][k] + map[k][j]) {
					map[i][j] = map[i][k] + map[k][j];
				}
			}
		}
	}

	for (int i = 1; i <= N; ++i) {
		for (int j = 1; j <= N; ++j) {
			printf("%d ", map[i][j]);
		}
		printf("\n");
	}

	// solve
	int s, a, b;
	scanf("%d", &s);

	while (s--) {
		scanf("%d %d", &a, &b);

		if (map[a][b] != INF) {
			printf("-1\n");
		}
		else if (map[b][a] != INF) {
			printf("1\n");
		}
		else {
			printf("0\n");
		}
	}
	return 0;
}