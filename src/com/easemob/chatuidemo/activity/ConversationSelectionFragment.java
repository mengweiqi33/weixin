package com.easemob.chatuidemo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.adapter.ConversationSelectionAdapter;


public class ConversationSelectionFragment extends Fragment{
    private InputMethodManager inputMethodManager;
    private ExpandableListView listView;
    private ConversationSelectionAdapter adapter;
    private List<String> groupArray;// 组列表
    private List<List<String>> childArray;// 子列表
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation_selection, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        activity = ConversationSelectionFragment.this.getActivity();
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        
        groupArray =new ArrayList<String>();
        childArray = new ArrayList<List<String>>();
        
        /*-第一季-*/
        initdate();
        listView = (ExpandableListView) getView().findViewById(R.id.conversation_selection_list);
        adapter = new ConversationSelectionAdapter(getActivity());
        adapter.addInfo(groupArray, childArray);
        // 设置adapter
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new OnChildClickListener() {
            
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(activity, DoctorlistActivity.class);
                activity.startActivity(intent);
                return false;
            }
        });

        
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                EMConversation conversation = adapter.getItem(position);
//                String username = conversation.getUserName();
//                if (username.equals(DemoApplication.getInstance().getUserName()))
//                    Toast.makeText(getActivity(), "不能和自己聊天", 0).show();
//                else {
//                    // 进入聊天页面
//                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    EMContact emContact = null;
//                    groups = EMGroupManager.getInstance().getAllGroups();
//                    for (EMGroup group : groups) {
//                        if (group.getGroupId().equals(username)) {
//                            emContact = group;
//                            break;
//                        }
//                    }
//                    if (emContact != null && emContact instanceof EMGroup) {
//                        // it is group chat
//                        intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
//                        intent.putExtra("groupId", ((EMGroup) emContact).getGroupId());
//                    } else {
//                        // it is single chat
//                        intent.putExtra("userId", username);
//                    }
//                    startActivity(intent);
//                }
            }
        });
        // 注册上下文菜单
        registerForContextMenu(listView);

        listView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 隐藏软键盘
                hideSoftKeyboard();
                return false;
            }

        });


    }
    
    void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void initdate() {
        addInfo("情绪", new String[] { "焦躁", "孤独", "深沉", "疲惫" });
        addInfo("家庭",
                new String[] { "夫妻相处", "婆媳关系", "产后抑郁", "家庭暴力", "其他" });
        addInfo("孩子",
                new String[] { "注意力不集中", "沉迷网络", "多动" });
        addInfo("职场",
                new String[] { "职业发展", "人际关系", "职场发泄" });
        addInfo("自我",
                new String[] { "人生规划", "人际关系", "其他" });
        addInfo("情感",
                new String[] { "失恋", "情感困惑", "其他" });
        addInfo("其他",
                new String[] { "随意聊聊" });
    }

    private void addInfo(String group, String[] child) {

        groupArray.add(group);

        List<String> childItem = new ArrayList<String>();

        for (int index = 0; index < child.length; index++) {
            childItem.add(child[index]);
        }
        childArray.add(childItem);
    }
}
