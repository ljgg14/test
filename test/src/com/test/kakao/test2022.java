package com.test.kakao;

import java.util.*;
import java.util.stream.Collectors;

public class test2022 {


	/**
	 * 신고결과 받기
	 * @Description : 신고결과 받기
	 */
    public static int[] solution1() {
    	
    	String[] id_list = {"muzi", "frodo", "apeach", "neo"};
    	String[] report  = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
//    	String[] id_list = {"con", "ryan"};
//    	String[] report  = {"ryan con", "ryan con", "ryan con", "ryan con"};
    	
    	int k = 2;
    	
    	int[] answer = new int[id_list.length];

        System.out.println("########### id_list \t" + Arrays.toString(id_list));
        System.out.println("########### report \t" + Arrays.toString(report));
        
        
        // 신고 중복 제거
        String[] rptArrDst = Arrays.stream(report).distinct().toArray(String[]::new);
        
        // 신고 2차원배열 생성
        String[][] rptArr2D = Arrays.stream(rptArrDst)
                                .map(item -> item.split(" "))
                                .toArray(String[][]::new);
        
        // 신고된 ID 추출 List
        List<String> rptdList = Arrays.stream(rptArr2D).map(item->item[1]).collect(Collectors.toList());
        System.out.println("########### rptdList \t" + rptdList);
        
        // 신고된 ID 추출 및 중복 제거
        String[] rptdArrDst = Arrays.stream(rptArr2D).map(item->item[1]).distinct().toArray(String[]::new);
        // 신고된 ID, 신고 건수 2차원배열 생성
        String[][] rptdArrCnt2D = Arrays.stream(rptdArrDst)
                                        .map(item->new String[]{item, String.valueOf(Collections.frequency(rptdList,item))} )
                                        .toArray(String[][]::new);
        System.out.println("########### rptdArrCnt2D " + Arrays.deepToString(rptdArrCnt2D));
        
        // 로직
        for(int i=0; i<id_list.length; i++) {
            String strId = id_list[i];
            int ben_cnt = 0;
            
            // 해당 유저가 신고한 ID 필터링
            String[] rptIdArr = Arrays.stream(rptArr2D)
                                        .filter(item->item[0].equals(strId))
                                        .map(item->item[1]).toArray(String[]::new);
            System.out.println("########### rptIdArr ("+strId+") \t" + Arrays.toString(rptIdArr));
            
            // 신고한 ID의 이용정지 기준 충족 횟수
            for(int j=0;j<rptIdArr.length;j++) {
                String strRptId = rptIdArr[j];
                
                String[] tmpArr = Arrays.stream(rptdArrCnt2D)
                							.filter(f->f[0].equals(strRptId))
                							.map(f->f[1]).toArray(String[]::new);
                int rptIdCnt = Integer.valueOf(tmpArr[0]);
                
                // 정지 기준 충족할 경우 정지 카운트 증가
                if(rptIdCnt >= k) ben_cnt++;
            }
            
            // 해당 유저의 신고결과 할당
            answer[i] = ben_cnt;
        }
        
        

        System.out.println("########### answer \t" + Arrays.toString(answer));
        
        return answer;
    }
    
    
    /**
	 * 신고결과 받기1_1
	 * @Description : 신고결과 받기 정답 풀이 버전
	 */
    public static int[] solution1_1() {
    	
    	String[] id_list = {"muzi", "frodo", "apeach", "neo"};
    	String[] report  = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
//    	String[] id_list = {"con", "ryan"};
//    	String[] report  = {"ryan con", "ryan con", "ryan con", "ryan con"};
    	
    	int k = 2;
    	
    	int[] answer = new int[id_list.length];

        System.out.println("########### id_list \t" + Arrays.toString(id_list));
        System.out.println("########### report \t" + Arrays.toString(report));

        
        // 신고 중복 제거
        String[] rptArrDst = Arrays.stream(report).distinct().toArray(String[]::new);
        
        // 신고 2차원배열 생성
        String[][] rptArr2D = Arrays.stream(rptArrDst)
                                .map(item -> item.split(" "))
                                .toArray(String[][]::new);
        
        // 신고된 ID 추출 및 중복 제거
        String[] rptdArrDst = Arrays.stream(rptArr2D).map(item->item[1]).distinct().toArray(String[]::new);
        System.out.println("########### rptdArrDst  " + Arrays.toString(rptdArrDst));

        // report를 하나씩 처리하면서 각 유저가 누구에게 신고를 당했는지 목록을 만듭니다.
        // == [신고된 유저 아이디 1] : [신고한 유저 A, 신고한 유저 B, ... ]
        String[][][] rptdArrDst3D = Arrays.stream(rptdArrDst)
							                .map(item-> new String[][] {new String[]{item},
							                                            Arrays.stream(rptArr2D)
							                                                    .filter(f->f[1].equals(item))
							                                                    .map(f->f[0])
							                                                    .toArray(String[]::new)})
							                .toArray(String[][][]::new);
        System.out.println("########### rptdArrDst3D  " + Arrays.deepToString(rptdArrDst3D));
        
        
        // 신고 결과 초기화
        String[][] answer2D = Arrays.stream(id_list).map(item->new String[] {item, "0"}).toArray(String[][]::new);
        
        // 로직------------
        for(int i=0; i<rptdArrDst3D.length; i++) {
        	// 신고된 유저 아이디를 순회하면서 정지 기준을 만족하는 유저가 있다면(신고한 유저 수가 k 이상이면)
        	if(rptdArrDst3D[i][1].length >= k) {
        		// 신고한 유저 목록을 순회하면서
        		for(int j=0;j<answer2D.length;j++) {
        			// 정지 유저를 신고한 유저의 메일 카운트 1 증가
        			if( Arrays.stream(rptdArrDst3D[i][1]).anyMatch(answer2D[j][0]::equals)  == true) {
        				
        				int cnt = Integer.valueOf(answer2D[j][1]) + 1;
        				answer2D[j][1] = String.valueOf(cnt);
        			}
        		}

                System.out.println("########### answer2D  " + Arrays.deepToString(answer2D));
        	}
        }
        // 신고 결과 int 배열 추출
        answer = Arrays.stream(answer2D).mapToInt(item->Integer.valueOf(item[1])).toArray();
        

        System.out.println("########### answer \t" + Arrays.toString(answer));
        
        
        return answer;
    }
    
    
    /**
	 * 신고결과 받기1_2
	 * @Description : 신고결과 받기 다른 사람 풀이 버전 참고
	 */
    public static int[] solution1_2() {
    	
    	String[] id_list = {"muzi", "frodo", "apeach", "neo"};
    	String[] report  = {"muzi frodo", "apeach frodo", "frodo neo", "muzi neo", "apeach muzi"};
//    	String[] id_list = {"con", "ryan"};
//    	String[] report  = {"ryan con", "ryan con", "ryan con", "ryan con"};
    	
    	int k = 2;
    	
    	int[] answer;

        System.out.println("########### id_list \t" + Arrays.toString(id_list));
        System.out.println("########### report \t" + Arrays.toString(report));

        
        // 신고 중복 제거
        String[] rptArrDst = Arrays.stream(report).distinct().toArray(String[]::new);
        
        // 신고 2차원배열 생성
        String[][] rptArr2D = Arrays.stream(rptArrDst)
                                .map(item -> item.split(" "))
                                .toArray(String[][]::new);
        
        // Map에 신고된 유저, 신고횟수 누적 업데이트 
        HashMap<String, Integer> rptdCntMap = new HashMap<String, Integer>();
        for(String[] strArr : rptArr2D) {
            String strRtpd = strArr[1];
            rptdCntMap.put(strRtpd, rptdCntMap.getOrDefault(strRtpd, 0) + 1);
        }
        
        // 신고 결과 생성
        answer = Arrays.stream(id_list)
        				.map(item->{
							// 해당 유저가 신고한 ID 필터링
				            String[] rptIdArr = Arrays.stream(rptArr2D)
				                                        .filter(x->x[0].equals(item))
				                                        .map(x->x[1]).toArray(String[]::new);
				            // 신고한 ID가 정지기준에 적합한 경우 카운트
				            return Arrays.stream(rptIdArr)
				            				.filter(z->rptdCntMap.getOrDefault(z, 0) >= k)
				            				.count();
						}).mapToInt(Long::intValue).toArray();
        

        System.out.println("########### answer \t" + Arrays.toString(answer));
        
        
        return answer;
    }
    
}
