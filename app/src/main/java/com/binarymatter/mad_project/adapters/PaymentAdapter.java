package com.binarymatter.mad_project.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.binarymatter.mad_project.R;
import com.binarymatter.mad_project.activities.ShowAllPaymentActivity;
import com.binarymatter.mad_project.models.PaymentModel;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    private ShowAllPaymentActivity activity;
    private List<PaymentModel> mList;

    public PaymentAdapter(ShowAllPaymentActivity activity, List<PaymentModel>mList) {

        this.activity = activity;
        this.mList = mList;

    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.paymentdetails, parent, false);
        return new PaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {

        holder.CardHolderName.setText(mList.get(position).getCardHolderName());
        holder.CardNumber.setText(mList.get(position).getCardNumber());
        holder.ExpireDate.setText(mList.get(position).getExpireDate());
        holder.Cvv.setText(mList.get(position).getCvv());



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class PaymentViewHolder extends RecyclerView.ViewHolder {

        TextView CardHolderName, CardNumber, ExpireDate, Cvv;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);

            CardHolderName = itemView.findViewById(R.id.cardholder);
            CardNumber = itemView.findViewById(R.id.cardnumber);
            ExpireDate = itemView.findViewById(R.id.expiredate);
            Cvv = itemView.findViewById(R.id.cvv);
        }
    }

}
