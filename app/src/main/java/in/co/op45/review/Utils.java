package in.co.op45.review;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sam10795 on 22/2/18.
 */

public class Utils {

    static String filename = "Reviews";

    public static void writeToFile(Context context, String session, String paper, String score)
    {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(filename,MODE_PRIVATE);
            fos.write(encode_data(session,paper,score).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String encode_data(String session, String paper, String score)
    {
        return session+":"+paper+":"+"~<"+score+">~\n";
    }

    public static boolean exists(Context context, String session, String paper)
    {
        FileInputStream fis;
        String enc;
        try {
            fis = context.openFileInput(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                enc = encode_data(session,paper,"0");
                if(line.substring(0,line.lastIndexOf("~<")).
                        equalsIgnoreCase(enc.substring(0,enc.lastIndexOf("~<"))))
                {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
