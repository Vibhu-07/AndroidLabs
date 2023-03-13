package data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cst2335.sikk0006.ChatMessage;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
}
