package com.example.cuvc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MemberListAdapter extends ArrayAdapter<Member> {
    private Context context;
    private List<Member> memberList;

    public MemberListAdapter(Context context, List<Member> memberList) {
        super(context, 0, memberList);
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.member_list_item, parent, false);
        }

        Member currentMember = memberList.get(position);
        TextView nameTextView = listItem.findViewById(R.id.member_name);
        TextView idTextView = listItem.findViewById(R.id.member_id);
        TextView adminTitleTextView = listItem.findViewById(R.id.admin_title); // Assuming you have a TextView for admin title in your member_list_item layout

        nameTextView.setText(currentMember.getName());
        idTextView.setText(currentMember.getId());

        // Check if the member is an admin and display the admin title if true
        if (currentMember.isAdmin()) {
            adminTitleTextView.setVisibility(View.VISIBLE);
        } else {
            adminTitleTextView.setVisibility(View.GONE);
        }

        return listItem;
    }

}
