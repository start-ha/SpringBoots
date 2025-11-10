package Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Horse extends Thread {
    private static final int FINISH_LINE = 50; // 결승선 위치
    private static int rankCounter = 1; // 순위 계산
    private static final List<String> ranking = new ArrayList<>();
    
    private final String name;
    private int position = 0;
    private final Random random = new Random();

    public Horse(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (position < FINISH_LINE) {
            position += random.nextInt(3) + 1; // 1~3칸 랜덤 이동
            printTrack();
            try {
                Thread.sleep(200); // 0.2초마다 이동 (속도 조절)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        // 결승선 도착 시 순위 기록 (동기화)
        synchronized (ranking) {
            ranking.add(name + " - " + rankCounter + "위");
            rankCounter++;
        }
    }

    // 트랙을 출력하는 메서드
    private void printTrack() {
        StringBuilder track = new StringBuilder();
        for (int i = 0; i < FINISH_LINE; i++) {
            if (i == position) {
                //track.append(name.charAt(0)); // 말의 첫 글자로 표시
            	track.append(name);
            } else {
                track.append("*");
            }
        }
        System.out.println(track.toString());
    }

    // 전체 순위 출력
    public static void printFinalRanking() {
        System.out.println("\n🏁 최종 순위 🏁");
        ranking.forEach(System.out::println);
    }
}

public class HorseRacingGame {
    public static void main(String[] args) {
        int horseCount = 5; // 경주 말 수
        List<Horse> horses = new ArrayList<>();

        System.out.println("🏇 승마 경주 시작! 🏇");

        // 말 생성 및 시작
        for (int i = 1; i <= horseCount; i++) {
            Horse horse = new Horse("Horse" + i +"번말");
            horses.add(horse);
            horse.start();
        }

        // 모든 말이 도착할 때까지 대기
        for (Horse horse : horses) {
            try {
                horse.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 최종 순위 출력
        Horse.printFinalRanking();
    }
}
