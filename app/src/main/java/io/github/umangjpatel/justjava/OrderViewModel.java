package io.github.umangjpatel.justjava;

import android.arch.lifecycle.ViewModel;

import java.text.NumberFormat;
import java.util.Locale;

public class OrderViewModel extends ViewModel {

    private static final int PRICE_PER_CUP = 20;

    private int mQuantity, mPrice;
    private boolean mRequiresCreamTopping, mRequiresChocolateTopping;
    private String mName;

    public String getQuantity() {
        return String.format(Locale.getDefault(), "%d", mQuantity);
    }

    public String getPrice() {
        return NumberFormat.getCurrencyInstance().format(mPrice);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void incrementQuantity() {
        mQuantity++;
        updatePrice();
    }

    public void decrementQuantity() {
        if (mQuantity > 0)
            mQuantity--;
        updatePrice();
    }

    public String isRequiresCreamTopping() {
        return Boolean.toString(mRequiresCreamTopping);
    }

    public String isRequiresChocolateTopping() {
        return Boolean.toString(mRequiresChocolateTopping);
    }


    public void setRequiresCreamTopping(boolean requiresCreamTopping) {
        mRequiresCreamTopping = requiresCreamTopping;
    }

    public void setRequiresChocolateTopping(boolean requiresChocolateTopping) {
        mRequiresChocolateTopping = requiresChocolateTopping;
    }

    private void updatePrice() {
        mPrice = mQuantity * PRICE_PER_CUP;
    }

}
