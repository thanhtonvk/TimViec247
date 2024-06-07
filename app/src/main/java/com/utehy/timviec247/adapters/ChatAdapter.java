package com.utehy.timviec247.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utehy.timviec247.R;
import com.utehy.timviec247.models.ChatContent;
import com.utehy.timviec247.utils.Common;

import java.util.ArrayList;
import java.util.List;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private static final int MSG_LEFT = 319;
    private static final int MSG_RIGHT = 276;
    private Context context;
    private List<ChatContent> list;

    public ChatAdapter(Context context, List<ChatContent> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case MSG_LEFT:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_left, parent, false);
                break;
            case MSG_RIGHT:
                view = LayoutInflater.from(context).inflate(R.layout.item_chat_right, parent, false);
                break;
        }
        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatContent content = list.get(position);
        holder.tv_message.setText(content.getMessage());
        if(content.getMessage().contains("//meeting")){
            holder.btnThamGia.setVisibility(View.VISIBLE);
            holder.tv_message.setText("Tham gia video call ngay");
            holder.btnThamGia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String room = content.getMessage().split("#")[1];
                    JitsiMeetConferenceOptions options
                            = new JitsiMeetConferenceOptions.Builder()
                            .setRoom(room)
                            .build();
                    JitsiMeetActivity.launch(context, options);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getSenderUser().equals(Common.account.getId())) {
            return MSG_RIGHT;
        } else {
            return MSG_LEFT;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_message;
        Button btnThamGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_message = itemView.findViewById(R.id.show_message);
            btnThamGia = itemView.findViewById(R.id.btnThamGia);
        }
    }
}
