package a.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.Random;

/**
 * Created by TUSHAR on 28-03-18.
 */

public class OTP_Generator implements VerificationListener {
    static OTP_Generator myGenerator;
    Context context;
    boolean flag = false;

    OTP_Generator(Context context)
    {
        this.context = context;
    }

    public String Generate() {
        Random generate = new Random();
        char[] otp = new char[4];
        for (int i = 0; i < 4; i++) {
            int x = generate.nextInt(10);
            otp[i] = (char) (x + '0');
        }
        String Message = String.valueOf(otp);
        return Message;
    }

    public boolean sendMessage(String OTP,String Contact)
    {
        Verification Verify = SendOtpVerification.createSmsVerification(
                SendOtpVerification
                        .config("91"+Contact)  //specify the mobile number ID here
                        .context(context)
                        .autoVerification(true)
                        .expiry("2")
                        .senderId("DOTSMS")
                        .otp(OTP)
                        .otplength("4")
                        .build(),this);

        Verify.initiate();
        return flag;

    }
    @Override
    public void onInitiated(String response) {
        Log.d("HAR", "Response Success with mesage " + response);
        flag = true;
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.d("HAR","Response not Success with mesage "+paramException.toString());
        //Nitish and pooja show required error message if message is not sent
    }

    @Override
    public void onVerified(String response) {
        Log.d("HAR", "Verify Success with mesage " + response);
        //OTP verified Success
        //Redirect to Restaurant View
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.d("HAR", "Error In verification " + paramException.toString());
        //Nitish and pooja show suitable alert box if otp not verified or otp is expired i.e 2 mins
    }





    public static OTP_Generator getInstance(Context context)
    {
        if(myGenerator == null)
        {
            myGenerator = new OTP_Generator(context);
        }
        return myGenerator;
    }
}
