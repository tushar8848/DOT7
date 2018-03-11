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
    private String userName,password;
    private static LoginCheck loginCheck;
    private LoginCheck(Context context)
    {
        this.context=context;
    }
    public UserCredentials CheckUserCredentials()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("logDetails",
                Context.MODE_PRIVATE);
        userName = sharedPreferences.getString("UserName",null);
        password = sharedPreferences.getString("Password",null);
        if(userName!=null && password!=null &&
                ValidateUserCredentials.getInstance(context).validateCredentials(userName,password))
            credentials = new UserCredentials(userName,password);
        return credentials;
    }
   public static LoginCheck getInstance(Context context)
   {
       if(loginCheck==null)
           loginCheck = new LoginCheck(context);
       return loginCheck;
   }
}