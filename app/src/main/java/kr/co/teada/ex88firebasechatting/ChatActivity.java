package kr.co.teada.ex88firebasechatting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    ListView listView;
    ChatAdapter chatAdapter;

    EditText etMsg;

    ArrayList<MessageItem> messageItems=new ArrayList<>();

    DatabaseReference chatRef; //전역으로 뺐다~~


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //제목줄에 내 닉네임이 표시되도록
        getSupportActionBar().setTitle(G.nickName);

        listView=findViewById(R.id.listview);
        chatAdapter=new ChatAdapter(messageItems, getLayoutInflater());
        listView.setAdapter(chatAdapter); // 리스트뷰에 챗 아답터 붙였어

        etMsg=findViewById(R.id.et);

        //'chat'노드의 참조객체 얻어오기
        chatRef=FirebaseDatabase.getInstance().getReference("chat");

        //'chat'노드에 저장되어 있는 데이터들을 읽어오기 ****************************************************중요 *******
        //chatRef 에 데이터가 변경되는 것을 듣는 리스너 추가
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //첫 번째 파라미터 : 새로 추가된 데이터 노드의 정보를 가진 dataSnapshot 객체

                MessageItem messageItem=dataSnapshot.getValue(MessageItem.class); //객체 통으로

                //리스트뷰가 보여주는 대량의 데이터인 ArrayList 에 추가
                messageItems.add(messageItem);

                //리스트뷰화면 갱신
                chatAdapter.notifyDataSetChanged();
                //리스트뷰의 커서를 가장 마지막 위치로..
                listView.setSelection(messageItems.size()-1); //5개 있으면 마지막 방 번호는 4니까 (배열개념)
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }//end of onCreate

    public void clickSend(View view) {

        String nickName=G.nickName;
        String message=etMsg.getText().toString();
        String profileUrl=G.profileUrl;

        Calendar calendar=Calendar.getInstance();
        String time=calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);


        //메세지를 firebase DB 에 객체 통째로 저장 //////////////////////////////////////****************중요*********
        MessageItem messageItem=new MessageItem(nickName,message,time,profileUrl);

        //'chat'노드에 객체 통쨰로 값 추가(push): 안그러면 계속 갱신되니까
        chatRef.push().setValue(messageItem);

        etMsg.setText("");

        //소프트 키보드 안 보이도록
        InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


    }
}//end of ChatActivity
