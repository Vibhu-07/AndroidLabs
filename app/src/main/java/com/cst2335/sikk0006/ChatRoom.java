package com.cst2335.sikk0006;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cst2335.sikk0006.databinding.ActivityChatRoomBinding;
import com.cst2335.sikk0006.databinding.RecieveMessageBinding;
import com.cst2335.sikk0006.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import data.ChatRoomViewModel;

public class ChatRoom extends AppCompatActivity {


    //binding object from Binding class
    ActivityChatRoomBinding binding;

    //ArrayList of String objects that will store messages in each go
    ArrayList<ChatMessage> messages = new ArrayList<>();

    //ChatModel obj used for retrieving info at rotation
    ChatRoomViewModel chatModel ;

    // ChatMessage DAO object
    ChatMessageDAO mDAO;


    //Adapter for recycler that notifies it
    private RecyclerView.Adapter myAdapter;

    //INNER CLASS TO MANAGE AND CREATE STRUCTURE OF RECYCLER VIEW
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;  //variables used in chat layout
        public MyRowHolder(@NonNull View itemView) { //constructor
            super(itemView);
            itemView.setOnClickListener(clk->{
                // Get the item at the clicked position
                int position = getBindingAdapterPosition();

                // Create an alert dialog to confirm deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete the message: " + messageText.getText ())
                .setTitle("Question: ")
                .setNegativeButton( "No", (dialog, ol) -> { })
                .setPositiveButton( "Yes", (dialog, cl) -> {
                    messages.remove(position);
                    // Delete the message from the database
//                    mDAO.deleteMessage(deletedMessage);
                    myAdapter.notifyItemRemoved(position);
                }).create().show();
            });
            messageText = itemView.findViewById(R.id.textViewMessage);
            timeText = itemView.findViewById(R.id.textViewTime);
        }
    }

    // Method to load all chats into database at once
    private void loadMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<ChatMessage> loadedMessages = mDAO.getAllMessages();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messages.addAll(loadedMessages);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Creating database instance
        MessageDatabase db = MessageDatabase.getInstance(getApplicationContext());
        mDAO = db.chatMessageDAO();

        // Load all messages into Database
        loadMessages();

        // RETRIEVING LIST UPON ROTATION (SURVIVING THE ROTATION)
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        //next retrieve the ArrayList<> that it was storing before rotation
        messages = chatModel.messages.getValue();
        //verify if the chatModel.messages variable has never been set before.
        if(messages == null){
            chatModel.messages.postValue( messages = new ArrayList<>());
        }

        //CHANGING OLD WAY TO BINDING WAY OF SetContentView()
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //SET LAYOUT MANAGER FOR RECYCLER VIEW (VERTICAL SCROLLING)
        binding.recyclerViewId.setLayoutManager(new LinearLayoutManager(this));

        //ADAPTER TO THE RECYCLERVIEW BINDING TAKEN FURTHER BY IMPLEMENTING METHODS (LOADS RECYCLERVIEW)
        binding.recyclerViewId.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            //all the onCreateViewHolder() needs to do is to load the correct View for the type viewType.
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 0) {
                    SentMessageBinding sendBinding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sendBinding.getRoot());
                } else {
                    RecieveMessageBinding receiveBinding = RecieveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(receiveBinding.getRoot());
                }
            }

            @Override
            //onBindViewHolder is where you set the objects in your layout for the row.
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // Get the message at the given position in the dataset
                ChatMessage message = messages.get(position);
                // Set the message text on the TextView object
                TextView messageTextView = holder.itemView.findViewById(R.id.textViewMessage);
                TextView timeTextView = holder.itemView.findViewById(R.id.textViewTime);
                messageTextView.setText(message.getMessage());
                timeTextView.setText(message.getTimeSent());
            }


            @Override
            //getItemCount function should just return the number of rows in the list
            public int getItemCount() {
                // since we want to show whatever is in our ArrayList, the number of rows will be just the size of the list
                return messages.size();
            }

            @Override
            //This function returns an int which is the parameter which gets passed in to the onCreateViewHolder(ViewGroup parent, int viewType) function
            public int getItemViewType(int position){
                ChatMessage message = messages.get(position);
                if (message.isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        //CLICK LISTENER ON SEND BUTTON (ADD MESSAGE TO LIST + CLEAR BOX)
        binding.sendBtn.setOnClickListener(click -> {
            String messageText = binding.textInput.getText().toString();

            //date format
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(messageText, currentDateandTime, true);
            messages.add(chatMessage); //add message

            //notify item added to the recyclerView Adapter to show the changes + Dataset changed
            myAdapter.notifyItemInserted(messages.size()-1); // (-1) as inserted at end
            myAdapter.notifyDataSetChanged();

            //clear previous text
            binding.textInput.setText("");

            // Insert the chat message into the database
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mDAO.insertMessage(chatMessage);
                }
            }).start();
        });

        // Set the click listener for the Receive button
        binding.recieveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = binding.textInput.getText().toString();
                //date format
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
                String currentDateandTime = sdf.format(new Date());

                // Create a new ChatMessage object with the message and current time
                ChatMessage newMessage = new ChatMessage(messageText, currentDateandTime, false);

                // Add the new ChatMessage object to the messages ArrayList
                //notify item added to the recyclerView Adapter to show the changes + Dataset changed
                myAdapter.notifyItemInserted(messages.size()-1); // (-1) as inserted at end
                messages.add(newMessage);

                // Notify the adapter that the data set has changed
                myAdapter.notifyDataSetChanged();

                //clear previous text
                binding.textInput.setText("");

                // Insert the chat message into the database
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDAO.insertMessage(newMessage);
                    }
                }).start();
            }
        });

    }
}