package Boot;
import java.io.*;
import java.util.Observable;

import static java.lang.Thread.sleep;

public class ClientHandler extends Observable implements Runnable{

    private Client c;
    private boolean status=true;




    public ClientHandler(Client cli) {
        this.c = cli;
        String s;
        if(c.getuSocket().isBound())
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getuSocket().getInputStream()));
                s=br.readLine();
                while(s.isEmpty()||s.equals(""))s=br.readLine();
                c.setName(s);

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    private int solveExercise(MathExercise ex) {
        switch (ex.getOp()) {
            case "+":
                return ex.getNum1() + ex.getNum2();
            case "-":
                return ex.getNum1() - ex.getNum2();
            case "*":
                return ex.getNum1() * ex.getNum2();
            case "/":
                return ex.getNum1() / ex.getNum2();
            default:
                return Integer.MAX_VALUE;
        }
    }
    @Override
    public void run() {

        while (c.getuSocket().isBound())
        {
            PrintWriter pw = null;
            String str;
            try {
                pw = new PrintWriter(c.getuSocket().getOutputStream(), true);
                if (pw==null){sleep(1000);continue;}
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getuSocket().getInputStream()));
                if (c.getuSocket().isBound())
                    for (String line = br.readLine(); line != null; line = br.readLine()) {
                    }
                else break;
                sleep(500);

            } catch (IOException e) {
                status=false;
                return;

            } catch (InterruptedException e) {
                status=false;
                return;
            }
        }

        c.getuRequest().setExit(true);
        status=false;
        return;


    }

    public boolean isStatus() {
        return status;
    }
    public Client getC() {
        return c;
    }
}

