package a.common;

import android.content.Context;
import android.content.SharedPreferences;

import a.getter_setter.LoginData;

/**
 * Created by TUSHAR on 10-03-18.
 */

public class LoginCheck
{
    private Context context;
    private LoginData credentials=null;
    private String UserName,Password;
    private static LoginCheck loginCheck;
    private LoginCheck(Context context)
    {
        this.context=context;
    }
    public LoginData CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName",null);
        Password = sharedPreferences.getString("Password",null);
        if(UserName != null && Password != null &&
                Services.getInstance(context).Validate(UserName,Password))
            credentials = new LoginData(UserName,Password);
        return credentials;
    }
   public static LoginCheck getInstance(Context context)
   {
       if(loginCheck == null)
           loginCheck = new LoginCheck(context);
       return loginCheck;
   }
}