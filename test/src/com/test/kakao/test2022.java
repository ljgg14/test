package com.test.kakao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
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

					                System.out.println("########### strCar \t" + strCar + "\t###########");
					                
					                //== 해당 차량의 누적 주차시간(분) 계산
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
					                
					                //== 추가시간
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

                                    //== 누적주차시간 + 추가주차시간
					                long lngAccTm = Long.valueOf(timeMap.getOrDefault("ACC_TIME", "0")) + Math.abs(addTm);
					                System.out.println("########### lngAccTm \t" + lngAccTm);

					                return lngAccTm;
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
    
    
    /**
	 * 주차 요금 계산
	 * @Description : 주차 요금 계산 다른사람풀이
	 */
    public static int[] solution3_1() {
    	int[] answer = {};
    	
    	int[] fees = {180, 5000, 10, 600};
    	String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
//    	int[] fees = {1, 461, 1, 10};
//    	String[] records = {"00:00 1234 IN"};
    	

        // 자동차별 누적주차시간(분) Map (TreeMap은 Key값 기준 자동을 오름차순 정렬됨)
        TreeMap<String, Integer> carTimeMap = new TreeMap<String, Integer>();
        
        // 자동차별 누적주차시간(분) Map 설정
        for(String strHis : records) {
            // 공백 기준으로 분리
            String[] hisDtlArr = strHis.split(" ");
            // 입차일 경우 시간(분)을 마이너스 처리
            int intTime = "IN".equals(hisDtlArr[2]) ? -1 : 1;
            intTime *= timeToInt(hisDtlArr[0]);
            // Map에 누적주차시간(분) 갱신
            carTimeMap.put(hisDtlArr[1], carTimeMap.getOrDefault(hisDtlArr[1], 0) + intTime );
        }
		System.out.println("########### carTimeMap \t" + carTimeMap);
        
        // 리턴 배열 초기화
        int idx = 0;
        answer = new int[carTimeMap.size()];
        // 자동차별 주차요금 계산
        for(int x : carTimeMap.values()) {
            // 누적시간이 0 또는 음수일 경우 출차하지 않은 경우이므로 1,439분을 더한다
            int intAccTime = x <= 0 ? x + 1439 : x; // 1,439분 (00:00~23:59)
            // 기본시간 차감
            intAccTime -= fees[0];

            // 주차 요금 계산
            int intCost = fees[1]; // 기본요금으로 초기화
            // 기본시간을 초과한 경우
            if(intAccTime > 0) {
                // 기본요금 + [(누적시간-기본시간)/단위시간] * 단위요금
                if(intAccTime%fees[2] == 0) {
                    intCost += (intAccTime/fees[2]) * fees[3];
                } else { // 단위시간으로 나누어 떨어지지 않으면 올림한다
                    intCost += (intAccTime/fees[2] + 1) * fees[3];
                }
            }
            answer[idx] = intCost;
            idx++;
        }
		System.out.println("########### answer \t" + Arrays.toString(answer));
		
		return answer;
    }

    // 문자열 시간을 int 분으로 변환
    public static int timeToInt(String strTime) {
        String[] timeArr = strTime.split(":");
        return (Integer.parseInt(timeArr[0]) * 60) + Integer.parseInt(timeArr[1]);
    }

    
    /**
	 * 양궁대회
	 * @Description : 양궁대회
	 */
    public static int[] solution4() {
    	int[] answer = {};
    	
//    	int n = 5;
//    	int[] info = {2,1,1,1,0,0,0,0,0,0,0}; // {0,2,2,0,1,0,0,0,0,0,0}
//    	int n = 9;
//    	int[] info = {0,0,1,2,0,1,1,1,1,1,1}; //  {1,1,2,0,1,2,2,0,0,0,0}
//    	int n = 1;
//    	int[] info = {1,0,0,0,0,0,0,0,0,0,0}; // {-1}
    	int n = 10;
    	int[] info = {0,0,0,0,0,0,0,0,3,4,3}; // {1,1,1,1,1,1,1,1,0,0,2}

		System.out.println("\t########### info \t" + Arrays.toString(info));

		
    	int[] liTmpArr = new int[info.length]; // 임시 케이스 배열
    	int r = n; // 할당치 n에서 남은 갯수(초기값은 n)
    	HashMap<String, Object> answerMap = new HashMap<String, Object>();
    	
		// 조합 구하기 (재귀를 이용해 구현)
		combinationViaRecursion(info, liTmpArr, 0, r, n, answerMap);

		// 결과 int배열 설정
		if(answerMap.get("ANSWER") == null) {
			answer = new int[]{-1};
		} else {
			answer = Arrays.stream(String.valueOf(answerMap.get("ANSWER")).split(",")).mapToInt(Integer::valueOf).toArray();
		}
		
    	System.out.println("\t########### answer \t" + Arrays.toString(answer));
		
    	return answer;
    }


    /**
	 * 재귀식을 사용한 조합 함수
	 * @Description : 재귀식을 사용한 조합 함수
	 * @param - int[] info: 어피치가 맞춘 내역
	 *        - int[] liTmpArr : 케이스 배열 
	 *        - int depth : 탐색 깊이 인덱스
	 *        - int r : 남은 화살 갯수
	 *        - int n : 총 화살 갯수
	 */
	static void combinationViaRecursion(int[] info, int[] liTmpArr, int depth, int r, int n, HashMap<String, Object> map) {
		
		// 화살을 다 쏘거나 depth가 끝까지 가면 해당 케이스를 검증한다
		if(r == 0 || depth == info.length) {
            int intApScore = 0; // 어피치 점수
            int intLiScore = 0; // 라이언 점수
            int intScoreGap = (int) map.getOrDefault("SCORE_GAP", 0);
            int[] rtnArr = Arrays.copyOf(liTmpArr, liTmpArr.length);
            
            // 화살이 남았다면 0점에 남은 화살을 모두 쏜다
            if(r > 0) rtnArr[10] = rtnArr[10] + r;
            
            // 점수 계산
            for(int k=0; k<info.length; k++) {
                // 라이언 화살 갯수가 클 경우만 라이언에게 점수 배정
                if(rtnArr[k] > info[k]) {
                    if(rtnArr[k] > 0) intLiScore += 10 - k;
                } else {
                	if(info[k] > 0) intApScore += 10 - k;
                }
            }
            
            //== 점수차 계산 및 결과 갱신
            int tmpGap = intLiScore - intApScore;
            // 라이언이 이전 점수차 이상으로 우승할 경우 answer 갱신
            if(tmpGap > 0 && tmpGap >= intScoreGap) {
            	
            	// 결과 내역을 물자열 모양으로 변환
            	String strAnswer = String.join(",", Arrays.stream(rtnArr).mapToObj(String::valueOf).toArray(String[]::new)); 
            	//0,0,0,0,0,0,0,0,0,0,0
            	String preRev = new StringBuilder(String.valueOf(map.getOrDefault("ANSWER", ""))).reverse().toString();
            	String tmpRev = new StringBuilder(strAnswer).reverse().toString();
            	
            	if(tmpRev.compareTo(preRev) > 0) {
                    // 결과 갱신
                    map.put("SCORE_GAP", tmpGap); // 라이언 우승 점수차
                    map.put("ANSWER", strAnswer); // 라이언 우승 케이스 배열(문자열 포맷)
            	}
            	
                System.out.println("\t########### rtnArr \t" + Arrays.toString(rtnArr) + " - WIN!! depth:[" + (depth-1) + "], r:[" + r + "] \t" + intApScore + ", " + intLiScore + "(" + tmpGap + ")");
            }
            
			return;
		}
		

		/** Case[TRUE] - 화살 발사 (어피치보다 많이 쏠 수 있을 경우) **/ 
        int intShtCnt = 0;
        int intApCnt = info[depth]; // 어피치가 쏜 화살갯수
        // 어피치가 맞춘 화살갯수 +1 이 라이언의 남은 화살갯수 이하일 때 발사!
        if((intApCnt + 1) <= r) {
        	intShtCnt = intApCnt + 1; // 발사 갯수 설정
        	liTmpArr[depth] = intShtCnt; // 배열에 발사 갯수 갱신
        }
		//= 재귀 호출 (발사한 만큼 화살갯수 차감)
		combinationViaRecursion(info, liTmpArr, depth + 1, r - intShtCnt, n, map);

		
		/** Case[FALSE] - 화살 무조건 안쏠 경우 **/
		liTmpArr[depth] = 0; // 배열에 발사 갯수 갱신
		//= 재귀 호출 (차감 안함)
		combinationViaRecursion(info, liTmpArr, depth + 1, r, n, map);
	}
    
}



