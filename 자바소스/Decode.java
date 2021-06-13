import java.io.*;

public class Decode {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("test.txt"));
        int count = 0;
        String result = "";

        while(true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) break;
            count++;

            String[] strArr = line.split(":");
            String name = strArr[0]; // 결함 이름
            String n = strArr[1]; // 결함 개수
            result += name + ": " + n + "개 ";
        }
        if (count == 0) {
            result = "결함 없음";
        }
        System.out.println(result);
        br.close();
    }
}
