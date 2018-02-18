package com.example.sajagjain.mework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sajagjain.mework.adapter.TodoAdapter;
import com.example.sajagjain.mework.model.TodoListModel;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.gson.Gson;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, TodoAdapter.TodoAdapterListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<TodoListModel> todoList = new ArrayList<>();
    private TodoAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;
    EditText taskName, taskDescription, taskDate;
    Button taskSubmit;
    SharedPreferences pref;
    Calendar calendar;
    int year, month, day;
    LinearLayout newTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        AwesomeBar bar = findViewById(R.id.bar);
        bar.addAction(R.drawable.awsb_ic_edit_animated, "Add Task");

        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {

                if (newTask.getVisibility() == View.VISIBLE) {

                    findViewById(R.id.container).setBackgroundColor(Color.WHITE);
                    recyclerView.setBackgroundColor(Color.WHITE);


//                    ValueAnimator anim = ValueAnimator.ofInt(newTask.getMeasuredHeight(), -250);
//                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                            int val = (Integer) valueAnimator.getAnimatedValue();
//                            Log.d("anim", val + "");
//                            ViewGroup.LayoutParams layoutParams = newTask.getLayoutParams();
//                            layoutParams.height = val;
//                            newTask.setLayoutParams(layoutParams);
//                            if (val == -250) {
//                                newTask.setVisibility(View.GONE);
//                            }
//                        }
//                    });
//                    anim.setDuration(1000);
//                    TransitionManager.beginDelayedTransition(newTask);
//                    anim.start();


                    newTask.setVisibility(View.GONE);

                } else {
                    TransitionManager.beginDelayedTransition(recyclerView, new Slide());
                    findViewById(R.id.container).setBackgroundColor(getResources().getColor(R.color.white));


                    newTask.animate()
                            .translationY(0).alpha(1.0f).setDuration(300)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);
                                    newTask.setVisibility(View.VISIBLE);
//                                    newTask.setAlpha(0.0f);
                                }
                            });
//                    ValueAnimator anim = ValueAnimator.ofInt(newTask.getMeasuredHeight(), 250);
//                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                            int val = (Integer) valueAnimator.getAnimatedValue();
//                            ViewGroup.LayoutParams layoutParams = newTask.getLayoutParams();
//                            layoutParams.height = val;
//                            newTask.setLayoutParams(layoutParams);
//                            if(val==250){
//                                newTask.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    });
//                    anim.setDuration(1000);
//                    anim.start();


                    taskName.setText(" ");
                    taskDescription.setText(" ");
                    taskDate.setText(new Date().toString());
                    taskDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new SingleDateAndTimePickerDialog.Builder(MainActivity.this)
                                    .bottomSheet()
                                    .curved()
                                    .minutesStep(1)
                                    .mustBeOnFuture()
                                    .mainColor(getResources().getColor(R.color.colorPrimary))
                                    .titleTextColor(getResources().getColor(R.color.colorPrimary))

                                    //.displayHours(false)
                                    //.displayMinutes(false)

                                    //.todayText("aujourd'hui")

                                    .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                                        @Override
                                        public void onDisplayed(SingleDateAndTimePicker picker) {
                                            //retrieve the SingleDateAndTimePicker
                                        }
                                    })

                                    .title("Pick a Date")
                                    .listener(new SingleDateAndTimePickerDialog.Listener() {
                                        @Override
                                        public void onDateSelected(Date date) {
                                            taskDate.setText(date.toString());
                                        }
                                    }).display();
                        }
                    });
                    taskSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            findViewById(R.id.container).setBackgroundColor(Color.WHITE);
                            recyclerView.setBackground(getDrawable(R.drawable.white_background));

                            String tName = taskName.getText().toString();
                            String tDescription = taskDescription.getText().toString();
                            String tDate = taskDate.getText().toString();
                            if (tName.equals(" ") || tDescription.equals(" ")) {

                                Snackbar.make(view, "Please Enter the details", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                newTask.setVisibility(View.GONE);
                            } else {


                                todoList.add(todoList.size(), new TodoListModel(tName, tDescription, taskDate.getText().toString(), "2hrs",false));
                                SharedPreferences.Editor prefsEditor = pref.edit();
                                Iterator it = todoList.iterator();
                                recyclerView.getAdapter().notifyDataSetChanged();
                                HashSet<String> set = new HashSet();
                                while (it.hasNext()) {
                                    Gson gson1 = new Gson();
                                    set.add(gson1.toJson(it.next()));
                                    prefsEditor.putStringSet("todo", set);

                                }
                                prefsEditor.commit();
                                newTask.setVisibility(View.GONE);

                                startAlarm(taskDate.getText().toString(), todoList.size() - 1, todoList.get(todoList.size() - 1).getName(), todoList.get(todoList.size() - 1).getDescription());

                                Snackbar.make(view, "Added to List", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();


                            }
                        }
                    });


                }

            }
        });

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ContactUs.class));
            }
        });


        bar.displayHomeAsUpEnabled(false);

        newTask = (LinearLayout) findViewById(R.id.task_add_linear_layout);
        taskName = (EditText) findViewById(R.id.dialog_name);
        taskDescription = (EditText) findViewById(R.id.dialog_description);
        taskDate = (EditText) findViewById(R.id.dialog_date);
        taskSubmit = (Button) findViewById(R.id.dialog_task_submit);

        //Calendar Instances

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);


        pref = getApplicationContext().getSharedPreferences("TODO_PLANS", MODE_PRIVATE);
        Gson gson = new Gson();
        Set<String> planFromSharedPref = pref.getStringSet("todo", null);
        if (planFromSharedPref != null) {
            Iterator it = planFromSharedPref.iterator();
            while (it.hasNext()) {
                todoList.add(gson.fromJson((String) it.next(), TodoListModel.class));
            }
        } else {
            todoList = new ArrayList<>();
            SharedPreferences.Editor prefsEditor = pref.edit();
            Iterator it = todoList.iterator();
            HashSet<String> set = new HashSet();
            while (it.hasNext()) {
                prefsEditor.putStringSet("todo", set);

            }
            prefsEditor.commit();
        }


        recyclerView = findViewById(R.id.recycler_view);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        mAdapter = new TodoAdapter(this, todoList, MainActivity.this);


        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showDate1(int year, int month, int day) {

        String dayparse = day + "";
        String monthparse = month + "";
        if (day < 10) {
            dayparse = "0" + day;
        }
        if (month < 10) {
            monthparse = "0" + month;
        }
        taskDate.setText(dayparse + "/" + monthparse + "/" + year);

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof TodoAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = todoList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final TodoListModel deletedItem = todoList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            cancelAlarm(todoList.get(deletedIndex).getDate(), deletedIndex, todoList.get(deletedIndex).getName(), todoList.get(deletedIndex).getDescription());
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " Removed from list!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // undo is selected, restore the deleted item
                        mAdapter.restoreItem(deletedItem, deletedIndex);
                        SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");

                        Date dated = (input.parse(deletedItem.getDate()));
                        Date currentDate = input.parse(new Date().toString());
                        if (dated.after(currentDate)) {
                            startAlarm(todoList.get(deletedIndex).getDate(), deletedIndex, todoList.get(deletedIndex).getName(), todoList.get(deletedIndex).getDescription());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                snackbar.setActionTextColor(getColor(R.color.colorPrimaryDark));
            } else {
                snackbar.setActionTextColor(Color.YELLOW);
            }
            snackbar.show();
            SharedPreferences.Editor prefsEditor = pref.edit();
            Iterator it = todoList.iterator();
            mAdapter = new TodoAdapter(this, todoList, MainActivity.this);
            recyclerView.setAdapter(mAdapter);

            HashSet<String> set = new HashSet();
            if (todoList.size() != 0) {
                while (it.hasNext()) {
                    Gson gson1 = new Gson();
                    set.add(gson1.toJson(it.next()));
                    prefsEditor.putStringSet("todo", set);

                }
            } else {
                prefsEditor.clear();
            }
            prefsEditor.commit();

        }
    }

    @Override
    public void onCheckBoxOptionMenuClicked(int position, View view) {
        CheckBox box=(CheckBox) view;
        if (newTask.getVisibility() == View.VISIBLE) {
            newTask.setVisibility(View.GONE);
        }
        if(((CheckBox) view).isChecked()){
            todoList.get(position).setComplete(true);
        }else{
            todoList.get(position).setComplete(false);
        }
        SharedPreferences.Editor prefsEditor = pref.edit();
        Iterator it = todoList.iterator();
        mAdapter = new TodoAdapter(this, todoList, MainActivity.this);
        recyclerView.setAdapter(mAdapter);

        HashSet<String> set = new HashSet();
        if (todoList.size() != 0) {
            while (it.hasNext()) {
                Gson gson1 = new Gson();
                set.add(gson1.toJson(it.next()));
                prefsEditor.putStringSet("todo", set);

            }
        } else {
            prefsEditor.clear();
        }
        prefsEditor.commit();
    }

    @Override
    public void onEverythingElseClicked(int position, View view) {
        if (newTask.getVisibility() == View.VISIBLE) {
            findViewById(R.id.container).setBackgroundColor(Color.WHITE);
            recyclerView.setBackground(getDrawable(R.drawable.white_background));
            newTask.setVisibility(View.GONE);
        }
    }

    private void startAlarm(String date, int requestCode, String name, String desc) {
        try {
            SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            Date dated;

            dated = (input.parse(date));

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReciever.class).putExtra("name", name).putExtra("desc", desc);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);


            alarmManager.setExact(AlarmManager.RTC_WAKEUP, dated.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void cancelAlarm(String date, int requestCode, String name, String desc) {
        try {
            SimpleDateFormat input = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            Date dated;

            dated = (input.parse(date));

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlertReciever.class).putExtra("name", name).putExtra("desc", desc);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);


            alarmManager.cancel(pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
