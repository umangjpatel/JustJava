package io.github.umangjpatel.justjava;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

public class OrderActivity extends AppCompatActivity {

    private OrderViewModel mOrderViewModel;
    private AppCompatTextView mQuantityTextView, mPriceTextView;
    private AppCompatEditText mNameEditText;
    private AppCompatButton mSubmitOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        wireUpWidgets();
        mOrderViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        updateUI();
    }

    private void updateUI() {
        mQuantityTextView.setText(mOrderViewModel.getQuantity());
        mPriceTextView.setText(mOrderViewModel.getPrice());
    }

    private void wireUpWidgets() {
        mQuantityTextView = findViewById(R.id.quantity_text_view);
        mPriceTextView = findViewById(R.id.price_text_view);
        mSubmitOrderButton = findViewById(R.id.order_button);
        mNameEditText = findViewById(R.id.name_edit_text);
        //26a69a
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mOrderViewModel.setName(s.toString());
                    mNameEditText.setError(null);
                    mSubmitOrderButton.setEnabled(true);
                } else {
                    mNameEditText.setError(getResources().getString(R.string.no_name_error));
                    mSubmitOrderButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void incrementQuantity(View view) {
        mOrderViewModel.incrementQuantity();
        updateUI();
    }

    public void decrementQuantity(View view) {
        mOrderViewModel.decrementQuantity();
        updateUI();
    }

    public void addCreamTopping(View view) {
        mOrderViewModel.setRequiresCreamTopping(((AppCompatCheckBox) view).isChecked());
    }

    public void addChocolateTopping(View view) {
        mOrderViewModel.setRequiresChocolateTopping(((AppCompatCheckBox) view).isChecked());
    }

    public void submitOrder(View view) {
        String emailSubject = getResources().getString(R.string.order_subject, mOrderViewModel.getName());
        String emailText = getResources().getString(R.string.order_text,
                mOrderViewModel.getName(),
                mOrderViewModel.isRequiresCreamTopping(),
                mOrderViewModel.isRequiresChocolateTopping(),
                mOrderViewModel.getQuantity(),
                mOrderViewModel.getPrice());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }


}
