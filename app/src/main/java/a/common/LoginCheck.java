package a.common;

import android.content.Context;
import android.content.SharedPreferences;

import a.getter_setter.*;

/**
 * Created by TUSHAR on 10-03-18.
 */

public class LoginCheck
{
    private Context context;
    private UserCredentials credentials=null;
    private String UserName,Password;
    private static LoginCheck loginCheck;
    private LoginCheck(Context context)
    {
        this.context=context;
    }
    public UserCredentials CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        UserName = sharedPreferences.getString("UserName",null);
        Password = sharedPreferences.getString("Password",null);
        if(UserName != null && Password != null &&
                ValidateUserCredentials.getInstance(context).validateCredentials(UserName,Password))
            credentials = new UserCredentials(UserName,Password);
        return credentials;
    }
   public static LoginCheck getInstance(Context context)
   {
       if(loginCheck == null)
           loginCheck = new LoginCheck(context);
       return loginCheck;
   }
}