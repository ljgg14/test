package com.test.kakao;

import java.util.*;
import java.util.stream.Collectors;
import java.text.*;

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
    
    
    /**
	 * k진수에서 소수 개수 구하기
	 * @Description : k진수에서 소수 개수 구하기
	 */
    public static int solution2() {
    	
    	int n = 437674;
    	int k = 3;
//    	int n = 110011;
//    	int k = 10;
//    	int n = 999999;
//    	int k = 3;
    	
    	int answer = 0;
    	
        System.out.println("########### n: " + String.valueOf(n));
        System.out.println("########### k: " + String.valueOf(k));
        System.out.println("########### 변환: " + Integer.toString(n, k));
    	
//    	// 10진법 -> k진법
//        int intCur = n;
//        StringBuilder sb = new StringBuilder();
//        // 진법 변환할 숫자가 0보다 큰 경우 지속 진행
//        while(intCur > 0) {
//            // 만약 N으로 나누었는데 10보다 작다면 해당 숫자를 바로 append
//            if(intCur % k < 10) {
//                sb.append(intCur % k);
//            // 만약 N이 10보다 큰 경우, N으로 나누었는데 10 이상이면 10진법이므로 10만큼 빼고 'A'를 더한다.
//            } else {
//                sb.append(String.valueOf(intCur % k - 10 + 'A'));
//            }
//            
//            intCur /= k;
//        }
//        // 변환된 수
//        String strCnv = sb.reverse().toString();
        
        // 10진법 -> k진법 변환을 함수로 심플하게
        // 문제 조건에 맞는 소수를 분리하여 long배열 생성
        long[] numArr = Arrays.stream(Integer.toString(n, k).split("0"))
                                .filter(c->"".equals(c) == false)
                                .mapToLong(Long::valueOf).toArray();
        System.out.println("########### numArr \t" + Arrays.toString(numArr));
        
        // 소수 구하기 : 1을 제외하고 자기 자신으로 한 번만 나눠질때 소수
        for(long num : numArr) {
            // 0과 1은 소수가 아니다
            if(num < 2) continue;
            
            // 제곱근 까지 약수가 없는 경우 소수
            int cnt = 0;
            for(long l=2; l<=Math.sqrt(num); l++) {
                if(num % l == 0) {
                    cnt ++;
                    break;
                }
            }
            // 제곱근 까지 약수가 없는 경우 소수
            if(cnt==0) {
            	answer++;
                System.out.println("########### 소수: \t" + String.valueOf(num));
            }
        }

        System.out.println("########### answer \t" + String.valueOf(answer));
    	
    	return answer;
    }
    
    
    /**
	 * 주차 요금 계산
	 * @Description : 주차 요금 계산
	 */
    public static int[] solution3() {
    	
    	int[] fees = {180, 5000, 10, 600};
    	String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
//    	int[] fees = {1, 461, 1, 10};
//    	String[] records = {"00:00 1234 IN"};
    	
    	
    	int[] answer = {};
    	
        String[][] carArr2D = Arrays.stream(records)
					                .map(r->r.split(" "))
					                .toArray(String[][]::new);
		// 차량번호 중복제거
		String[] carArrDst = Arrays.stream(carArr2D)
					                .map(r->r[1])
					                .distinct().toArray(String[]::new);
		// 오름차순 정렬
		Arrays.sort(carArrDst);
		
		
		// 중복 제거 차량 번호 기준으로 써치
		long[] carTmArr = Arrays.stream(carArrDst)
					            .map(x-> {
					        		// 시간계산을 위한 날짜 포멧 yy/MM/dd HH:mm:ss
					        		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
					                // 해당 차량번호
					                String strCar = x;
					                // 시간 계산을 위한 Map 생성
					                HashMap<String, String> timeMap = new HashMap<String, String>();

					                System.out.println("########### strCar \t" + strCar + "/t###########");
					                
					                // 해당 차량의 누적 주차시간(분) 계산
					                Arrays.stream(carArr2D)
	                                        .filter(y->y[1].equals(strCar))
	                                        .map(y->{
	                                            // 내역, 시각(시:분) Map에 입력
	                                            timeMap.put(y[2], y[0]);

	                                            // 내역이 OUT일 때 주차 시간 계산
	                                            if("OUT".equals(y[2])) {
	                                                try {
	                                                    Date dtIn = format.parse(timeMap.get("IN"));
	                                                    Date dtOut = format.parse(timeMap.get("OUT"));
	                                                    // 분 차이
	                                                    long diffMin = (dtOut.getTime() - dtIn.getTime()) / 60000;
	                                                    // 누적 주차시간(분) 갱신
	                                                    long lngAccTm = Long.valueOf(timeMap.getOrDefault("ACC_TIME", "0")) + Math.abs(diffMin);
	                                                    // Map에 누적주차시간 갱신
	                                                    timeMap.put("ACC_TIME", String.valueOf(lngAccTm));
	                                                } catch (ParseException e) {
	                                                    e.printStackTrace();
	                                                }
	                                            }
	                                            return timeMap.getOrDefault("ACC_TIME", "0");
	                                        }).toArray(String[]::new);
					                System.out.println("########### timeMap  \t" + timeMap);
					                
					                // 추가시간
					                long addTm = 0;
					                // OUT이 IN보다 작거나 없는 경우 IN ~ 23:59 까지 시간 추가
					                try {
					                    Date dtIn = format.parse(timeMap.get("IN"));
					                    Date dtOut = format.parse(timeMap.getOrDefault("OUT", "00:00"));
					
					                    // 입차시각이 출차시각보다 크거나 같을 경우 즉, 출차하지 않은 경우 23:59 까지 시간(분)을 추가 계산한다
					                    if(dtIn.getTime() >= dtOut.getTime()) {
					                        // 분 차이
					                        addTm = (format.parse("23:59").getTime() - dtIn.getTime()) / 60000; // 1초 : 1000
					                    }
					                } catch (ParseException e) {
					                    e.printStackTrace();
					                }
					                System.out.println("########### addTm \t" + addTm);
					                
					                return Long.valueOf(timeMap.getOrDefault("ACC_TIME", "0")) + Math.abs(addTm);
					
					            }).mapToLong(Long::valueOf).toArray();
		
		// 차량별 청구 주차요금 int배열 설정
		answer = Arrays.stream(carTmArr)
		    .map(x->{
		        // 기본시간(분) 이하 일경우 기본요금만 받는다
		        if(x<=fees[0]) {
		            return fees[1];
		        } else {
		            // 주차요금 =  기본요금 + 올림[(누적주차시간-기본시간)/단위시간] * 단위요금
		            return fees[1] + (int) Math.ceil(Float.valueOf(x - fees[0]) / fees[2]) * fees[3];
		        }
		    }).mapToInt(x->Long.valueOf(x).intValue()).toArray();
		
		System.out.println("########### answer \t" + Arrays.toString(answer));
		
		return answer;
    }
    
}
