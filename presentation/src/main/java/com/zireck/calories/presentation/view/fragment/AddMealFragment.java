package com.zireck.calories.presentation.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.codetroopers.betterpickers.numberpicker.NumberPickerBuilder;
import com.codetroopers.betterpickers.numberpicker.NumberPickerDialogFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.vstechlab.easyfonts.EasyFonts;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.zireck.calories.presentation.dagger.component.FoodComponent;
import com.zireck.calories.presentation.enumeration.Mealtime;
import com.zireck.calories.presentation.listener.OnAddMealFinishedListener;
import com.zireck.calories.presentation.model.FoodModel;
import com.zireck.calories.presentation.presenter.AddMealPresenter;
import com.zireck.calories.presentation.util.MathUtils;
import com.zireck.calories.presentation.util.PictureUtils;
import com.zireck.calories.presentation.util.SnackbarUtils;
import com.zireck.calories.presentation.view.AddMealView;
import com.zireck.calories.presentation.view.adapter.FoodListAdapter;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Zireck on 06/08/2015.
 */
public class AddMealFragment extends BaseFragment implements AddMealView,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        NumberPickerDialogFragment.NumberPickerDialogHandler {

    private OnAddMealFinishedListener mCallback;

    @Bind(com.zireck.calories.R.id.root_layout)
    LinearLayout mRootLayout;

    @Bind(com.zireck.calories.R.id.layout_pick_food)
    RelativeLayout mLayoutPickFood;
    @Bind(com.zireck.calories.R.id.food_item_layout) RelativeLayout mLayoutFoodItem;

    @Bind(com.zireck.calories.R.id.text_pick_your_food) TextView mTextPickYourFood;

    @Bind(com.zireck.calories.R.id.food_id) TextView mFoodId;
    @Bind(com.zireck.calories.R.id.food_picture) CircleImageView mFoodPicture;
    @Bind(com.zireck.calories.R.id.food_textdrawable) ImageView mFoodTextDrawable;
    @Bind(com.zireck.calories.R.id.food_name) TextView mFoodName;
    @Bind(com.zireck.calories.R.id.food_brand) TextView mFoodBrand;
    @Bind(com.zireck.calories.R.id.food_calories) TextView mFoodCalories;

    @Bind(com.zireck.calories.R.id.layout_date)
    LinearLayout mLayoutDate;
    @Bind(com.zireck.calories.R.id.layout_time)
    LinearLayout mLayoutTime;
    @Bind(com.zireck.calories.R.id.layout_daily)
    LinearLayout mLayoutDaily;
    @Bind(com.zireck.calories.R.id.layout_amount)
    LinearLayout mLayoutAmount;

    @Bind(com.zireck.calories.R.id.icon_date)
    ImageView mIconDate;
    @Bind(com.zireck.calories.R.id.icon_time)
    ImageView mIconTime;
    @Bind(com.zireck.calories.R.id.icon_daily)
    ImageView mIconDaily;
    @Bind(com.zireck.calories.R.id.icon_amount)
    ImageView mIconAmount;

    @Bind(com.zireck.calories.R.id.text_date)
    TextView mTextDate;
    @Bind(com.zireck.calories.R.id.text_time)
    TextView mTextTime;
    @Bind(com.zireck.calories.R.id.text_daily)
    TextView mTextDaily;
    @Bind(com.zireck.calories.R.id.text_amount)
    TextView mTextAmount;

    @Bind(com.zireck.calories.R.id.nutrients)
    TextView mNutrients;

    /*
    @Bind(R.id.button_meal_date) Button mButtonDate;
    @Bind(R.id.button_meal_time) Button mButtonTime;
    @Bind(R.id.button_meal_daily) Button mButtonDaily;
    @Bind(R.id.button_meal_amount) Button mButtonAmount;*/
    @Bind(com.zireck.calories.R.id.meal_calories)
    TextView mMealCalories;
    @Bind(com.zireck.calories.R.id.meal_fats)
    TextView mMealFats;
    @Bind(com.zireck.calories.R.id.meal_carbohydrates)
    TextView mMealCarbohydrates;
    @Bind(com.zireck.calories.R.id.meal_proteins)
    TextView mMealProteins;

    @Inject
    AddMealPresenter mPresenter;

    private DatePickerDialog mDatePickerDialog;
    private TimePickerDialog mTimePickerDialog;
    private MaterialDialog mMaterialDialog;
    private NumberPickerBuilder mNumberPickerBuilder;

    private FoodListAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = ((OnAddMealFinishedListener) activity);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLayoutFoodItem.setVisibility(View.INVISIBLE);
        mLayoutPickFood.setVisibility(View.VISIBLE);

        mTextPickYourFood.setTypeface(EasyFonts.robotoLight(getActivity()));

        mTextDaily.setText("Breakfast");
        mTextAmount.setText("100gr");

        mAdapter = new FoodListAdapter(getActivity(), FoodListAdapter.ITEM_LAYOUT);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();

        initIcons();
        initDateTime();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(com.zireck.calories.R.menu.menu_add_edit, menu);
        menu.findItem(com.zireck.calories.R.id.action_save).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case com.zireck.calories.R.id.action_save:
                validateData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        getComponent(FoodComponent.class).inject(this);
        mPresenter.setView(this);
        mPresenter.initialize();
    }

    @Override
    protected int getFragmentLayout() {
        return com.zireck.calories.R.layout.fragment_add_meal;
    }

    @Override
    public void setDateText(String date) {
        mTextDate.setText(date);
    }

    @Override
    public void setTimeText(String time) {
        mTextTime.setText(time);
    }

    @Override
    public void setAmountText(String amount) {
        mTextAmount.setText(amount);

        mNutrients.setText("ENERGY & NUTRIENTS (PER " + amount + ")");
    }

    @Override
    public void setCaloriesText(String calories) {
        mMealCalories.setText(calories);
    }

    @Override
    public void setFatsText(String fats) {
        mMealFats.setText(fats);
    }

    @Override
    public void setCarbohydratesText(String carbohydrates) {
        mMealCarbohydrates.setText(carbohydrates);
    }

    @Override
    public void setProteinsText(String proteins) {
        mMealProteins.setText(proteins);
    }

    @Override
    public String getAmount() {
        return mTextAmount.getText().toString();
    }

    @Override
    public void setFoodError() {
        setError("food");
    }

    @Override
    public void setDateError() {
        setError("date");
    }

    @Override
    public void setTimeError() {
        setError("time");
    }

    @Override
    public void setDailyError() {
        setError("daily meal");
    }

    @Override
    public void setAmountError() {
        setError("amount");
    }

    @Override
    public void setGr() {
        setNutrientsHeader("GR");
    }

    @Override
    public void setMl() {
        setNutrientsHeader("ML");
    }

    @Override
    public void setFoodItems(Collection<FoodModel> foodItems) {
        mAdapter.setFoodsCollection(foodItems);
    }

    @Override
    public void renderFoodInView(final FoodModel food) {
        mLayoutPickFood.setVisibility(View.INVISIBLE);
        mLayoutFoodItem.setVisibility(View.VISIBLE);

        mFoodId.setText(String.valueOf(food.getId()));
        mFoodName.setText(food.getName());
        mFoodBrand.setText(food.getBrand());
        mFoodCalories.setText(String.valueOf(MathUtils.formatDouble(food.getCalories())));

        if (food.getPicture() != null && !TextUtils.isEmpty(food.getPicture())) {
            Picasso.with(getActivity()).load(PictureUtils.getPhotoFileUri(food.getPicture())).fit().centerCrop().into(mFoodPicture, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    setFoodTextDrawable(food);
                }
            });
            mFoodTextDrawable.setVisibility(View.INVISIBLE);
            mFoodPicture.setVisibility(View.VISIBLE);
        } else {
            setFoodTextDrawable(food);
        }

    }

    private void setFoodTextDrawable(FoodModel foodModel) {
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getColor(foodModel.getName());
        TextDrawable textDrawable = TextDrawable.builder()
                .buildRound(String.valueOf(foodModel.getName().charAt(0)).toUpperCase(), color);
        mFoodTextDrawable.setImageDrawable(textDrawable);

        mFoodPicture.setVisibility(View.INVISIBLE);
        mFoodTextDrawable.setVisibility(View.VISIBLE);
    }

    @Override
    public void mealSuccessfullyAdded() {
        if (mCallback != null) {
            mCallback.mealAdded();
        }
    }

    private void setNutrientsHeader(String measure) {
        //mNutrients.setText("ENERGY & NUTRIENTS (PER 100" + measure + ")");
        mNutrients.setText(mNutrients.getText().toString().replace("ML", measure));
        mNutrients.setText(mNutrients.getText().toString().replace("GR", measure));
    }

    private void setError(String what) {
        SnackbarUtils.showShortMessage(mRootLayout, "Invalid " + what);
    }

    @OnClick(com.zireck.calories.R.id.layout_food_item)
    public void onLayoutFoodItemClick() {
        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setIcon(MaterialDrawableBuilder.IconValue.FOOD_APPLE);

        MaterialDialog materialDialog =
                new MaterialDialog.Builder(getActivity())
                        .title("Pick Food")
                        .theme(Theme.LIGHT)
                        .adapter(mAdapter, new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog materialDialog, View view,
                                                    int which, CharSequence charSequence) {
                                //ToastUtils.showShortMessage(getActivity(), "Clicked: " + which);
                                mPresenter.setFood(mAdapter.getItem(which));
                                materialDialog.dismiss();
                            }
                        })
                        .icon(icon.getDrawable())
                        .show();
    }

    @OnClick(com.zireck.calories.R.id.layout_date)
    public void onDateClick() {
        int year, month, dayOfMonth;

        Date currentDate = mPresenter.getCurrentDate();
        if (currentDate != null) {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentDate);
            year = currentCalendar.get(Calendar.YEAR);
            month = currentCalendar.get(Calendar.MONTH);
            dayOfMonth = currentCalendar.get(Calendar.DAY_OF_MONTH);
        } else {
            Calendar now = Calendar.getInstance();
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH);
            dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        }

        mDatePickerDialog = DatePickerDialog.newInstance(
                this,
                year,
                month,
                dayOfMonth
        );

        mDatePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @OnClick(com.zireck.calories.R.id.layout_time)
    public void onTimeClick() {
        int hourOfDay, minute;

        Date currentTime = mPresenter.getCurrentTime();
        if (currentTime != null) {
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);
            hourOfDay = currentCalendar.get(Calendar.HOUR_OF_DAY);
            minute = currentCalendar.get(Calendar.MINUTE);

        } else {
            Calendar now = Calendar.getInstance();
            hourOfDay = now.get(Calendar.HOUR_OF_DAY);
            minute = now.get(Calendar.MINUTE);
        }

        mTimePickerDialog = TimePickerDialog.newInstance(
                this,
                hourOfDay,
                minute,
                true
        );

        mTimePickerDialog.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @OnClick(com.zireck.calories.R.id.layout_daily)
    public void onDailyClick() {
        mMaterialDialog = new MaterialDialog.Builder(getActivity())
                .title("Daily Meal")
                .items(Mealtime.getStringValues(getActivity()))
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int which,
                                               CharSequence text) {
                        mTextDaily.setText(text);
                        return false;
                    }
                })
                .theme(Theme.LIGHT)
                .widgetColorRes(com.zireck.calories.R.color.primary)
                .positiveText("Choose")
                .positiveColorRes(com.zireck.calories.R.color.primary)
                .negativeText("Cancel")
                .negativeColorRes(com.zireck.calories.R.color.primary)
                .show();
    }

    @OnClick(com.zireck.calories.R.id.layout_amount)
    public void onAmountClick() {
        mNumberPickerBuilder = new NumberPickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setTargetFragment(this)
                .setStyleResId(com.zireck.calories.R.style.BetterPickersDialogFragment_Light)
                .setPlusMinusVisibility(View.INVISIBLE)
                .setDecimalVisibility(View.INVISIBLE)
                .setCurrentNumber(MathUtils.getAmountFromText(mTextAmount.getText().toString()))
                .setLabelText(mPresenter.getCurrentMeasure());

        mNumberPickerBuilder.show();
    }

    @Override
    public void onDateSet(
            DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        mPresenter.setDate(year, monthOfYear, dayOfMonth);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        mPresenter.setTime(hourOfDay, minute);
    }

    @Override
    public void onDialogNumberSet(int reference, int number, double decimal, boolean isNegative,
                                  double fullNumber) {
        mPresenter.setAmount(number);
    }

    private void initIcons() {
        MaterialIconView icon = new MaterialIconView(getActivity());
        icon.setIcon(MaterialDrawableBuilder.IconValue.CALENDAR_TEXT);
        icon.setColorResource(com.zireck.calories.R.color.primary);
        mIconDate.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.CALENDAR_CLOCK);
        mIconTime.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.MORE);
        mIconDaily.setImageDrawable(icon.getDrawable());

        icon.setIcon(MaterialDrawableBuilder.IconValue.WEIGHT);
        mIconAmount.setImageDrawable(icon.getDrawable());
    }

    private void initDateTime() {
        Calendar now = Calendar.getInstance();
        now.get(Calendar.DATE);
        mPresenter.setDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        mPresenter.setTime(now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE));
    }

    private void validateData() {
        FoodModel food = mPresenter.getFood();
        String date = mPresenter.getCurrentDateReadable();
        String time = mPresenter.getCurrentTimeReadable();
        //String daily = mTextDaily.getText().toString();
        Mealtime daily = Mealtime.fromValue(getActivity(), mTextDaily.getText().toString());
        String amount = mTextAmount.getText().toString();

        mPresenter.validateData(food, date, time, daily, amount);
    }
}
