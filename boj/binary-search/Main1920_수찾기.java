/**
 * @Date
 * 2019-09-25
 *
 * @Author
 * �ֺ���
 *
 * @��ó
 * https://www.acmicpc.net/problem/1920
 *
 * @����
 * ���� 1920. �� ã��
 *
 * N���� ���� A[1], A[2], ��, A[N]�� �־��� ���� ��, �� �ȿ� X��� ������ �����ϴ��� �˾Ƴ���
 * 
 * @�Է°�
 * ù° �ٿ� �ڿ��� N(1��N��100,000)�� �־�����. ���� �ٿ��� N���� ���� A[1], A[2], ��, A[N]�� �־�����. 
 * ���� �ٿ��� M(1��M��100,000)�� �־�����. ���� �ٿ��� M���� ������ �־����µ�, �� ������ A�ȿ� �����ϴ��� �˾Ƴ��� �ȴ�. 
 * ��� �������� ������ int �� �Ѵ�.
 * 
 * @Ǯ�̹��
 * 1. A�迭�� ���� �� �̺�Ž���� �ϱ����ؼ� ������ �Ѵ�.
 * 2. key���� ���Ͽ� �̺�Ž���� �����Ѵ�.
 *
 */

package algo.binarysearch;

import java.io.*;
import java.util.*;

public class Main1920_��ã�� {
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input1920.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = new Integer(br.readLine());
		int[] A = new int[N];
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < N; ++i) {
			A[i] = new Integer(st.nextToken());
		}

		Arrays.sort(A);

		StringBuilder sb = new StringBuilder();
		int answer;
		int start, end, mid;
		int key;
		int M = new Integer(br.readLine());
		st = new StringTokenizer(br.readLine());

		for (int i = 0; i < M; ++i) {
			start = 0;
			end = N - 1;
			answer = 0;
			key = new Integer(st.nextToken());

			while (start <= end) {
				mid = (start + end) >> 1;

				if (A[mid] < key) {
					start = mid + 1;
				} else if (A[mid] > key) {
					end = mid - 1;
				} else {
					answer = 1;
					break;
				}

			}

			sb.append(answer).append("\n");
		}

		System.out.println(sb.toString());
		br.close();
	}
}
