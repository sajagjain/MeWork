package com.example.sajagjain.mework.adapter;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.example.sajagjain.mework.R;
import com.example.sajagjain.mework.model.TodoListModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sajag jain on 14-02-2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {
    private Context context;
    private List<TodoListModel> todoList;
    private TodoAdapterListener listener;
    CountDownTimer countDownTimer;
    private long timeLeftInMillis=600000;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, description, timeLeft;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;
        private CheckBox isCompleted;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            timeLeft = view.findViewById(R.id.time_left);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            isCompleted=view.findViewById(R.id.isTodoTask_finished);
        }
    }


    public TodoAdapter(Context context, List<TodoListModel> todoList,TodoAdapterListener listener) {
        this.context = context;
        this.todoList = todoList;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final TodoListModel item = todoList.get(position);
        holder.name.setText(item.getName());
        holder.description.setText(item.getDescription());

        SimpleDateFormat input=new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date date,date1;
            date=(input.parse(todoList.get(position).getDate()));
            date1=(input.parse(new Date().toString()));

            todoList.get(position).setTimeRemaining("Pending");
            long diff = date.getTime() - date1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);

            timeLeftInMillis=diff;
            countDownTimer=new CountDownTimer(timeLeftInMillis,1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMillis=l;
                    int hours=(int)timeLeftInMillis/(60*60*1000);
                    int minutes=(int)timeLeftInMillis%(60*60*1000)/60000;
                    int seconds=(int)timeLeftInMillis%60000/1000;
                    if(hours==0&&minutes==0){
                        holder.timeLeft.setTextColor(Color.RED);
                    }
                    holder.timeLeft.setText(hours+" Hours "+minutes+" Minutes "+seconds+" Seconds Left");
                }

                @Override
                public void onFinish() {
                    if(countDownTimer!=null) {
                        countDownTimer.cancel();
                    }
                    holder.timeLeft.setText("Finished");
                    todoList.get(position).setTimeRemaining("Finished");

                }
            }.start();

            Log.d("hours",diff+"");
//            holder.timeLeft.setText(diffHours+" Hours");

        } catch (ParseException e) {
            e.printStackTrace();
        }



        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        int color2 = generator.getColor(holder.name.getText().toString());


        TextDrawable drawable = TextDrawable.builder()
                 .buildRound(holder.name.getText().toString().toUpperCase().charAt(1)+"", color2);

        holder.thumbnail.setImageDrawable(drawable);

        if(todoList.get(position).isComplete()){
            holder.isCompleted.setChecked(true);
        }


        applyClickEvents(holder, position);
    }

    private void applyClickEvents(TodoAdapter.MyViewHolder holder, final int position) {

        holder.timeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEverythingElseClicked(position, view);
            }
        });
        holder.isCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCheckBoxOptionMenuClicked(position,view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void removeItem(int position) {
        todoList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(TodoListModel item, int position) {
        todoList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }

    public interface TodoAdapterListener {
        void onCheckBoxOptionMenuClicked(int position, View view);

        void onEverythingElseClicked(int position, View view);
    }
}