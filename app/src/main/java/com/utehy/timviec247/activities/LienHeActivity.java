package com.utehy.timviec247.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utehy.timviec247.R;
import com.utehy.timviec247.activities.business.ChatActivity;
import com.utehy.timviec247.adapters.ChatAdapter;
import com.utehy.timviec247.models.ChatContent;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class LienHeActivity extends AppCompatActivity {
    List<ChatContent> chatList;
    DatabaseReference reference;
    ChatAdapter chatAdapter;
    RecyclerView recyclerView;

    Button btn_send, btnVideo;
    EditText edt_content;

    //Tem
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);
        initView();
        readMessage(Common.account.getId(), Common.ungTuyen.getIdCongTy());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edt_content.getText().toString();
                if (!message.equals("")) {
                    Log.e("ID Cong ty", "onClick: "+Common.ungTuyen.getIdCongTy() );
                    sendMessage(Common.account.getId(), Common.ungTuyen.getIdCongTy(), message);
                } else {
                    Toast.makeText(getApplicationContext(), "The feild is not empty", Toast.LENGTH_LONG).show();
                }
                edt_content.setText("");
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ID Cong ty", "onClick: "+Common.ungTuyen.getIdCongTy() );
                Random random = new Random();
                String room = "//meeting#" + random.nextInt(1000);
                sendMessage(Common.account.getId(), Common.ungTuyen.getIdCongTy(), room);
            }
        });
    }

    private void initView() {
        btn_send = findViewById(R.id.btn_send);
        edt_content = findViewById(R.id.edt_content);
        recyclerView = findViewById(R.id.lv_chat);
        btnVideo = findViewById(R.id.btnVideo);

    }

    private void sendMessage(String sender, String reciever, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("senderUser", sender);
        hashMap.put("receiveUser", reciever);
        hashMap.put("message", message);
        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(String myID, String userID) {
        chatList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatContent chat = dataSnapshot.getValue(ChatContent.class);
                    assert chat != null;
                    if (chat.getReceiveUser().equals(myID) && chat.getSenderUser().equals(userID) || chat.getReceiveUser().equals(userID) && chat.getSenderUser().equals(myID)) {
                        chatList.add(chat);
                        chatAdapter = new ChatAdapter(LienHeActivity.this, chatList);
                        recyclerView.setAdapter(chatAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}