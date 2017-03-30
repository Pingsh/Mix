package com.example.sphinx.mix.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sphinx.mix.R;
import com.example.sphinx.mix.theme.ThemeHelper;

/**
 * 弹出主题选择器
 * Created by Sphinx on 2017/3/17.
 */

public class CardPickerDialog extends DialogFragment implements View.OnClickListener {
    public static final String TAG = "CardPickerDialog";
    ImageView[] mCards = new ImageView[8];
    Button mConfirm;
    Button mCancel;

    private int mCurrentTheme;
    private ClickListener mClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert);
        mCurrentTheme = ThemeHelper.getTheme(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_theme_picker, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initListener() {
        for (ImageView card : mCards) {
            card.setOnClickListener(this);
        }
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                if (mClickListener != null) {
                    mClickListener.onConfirm(mCurrentTheme);
                }
            case R.id.button2:
                dismiss();
                break;
            case R.id.theme_pink2:
                mCurrentTheme = ThemeHelper.CARD_SAKURA;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_purple2:
                mCurrentTheme = ThemeHelper.CARD_HOPE;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_blue2:
                mCurrentTheme = ThemeHelper.CARD_STORM;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green2:
                mCurrentTheme = ThemeHelper.CARD_WOOD;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_green_light2:
                mCurrentTheme = ThemeHelper.CARD_LIGHT;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_yellow2:
                mCurrentTheme = ThemeHelper.CARD_THUNDER;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_orange2:
                mCurrentTheme = ThemeHelper.CARD_SAND;
                setImageButtons(mCurrentTheme);
                break;
            case R.id.theme_red2:
                mCurrentTheme = ThemeHelper.CARD_FIREY;
                setImageButtons(mCurrentTheme);
                break;
            default:
                break;

        }

    }

    private void initView(View view) {
        mCancel = (Button) view.findViewById(R.id.button2);
        mConfirm = (Button) view.findViewById(R.id.button1);
        mCards[0] = (ImageView) view.findViewById(R.id.theme_pink2);
        mCards[1] = (ImageView) view.findViewById(R.id.theme_purple2);
        mCards[2] = (ImageView) view.findViewById(R.id.theme_blue2);
        mCards[3] = (ImageView) view.findViewById(R.id.theme_green2);
        mCards[4] = (ImageView) view.findViewById(R.id.theme_green_light2);
        mCards[5] = (ImageView) view.findViewById(R.id.theme_yellow2);
        mCards[6] = (ImageView) view.findViewById(R.id.theme_orange2);
        mCards[7] = (ImageView) view.findViewById(R.id.theme_red2);

        setImageButtons(mCurrentTheme);
    }

    private void setImageButtons(int currentTheme) {
        mCards[0].setSelected(currentTheme == ThemeHelper.CARD_SAKURA);
        mCards[1].setSelected(currentTheme == ThemeHelper.CARD_HOPE);
        mCards[2].setSelected(currentTheme == ThemeHelper.CARD_STORM);
        mCards[3].setSelected(currentTheme == ThemeHelper.CARD_WOOD);
        mCards[4].setSelected(currentTheme == ThemeHelper.CARD_LIGHT);
        mCards[5].setSelected(currentTheme == ThemeHelper.CARD_THUNDER);
        mCards[6].setSelected(currentTheme == ThemeHelper.CARD_SAND);
        mCards[7].setSelected(currentTheme == ThemeHelper.CARD_FIREY);
        initListener();
    }

    public void setClickListener(ClickListener clickListener) {
        mClickListener = clickListener;
    }

    public interface ClickListener {
        void onConfirm(int currentTheme);
    }
}
