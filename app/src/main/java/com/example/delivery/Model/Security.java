package com.example.delivery.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Security {
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        return kp;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getPublicKey(KeyPair kp, String email) {
        Base64.Encoder encoder = Base64.getEncoder();
        Key pub = kp.getPublic();
        String pubString;
        pubString = encoder.encodeToString(pub.getEncoded());

        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference patientRef = db.collection("patients").document(email);
        patientRef.update("publicKey", pubString);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getPrivateKey(KeyPair kp) {
        Base64.Encoder encoder = Base64.getEncoder();
        Key pvt = kp.getPrivate();
        String pvtString;
        pvtString = encoder.encodeToString(pvt.getEncoded());

        return pvtString;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decryptCode(String pvk, String code) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        //Getting private key from string
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(pvk.getBytes()));
        KeyFactory keyFactory = null;
        try{
            keyFactory = KeyFactory.getInstance("RSA");
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try{
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch(InvalidKeySpecException e){
            e.printStackTrace();
        }

        //Decrypting code
        byte[] codeBytes = Base64.getDecoder().decode(code.getBytes());
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(codeBytes));
    }
}
