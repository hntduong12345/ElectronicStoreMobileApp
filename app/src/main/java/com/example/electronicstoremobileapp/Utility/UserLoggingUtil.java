package com.example.electronicstoremobileapp.Utility;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.auth0.android.jwt.JWT;

import kotlin.Triple;

public class UserLoggingUtil {
    public static Triple<String, String, String> GetUserInfo(Context context){
        String id = Preference.Get(context, "Authentication", "UserId");
        String email = Preference.Get(context, "Authentication", "UserEmail");
        String role = Preference.Get(context, "Authentication", "UserRole");
        return new Triple<>(id,email,role);
    }
    public static String LogIn(Context context, String token){
        JWT jwt = JwtUtil.getJWT(token);
        String id = jwt.getClaim("nameid").asString();
        String email = jwt.getClaim("email").asString();
        String role = jwt.getClaim("Role").asString();
        Log.d("id", id);
        Log.d("email", email);
        Log.d("role", role);
        Preference.Add(context, "Authentication","UserId", id);
        Preference.Add(context, "Authentication","UserEmail", email);
        Preference.Add(context, "Authentication","UserRole", role);
        return role;
    }
    public static void LogOut(Context context){
        //Preference.AddPreference(this,"Authentication","Token",token);
        Preference.Remove(context, "Authentication","UserId");
        Preference.Remove(context, "Authentication","UserEmail");
        Preference.Remove(context, "Authentication","UserRole");
    }
}
