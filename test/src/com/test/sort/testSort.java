package com.test.sort;

import java.util.*;

public class testSort {


	/**
	 * K번째 수
	 * @Description : K번째 수 구하기
	 */
	public static int[] solution() {

		int[] array = {1, 5, 2, 6, 3, 7, 4};
		int[][] commands = {{2, 5, 3}, {4, 4, 1}, {1, 7, 3}};
		
		
        int[] answer = new int[commands.length];
        
        int idxI, idxJ, idxK;
        int[] arrTmp, arrAnsOne;
        
        // For commands[i]
        for(int i=0; i<commands.length; i++) {
        	
//            idxI = commands[i][0];
//            idxJ = commands[i][1];
//            idxK = commands[i][2];
//            
//        	int[] temp = Arrays.copyOfRange(array, idxI-1, idxJ);
//        	// 오름차순
//        	Arrays.sort(temp);
//        	// 내림차순
//        	Integer[] arrTemp = Arrays.stream(temp).boxed().toArray(Integer[]::new);
//        	Arrays.sort(arrTemp, Collections.reverseOrder());
//        	
//        	
//        	
//        	printArray(temp);
//        	
//        	answer[i] = temp[idxK - 1];
        	
            arrAnsOne = null;
            idxI = commands[i][0];
            idxJ = commands[i][1];
            idxK = commands[i][2];
            
            // 1. Cut
            for(int j=0;j<array.length;j++) {
                // idxI ~ idxJ 자르기
                if (j+1 >= idxI && j+1 <= idxJ) {
                    // 배열 자르기
                    if(arrAnsOne != null && arrAnsOne.length > 0) {
                        // 배열 크기 1증가
                        arrTmp = new int[arrAnsOne.length + 1];
                        // 배열 복사
                        for(int k=0; k<arrAnsOne.length; k++) {
                            arrTmp[k] = arrAnsOne[k];
                        }
                        // 값 할당
                        arrTmp[arrTmp.length - 1] = array[j];
                        arrAnsOne = arrTmp;
                    } else {
                         // 값 할당
                        arrAnsOne = new int[]{array[j]};
                    }
                } //-- End fo If
            }
            // 2. Sort
            Arrays.sort(arrAnsOne);
            
            // 3. 출력 값 할당
            answer[i] = arrAnsOne[idxK - 1];
            
        } //-- End of For commands[i]
        
        printArray(answer);
        
        return answer;
    }
	
	
	/**
	 * 가장 큰 수
	 * @Description : 가장 큰 수 구하기
	 */
	public static void solution2() {
//		int[] numbers = {3, 30, 34, 5, 9}; // 9534330
//		int[] numbers = {1, 10, 100, 1000}; // 1101001000
//		int[] numbers = {3, 30, 34, 5, 9, 124, 124, 412, 1000};
//		int[] numbers = {67,676,677}; // 67767676
		int[] numbers = {0, 0, 0}; // 40403
		
        String answer = "";
        
        // 문자열 배열로 변환
        String[] strArrTmp = Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new);
        
        // 문자열 길이를 추가한 2차원 배열 생성 (문자열은 4회씩 복제 시킨다. ex.15->15151515)
        String[][] strArr2D = Arrays.stream(strArrTmp)
                                    .map(item -> new String[]{item+item+item+item, String.valueOf(item.length())})
                                    .toArray(String[][]::new);
        
        // 2차원 배열 행별 0번째 인덱스 기준으로 내림차순 정렬
        Arrays.sort(strArr2D, Comparator.comparing((String[] order) -> order[0]).reversed());
        
        // 원래 문자열들의 길이로 잘라 1차원 배열로 반환
        String[] strArrAns = Arrays.stream(strArr2D)
                                    .map(item -> item[0].substring(0, Integer.valueOf(item[1])))
                                    .toArray(String[]::new);
        
        
        // 결과 조합
        StringBuffer sb = new StringBuffer();
        boolean zeroFlag = false;
        for(String str : strArrAns) {
            sb.append(str);
        }
        String strAns = sb.toString();
        // 원소가 모두 0일 경우 000 이 아닌 0으로 리턴
        answer = strAns.chars().filter(c->c=='0').count() == strAns.length() ? "0" : strAns;
        
        
        System.out.println("########### \t" + answer);
		
	}
	
    
    public static void printArray(Object array) {
    	
        if(array != null) {
            if(array instanceof int[])
            {
               int[] intArray = (int[])array;
               System.out.println(Arrays.toString(intArray));
               
            } else {
            	Object[] arrObj = (Object[]) array;
            	System.out.println(Arrays.toString(arrObj));
            }
        }
    }
	
    
    public static String printArrayStr(String[] array) {
    	StringBuffer sb = new StringBuffer();
        for(String i : array) {
            sb.append(String.valueOf(i));
        }
        
        return sb.toString();
    }
    
}
