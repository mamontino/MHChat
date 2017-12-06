//package com.medhelp2.mhchat.ui.contacts.contacts_list;
//
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.medhelp2.mhchat.R;
//import com.medhelp2.mhchat.data.model.RoomResponse;
//import com.medhelp2.mhchat.di.component.ActivityComponent;
//import com.medhelp2.mhchat.ui.base.BaseFragment;
//import com.medhelp2.mhchat.ui.chat.ChatActivity;
//import com.medhelp2.mhchat.ui.contacts.ContactsAdapter;
//import com.medhelp2.mhchat.utils.main.AppConstants;
//import com.medhelp2.mhchat.utils.main.NotificationUtils;
//import com.medhelp2.mhchat.utils.view.ContactsDecorator;
//import com.medhelp2.mhchat.utils.view.RecyclerViewClickListener;
//import com.medhelp2.mhchat.utils.view.RecyclerViewTouchListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Inject;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import timber.log.Timber;
//
//import static com.medhelp2.mhchat.ui.chat.chat_list.ChatListFragment.BROADCAST_INCOMING_MESSAGE;
//
//public class ContactsListFragment extends BaseFragment implements ContactsListViewHelper
//{
//    public static final String TAG = "ChatListFragment";
//    public static final String PARAM_STATUS = "status";
//    public static final int STATUS_START = 150;
//    public static final int STATUS_FINISH = 250;
//
//    private BroadcastReceiver incomingMessageReceiver;
//
//    @Inject
//    ContactsListPresenterHelper<ContactsListViewHelper> presenter;
//
//    @Inject
//    ContactsAdapter adapter;
//
//    @Inject
//    LinearLayoutManager layoutManager;
//
//    @BindView(R.id.rv_contacts)
//    RecyclerView recyclerView;
//
//    private ArrayList<RoomResponse> contactsList;
//
//    public static ContactsListFragment newInstance()
//    {
//        Bundle args = new Bundle();
//        ContactsListFragment fragment = new ContactsListFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
//            @Nullable Bundle savedInstanceState)
//    {
//        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
//        ActivityComponent component = getActivityComponent();
//        if (component != null)
//        {
//            component.inject(this);
//            setUnBinder(ButterKnife.bind(this, view));
//            presenter.onAttach(this);
//            if (savedInstanceState == null){
//                presenter.updateUserList();
//            }
//        }
//        return view;
//    }
//
//    @Override
//    protected void setUp(View view)
//    {
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(new ContactsDecorator(getContext()));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), recyclerView, new RecyclerViewClickListener()
//        {
//            @Override
//            public void onClick(View view, int position)
//            {
//                Intent intent = new Intent(getActivity(), ChatActivity.class);
//                intent.putExtra(AppConstants.ID_ROOM, contactsList.get(position).getIdRoom());
//                intent.putExtra(AppConstants.ROOM_NAME, contactsList.get(position).getFullName());
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position)
//            {
//
//            }
//        }));
//    }
//
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        registerIncomingMessageReceiver();
//    }
//
//    @Override
//    public void onDestroyView()
//    {
//        presenter.onDetach();
//        if (incomingMessageReceiver != null)
//        {
//            getActivity().unregisterReceiver(incomingMessageReceiver);
//        }
//        super.onDestroyView();
//    }
//
//    @Override
//    public void updateUserListData(List<RoomResponse> response)
//    {
//        contactsList = new ArrayList<>();
//        contactsList.addAll(response);
//        adapter.addItems(contactsList);
//        adapter.addItems(response);
//    }
//
//    private void registerIncomingMessageReceiver()
//    {
//        incomingMessageReceiver = new BroadcastReceiver()
//        {
//            @Override
//            public void onReceive(Context context, Intent intent)
//            {
//                Timber.d("Получено новое сообщение");
//                int status = intent.getIntExtra(PARAM_STATUS, 0);
//                Timber.d("onReceive: status = " + status);
//
//                if (status == STATUS_START)
//                {
//                    Timber.d("Запуск BroadcastReceiver");
//                }
//                if (status == STATUS_FINISH)
//                {
//                    Timber.d("Остановка BroadcastReceiver");
//                    presenter.updateUserList();
//                    NotificationUtils.clearNotifications(getContext());
//                }
//            }
//        };
//        IntentFilter filterIncomingMessage = new IntentFilter(BROADCAST_INCOMING_MESSAGE);
//        getActivity().registerReceiver(incomingMessageReceiver, filterIncomingMessage);
//    }
//}
