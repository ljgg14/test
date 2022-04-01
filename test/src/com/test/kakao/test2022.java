package com.test.kakao;

import java.util.*;

public class test2022 {


	/**
	 * 신고결과 받기
	 * @Description : K번째 수 구하기
	 */
    public static int[] solution() {
    	
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
        
        // 신고된 ID 추출
        String[] rptdArr = Arrays.stream(rptArr2D).map(item->item[1]).toArray(String[]::new);
        // list 변환
        List<String> rptdList = (List<String>) Arrays.asList(rptdArr);
        System.out.println("########### rptdList \t" + rptdList);
        
        // 신고된 ID 중복 제거
        String[] rptdArrDst = Arrays.stream(rptdArr).distinct().toArray(String[]::new);
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
                
                String[] tmpArr = Arrays.stream(rptdArrCnt2D).filter(f->f[0].equals(strRptId)).map(f->f[1]).toArray(String[]::new);
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
    
}
