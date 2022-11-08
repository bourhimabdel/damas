package com.damasingo.SendNotificationPack;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseIdService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s)
    {
        super.onNewToken(s);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseMessaging.getInstance().getToken().toString();

        if(firebaseUser!=null){
            updateToken(refreshToken);
        }
    }
    private void updateToken(String refreshToken){
        Token token1= new Token(refreshToken);
        FirebaseUser f=FirebaseAuth.getInstance().getCurrentUser();
        assert f != null;
        FirebaseDatabase.getInstance().getReference("Tokens").child(f.getUid()).setValue(token1);
    }
}
