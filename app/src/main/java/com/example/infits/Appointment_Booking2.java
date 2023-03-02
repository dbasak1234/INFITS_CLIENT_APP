package com.example.infits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Appointment_Booking2 extends AppCompatActivity {

    FrameLayout confirmBtn;
    Dialog confirmDialog, customDialog;
    ImageView imgBack;

    FrameLayout customBtnMorningSlot, customBtnEveningSlot;

    private RecyclerView recyclerView;
    private List<Date> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_booking2);

        imgBack = findViewById(R.id.imgBackAppointment);
        confirmBtn = findViewById(R.id.confirm_btn);

        confirmDialog = new Dialog(this);
        customDialog = new Dialog(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Appointment_booking.class));
            }
        });

        View.OnClickListener dayClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout dayLayout = (FrameLayout) v;
                boolean isSelected = dayLayout.isSelected();
                dayLayout.setSelected(!isSelected);
                dayLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), isSelected ? R.drawable.rounded_background_grey : R.drawable.rounded_background));
                TextView dayText = (TextView) dayLayout.getChildAt(0);
                dayText.setTextColor(ContextCompat.getColor(getApplicationContext(), isSelected ? R.color.black : R.color.white));
                TextView dateText = (TextView) dayLayout.getChildAt(1);
                dateText.setTextColor(ContextCompat.getColor(getApplicationContext(), isSelected ? R.color.black : R.color.white));

            }
        };

        View.OnClickListener appointmentTime = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout dayLayout = (FrameLayout) v;

                // Check if this is the now or anytime button
                boolean isNowButton = dayLayout.getId() == R.id.nowBtn;
                boolean isAnytimeButton = dayLayout.getId() == R.id.anyTimeBtn;

                FrameLayout nowButton, anyTimeButton;
                TextView nowText, anytimeText;

                nowButton = findViewById(R.id.nowBtn);
                anyTimeButton = findViewById(R.id.anyTimeBtn);

                anytimeText = anyTimeButton.findViewById(R.id.anyTimeText);
                nowText = nowButton.findViewById(R.id.nowText);

                // If the now button is selected, deselect the anytime button (and vice versa)
                if (isNowButton) {
                    anyTimeButton.setSelected(false);
                    anyTimeButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background_white));
                    anytimeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.skyBlue));

                    nowButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background));
                    nowText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                } else if (isAnytimeButton) {
                    nowButton.setSelected(false);
                    nowButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background_white));
                    nowText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.skyBlue));

                    anyTimeButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background));
                    anytimeText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }

                // Toggle the selection state of the clicked button
                dayLayout.setSelected(!dayLayout.isSelected());
                dayLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), dayLayout.isSelected() ? R.drawable.rounded_background : R.drawable.rounded_background_white));

                // Check if the child views exist before accessing them
                TextView dayText = null;
                TextView dateText = null;
                if (dayLayout.getChildCount() > 0) {
                    View child0 = dayLayout.getChildAt(0);
                    if (child0 instanceof TextView) {
                        dayText = (TextView) child0;
                    }
                }
                if (dayLayout.getChildCount() > 1) {
                    View child1 = dayLayout.getChildAt(1);
                    if (child1 instanceof TextView) {
                        dateText = (TextView) child1;
                    }
                }

                if (dayText != null) {
                    dayText.setTextColor(ContextCompat.getColor(getApplicationContext(), dayLayout.isSelected() ? R.color.white : R.color.skyBlue));
                }
                if (dateText != null) {
                    dateText.setTextColor(ContextCompat.getColor(getApplicationContext(), dayLayout.isSelected() ? R.color.white : R.color.skyBlue));
                }
            }
        };

        // Morning slot ...
        // Declare a list to hold all the morning slot FrameLayouts
        List<FrameLayout> morningSlots = new ArrayList<>();

        // Find all the morning slot FrameLayouts and add them to the list
        morningSlots.add(findViewById(R.id.morningSlot1));
        morningSlots.add(findViewById(R.id.morningSlot2));
        morningSlots.add(findViewById(R.id.morningSlot3));
        morningSlots.add(findViewById(R.id.morningSlot4));
        morningSlots.add(findViewById(R.id.morningSlot5));

        //TextView
        TextView[] slotTexts = new TextView[6];
        slotTexts[0] = findViewById(R.id.slotText1);
        slotTexts[1] = findViewById(R.id.slotText2);
        slotTexts[2] = findViewById(R.id.slotText3);
        slotTexts[3] = findViewById(R.id.slotText4);
        slotTexts[4] = findViewById(R.id.slotText5);

        // Set the onClickListener for all the morning slot FrameLayouts
        for (FrameLayout morningSlot : morningSlots) {
            morningSlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform the same operations on all the morning slot FrameLayouts here
                    for (int i = 0; i < morningSlots.size(); i++) {
                        FrameLayout slot = morningSlots.get(i);

                        slot.setSelected(false);
                        slot.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background_grey));

                        TextView slotText = slotTexts[i];
                        slotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    }

                    FrameLayout selectedSlot = (FrameLayout) v;
                    selectedSlot.setSelected(true);
                    selectedSlot.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background));

                    TextView selectedSlotText;
                    switch (selectedSlot.getId()) {
                        case R.id.morningSlot1:
                            selectedSlotText = findViewById(R.id.slotText1);
                            break;
                        case R.id.morningSlot2:
                            selectedSlotText = findViewById(R.id.slotText2);
                            break;
                        case R.id.morningSlot3:
                            selectedSlotText = findViewById(R.id.slotText3);
                            break;
                        case R.id.morningSlot4:
                            selectedSlotText = findViewById(R.id.slotText4);
                            break;
                        case R.id.morningSlot5:
                            selectedSlotText = findViewById(R.id.slotText5);
                            break;
                        default:
                            selectedSlotText = null;
                            break;
                    }
                    if (selectedSlotText != null) {
                        selectedSlotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    }

                    selectedSlotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
            });
        }

        // Evening slots ...
        List<FrameLayout> eveningSlots = new ArrayList<>();

        eveningSlots.add(findViewById(R.id.eveningSlot1));
        eveningSlots.add(findViewById(R.id.eveningSlot2));
        eveningSlots.add(findViewById(R.id.eveningSlot3));
        eveningSlots.add(findViewById(R.id.eveningSlot4));
        eveningSlots.add(findViewById(R.id.eveningSlot5));

        TextView[] slotText1 = new TextView[6];
        slotText1[0] = findViewById(R.id.slotText7);
        slotText1[1] = findViewById(R.id.slotText8);
        slotText1[2] = findViewById(R.id.slotText9);
        slotText1[3] = findViewById(R.id.slotText10);
        slotText1[4] = findViewById(R.id.slotText11);

        // Set the onClickListener for all the evening slot FrameLayouts
        for (FrameLayout eveningSlot : eveningSlots) {
            eveningSlot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform the same operations on all the morning slot FrameLayouts here
                    for (int i = 0; i < eveningSlots.size(); i++) {
                        FrameLayout slot = eveningSlots.get(i);

                        slot.setSelected(false);
                        slot.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background_grey));

                        TextView slotText = slotText1[i];
                        slotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
                    }

                    FrameLayout selectedSlot = (FrameLayout) v;
                    selectedSlot.setSelected(true);
                    selectedSlot.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_background));

                    TextView selectedSlotText;
                    switch (selectedSlot.getId()) {
                        case R.id.eveningSlot1:
                            selectedSlotText = findViewById(R.id.slotText7);
                            break;
                        case R.id.eveningSlot2:
                            selectedSlotText = findViewById(R.id.slotText8);
                            break;
                        case R.id.eveningSlot3:
                            selectedSlotText = findViewById(R.id.slotText9);
                            break;
                        case R.id.eveningSlot4:
                            selectedSlotText = findViewById(R.id.slotText10);
                            break;
                        case R.id.eveningSlot5:
                            selectedSlotText = findViewById(R.id.slotText11);
                            break;
                        default:
                            selectedSlotText = null;
                            break;
                    }
                    if (selectedSlotText != null) {
                        selectedSlotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                    }

                    selectedSlotText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }
            });
        }

        /*
        View.OnClickListener allSlot = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout dayLayout = (FrameLayout) v;
                boolean isSelected = dayLayout.isSelected();
                dayLayout.setSelected(!isSelected);
                dayLayout.setBackground(ContextCompat.getDrawable(getApplicationContext(), isSelected ? R.drawable.rounded_background_grey : R.drawable.rounded_background));

                // Check if the child views exist before accessing them
                TextView dayText = null;
                TextView dateText = null;
                if (dayLayout.getChildCount() > 0) {
                    View child0 = dayLayout.getChildAt(0);
                    if (child0 instanceof TextView) {
                        dayText = (TextView) child0;
                    }
                }
                if (dayLayout.getChildCount() > 1) {
                    View child1 = dayLayout.getChildAt(1);
                    if (child1 instanceof TextView) {
                        dateText = (TextView) child1;
                    }
                }

                if (dayText != null) {
                    dayText.setTextColor(ContextCompat.getColor(getApplicationContext(), isSelected ? R.color.black : R.color.white));
                }
                if (dateText != null) {
                    dateText.setTextColor(ContextCompat.getColor(getApplicationContext(), isSelected ? R.color.black : R.color.white));
                }
            }
        };
        */

        // Select Schedule
//        findViewById(R.id.monday).setOnClickListener(dayClickListener);
//        findViewById(R.id.tuesday).setOnClickListener(dayClickListener);
//        findViewById(R.id.wednesday).setOnClickListener(dayClickListener);
//        findViewById(R.id.thursday).setOnClickListener(dayClickListener);
//        findViewById(R.id.friday).setOnClickListener(dayClickListener);
//        findViewById(R.id.saturday).setOnClickListener(dayClickListener);
//        findViewById(R.id.sunday).setOnClickListener(dayClickListener);

//        // Morning slots
//        findViewById(R.id.morningSlot1).setOnClickListener(allSlot);
//        findViewById(R.id.morningSlot2).setOnClickListener(allSlot);
//        findViewById(R.id.morningSlot3).setOnClickListener(allSlot);
//        findViewById(R.id.morningSlot4).setOnClickListener(allSlot);
//        findViewById(R.id.morningSlot5).setOnClickListener(allSlot);
//        findViewById(R.id.morningSlot6).setOnClickListener(allSlot);
//
//        // Evening slots
//        findViewById(R.id.eveningSlot1).setOnClickListener(allSlot);
//        findViewById(R.id.eveningSlot2).setOnClickListener(allSlot);
//        findViewById(R.id.eveningSlot3).setOnClickListener(allSlot);
//        findViewById(R.id.eveningSlot4).setOnClickListener(allSlot);
//        findViewById(R.id.eveningSlot5).setOnClickListener(allSlot);
//        findViewById(R.id.eveningSlot6).setOnClickListener(allSlot);
//

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog();
            }
        });

        Date today = new Date();
        dateList = new ArrayList<>();

        // Add some test data to the dateList
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        dateList.add(today);
        for (int i = 0; i < 100; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(calendar.getTime());
        }

        recyclerView = findViewById(R.id.date_recyclerView);

        // Create and set adapter to the recyclerview


        CalendarAdapter adapter = new CalendarAdapter(dateList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);




        // Custom Time section

        customBtnMorningSlot = findViewById(R.id.customBtn_morningSlot);
        customBtnEveningSlot = findViewById(R.id.customBtn_EveningSlot);

        customBtnMorningSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTimeDialog();
            }
        });

    }

    private void customTimeDialog(){
        customDialog.setContentView(R.layout.custom_time_for_booking_appointment);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView confirmCard = customDialog.findViewById(R.id.custom_time);
        customDialog.show();

        RecyclerView hoursRV = customDialog.findViewById(R.id.hours_recycler_view);

        List<String> hoursData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            hoursData.add(String.valueOf(i));
        }
        hoursRV.setLayoutManager(new CenteredLinearLayoutManager(this));
        NumberedAdapter hoursAdapter = new NumberedAdapter(hoursData, true);
        hoursRV.setAdapter(hoursAdapter);
//        hoursRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RecyclerView minutesRV = customDialog.findViewById(R.id.min_recycler_view);
        List<String> minutesData = new ArrayList<>();
        for (int i = 1; i <= 60; i++) {
            minutesData.add(String.valueOf(i));
        }
        NumberedAdapter minutesAdapter = new NumberedAdapter(minutesData, false);
        minutesRV.setAdapter(minutesAdapter);
        minutesRV.setLayoutManager(new CenteredLinearLayoutManager(this));
        minutesRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void showConfirmDialog() {
        confirmDialog.setContentView(R.layout.confirm_appointment);
        confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CardView confirmCard = confirmDialog.findViewById(R.id.confirm_appointment_card);
        confirmDialog.show();

        FrameLayout confirmButton = confirmDialog.findViewById(R.id.confirm_appointment_dialog_confirm_btn);
        FrameLayout cancelButton = confirmDialog.findViewById(R.id.confirm_appointment_dialog_cancel_btn);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View confirmationView = LayoutInflater.from(Appointment_Booking2.this).inflate(R.layout.appointment_book, null);
                Dialog dialog = new Dialog(Appointment_Booking2.this, android.R.style.Theme_Translucent_NoTitleBar);
                dialog.setContentView(confirmationView);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                CardView confirmCard = confirmDialog.findViewById(R.id.appointment_booked);
                dialog.setCancelable(true);
                dialog.show();
                confirmDialog.dismiss();

//                Toast.makeText(Appointment_Booking2.this, "Appointment confirmed", Toast.LENGTH_SHORT).show();
//                confirmDialog.dismiss();
//                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });


    }

}