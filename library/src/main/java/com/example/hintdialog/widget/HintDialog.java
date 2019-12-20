package com.example.hintdialog.widget;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.hintdialog.R;

/**
 * Author: ZhengHuaizhi
 * Date: 2019/11/5
 * Description:
 */
public class HintDialog extends DialogFragment implements DialogInterface {

    private View mView;
    private ImageView ivIcon;
    private TextView tvTitle;
    private TextView tvPrimaryContent;
    private TextView tvSecondaryContent;
    private Group groupTitle;
    private Group groupBtn;
    private EditText etInput;
    private ImageView ivClose;
    private Button btnCancel;
    private Button btnConfirm;
    private Button btnSingleConfirm;

    private int mTitleIconId;
    private boolean mIsInput;
    private CharSequence mTitle;
    private CharSequence mInputContent;
    private CharSequence mInputHint;
    private int maxInputLength = 30;
    private CharSequence mPrimaryContent;
    private CharSequence mSecondaryContent;
    private CharSequence mCancelText;
    private CharSequence mConfirmText;
    private CharSequence mSingleConfirmText;
    private DialogInterface.OnDismissListener mDismissListener;
    private DialogInterface.OnClickListener mClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_hint_message, container);

        findViewByIds();

        initListeners();

        setupView();

        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.hint_dialog_style);
    }

    /**
     * 设置弹窗标题图片
     *
     * @param titleIconId 显示的图片id
     */
    public void setTitleIconId(@DrawableRes int titleIconId) {
        this.mTitleIconId = titleIconId;
    }

    /**
     * 设置弹窗标题文本
     *
     * @param title 文本字符
     */
    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

    /**
     * 设置弹窗主要提示内容
     */
    public void setPrimaryContent(CharSequence primaryContent) {
        this.mPrimaryContent = primaryContent;
    }

    /**
     * 设置弹窗次要提示内容
     */
    public void setSecondaryContent(CharSequence secondaryContent) {
        this.mSecondaryContent = secondaryContent;
    }

    /**
     * 设置弹窗是否可输入内容
     */
    public void setInput(boolean isInput) {
        this.mIsInput = isInput;
    }

    /**
     * 设置弹窗是否带可输入内容，并带hint和默认值
     *
     * @param content 默认值
     */
    public void setInput(String content, String hint) {
        this.mIsInput = true;
        mInputContent = content;
        mInputHint = hint;
    }

    /**
     * 设置输入内容的最大字符长度
     *
     * @param maxLength 输入的文本长度
     */
    public void setInputMaxLength(int maxLength) {
        maxInputLength = maxLength;
    }

    /**
     * 设置{@link #btnCancel}的显示内容
     *
     * @param cancelText 取消按钮的显示文本
     */
    public void setCancelText(CharSequence cancelText) {
        this.mCancelText = cancelText;
    }

    /**
     * 设置{@link #btnConfirm}的显示内容
     *
     * @param confirmText 确认按钮的显示文本
     */
    public void setConfirmText(CharSequence confirmText) {
        this.mConfirmText = confirmText;
    }

    /**
     * 设置{@link #btnSingleConfirm}的显示内容
     *
     * @param singleConfirmText 单一确认按钮的显示文本
     */
    public void setSingleConfirmText(CharSequence singleConfirmText) {
        this.mSingleConfirmText = singleConfirmText;
    }

    /**
     * 设置关闭弹窗的监听
     */
    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    /**
     * 设置弹窗中的按钮监听
     */
    public void setClickListener(DialogInterface.OnClickListener clickListener) {
        mClickListener = clickListener;
    }

    private void findViewByIds() {
        if (mView == null) {
            return;
        }
        ivIcon = mView.findViewById(R.id.iv_title);
        tvTitle = mView.findViewById(R.id.tv_title);
        tvPrimaryContent = mView.findViewById(R.id.tv_primary_content);
        tvSecondaryContent = mView.findViewById(R.id.tv_secondary_content);
        etInput = mView.findViewById(R.id.et_input);
        ivClose = mView.findViewById(R.id.iv_close);
        btnCancel = mView.findViewById(R.id.btn_cancel);
        btnConfirm = mView.findViewById(R.id.btn_confirm);
        btnSingleConfirm = mView.findViewById(R.id.btn_single_confirm);
        groupTitle = mView.findViewById(R.id.group_tv_title);
        groupBtn = mView.findViewById(R.id.group_btn);

        InputFilter[] filters = {new InputFilter.LengthFilter(maxInputLength)};
        etInput.setFilters(filters);
    }

    private void initListeners() {
        if (mClickListener != null) {
            btnCancel.setOnClickListener(v -> {
                mClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
                dismiss();
            });
            btnConfirm.setOnClickListener(v -> {
                mClickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
                dismiss();
            });
            btnSingleConfirm.setOnClickListener(v -> {
                mClickListener.onClick(this, DialogInterface.BUTTON_NEUTRAL);
                dismiss();
            });
            ivClose.setOnClickListener(v -> {
                mClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
                dismiss();
            });
        } else {
            View.OnClickListener defaultListener = v -> dismiss();
            btnCancel.setOnClickListener(defaultListener);
            btnConfirm.setOnClickListener(defaultListener);
            btnSingleConfirm.setOnClickListener(defaultListener);
            ivClose.setOnClickListener(defaultListener);
        }
    }

    private void setupView() {
        if (mTitleIconId != 0) {
            ivIcon.setVisibility(View.VISIBLE);
            groupTitle.setVisibility(View.INVISIBLE);
            ivIcon.setImageResource(mTitleIconId);
        }

        if (!TextUtils.isEmpty(mTitle)) {
            ivIcon.setVisibility(View.INVISIBLE);
            groupTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(mTitle);
        }

        if (!TextUtils.isEmpty(mPrimaryContent)) {
            tvPrimaryContent.setVisibility(View.VISIBLE);
            tvPrimaryContent.setText(mPrimaryContent);
        } else {
            tvPrimaryContent.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mSecondaryContent)) {
            tvSecondaryContent.setVisibility(View.VISIBLE);
            tvSecondaryContent.setText(mSecondaryContent);
        } else {
            tvSecondaryContent.setVisibility(View.GONE);
        }

        if (mIsInput) {
            etInput.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(mInputContent)) {
                etInput.setText(mInputContent);
                etInput.setSelection(mInputContent.length());
            } else {
                etInput.setHint(mInputHint);
            }
        } else {
            etInput.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mCancelText)) {
            btnSingleConfirm.setVisibility(View.INVISIBLE);
            groupBtn.setVisibility(View.VISIBLE);
            btnCancel.setText(mCancelText);
        }

        if (!TextUtils.isEmpty(mConfirmText)) {
            btnSingleConfirm.setVisibility(View.INVISIBLE);
            groupBtn.setVisibility(View.VISIBLE);
            btnConfirm.setText(mConfirmText);
        }

        if (!TextUtils.isEmpty(mSingleConfirmText)) {
            btnSingleConfirm.setVisibility(View.VISIBLE);
            groupBtn.setVisibility(View.INVISIBLE);
            btnSingleConfirm.setText(mSingleConfirmText);
        }
    }

    private void clear() {
        mTitleIconId = 0;
        mIsInput = false;
        mTitle = null;
        mInputContent = null;
        mInputHint = null;
        maxInputLength = 30;
        mPrimaryContent = null;
        mSecondaryContent = null;
        mCancelText = null;
        mConfirmText = null;
        mSingleConfirmText = null;
        mDismissListener = null;
        mClickListener = null;
    }

    public View getView() {
        return mView;
    }

    public String getInputContent() {
        return TextUtils.isEmpty(etInput.getText().toString()) ? "" : etInput.getText().toString();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        onDismiss(dialog);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDismissListener != null) {
            mDismissListener.onDismiss(dialog);
        }
        clear();
    }

    @Override
    public void cancel() {

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager == null) {
            return;
        }
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            FragmentTransaction ft = manager.beginTransaction();
            ft.remove(fragment);
            ft.commit();
        }
        super.show(manager, tag);
    }
}
