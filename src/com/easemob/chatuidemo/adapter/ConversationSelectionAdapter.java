package com.easemob.chatuidemo.adapter;

import java.util.ArrayList;
import java.util.List;

import com.easemob.chatuidemo.R;

import android.R.integer;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ConversationSelectionAdapter extends BaseExpandableListAdapter {
    // 定义两个List用来控制Group和Child中的String;

    private List<String> groupArray;// 组列表
    private List<List<String>> childArray;// 子列表
    private ExpandableListView expandableListView_one;
    private LayoutInflater layoutInflater;

    Activity activity;

    public ConversationSelectionAdapter(Activity a) {
        activity = a;
        layoutInflater = LayoutInflater.from(activity);
    }

    public void addInfo(List<String> gArray, List<List<String>> cArray) {

        groupArray = gArray;
        childArray = cArray;
    }

    /*-----------------Child */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childArray.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {

        String string = childArray.get(groupPosition).get(childPosition);

        return getChildView(groupPosition,childPosition,convertView);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        return childArray.get(groupPosition).size();
    }

    /* ----------------------------Group */
    @Override
    public Object getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return getGroup(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return groupArray.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {

        String string = groupArray.get(groupPosition);
        return getGroupView(groupPosition, convertView);
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return true;
    }

    private View getGroupView(int groupPos, View convertView) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.conversation_selection, null);
        }
        
        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView unreadMsgView = (TextView) convertView.findViewById(R.id.unread_msg_number);
        TextView nameTextview = (TextView) convertView.findViewById(R.id.name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        nameTextview.setText(groupArray.get(groupPos));
        return convertView;
    }
    
    private View getChildView(int groupPos, int childPos, View convertView) {
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.conversation_selection_child, null);
        }
        
        ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
        TextView unreadMsgView = (TextView) convertView.findViewById(R.id.unread_msg_number);
        TextView nameTextview = (TextView) convertView.findViewById(R.id.name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        List<String> childList = childArray.get(groupPos);
        nameTextview.setText(childList.get(childPos));
        return convertView;
    }

}
