package com.example.electronicstoremobileapp.Utility;

import android.util.Base64;
import android.util.Log;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    public static JWT getJWT(String token){
        JWT jwt = new JWT(token);
        return jwt;
    }
}
