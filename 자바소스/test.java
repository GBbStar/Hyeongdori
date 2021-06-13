import java.io.IOException;

class Test {

    public static void main(String[] args) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec("python3 detect.py --source " + args[0]);
        p.waitFor();
        p.destroy();
	System.out.println("End!");
    }
}

