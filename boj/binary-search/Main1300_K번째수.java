/**
 * @Date
 * 2019-09-25
 *
 * @Author
 * 최병길
 *
 * @출처
 * https://www.acmicpc.net/problem/1300
 *
 * @문제
 * 백준 1300. K번째 수
 *
 * 세준이는 N*N크기의 배열을 만들었다. (배열의 방 번호는 1부터 시작한다.)
 * 그 배열을 A라고 했을 때, 배열에 들어가는 수는 A[i][j] = i*j 이다.
 * 세준이는 이 수를 일차원 배열 B에 넣으려고 한다. 그렇게 되면, B의 크기는 N*N이 될 것이다. 
 * 그러고 난 후에, B를 오름차순 정렬해서 k번째 원소를 구하려고 한다.
 * N이 주어졌을 때, k번째 원소를 구하는 프로그램을 작성하시오.

 * @입력값
 * 첫째 줄에 배열의 크기 N이 주어진다. N은 105보다 작거나 같은 자연수이다. 둘째 줄에 k가 주어진다. 
 * k는 min(109, n2)보다 작거나 같은 자연수이다.
 * 
 * @풀이방법
 * 1. 이분탐색을 이용한다.
 * 2. 임의의 숫자 mid를 골라 mid보다 작은 숫자의 개수를 파악해서 K번째 숫자를 구한다.
 * 3. mid보다 작은 숫자를 효과적으로 구하기 위해 1 ~ N까지 반복문을 돌린다. i * j <= mid이므로 (mid / i)가 조건을 만족하는 j이다.
 * 4. N이 1000보다 크면 mid / i 가 N보다 커질 수 있으므로 mid/i 와 N 중 작은 값을 더해 mid보다 작은 숫자의 개수를 파악
 *
 */

package algo.binarysearch;

import java.io.*;

public class Main1300_K번째수 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = new Integer(br.readLine());
		int k = new Integer(br.readLine());

		int start = 1, end = k, mid, answer = 0;
		long cnt;

		while (start <= end) {
			cnt = 0;
			mid = (start + end) >> 1;

			for (int i = 1; i <= N; ++i) {
				cnt += Math.min(mid / i, N);
			}

			if (cnt < k) {
				start = mid + 1;
			} else {
				answer = mid;
				end = mid - 1;
			}
		}

		System.out.println(answer);
		br.close();
	}
}
