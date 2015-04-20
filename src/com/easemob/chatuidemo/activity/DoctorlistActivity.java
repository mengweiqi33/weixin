package com.easemob.chatuidemo.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.chat.EMContactManager;
import com.easemob.chatuidemo.Constant;
import com.easemob.chatuidemo.DemoApplication;
import com.easemob.chatuidemo.R;
import com.easemob.chatuidemo.adapter.ContactAdapter;
import com.easemob.chatuidemo.domain.User;
import com.easemob.exceptions.EaseMobException;

/**
 * 心理医生列表页面
 * 
 */
public class DoctorlistActivity extends Activity {
    private ListView listView;
    private ContactAdapter adapter;
    private Activity activity;
    private ArrayList<User> contactList;
    private List<String> blackList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_list);
        activity = this;
        TextView titleTextView = (TextView) findViewById(R.id.name);
        titleTextView.setText("推荐医生列表");
        listView = (ListView) findViewById(R.id.list);
        //黑名单列表
        blackList = EMContactManager.getInstance().getBlackListUsernames();


        contactList = new ArrayList<User>();
        // 获取设置contactlist
        getContactList();

        adapter = new ContactAdapter(this, R.layout.row_contact, contactList,
                null);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                String username = adapter.getItem(position).getUsername();
                if (Constant.NEW_FRIENDS_USERNAME.equals(username)) {
                    // 进入申请与通知页面
                    User user = DemoApplication.getInstance().getContactList()
                            .get(Constant.NEW_FRIENDS_USERNAME);
                    user.setUnreadMsgCount(0);
                    startActivity(new Intent(activity,
                            NewFriendsMsgActivity.class));
                } else if (Constant.GROUP_USERNAME.equals(username)) {
                    // 进入群聊列表页面
                    startActivity(new Intent(activity, GroupsActivity.class));
                } else {
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
                    startActivity(new Intent(activity, ChatActivity.class)
                            .putExtra("userId", adapter.getItem(position)
                                    .getUsername()));
                }
            }
        });

        // 注册上下文菜单
        registerForContextMenu(listView);

    }

    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
        contactList.clear();
        // 获取本地好友列表
        Map<String, User> users = DemoApplication.getInstance()
                .getContactList();
        Iterator<Entry<String, User>> iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, User> entry = iterator.next();
            if (!entry.getKey().equals(Constant.NEW_FRIENDS_USERNAME)
                    && !entry.getKey().equals(Constant.GROUP_USERNAME)&& !blackList.contains(entry.getKey()))
                contactList.add(entry.getValue());
        }
        // 排序
        Collections.sort(contactList, new Comparator<User>() {

            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getUsername().compareTo(rhs.getUsername());
            }
        });

//        // 加入"申请与通知"和"群聊"
//        contactList.add(0, users.get(Constant.GROUP_USERNAME));
//        // 把"申请与通知"添加到首位
//        contactList.add(0, users.get(Constant.NEW_FRIENDS_USERNAME));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.remove_from_blacklist, menu);
    }

    /**
     * 返回
     * 
     * @param view
     */
    public void back(View view) {
        finish();
    }
}
