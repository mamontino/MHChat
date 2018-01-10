package com.medhelp2.mhchat.ui.chat.chat_list;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.medhelp2.mhchat.MainApp;
import com.medhelp2.mhchat.R;
import com.medhelp2.mhchat.bg.MessagingService;
import com.medhelp2.mhchat.data.DataHelper;
import com.medhelp2.mhchat.data.model.MessageResponse;
import com.medhelp2.mhchat.di.component.ActivityComponent;
import com.medhelp2.mhchat.ui.base.BaseFragment;
import com.medhelp2.mhchat.utils.main.AppConstants;
import com.medhelp2.mhchat.utils.main.NotificationUtils;
import com.medhelp2.mhchat.utils.rx.RxEvents;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ChatListFragment extends BaseFragment implements ChatListViewHelper
{
    public final static int STATUS_START = 100;
    public final static int STATUS_FINISH = 200;
    public final static String CHAT_ROOM_ID = "ID_ROOM";
    public final static String PARAM_STATUS = "status";
    public static final String TAG = "ChatListFragment";
    public final static String BROADCAST_OUTGOING_MESSAGE = "com.medhelp2.mhchat.ui.chat.chat_list.servicebackbroadcast";
    public final static String BROADCAST_INCOMING_MESSAGE = "com.medhelp2.mhchat.ui.chat.chat_list.incomingmessagebackbroadcast";

    private int idChat;
    private int idUser;

    @Inject
    ChatListPresenterHelper<ChatListViewHelper> presenter;

    @Inject
    CompositeDisposable disposables;

    @Inject
    DataHelper dataHelper;

    @BindView(R.id.err_tv_message)
    TextView errMessage;

    @BindView(R.id.tv_no_con_center)
    TextView noCon;

    @Inject
    LinearLayoutManager layoutManager;

    @BindView(R.id.rv_chat_list)
    RecyclerView recyclerView;

    @BindView(R.id.et_send_message)
    EditText etSend;

    private ChatAdapter adapter;
    private BroadcastReceiver outgoingMessageReceiver;
    private BroadcastReceiver incomingMessageReceiver;

    public static ChatListFragment newInstance(int idChat)
    {
        Bundle args = new Bundle();
        ChatListFragment fragment = new ChatListFragment();
        if (idChat != 0)
        {
            args.putInt(AppConstants.ID_ROOM, idChat);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ActivityComponent component = getActivityComponent();

        if (component != null)
        {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            if (idChat != 0)
            {
                presenter.loadMessageList(idChat);
                presenter.readMessages(idChat);
            }
        }

        idUser = dataHelper.getCurrentUserId();
        return view;
    }

    @SuppressWarnings("all")
    private void registerIncomingMessageReceiver()
    {
        incomingMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                Timber.d("Получено новое сообщение");
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                int idRoomInt = intent.getIntExtra(CHAT_ROOM_ID, 0);
                Timber.d("onReceive: status = " + status);

                if (status == STATUS_START)
                {
                    Timber.d("Запуск BroadcastReceiver");
                }
                if (status == STATUS_FINISH)
                {
                    Timber.d("Остановка BroadcastReceiver");
                    presenter.loadMessageList(idChat);
                    NotificationUtils.clearNotifications(getContext());
                }
                if (idRoomInt == idChat)
                {
                    presenter.loadMessageList(idChat);
                    Timber.d("Запуск BroadcastReceiver");
                }
            }
        };
        IntentFilter filterIncomingMessage = new IntentFilter(BROADCAST_INCOMING_MESSAGE);
        getActivity().registerReceiver(incomingMessageReceiver, filterIncomingMessage);
    }

    @SuppressWarnings("all")
    private void registerOutgoingMessageReceiver()
    {
        outgoingMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                int status = intent.getIntExtra(PARAM_STATUS, 0);
                Timber.d("onReceive: status = " + status);

                if (status == STATUS_START)
                {
                    Timber.d("Запуск BroadcastReceiver");
                }
                if (status == STATUS_FINISH)
                {
                    Timber.d("Остановка BroadcastReceiver");
                    presenter.loadMessageList(idChat);
                }
            }
        };
        IntentFilter filter = new IntentFilter(BROADCAST_OUTGOING_MESSAGE);
        getActivity().registerReceiver(outgoingMessageReceiver, filter);
    }

    @OnClick(R.id.btn_send_message)
    public void sendMessageToServer()
    {
        String message = etSend.getText().toString().trim();
        if (!message.equals(""))
        {
            sendMessage(idChat, idUser, message);
        }
        etSend.setText("");
        hideKeyboard();
    }

    @SuppressWarnings("all")
    private void sendMessage(int idChat, int idUser, String message)
    {
        Intent startOutgoingService = MessagingService.getStartIntent(getContext());
        startOutgoingService.putExtra(MessagingService.SERVICE_MESSAGE, message);
        startOutgoingService.putExtra(MessagingService.SERVICE_CHAT_ROOM, idChat);
        startOutgoingService.putExtra(MessagingService.SERVICE_USER_ID, idUser);
        getContext().startService(startOutgoingService);
    }

    @Override
    @SuppressWarnings("all")
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        idChat = getArguments().getInt(AppConstants.ID_ROOM);
    }

    @Override
    public void onStart()
    {
        registerOutgoingMessageReceiver();
        registerIncomingMessageReceiver();
        super.onStart();
    }

    @Override
    protected void setUp(View view)
    {
    }

    @SuppressWarnings("all")
    private void setupRxBus()
    {
        disposables.add(((MainApp) getActivity().getApplication())
                .bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object ->
                {
                    if (object instanceof RxEvents.startChatRefreshing)
                    {
                        presenter.loadMessagesFromServer(idChat);
                    } else if (object instanceof RxEvents.noConnection)
                    {
                        noCon.setVisibility(View.VISIBLE);
                    } else if (object instanceof RxEvents.hasConnection)
                    {
                        noCon.setVisibility(View.GONE);
                    }
                }));
    }

    @Override
    public void onResume()
    {
        setupRxBus();
        super.onResume();
    }

    @Override
    @SuppressWarnings("all")
    public void onPause()
    {
        disposables.dispose();

        if (incomingMessageReceiver != null)
        {
            getActivity().unregisterReceiver(incomingMessageReceiver);
        }

        if (outgoingMessageReceiver != null)
        {
            getActivity().unregisterReceiver(outgoingMessageReceiver);
        }

        super.onPause();
    }

    @Override
    public void onDestroy()
    {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void updateMessageList(List<MessageResponse> response)
    {
        errMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        adapter = new ChatAdapter(response, idUser);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        NotificationUtils.clearNotifications(getContext());
    }

    @Override
    @SuppressWarnings("all")
    public void stopRefreshing()
    {
        ((MainApp) getActivity().getApplication())
                .bus()
                .send(new RxEvents.stopChatRefreshing());
    }

    @Override
    public void showErrorScreen()
    {
        errMessage.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
